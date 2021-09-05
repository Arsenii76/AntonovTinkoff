package com.antonov.tinkoff.fintex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.antonov.tinkoff.fintex.databinding.ActivityMainBinding
import com.antonov.tinkoff.fintex.ui.features.random.adapter.ViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val titles = ArrayList<String>().apply {
                add(getString(R.string.tabs_random))
                add(getString(R.string.tabs_last))
                add(getString(R.string.tabs_the_best))
            }
            pager2.adapter = ViewPager2Adapter(this@MainActivity)
            TabLayoutMediator(tabs, pager2) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }
}