package com.kapital.kapitalApp.compose.components

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.kapital.kapitalApp.biometrics.BiometricsConstants
import com.kapital.kapitalApp.tools.findActivity

@Composable
fun biometricAuthentication(onAuthenticationSuccess: () -> Unit) {
    var isBiometricEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current.findActivity()

    DisposableEffect(Unit) {
        isBiometricEnabled = checkBiometricSupport(context)
        onDispose { }
    }

    if (isBiometricEnabled) {
        Button(
            onClick = { showBiometricPrompt(activity!!, onAuthenticationSuccess) }
        ) {
            Text("Authenticate with Biometrics")
        }
    } else {
        // Biometrics not available on this device
        Text("Biometric authentication is not available.")
    }
}

@Suppress("MaxLineLength")
private fun checkBiometricSupport(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return biometricManager.canAuthenticate(BiometricsConstants.ALLOWED_AUTHENTICATORS) == BiometricManager.BIOMETRIC_SUCCESS
}

private fun showBiometricPrompt(
    activity: FragmentActivity,
    onAuthenticationSuccess: () -> Unit
) {
    val biometricPrompt = BiometricPrompt(
        activity,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onAuthenticationSuccess()
            }
        }
    )

    val promptInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación Biométrica")
            .setSubtitle("Por favor, usa tu huella digital")
            .setAllowedAuthenticators(BiometricsConstants.ALLOWED_AUTHENTICATORS)
            .build()
    } else {
        @Suppress("DEPRECATION")
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación Biométrica")
            .setSubtitle("Por favor, usa tu huella digital")
            .setDeviceCredentialAllowed(true)
            .build()
    }

    biometricPrompt.authenticate(promptInfo)
}
