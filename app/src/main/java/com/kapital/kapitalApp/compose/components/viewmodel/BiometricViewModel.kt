package com.kapital.kapitalApp.compose.components.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapital.kapitalApp.compose.utils.ValueStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BiometricViewModel @Inject constructor() : ViewModel() {

    var showLoader: Boolean by mutableStateOf(false)
    var successBiometrics: Boolean? by mutableStateOf(null)

    private val _capturedPhone = MutableStateFlow("")
    val capturedPhone = _capturedPhone.asStateFlow()

    private val _errors = MutableStateFlow<LoginError>(LoginError.None)
    val errors = _errors.asStateFlow()

    private var validPhone: Phone? = null

    var buttonEnabled by mutableStateOf(false)
        private set

    val categories = mutableStateOf(
        listOf(
            "Todas",
            "En tienda",
            "En línea",
            "Destacadas",
            "Descuentos",
            "Las más buscadas",
            "Accesorios",
            "Belleza",
            "Calzado",
            "Deporte",
            "Electrodomésticos",
            "Hogar",
            "Moda",
            "Otros"
        )
    )

    val filterStore =
        ValueStateFlow.createValueState(categories.value[5]) {
            addScope(viewModelScope)
            addOnError(error = { "" }, initialError = { "" })
        }

    fun updateCapturedPhone(captured: String) {
        _capturedPhone.value = captured
        runCatching {
            Phone.mexicoRegionPhone(capturedPhone.value)
        }.onFailure {
            buttonEnabled = false
            _errors.value = when (it.message) {
                MessagesErrors.EMPTY -> LoginError.EmptyPhoneCaptured
                MessagesErrors.LENGTH -> LoginError.InvalidPhoneLengthPhoneCaptured
                else -> LoginError.None
            }
        }.onSuccess {
            validPhone = it
            buttonEnabled = true
            _errors.value = LoginError.None
        }
    }

    private fun String.onlyText(): Boolean = !contains("[0-9]".toRegex())
    private fun String.onlyNumber(): Boolean = contains("[0-9]".toRegex())

    val phoneState = ValueStateFlow.createValueState<String, PersonalInfoErrors> {
        addTransformCheckValue { it?.let { Phone(it) } }
        addUpdatable {
            it?.onlyNumber() == true &&
                    it.length < Name.maxLength
        }
        addScope(viewModelScope)
        addOnError(
            error = {
                PersonalInfoErrors.NameError(
                    length = it?.message == MessagesErrors.LENGTH,
                    empty = it?.message == MessagesErrors.EMPTY,
                    format = it?.message == MessagesErrors.INVALID_FORMAT
                )
            },
            initialError = { PersonalInfoErrors.None }
        )
    }

    val nameState = ValueStateFlow.createValueState<String, PersonalInfoErrors> {
        addTransformCheckValue { it?.let { Name(it) } }
        addUpdatable {
            it?.onlyText() == true &&
                    it.length < Name.maxLength
        }
        addScope(viewModelScope)
        addOnError(
            error = {
                PersonalInfoErrors.NameError(
                    length = it?.message == MessagesErrors.LENGTH,
                    empty = it?.message == MessagesErrors.EMPTY,
                    format = it?.message == MessagesErrors.INVALID_FORMAT
                )
            },
            initialError = { PersonalInfoErrors.None }
        )
    }

}

sealed class LoginError {
    object InvalidPhoneError : LoginError()
    object EmptyPhoneCaptured : LoginError()
    object InvalidPhoneLengthPhoneCaptured : LoginError()
    object None : LoginError()
}


@JvmInline
value class Phone(private val value: String) : MessagesErrors {
    companion object {
        @JvmStatic
        private val regex = "(52)([0-9]{2})([0-9]{4})([0-9]{4})".toRegex()
        private const val PHONE_REGION = 52
        const val PHONE_LENGTH = 12

        fun mexicoRegionPhone(phone: String): Phone {
            return Phone("${PHONE_REGION}$phone")
        }
    }

    init {
        check(value.removePrefix(PHONE_REGION.toString()).isNotEmpty()) {
            MessagesErrors.EMPTY
        }
        check(value.length == PHONE_LENGTH) {
            MessagesErrors.LENGTH
        }
        require(regex.matches(value)) {
            MessagesErrors.INVALID_FORMAT
        }
    }

    fun getFormatted(): String {
        val buffer = StringBuilder()
        regex.matchEntire(value)
            ?.groupValues
            ?.forEachIndexed { index, value ->
                when (index) {
                    0 -> Unit
                    1 -> buffer.append("+").append(value).append(" ")
                    2 -> buffer.append(value)
                    else -> buffer.append("-").append(value)
                }
            }
        return buffer.toString()
    }

    fun getPhoneWithoutRegion(): String {
        return value.substring(2)
    }
}


sealed class PersonalInfoErrors(val hasErrors: Boolean) {
    class NameError(val length: Boolean, val empty: Boolean, val format: Boolean) :
        PersonalInfoErrors(length || empty || format)

    class LastNameError(val length: Boolean, val empty: Boolean, val format: Boolean) :
        PersonalInfoErrors(length || empty || format)

    class MotherNameError(val length: Boolean, val format: Boolean) :
        PersonalInfoErrors(length || format)

    class CURPError(val empty: Boolean, val format: Boolean) :
        PersonalInfoErrors(empty || format)

    object None : PersonalInfoErrors(false)
    object Empty : PersonalInfoErrors(true)
    object InvalidAge : PersonalInfoErrors(true)
    class ErrorMessage(val message: String) : PersonalInfoErrors(false)
}

@JvmInline
value class Name(private val value: String) : MessagesErrors {

    companion object {
        const val maxLength = 50
        private val regex = "[\\w ]{3,$maxLength}".toRegex()
    }

    init {
        require(value.isNotEmpty()) {
            MessagesErrors.EMPTY
        }
        require(value.length < maxLength) {
            MessagesErrors.LENGTH
        }
        check(regex.matches(value)) {
            MessagesErrors.INVALID_FORMAT
        }
    }

    override fun toString(): String {
        return value
    }
}

interface MessagesErrors {
    companion object {
        const val EMPTY = "Can be empty"
        const val LENGTH = "Invalid length"
        const val INVALID_FORMAT = "Invalid format"
    }
}
