/*
package com.oguzhan.shared.ui.screen.coin.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.placeholder.material3.placeholder
import com.oguzhan.shared.core.domain.model.CoinUiModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreenRoute(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (id: String) -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    viewModel: CryptoListScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CryptoListScreenEffect.ShowSnackBar -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is CryptoListScreenEffect.NavigateToAuth -> {
                    onNavigateToAuth()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Crypto Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.signOut() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sign out",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToFavorite,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { innerPadding ->
        CryptoListScreenContent(
            modifier.padding(innerPadding),
            state,
            onQueryChange = viewModel::updateQuery,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Composable
fun CryptoListScreenContent(
    modifier: Modifier = Modifier,
    state: CryptoListScreenState,
    onQueryChange: (String) -> Unit,
    onNavigateToDetail: (id: String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { onQueryChange(it) },
            placeholder = { Text("Search...") },
            trailingIcon = {
                if (state.query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear text"
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        if (state.isLoading) {
            CryptoListShimmer()
        } else {
            LazyColumn(
                modifier = Modifier
            ) {
                items(state.coinList) { coin ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(Modifier.clickable {
                            onNavigateToDetail(coin.id)
                        }) {
                            HorizontalDivider(Modifier.padding(bottom = 5.dp))
                            Text(
                                text = "${coin.name} (${coin.symbol?.uppercase()})",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCryptoListScreenContent() {
    CryptoListScreenContent(
        state = CryptoListScreenState(
            query = "",
            isLoading = false,
            coinList = listOf(
                CoinUiModel("bitcoin", "Bitcoin", "BTC"),
                CoinUiModel("ethereum", "Ethereum", "ETH")
            )
        ),
        onQueryChange = {},
        onNavigateToDetail = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCryptoListShimmer() {
    CryptoListShimmer()
}

@Preview
@Composable
fun CryptoListShimmer(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.testTag("coinListShimmer")) {
        items(10) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .placeholder(visible = true)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(15.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .placeholder(visible = true)
                    )
                }
            }
        }
    }
}*/
