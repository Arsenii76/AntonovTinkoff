package com.antonov.tinkoff.fintex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.antonov.tinkoff.fintex.ui.features.random.adapter.ViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainPager2 = findViewById<ViewPager2>(R.id.mainPager2)
        val mainTabs = findViewById<TabLayout>(R.id.mainTabs)

        val titles = ArrayList<String>().apply {
            add(getString(R.string.tabs_random))
            add(getString(R.string.tabs_last))
            add(getString(R.string.tabs_the_best))
        }
        val fragmentAdapter = ViewPager2Adapter(this)
        mainPager2.adapter = fragmentAdapter
        TabLayoutMediator(mainTabs, mainPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}