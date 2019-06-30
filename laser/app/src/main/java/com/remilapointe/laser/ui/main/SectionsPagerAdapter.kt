package com.remilapointe.laser.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.remilapointe.laser.R

private val TAB_TITLES = arrayOf(
    R.string.tab_colori_title,
    R.string.tab_taille_title,
    R.string.tab_placelogo_title,
    R.string.tab_produit
)
const val TAB_COLORI = 0
const val TAB_TAILLE = 1
const val TAB_PLACELOGO = 2
const val TAB_PRODUIT = 3
const val TAB_QUANTITEPRODUIT = 4
const val TAB_ACHATPRODUIT = 5

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        when(position) {
            TAB_COLORI -> return ColoriFragment(context)
            TAB_TAILLE -> return TailleFragment(context)
            TAB_PLACELOGO -> return PlaceLogoFragment(context)
            TAB_PRODUIT -> return ProduitFragment(context)
        }
        return ColoriFragment(context)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

}
