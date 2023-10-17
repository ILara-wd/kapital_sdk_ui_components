package com.kapital.kapitalApp.ktor.server

import com.kapital.kapitalApp.ktor.ApiRoutes
import com.kapital.kapitalApp.ktor.ApiService
import com.kapital.kapitalApp.ktor.RequestLoginModel
import com.kapital.kapitalApp.ktor.RequestModel
import com.kapital.kapitalApp.ktor.ResponseLoginModel
import com.kapital.kapitalApp.ktor.ResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url

class ApiServerImplement(
    private val client: HttpClient
) : ApiService {
    override suspend fun login(requestLoginModel: RequestLoginModel): ResponseLoginModel? {
        return try {
            client.post<ResponseLoginModel> {
                url(ApiRoutes.LOGIN)
                body = requestLoginModel
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }

    override suspend fun getProducts(): List<ResponseModel> {
        return try {
            client.get { url(ApiRoutes.PRODUCTS) }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            emptyList()
        }
    }

    override suspend fun createProducts(productRequest: RequestModel): ResponseModel? {
        return try {
            client.post<ResponseModel> {
                url(ApiRoutes.PRODUCTS)
                body = productRequest
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }

}
