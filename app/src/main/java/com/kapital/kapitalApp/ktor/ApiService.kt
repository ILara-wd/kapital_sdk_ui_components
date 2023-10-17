package com.kapital.kapitalApp.ktor

import android.util.Log
import com.Kapital.KapitalApp.BuildConfig
import com.kapital.kapitalApp.ktor.server.ApiServerImplement
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType


interface ApiService {

    suspend fun login(requestLoginModel: RequestLoginModel): ResponseLoginModel?

    suspend fun getProducts(): List<ResponseModel>

    suspend fun createProducts(productRequest: RequestModel): ResponseModel?

    companion object {
        fun create(): ApiService {
            return ApiServerImplement(
                client = HttpClient(Android) {
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                if (BuildConfig.DEBUG) {
                                    Log.v("Logger http Ktor =>", message)
                                }
                            }
                        }
                        level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(json)
                    }
                    install(HttpTimeout) {
                        requestTimeoutMillis = ConstantsKtor.TIMEOUT_MILLI
                        connectTimeoutMillis = ConstantsKtor.TIMEOUT_MILLI
                        socketTimeoutMillis = ConstantsKtor.TIMEOUT_MILLI
                    }
                    // Apply to all requests
                    defaultRequest {
                        // Parameter("api_key", "some_api_key")
                        // Content Type
                        if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }.apply {
                    sendPipeline.intercept(HttpSendPipeline.State) {
                        context.headers.append("AppVersion", BuildConfig.VERSION_NAME)
                    }
                }
            )
        }

        private val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = false
        }
    }

}
