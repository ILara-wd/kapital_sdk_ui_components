package com.kapital.kapitalApp.ktor

object ApiRoutes {
    private const val BASE_URL = "https://fakestoreapi.com"
    private const val BASE_URL_KAPITAL = "https://develop-api.kapital.cc"
    const val PRODUCTS = "$BASE_URL/products"
    const val LOGIN = "$BASE_URL_KAPITAL/auth/refresh_token"
}
