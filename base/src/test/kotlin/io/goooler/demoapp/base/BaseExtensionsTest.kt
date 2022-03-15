package io.goooler.demoapp.base

import io.goooler.demoapp.base.util.times
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BaseExtensionsTest {

  @Test
  fun `String times(Int)`() {
    assertEquals("1" * 3, "111")
    assertEquals("1" * 0, "")
    assertThrows<IllegalArgumentException> {
      "1" * -1
    }.message.let {
      assertEquals(it, "Param num should >= 0")
    }
  }
}
