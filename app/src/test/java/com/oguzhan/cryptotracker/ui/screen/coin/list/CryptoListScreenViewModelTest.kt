package com.oguzhan.cryptotracker.ui.screen.coin.list


import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.oguzhan.cryptotracker.TestDispatcherRule
import com.oguzhan.shared.core.Result
import com.oguzhan.shared.core.domain.model.CoinUiModel
import com.oguzhan.cryptotracker.domain.repository.AuthRepository
import com.oguzhan.cryptotracker.domain.usecase.GetCoinListUseCases
import com.oguzhan.cryptotracker.domain.usecase.SearchCoinListUseCases
import com.oguzhan.shared.ui.screen.coin.list.CryptoListScreenEffect
import com.oguzhan.shared.ui.screen.coin.list.CryptoListScreenViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CryptoListScreenViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestDispatcherRule()

    private lateinit var viewModel: CryptoListScreenViewModel

    @MockK
    private lateinit var authRepository: AuthRepository

    @MockK(relaxed = true)
    private lateinit var getCoinListUseCases: GetCoinListUseCases

    @MockK
    private lateinit var searchCoinListUseCases: SearchCoinListUseCases

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        mockkStatic(android.util.Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `fetchAndStoreCoins should update state when successful`() = runTest {
        val mockCoins = listOf(CoinUiModel("Bitcoin", "BTC", "image_url"))
        coEvery { getCoinListUseCases() } returns flowOf(Result.Success(mockCoins))

        viewModel =
            CryptoListScreenViewModel(authRepository, getCoinListUseCases, searchCoinListUseCases)

        advanceUntilIdle()
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(mockCoins, viewModel.state.value.coinList)
    }

    @Test
    fun `fetchAndStoreCoins should emit error effect when failed`() = runTest {
        coEvery { getCoinListUseCases() } returns flowOf(Result.Error("Network Error"))

        viewModel =
            CryptoListScreenViewModel(authRepository, getCoinListUseCases, searchCoinListUseCases)

        viewModel.effect.test {
            assertEquals(CryptoListScreenEffect.ShowSnackBar("Network Error"), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `updateQuery should call searchCoins`() = runTest {
        val query = "Bitcoin"
        val mockSearchResults = listOf(CoinUiModel("Bitcoin", "BTC", "image_url"))
        coEvery { searchCoinListUseCases(query) } returns mockSearchResults

        viewModel =
            CryptoListScreenViewModel(authRepository, getCoinListUseCases, searchCoinListUseCases)

        viewModel.updateQuery(query)
        advanceUntilIdle()

        assertEquals(query, viewModel.state.value.query)
        assertEquals(mockSearchResults, viewModel.state.value.coinList)
    }

    @Test
    fun `signOut should navigate to auth when successful`() = runTest {
        coEvery { authRepository.signOut() } returns true

        viewModel =
            CryptoListScreenViewModel(authRepository, getCoinListUseCases, searchCoinListUseCases)
         viewModel.signOut()

        viewModel.effect.test {
            val item = awaitItem()
            assertEquals(CryptoListScreenEffect.NavigateToAuth,  item)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signOut should show snackbar when failed`() = runTest {
        coEvery { authRepository.signOut() } returns false

        viewModel =
            CryptoListScreenViewModel(authRepository, getCoinListUseCases, searchCoinListUseCases)
        viewModel.signOut()

        viewModel.effect.test {
            assertEquals(CryptoListScreenEffect.ShowSnackBar("Sign out failed"), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
