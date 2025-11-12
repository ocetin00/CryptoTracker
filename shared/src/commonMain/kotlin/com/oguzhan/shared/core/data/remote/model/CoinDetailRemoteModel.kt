package com.oguzhan.shared.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


@Serializable
data class CoinDetailRemoteModel(
    @SerialName("additional_notices")
    val additionalNotices: List<JsonElement>? = null,
    @SerialName("asset_platform_id")
    val assetPlatformId: JsonElement? = null,
    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Double? = null,
    @SerialName("categories")
    val categories: List<String?>? = null,
    @SerialName("community_data")
    val communityData: CommunityData? = null,
    @SerialName("country_origin")
    val countryOrigin: String? = null,
    @SerialName("description")
    val description: Description? = null,
    @SerialName("developer_data")
    val developerData: DeveloperData? = null,
    @SerialName("genesis_date")
    val genesisDate: String? = null,
    @SerialName("hashing_algorithm")
    val hashingAlgorithm: String? = null,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: Image? = null,
    @SerialName("last_updated")
    val lastUpdated: String? = null,
    @SerialName("market_data")
    val marketData: MarketData? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("preview_listing")
    val previewListing: Boolean? = null,
    @SerialName("public_notice")
    val publicNotice: JsonElement? = null,
    @SerialName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double? = null,
    @SerialName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double? = null,
    @SerialName("status_updates")
    val statusUpdates: List<JsonElement>? = null,
    @SerialName("symbol")
    val symbol: String? = null,
    @SerialName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Double? = null,
    @SerialName("web_slug")
    val webSlug: String? = null
) {
    @Serializable
    data class CommunityData(
        @SerialName("facebook_likes")
        val facebookLikes: JsonElement? = null,
        @SerialName("reddit_accounts_active_48h")
        val redditAccountsActive48h: Double? = null,
        @SerialName("reddit_average_comments_48h")
        val redditAverageComments48h: Double? = null,
        @SerialName("reddit_average_posts_48h")
        val redditAveragePosts48h: Double? = null,
        @SerialName("reddit_subscribers")
        val redditSubscribers: Double? = null,
        @SerialName("telegram_channel_user_count")
        val telegramChannelUserCount: JsonElement? = null,
        @SerialName("twitter_followers")
        val twitterFollowers: Double? = null
    )

    @Serializable
    data class Description(
        @SerialName("de")
        val de: String? = null,
        @SerialName("en")
        val en: String? = null
    )

    @Serializable
    data class DeveloperData(
        @SerialName("closed_issues")
        val closedIssues: Double? = null,
        @SerialName("code_additions_deletions_4_weeks")
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks? = null,
        @SerialName("commit_count_4_weeks")
        val commitCount4Weeks: Double? = null,
        @SerialName("forks")
        val forks: Double? = null,
        @SerialName("last_4_weeks_commit_activity_series")
        val last4WeeksCommitActivitySeries: List<JsonElement>? = null,
        @SerialName("pull_request_contributors")
        val pullRequestContributors: Double? = null,
        @SerialName("pull_requests_merged")
        val pullRequestsMerged: Double? = null,
        @SerialName("stars")
        val stars: Double? = null,
        @SerialName("subscribers")
        val subscribers: Double? = null,
        @SerialName("total_issues")
        val totalIssues: Double? = null
    ) {
        @Serializable
        data class CodeAdditionsDeletions4Weeks(
            @SerialName("additions")
            val additions: Double? = null,
            @SerialName("deletions")
            val deletions: Double? = null
        )
    }

    @Serializable
    data class Image(
        @SerialName("large")
        val large: String? = null,
        @SerialName("small")
        val small: String? = null,
        @SerialName("thumb")
        val thumb: String? = null
    )

    @Serializable
    data class MarketData(
        @SerialName("current_price")
        val currentPrice: CurrentPrice? = null,
        @SerialName("high_24h")
        val high24h: High24h? = null,
        @SerialName("last_updated")
        val lastUpdated: String? = null,
        @SerialName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency? = null
    ) {

        @Serializable
        data class CurrentPrice(

            @SerialName("usd")
            val usd: Double? = null,
        )

        @Serializable
        data class High24h(
            @SerialName("usd")
            val usd: Double? = null,
        )

        @Serializable
        data class PriceChangePercentage24hInCurrency(
            @SerialName("usd")
            val usd: Double? = null,
        )

    }
}