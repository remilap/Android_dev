package eu.remilapointe.country.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import eu.remilapointe.country.R

class CountryFragment(passedContext: Context) : Fragment() {

    val passThroughContext: Context = passedContext

    private lateinit var countryViewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.country_fragment, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
