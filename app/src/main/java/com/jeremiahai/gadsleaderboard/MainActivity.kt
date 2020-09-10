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

    }

}