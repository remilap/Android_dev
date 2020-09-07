package eu.remilapointe.recyclerviewcheck.web

import eu.remilapointe.recyclerviewcheck.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebServiceClient {
    companion object {
        fun getClient() : Retrofit {
            var client = OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
            return Retrofit.Builder()
                .baseUrl(Constant.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}