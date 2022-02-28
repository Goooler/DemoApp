package io.goooler.demoapp.common

import com.squareup.moshi.JsonClass
import io.goooler.demoapp.common.util.JsonUtil
import org.junit.Test

class JsonUtilTest {
  @Test
  fun `jsonUtil's fromJson(String)`() {
    assert(JsonUtil.fromJson<Repo>(firstStr) == firstBean)
    assert(JsonUtil.fromJson<Repo>(secondStr) == secondBean)
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

    override fun hashCode(): Int {
      return id.hashCode()
    }
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
    private val beanArray = arrayOf(firstBean, secondBean)
  }
}
