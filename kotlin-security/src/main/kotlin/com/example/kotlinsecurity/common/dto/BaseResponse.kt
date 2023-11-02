package com.example.kotlinsecurity.common.dto

import com.example.kotlinsecurity.common.status.ResultCode

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg
)