package io.goooler.demoapp.main.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.goooler.demoapp.base.network.BaseResponse

@JsonClass(generateAdapter = true)
class MainRepoListBean(
    val id: Long,
    val private: Boolean,
    val fork: Boolean,
    val name: String?,
    val description: String?,
    val owner: OwnerBean? = null
) : BaseResponse {

    @JsonClass(generateAdapter = true)
    class OwnerBean(
        val id: Long,
        val login: String?,
        @Json(name = "avatar_url") val avatarUrl: String?
    )
}