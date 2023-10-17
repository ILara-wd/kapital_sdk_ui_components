package com.kapital.kapitalApp.biometrics

import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL

object BiometricsConstants {
    const val ALLOWED_AUTHENTICATORS = BIOMETRIC_WEAK or DEVICE_CREDENTIAL

    const val BIOMETRIC_UNAVAILABLE = "Debe contar con un tipo de autenticación para realizar esta acción"
    const val AUTHENTICATION_SUCCEEDED = "Authentication succeeded"
    const val AUTHENTICATION_ERROR = "Authentication error"

    const val BIOMETRIC_AUTHENTICATION = "Kapital Biometricos"
    const val USE_DEVICE_PASSWORD = "Use device password"
    const val BIOMETRIC_AUTHENTICATION_SUBTITLE = "Use su huella digital para autenticar"
    const val BIOMETRIC_AUTHENTICATION_DESCRIPTION =
        "Kapital usa su huella digital como medio de seguridad"

    const val AUTHENTICATE_OTHER = "Authenticate using Device Password/PIN"
    const val AUTHENTICATE_FINGERPRINT = "Authenticate using Fingerprint"

    const val AVAILABLE = "Available"
    const val UNAVAILABLE = "Unavailable"
    const val TRUE = "True"
    const val FALSE = "False"
    const val CANCEL = "Cancelar"

    const val PASSWORD_PIN_AUTHENTICATION = "Password/PIN Authentication"
    const val PASSWORD_PIN_AUTHENTICATION_SUBTITLE = "Authenticate using Device Password/PIN"
    const val PASSWORD_PIN_AUTHENTICATION_DESCRIPTION =
        "This app uses your makes use of device password/pin to authenticate the dialog."
}
