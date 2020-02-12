package com.remilapointe.laser

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.inthecheesefactory.thecheeselibrary.fragment.bus.ActivityResultBus
import com.inthecheesefactory.thecheeselibrary.fragment.bus.ActivityResultEvent
import com.log4k.d
import com.remilapointe.laser.ui.main.SectionsPagerAdapter

class ConfigurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.configuration_activity)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = findViewById(R.id.fab_word)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        d("onActivityResult in MainActivity with request= $requestCode and result=$resultCode")
        ActivityResultBus.getInstance().postQueue(
            ActivityResultEvent(requestCode, resultCode, data)
        )
    }

}
