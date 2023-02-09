package io.goooler.demoapp.base

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test

class BaseExtensionsTest {

  @Test
  fun `String times(Int)`() {
    assertEquals("1".repeat(3), "111")
    assertEquals("1".repeat(0), "")
    assertFailsWith<IllegalArgumentException> {
      "1".repeat(-1)
    }.message.let {
      assertEquals(it, "Count 'n' must be non-negative, but was -1.")
    }
  }
}
