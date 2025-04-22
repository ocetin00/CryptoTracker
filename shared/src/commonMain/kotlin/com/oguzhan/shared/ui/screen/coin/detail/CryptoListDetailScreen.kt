package com.oguzhan.shared.ui.screen.coin.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.material3.placeholder
import com.oguzhan.cryptotracker.R
import com.oguzhan.shared.core.domain.model.CoinDetailUiModel
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreenRoute(
    onNavigateToBack: () -> Unit,
    viewModel: CryptoListDetailViewModel
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CryptoListDetailScreenEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Coin Detail",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToBack) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        CoinDetailScreen(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onSetRefreshRate = { interval ->
                viewModel.startPriceRefresh(interval)
            },
            toggleFavorite = {
                viewModel.toggleFavorite()
            }
        )
    }
}

@Composable
fun CoinDetailScreen(
    state: CryptoListDetailScreenState,
    modifier: Modifier,
    onSetRefreshRate: (Int) -> Unit,
    toggleFavorite: () -> Unit
) {
    var refreshInterval by remember { mutableStateOf(state.refreshInterval.toString()) }

    if (state.coinDetailModel == null) {
        CoinDetailShimmer(modifier = modifier)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Coin Name
            Text(
                text = state.coinDetailModel.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Coin Image
            AsyncImage(
                model = state.coinDetailModel.imageUrl,
                contentDescription = state.coinDetailModel.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 8.dp),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            )

            // Hashing Algorithm
            Text(
                text = "Hashing Algorithm: ${state.coinDetailModel.hashingAlgorithm}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Price Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {


                    // Current Price
                    Text(
                        text = "Current Price: ${state.coinDetailModel.currentPrice}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // Price Change
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (state.coinDetailModel.isPositive) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                            contentDescription = "Price Change",
                            tint = if (state.coinDetailModel.isPositive) Color.Green else Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "${if (state.coinDetailModel.isPositive) "+" else ""}${state.coinDetailModel.priceChange24h}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (state.coinDetailModel.isPositive) Color.Green else Color.Red,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Refresh Interval Input
            OutlinedTextField(
                value = refreshInterval,
                onValueChange = { refreshInterval = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Refresh Interval (seconds)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            // "Set Refresh Rate" Button
            Button(
                onClick = {
                    val interval = refreshInterval.toIntOrNull() ?: 0
                    if (interval > 0) {
                        onSetRefreshRate(interval)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Set Refresh Rate")
            }

            // Favorite Button
            Button(
                onClick = { toggleFavorite() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isFavorite) "Remove from Favorites" else "Add to Favorites")
            }
        }


    }
}


@Preview
@Composable
fun CoinDetailScreenPreview(modifier: Modifier = Modifier) {
    CoinDetailScreen(
        state = CryptoListDetailScreenState(
            coinDetailModel = CoinDetailUiModel(
                id = "1",
                name = "Bitcoin",
                imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
                hashingAlgorithm = "SHA-256",
                currentPrice = "50,000",
                priceChange24h = "24",
                isPositive = true,
                description = "Bitcoin is a decentralized digital currency, without a central bank or single administrator, that can be sent from user to user on the peer-to-peer bitcoin network without the need for intermediaries.",
            ),
            isLoading = false,
            refreshInterval = 10,
            isFavorite = false
        ),
        modifier = modifier,
        onSetRefreshRate = {},
        toggleFavorite = {}
    )
}

@Preview
@Composable
fun CoinDetailShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Shimmer for Coin Name
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .placeholder(visible = true)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shimmer for Image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .placeholder(visible = true)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .placeholder(visible = true)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .placeholder(visible = true),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {}
    }
}




