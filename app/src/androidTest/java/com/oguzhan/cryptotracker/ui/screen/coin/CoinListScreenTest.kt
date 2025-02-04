
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oguzhan.cryptotracker.domain.usecase.SearchCoinListUseCases
import com.oguzhan.cryptotracker.ui.screen.coin.list.*
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CryptoListScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK(relaxed = true)
    private lateinit var searchCoinListUseCases: SearchCoinListUseCases

    private lateinit var viewModel: CryptoListScreenViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CryptoListScreenViewModel(
            mockk(relaxed = true),
            mockk(relaxed = true),
            searchCoinListUseCases
        )
    }

    @Test
    fun searchFavoriteShouldBeVisible() {
        composeTestRule.setContent {
            CryptoListScreenContent(
                state = CryptoListScreenState(
                    coinList = emptyList(),
                    isLoading = false
                ),
                onQueryChange = {},
                onNavigateToDetail = {}
            )
        }

        composeTestRule.onNodeWithText("Search...").assertIsDisplayed()
    }

    @Test
    fun showShimmerIfThereIsNoData() {
        composeTestRule.apply {
            setContent {
                CryptoListScreenContent(
                    state = CryptoListScreenState(
                        coinList = emptyList(),
                        isLoading = true
                    ),
                    onQueryChange = {},
                    onNavigateToDetail = {}
                )
            }

            onNodeWithTag("coinListShimmer").assertExists()
        }
    }

}
