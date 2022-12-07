package io.goooler.demoapp.main.bean

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "main_repo_list")
class MainRepoListBean(
  @PrimaryKey val id: Long,
  val private: Boolean,
  val name: String,
  @Json(name = "full_name")
  @ColumnInfo(name = "full_name")
  val fullName: String,
  @Embedded val owner: OwnerBean,
) {

  @JsonClass(generateAdapter = true)
  class OwnerBean(
    @Json(name = "login")
    @ColumnInfo(name = "owner_name")
    val ownerName: String,
    @Json(name = "avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
  )
}
