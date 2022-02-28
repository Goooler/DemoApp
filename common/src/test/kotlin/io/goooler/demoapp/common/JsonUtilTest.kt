package io.goooler.demoapp.common

import com.squareup.moshi.JsonClass
import io.goooler.demoapp.common.util.JsonUtil
import io.goooler.demoapp.common.util.fromJson
import io.goooler.demoapp.common.util.toJson
import org.junit.Test

class JsonUtilTest {
  @Test
  fun `jsonUtil's fromJson(String)`() {
    assert(JsonUtil.fromJson<Repo>(firstStr) == firstBean)
    assert(JsonUtil.fromJson<Repo>(secondStr) == secondBean)
  }

  @Test
  fun `jsonUtil's fromJson(String, Class, Class)`() {
    val array: Array<Repo> = JsonUtil.fromJson(strArray, Array::class.java, Repo::class.java)
      ?: throw Exception("Parse json error")
    assert(array.first() == firstBean)
    assert(array[1] == secondBean)
  }

  @Test
  fun `jsonUtil's toJson(T)`() {
    assert(JsonUtil.toJson(firstBean) == firstStr)
    assert(JsonUtil.toJson(secondBean) == secondStr)
  }

  @Test
  fun `string's fromJson()`() {
    assert(firstStr.fromJson<Repo>() == firstBean)
    assert(secondStr.fromJson<Repo>() == secondBean)
  }

  @Test
  fun `string's fromJson(Class, Class)`() {
    val array: Array<Repo> = strArray.fromJson(Array::class.java, Repo::class.java)
      ?: throw Exception("Parse json error")
    assert(array.first() == firstBean)
    assert(array[1] == secondBean)
  }

  @Test
  fun `any's toJson(T)`() {
    assert(firstBean.toJson() == firstStr)
    assert(secondBean.toJson() == secondStr)
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
    private val firstStr = """
      {
        "id": 126987864,
        "name": "1024_hosts",
        "owner": {
          "login": "Goooler"
        }
      }
    """.trimIndent()

    private val secondStr = """
      {
        "id": 374913489,
        "name": "AndroidUiDemo",
        "owner": {
          "login": "Goooler"
        }
      }
    """.trimIndent()

    // https://api.github.com/users/goooler/repos?&page=1&per_page=2
    private val strArray = "[$firstStr,$secondStr]"

    private val firstBean = Repo(126987864, "1024_hosts", Repo.Owner("Goooler"))
    private val secondBean = Repo(374913489, "AndroidUiDemo", Repo.Owner("Goooler"))
  }
}
