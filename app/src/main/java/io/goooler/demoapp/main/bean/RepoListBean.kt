package io.goooler.demoapp.main.bean

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient

@Entity
class RepoListBean(
    @Id(assignable = true) var id: Long,
    var private: Boolean,
    var fork: Boolean,
    var name: String?,
    var description: String?,
    @Transient val owner: OwnerBean?
)

class OwnerBean(
    val id: Long,
    val login: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)