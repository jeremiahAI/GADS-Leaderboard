package com.jeremiahai.gadsleaderboard.submission

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jeremiahai.gadsleaderboard.R
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_submit.*
import kotlinx.android.synthetic.main.submit_confirmation.view.*
import kotlinx.android.synthetic.main.submit_status.view.*

@AndroidEntryPoint
class SubmitActivity : AppCompatActivity() {

    private val submitStatusView: View by lazy {
        layoutInflater.inflate(R.layout.submit_status, null, false)
    }
    val viewModel: SubmitViewModel by viewModels()

    val progressDialog by lazy {
        SpotsDialog(this, "Please wait...")
    }

    var statusDialog: AlertDialog? = null
    var confirmationDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setupViewModel()
        setupView()
    }

    private fun setupView() {
        confirmationDialog = MaterialAlertDialogBuilder(this)
            .setView(layoutInflater.inflate(R.layout.submit_confirmation, null, false).apply {
                closeIcon.setOnClickListener {
                    confirmationDialog?.dismiss()
                }
                yesButton.setOnClickListener {
                    confirmationDialog?.dismiss()
                    viewModel.submit(
                        emailEt.text.toString(),
                        firstNameEt.text.toString(),
                        lastNameEt.text.toString(),
                        githubLinkEt.text.toString()
                    )
                }
            })
            .create()

        statusDialog = MaterialAlertDialogBuilder(this)
            .setView(submitStatusView)
            .create()

        nextBtn.setOnClickListener {
            validateData()
        }
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
        viewModel.submitStatusLiveData.observe(this, Observer {
            it?.getContentIfNotHandled()?.let { isSuccessful ->
                statusDialog?.setView(submitStatusView.apply {
                    this.statusIcon.setImageResource(
                        if (isSuccessful) R.drawable.ic_check_circle else R.drawable.ic_warning
                    )
                    this.message.text =
                        if (isSuccessful) "Submission Successful" else "Submission not Successful"
                })


                statusDialog?.show()
            }
        })
    }


    private fun validateData() {
        if (
            emailEt.text.isNullOrEmpty() ||
            firstNameEt.text.isNullOrEmpty() ||
            lastNameEt.text.isNullOrEmpty() ||
            githubLinkEt.text.isNullOrEmpty()
        ) Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show()
        else confirmationDialog?.show()
    }


}