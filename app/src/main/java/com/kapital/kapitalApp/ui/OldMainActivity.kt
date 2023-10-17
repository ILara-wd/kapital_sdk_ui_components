package com.kapital.kapitalApp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Kapital.KapitalApp.BuildConfig
import com.Kapital.KapitalApp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kapital.kapitalApp.ktor.ApiService
import com.kapital.kapitalApp.ktor.RequestLoginModel
import com.kapital.kapitalApp.ktor.ResponseModel
import kotlinx.coroutines.launch

class OldMainActivity : AppCompatActivity() {

    val tag = "OldMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askNotificationPermission()
        findViewById<Button>(R.id.tvMain)
            .apply {
                setOnClickListener {
                    ProductViewModel().accounts(this@OldMainActivity)
                }
                text = packageName
                    .plus("\n\n")
                    .plus(BuildConfig.BASE_URL).plus(BuildConfig.COUNTRY)
            }
    }

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(tag, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(
                this,
                getString(R.string.text_post_notifications),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                getString(R.string.text_no_notifications),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {// FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(
                    this,
                    getString(R.string.display_an_educational),
                    Toast.LENGTH_SHORT
                ).show()
            } else {// Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    //private val viewModel by viewModels<ProductViewModel>

}

class ProductViewModel : ViewModel() {

    private val _investments = MutableLiveData<List<ResponseModel>>().apply { value = emptyList() }
    val investments: LiveData<List<ResponseModel>> = _investments

    fun accounts(context: Context) {
        viewModelScope.launch {
            ApiService.create().login(
                RequestLoginModel(
                    phone = "+525515937486",
                    refreshToken = context.getString(R.string.jwt_example)
                )
            )
            //_investments.value = list
        }
    }
}
