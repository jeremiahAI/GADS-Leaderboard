package com.jeremiahai.gadsleaderboard.leaderBoard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jeremiahai.gadsleaderboard.R
import com.jeremiahai.gadsleaderboard.submission.SubmitActivity
import com.jeremiahai.gadsleaderboard.utils.TabAdapter
import com.jeremiahai.gadsleaderboard.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class LeaderBoardActivity : AppCompatActivity() {
    lateinit var adapter: TabAdapter

    val progressDialog by lazy {
        SpotsDialog(this, "Please wait...")
    }

    private val viewModel: LeaderBoardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupViewModel()
        viewModel.onInit()
    }

    private fun setupViewModel() {
        viewModel.progressIndicatorLiveData.observe(this, Observer {
            it?.let { active ->
                if (active) {
                    if (!progressDialog.isShowing && !isFinishing) {
                        progressDialog.show()
                    }
                } else {
                    progressDialog.dismiss()
                }
            }
        })
        viewModel.errorMessage.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                showError(it)
            }
        })
    }


    private fun setupView() {
        adapter = TabAdapter(tabLayout, supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        submitButton.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }

    }

}