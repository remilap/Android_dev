package eu.remilapointe.recyclerviewcheck.model

import com.google.gson.annotations.SerializedName

data class TopHeadline(
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: String,
    @SerializedName("articles")
    var articleList: List<Article>
) {
}