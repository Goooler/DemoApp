package io.goooler.demoapp.common

import io.goooler.demoapp.common.util.formatCurrency
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class CurrencyTest {

  @Test
  fun formatCurrency() {
    assertEquals(1000000.formatCurrency("zh-CN"), "¥1,000,000.00")
    assertEquals(1000000.formatCurrency("en-US"), "$1,000,000.00")
  }
}
