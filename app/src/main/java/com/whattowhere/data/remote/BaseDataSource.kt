package com.whattowhere.data.remote

import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO
import org.json.JSONObject
import retrofit2.Response


open class BaseDataSource {

    open suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResources<T> {
        try {
            val response = call()
            when {
                response.code() == 403 -> {
                    return ApiResources.error("", code = response.code())
                }
                response.code() == 400 -> {
                    val error = response.errorBody()!!.string()
                    val errorObject = JSONObject(error)
                    return ApiResources.error(errorObject.getString("error"))
                }
                response.code() == 409 -> {
                    return ApiResources.error(response.errorBody()!!.string())
                }
                response.code() == 302 -> {
                    val errorMessage = response.errorBody()?.string()
                    return ApiResources.error(message = errorMessage)
                }
                response.body() != null -> {
                    if (response.code() == 200) {
                        val baseResponse = (response.body() as BaseDTO)
                        if (response.isSuccessful&&baseResponse.isSuccess){
                            response.body()?.let {
                                return ApiResources.success(it, baseResponse.message)
                            }
                        } else {
                            val body = response.body()
                            if (body != null) {
                                var msg = baseResponse.message.trim()
                                if (msg.isEmpty()) {
                                    msg = "Internal server error"
                                }
                                return ApiResources.error(msg)
                            }
                        }
                    } else {
                        val body = response.body()
                        if (body != null) {
                            return ApiResources.error("")
                        }
                    }
                    /*   if (response.isSuccessful && baseResponse.status) {
                            response.body()?.let {
                                return ApiResources.success(it, baseResponse.message)
                            }
                        } else {
                            val body = response.body()
                            if (body != null) {
                                var msg = baseResponse.message.trim()
                                if (msg.isEmpty()) {
                                    msg = "Internal server error"
                                }
                                return ApiResources.error(msg)
                            }
                        }*/
                    return error("Success => ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            return error("Error => ${e.message} ?: $e")
        }
        return ApiResources.error("")
    }

    private fun <T> error(message: String): ApiResources<T> {
        return ApiResources.error(message)
    }
}