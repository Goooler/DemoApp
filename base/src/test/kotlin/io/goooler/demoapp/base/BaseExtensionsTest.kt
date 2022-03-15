package io.goooler.demoapp.base

import io.goooler.demoapp.base.util.times
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class BaseExtensionsTest {

  @Test
  fun `String times(Int)`() {
    assertEquals("1" * 3, "111")
  }
}
