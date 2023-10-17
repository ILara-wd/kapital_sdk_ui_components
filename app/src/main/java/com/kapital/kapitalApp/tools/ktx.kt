@file:Suppress("DEPRECATION")

package com.kapital.kapitalApp.tools

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.biometrics.BiometricsConstants
import com.kapital.kapitalApp.biometrics.BiometricsResultType


fun Context.findActivity(): FragmentActivity? {
    var currentContext = this
    var previousContext: Context? = null
    while (currentContext is ContextWrapper && previousContext != currentContext) {
        if (currentContext is FragmentActivity) {
            return currentContext
        }
        previousContext = currentContext
        currentContext = currentContext.baseContext
    }
    return null
}

fun getIconPassword(isShowInfo: Boolean): Int {
    return if (isShowInfo) R.drawable.ic_password_visible else R.drawable.ic_password_hidden
}


fun initBiometric(
    ctx: Context,
    fragmentActivity: FragmentActivity,
    onBiometricsResult: ((result: BiometricsResultType, error: String?) -> Unit)? = null
) {
    BiometricPrompt(fragmentActivity, ContextCompat.getMainExecutor(ctx),
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                onBiometricsResult?.let { it(BiometricsResultType.CANCEL, errString.toString()) }
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                onBiometricsResult?.let { it(BiometricsResultType.SUCCESS, null) }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onBiometricsResult?.let { it(BiometricsResultType.ERROR, null) }
            }
        }).authenticate(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
    )
}

fun Context.sendVibration() {
    val vibrator: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    // handle heavy click vibration button
    val vibrationEffect5: VibrationEffect
    // this type of vibration requires API 29
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // create vibrator effect with the constant EFFECT_HEAVY_CLICK
        vibrationEffect5 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        // it is safe to cancel other vibrations currently taking place
        vibrator.cancel()
        vibrator.vibrate(vibrationEffect5)
    }

    // handle tick effect vibration button
    val vibrationEffect4: VibrationEffect
    // this type of vibration requires API 29
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // create vibrator effect with the constant EFFECT_TICK
        vibrationEffect4 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
        // it is safe to cancel other vibrations currently taking place
        vibrator.cancel()
        vibrator.vibrate(vibrationEffect4)
    }

    // handle double click vibration button
    val vibrationEffect3: VibrationEffect
    // this type of vibration requires API 29
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // create vibrator effect with the constant EFFECT_DOUBLE_CLICK
        vibrationEffect3 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
        // it is safe to cancel other vibrations currently taking place
        vibrator.cancel()
        vibrator.vibrate(vibrationEffect3)
    }

    // handle click vibration button
    val vibrationEffect2: VibrationEffect
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // create vibrator effect with the constant EFFECT_CLICK
        vibrationEffect2 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
        // it is safe to cancel other vibrations currently taking place
        vibrator.cancel()
        vibrator.vibrate(vibrationEffect2)
    }

    // handle normal vibration button
    val vibrationEffect1: VibrationEffect
    // this is the only type of the vibration which requires system version Oreo (API 26)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // this effect creates the vibration of default amplitude for 1000ms(1 sec)
        vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        // it is safe to cancel other vibrations currently taking place
        vibrator.cancel()
        vibrator.vibrate(vibrationEffect1)
    }

}

sealed class Vibration {
    object VibrationDefault : Vibration()
    object VibrationClick : Vibration()
    object VibrationDoubleClick : Vibration()
    object VibrationTick : Vibration()
    object VibrationHeavyTick : Vibration()
}
