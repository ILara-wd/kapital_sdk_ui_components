package com.kapital.kapitalApp.ktor

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel(
    val title: String,
    val description: String,
    val image: String
)

@Serializable
data class RequestModel(
    val title: String,
    val description: String,
    val image: String
)

@Serializable
data class RequestLoginModel(
    val phone: String,
    val refreshToken: String
)

@Serializable
data class ResponseLoginModel(
    val body: String,
    val iv: String
)
