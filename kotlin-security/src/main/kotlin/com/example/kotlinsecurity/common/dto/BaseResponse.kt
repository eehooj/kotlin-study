package com.example.kotlinsecurity.common.dto

import com.example.kotlinsecurity.common.status.ResultCode
import com.fasterxml.jackson.annotation.JsonIgnore

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg
)