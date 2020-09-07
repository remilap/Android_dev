package eu.remilapointe.recyclerviewcheck.web

import eu.remilapointe.recyclerviewcheck.model.TopHeadline
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("apiKey")
        apiKey: String
    ) : Call<TopHeadline>
}