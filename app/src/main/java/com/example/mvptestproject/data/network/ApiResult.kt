package com.example.mvptestproject.data.network

sealed class ApiResult<out ResultType> {

    data class Success<out ResultType>(val value: ResultType) : ApiResult<ResultType>()

    data class Error<out ResultType>(val value: ResultType) : ApiResult<Nothing>()
}
