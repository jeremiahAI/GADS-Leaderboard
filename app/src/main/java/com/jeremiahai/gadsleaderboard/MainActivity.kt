package com.jeremiahai.gadsleaderboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeremiahai.gadsleaderboard.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }


    private fun setupView() {
        adapter = TabAdapter(tabLayout, supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

//        highLightCurrentTab(0) // for initial selected tab view
//
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                highLightCurrentTab(position) // for tab change
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {}
//        })
    }

//    private fun highLightCurrentTab(position: Int) {
//        for (i in 0 until tabLayout.tabCount) {
//            val tab = tabLayout.getTabAt(i)
//            tab?.customView = null
//            tab?.customView = adapter.getTabView(i)
//        }
//
//        val tab = tabLayout.getTabAt(position);
//        tab?.customView = null;
//        tab?.customView = adapter.getSelectedTabView(position);
//    }
}