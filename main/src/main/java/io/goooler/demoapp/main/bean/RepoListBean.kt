package io.goooler.demoapp.main.bean

import com.squareup.moshi.Json
import io.goooler.demoapp.base.http.BaseResponse

class RepoListBean(
    val id: Long,
    val private: Boolean,
    val fork: Boolean,
    val name: String?,
    val description: String?,
    val owner: OwnerBean? = null
) : BaseResponse

class OwnerBean(
    val id: Long,
    val login: String?,
    @Json(name = "avatar_url") val avatarUrl: String?
)