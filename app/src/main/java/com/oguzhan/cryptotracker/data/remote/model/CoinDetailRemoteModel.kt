package com.oguzhan.cryptotracker.data.remote.model


import com.google.gson.annotations.SerializedName

data class CoinDetailRemoteModel(
    @SerializedName("additional_notices")
    val additionalNotices: List<Any?>?,
    @SerializedName("asset_platform_id")
    val assetPlatformId: Any?,
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Int?,
    @SerializedName("categories")
    val categories: List<String?>?,
    @SerializedName("community_data")
    val communityData: CommunityData?,
    @SerializedName("country_origin")
    val countryOrigin: String?,
    @SerializedName("description")
    val description: Description?,
    @SerializedName("developer_data")
    val developerData: DeveloperData?,
    @SerializedName("genesis_date")
    val genesisDate: String?,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("market_data")
    val marketData: MarketData?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("preview_listing")
    val previewListing: Boolean?,
    @SerializedName("public_notice")
    val publicNotice: Any?,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double?,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double?,
    @SerializedName("status_updates")
    val statusUpdates: List<Any?>?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int?,
    @SerializedName("web_slug")
    val webSlug: String?
) {
    data class CommunityData(
        @SerializedName("facebook_likes")
        val facebookLikes: Any?,
        @SerializedName("reddit_accounts_active_48h")
        val redditAccountsActive48h: Int?,
        @SerializedName("reddit_average_comments_48h")
        val redditAverageComments48h: Int?,
        @SerializedName("reddit_average_posts_48h")
        val redditAveragePosts48h: Int?,
        @SerializedName("reddit_subscribers")
        val redditSubscribers: Int?,
        @SerializedName("telegram_channel_user_count")
        val telegramChannelUserCount: Any?,
        @SerializedName("twitter_followers")
        val twitterFollowers: Int?
    )

    data class Description(
        @SerializedName("de")
        val de: String?,
        @SerializedName("en")
        val en: String?
    )

    data class DeveloperData(
        @SerializedName("closed_issues")
        val closedIssues: Int?,
        @SerializedName("code_additions_deletions_4_weeks")
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks?,
        @SerializedName("commit_count_4_weeks")
        val commitCount4Weeks: Int?,
        @SerializedName("forks")
        val forks: Int?,
        @SerializedName("last_4_weeks_commit_activity_series")
        val last4WeeksCommitActivitySeries: List<Any?>?,
        @SerializedName("pull_request_contributors")
        val pullRequestContributors: Int?,
        @SerializedName("pull_requests_merged")
        val pullRequestsMerged: Int?,
        @SerializedName("stars")
        val stars: Int?,
        @SerializedName("subscribers")
        val subscribers: Int?,
        @SerializedName("total_issues")
        val totalIssues: Int?
    ) {
        data class CodeAdditionsDeletions4Weeks(
            @SerializedName("additions")
            val additions: Int?,
            @SerializedName("deletions")
            val deletions: Int?
        )
    }

    data class Image(
        @SerializedName("large")
        val large: String?,
        @SerializedName("small")
        val small: String?,
        @SerializedName("thumb")
        val thumb: String?
    )

    data class MarketData(
        @SerializedName("current_price")
        val currentPrice: CurrentPrice?,
        @SerializedName("high_24h")
        val high24h: High24h?,
        @SerializedName("last_updated")
        val lastUpdated: String?,
        @SerializedName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency?
    ) {


        data class CurrentPrice(

            @SerializedName("usd")
            val usd: Double?,
        )


        data class High24h(
            @SerializedName("usd")
            val usd: Double?,
        )

        data class PriceChangePercentage24hInCurrency(
            @SerializedName("usd")
            val usd: Double?,
        )

    }
}