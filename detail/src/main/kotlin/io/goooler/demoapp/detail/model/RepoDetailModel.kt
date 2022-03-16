package io.goooler.demoapp.detail.model

data class RepoDetailModel(
  val fullName: String = "",
  val description: String = "",
  val license: String = "",
  val starsCount: Int = 0,
  var forksCount: Int = 0,
  val openIssuesCount: Int = 0
)
