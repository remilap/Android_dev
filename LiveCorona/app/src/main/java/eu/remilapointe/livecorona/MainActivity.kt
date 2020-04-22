package eu.remilapointe.livecorona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.livecorona.network.data.Country
import eu.remilapointe.livecorona.ui.adapters.ModelListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        mProgressBar = progressBar
        progressBar.visibility = View.VISIBLE

        val data = Observer<Country> {
            recyclerview.adapter = ModelListAdapter(it.Countries)
            progressBar.visibility = View.GONE
        }

        viewModel.callAPI().observe(this,data)

    }
}
