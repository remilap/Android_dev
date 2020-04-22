package eu.remilapointe.livecorona.repos

import androidx.lifecycle.MutableLiveData
import com.log4k.e
import eu.remilapointe.livecorona.network.data.Country
import eu.remilapointe.livecorona.ui.interfaces.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {

    fun callAPI(): MutableLiveData<Country> {
        val mutableLiveData = MutableLiveData<Country>()

        RetrofitApiService().fetchData()
            .enqueue(object : Callback<Country> {
                override fun onFailure(call: Call<Country>, t: Throwable) {
                    e("Coudn't get the data")

                }
                override fun onResponse(call: Call<Country>, response: Response<Country>) {
                    if (response.isSuccessful) {
                        mutableLiveData.postValue(response.body())
                    } else {
                        e("Coudn't get the data")
                    }

                }
            })
        return mutableLiveData
    }

}
