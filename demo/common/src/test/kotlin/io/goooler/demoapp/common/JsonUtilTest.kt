package io.goooler.demoapp.common

import com.squareup.moshi.JsonClass
import io.goooler.demoapp.base.util.secondOrNull
import io.goooler.demoapp.common.util.JsonUtil
import io.goooler.demoapp.common.util.fromJson
import io.goooler.demoapp.common.util.toJson
import kotlin.test.assertEquals
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

class JsonUtilTest {
  @Test
  fun `JsonUtil fromJson(String)`() {
    assertEquals(JsonUtil.fromJson<Repo>(firstStr), firstBean)
    assertEquals(JsonUtil.fromJson<Repo>(secondStr), secondBean)
  }

  @Test
  fun `JsonUtil fromJson(String, Class, Class)`() {
    val list: List<Repo> = JsonUtil.fromJson(strArray, List::class.java, Repo::class.java)
      ?: throw AssertionError("Parse json error")
    assertEquals(list.firstOrNull(), firstBean)
    assertEquals(list.secondOrNull(), secondBean)
  }

  @Test
  fun `JsonUtil toJson(T)`() {
    assertEquals(JsonUtil.toJson(firstBean), firstStr)
    assertEquals(JsonUtil.toJson(secondBean), secondStr)
  }

  @Test
  fun `String fromJson()`() {
    assertEquals(firstStr.fromJson<Repo>(), firstBean)
    assertEquals(secondStr.fromJson<Repo>(), secondBean)
  }

  @Test
  fun `String fromJson(Class, Class)`() {
    val list: List<Repo> = strArray.fromJson(List::class.java, Repo::class.java)
      ?: throw AssertionError("Parse json error")
    assertEquals(list.first(), firstBean)
    assertEquals(list[1], secondBean)
  }

  @Test
  fun `Any toJson(T)`() {
    assertEquals(firstBean.toJson(), firstStr)
    assertEquals(secondBean.toJson(), secondStr)
  }

  @JsonClass(generateAdapter = true)
  internal data class Repo(val id: Long, val name: String, val owner: Owner) {
    @JsonClass(generateAdapter = true)
    data class Owner(val login: String)
  }

  companion object {
    @Language("JSON")
    private val firstStr = """
      {"id":126987864,"name":"1024_hosts","owner":{"login":"Goooler"}}
    """.trimIndent()

    @Language("JSON")
    private val secondStr = """
      {"id":374913489,"name":"AndroidUiDemo","owner":{"login":"Goooler"}}
    """.trimIndent()

    // https://api.github.com/users/goooler/repos?&page=1&per_page=2
    @Language("JSON")
    private val strArray = "[$firstStr,$secondStr]"

    private val firstBean = Repo(126987864, "1024_hosts", Repo.Owner("Goooler"))
    private val secondBean = Repo(374913489, "AndroidUiDemo", Repo.Owner("Goooler"))
  }
}
