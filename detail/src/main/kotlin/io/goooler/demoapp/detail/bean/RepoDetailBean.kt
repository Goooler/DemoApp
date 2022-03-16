package io.goooler.demoapp.detail.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RepoDetailBean(
  @Json(name = "full_name") val fullName: String,
  val description: String,
  @Json(name = "stargazers_count") val starsCount: Int,
  @Json(name = "forks_count") val forksCount: Int,
  @Json(name = "open_issues_count") val openIssuesCount: Int,
  val license: License
) {
  @JsonClass(generateAdapter = true)
  class License(val name: String)
}
