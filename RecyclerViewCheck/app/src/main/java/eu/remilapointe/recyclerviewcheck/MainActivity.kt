package eu.remilapointe.recyclerviewcheck

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.recyclerviewcheck.adapter.NewsAdapter
import eu.remilapointe.recyclerviewcheck.model.Article
import eu.remilapointe.recyclerviewcheck.model.TopHeadline
import eu.remilapointe.recyclerviewcheck.web.WebService
import eu.remilapointe.recyclerviewcheck.web.WebServiceClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var articleList: List<Article> = arrayListOf()
    lateinit var rvNews: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var newsAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvNews = rv_news
        progressBar = progress_bar

        getNews()
        setData()
    }

    private fun getNews() {
        val webService = WebServiceClient.getClient().create(WebService::class.java)
        val call = webService.getTopHeadlines(Constant.COUNTRY, Constant.API_KEY)
        call.enqueue(object : Callback<TopHeadline> {
            override fun onFailure(call: Call<TopHeadline>?, t: Throwable?) {
                Log.v(TAG, "call failed")
                val intent = Intent(this@MainActivity, MainActivity1::class.java)
                startActivity(intent)
            }
            override fun onResponse(call: Call<TopHeadline>, response: Response<TopHeadline>) {
                progressBar.visibility = View.GONE
                Log.d(TAG, "onResponse: " + response.code())
                articleList = response.body().articleList
                newsAdapter = NewsAdapter(applicationContext, articleList)
            }
        })
    }

    private fun setData() {
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = NewsAdapter(this, articleList)
    }

}
