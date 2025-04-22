package com.oguzhan.shared.core.data.remote.model

import kotlinx.serialization.SerialName


data class CoinDetailRemoteModel(
    @SerialName("additional_notices")
    val additionalNotices: List<Any?>?,
    @SerialName("asset_platform_id")
    val assetPlatformId: Any?,
    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Int?,
    @SerialName("categories")
    val categories: List<String?>?,
    @SerialName("community_data")
    val communityData: CommunityData?,
    @SerialName("country_origin")
    val countryOrigin: String?,
    @SerialName("description")
    val description: Description?,
    @SerialName("developer_data")
    val developerData: DeveloperData?,
    @SerialName("genesis_date")
    val genesisDate: String?,
    @SerialName("hashing_algorithm")
    val hashingAlgorithm: String?,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: Image?,
    @SerialName("last_updated")
    val lastUpdated: String?,
    @SerialName("market_data")
    val marketData: MarketData?,
    @SerialName("name")
    val name: String?,
    @SerialName("preview_listing")
    val previewListing: Boolean?,
    @SerialName("public_notice")
    val publicNotice: Any?,
    @SerialName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double?,
    @SerialName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double?,
    @SerialName("status_updates")
    val statusUpdates: List<Any?>?,
    @SerialName("symbol")
    val symbol: String?,
    @SerialName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int?,
    @SerialName("web_slug")
    val webSlug: String?
) {
    data class CommunityData(
        @SerialName("facebook_likes")
        val facebookLikes: Any?,
        @SerialName("reddit_accounts_active_48h")
        val redditAccountsActive48h: Int?,
        @SerialName("reddit_average_comments_48h")
        val redditAverageComments48h: Int?,
        @SerialName("reddit_average_posts_48h")
        val redditAveragePosts48h: Int?,
        @SerialName("reddit_subscribers")
        val redditSubscribers: Int?,
        @SerialName("telegram_channel_user_count")
        val telegramChannelUserCount: Any?,
        @SerialName("twitter_followers")
        val twitterFollowers: Int?
    )

    data class Description(
        @SerialName("de")
        val de: String?,
        @SerialName("en")
        val en: String?
    )

    data class DeveloperData(
        @SerialName("closed_issues")
        val closedIssues: Int?,
        @SerialName("code_additions_deletions_4_weeks")
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks?,
        @SerialName("commit_count_4_weeks")
        val commitCount4Weeks: Int?,
        @SerialName("forks")
        val forks: Int?,
        @SerialName("last_4_weeks_commit_activity_series")
        val last4WeeksCommitActivitySeries: List<Any?>?,
        @SerialName("pull_request_contributors")
        val pullRequestContributors: Int?,
        @SerialName("pull_requests_merged")
        val pullRequestsMerged: Int?,
        @SerialName("stars")
        val stars: Int?,
        @SerialName("subscribers")
        val subscribers: Int?,
        @SerialName("total_issues")
        val totalIssues: Int?
    ) {
        data class CodeAdditionsDeletions4Weeks(
            @SerialName("additions")
            val additions: Int?,
            @SerialName("deletions")
            val deletions: Int?
        )
    }

    data class Image(
        @SerialName("large")
        val large: String?,
        @SerialName("small")
        val small: String?,
        @SerialName("thumb")
        val thumb: String?
    )

    data class MarketData(
        @SerialName("current_price")
        val currentPrice: CurrentPrice?,
        @SerialName("high_24h")
        val high24h: High24h?,
        @SerialName("last_updated")
        val lastUpdated: String?,
        @SerialName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency?
    ) {


        data class CurrentPrice(

            @SerialName("usd")
            val usd: Double?,
        )


        data class High24h(
            @SerialName("usd")
            val usd: Double?,
        )

        data class PriceChangePercentage24hInCurrency(
            @SerialName("usd")
            val usd: Double?,
        )

    }
}