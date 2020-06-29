package io.goooler.demoapp.main.bean

import com.google.gson.annotations.SerializedName

class RepoListBean(
    val id: Long,
    val private: Boolean,
    val fork: Boolean,
    val name: String?,
    val description: String?,
    val owner: OwnerBean?
)

class OwnerBean(
    val id: Long,
    val login: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)