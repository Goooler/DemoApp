package io.goooler.demoapp.common

import com.squareup.moshi.JsonClass
import io.goooler.demoapp.common.util.JsonUtil
import io.goooler.demoapp.common.util.fromJson
import io.goooler.demoapp.common.util.toJson
import org.intellij.lang.annotations.Language
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonUtilTest {
  @Test
  fun `JsonUtil fromJson(String)`() {
    assertTrue(JsonUtil.fromJson<Repo>(firstStr) == firstBean)
    assertTrue(JsonUtil.fromJson<Repo>(secondStr) == secondBean)
  }

  @Test
  fun `JsonUtil fromJson(String, Class, Class)`() {
    val array: List<Repo> = JsonUtil.fromJson(strArray, List::class.java, Repo::class.java)
      ?: throw Exception("Parse json error")
    assertTrue(array.first() == firstBean)
    assertTrue(array[1] == secondBean)
  }

  @Test
  fun `JsonUtil toJson(T)`() {
    assertTrue(JsonUtil.toJson(firstBean) == firstStr)
    assertTrue(JsonUtil.toJson(secondBean) == secondStr)
  }

  @Test
  fun `String fromJson()`() {
    assertTrue(firstStr.fromJson<Repo>() == firstBean)
    assertTrue(secondStr.fromJson<Repo>() == secondBean)
  }

  @Test
  fun `String fromJson(Class, Class)`() {
    val array: List<Repo> = strArray.fromJson(List::class.java, Repo::class.java)
      ?: throw Exception("Parse json error")
    assertTrue(array.first() == firstBean)
    assertTrue(array[1] == secondBean)
  }

  @Test
  fun `Any? toJson(T)`() {
    assertTrue(firstBean.toJson() == firstStr)
    assertTrue(secondBean.toJson() == secondStr)
  }

  @JsonClass(generateAdapter = true)
  internal data class Repo(val id: Long, val name: String, val owner: Owner) {
    @JsonClass(generateAdapter = true)
    data class Owner(val login: String)

    override fun equals(other: Any?): Boolean = if (other is Repo) {
      this.id == other.id && this.name == other.name && this.owner.login == other.owner.login
    } else {
      false
    }

    override fun hashCode(): Int = id.hashCode()
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
