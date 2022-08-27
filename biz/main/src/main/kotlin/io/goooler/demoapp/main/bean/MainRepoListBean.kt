package io.goooler.demoapp.main.bean

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.goooler.demoapp.common.network.BaseResponse

@JsonClass(generateAdapter = true)
@Entity(tableName = "main_repo_list")
data class MainRepoListBean(
  @PrimaryKey val id: Long,
  @ColumnInfo val private: Boolean,
  @ColumnInfo val name: String,
  @Json(name = "full_name") val fullName: String,
  @Embedded val owner: OwnerBean,
) : BaseResponse() {
  @JsonClass(generateAdapter = true)
  data class OwnerBean(
    @ColumnInfo(name = "avatar_url") @Json(name = "avatar_url") val avatarUrl: String?,
  )
}
