package io.goooler.demoapp.common

import io.goooler.demoapp.common.test.R
import io.goooler.demoapp.common.util.getQuantityString
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class CommonExtensionsTest {

  @Test
  fun intGetQuantityStringTest() {
    assertEquals(R.plurals.number_of_songs_available.getQuantityString(1), "1 song found.")
    assertEquals(R.plurals.number_of_songs_available.getQuantityString(2), "2 songs found.")
  }
}
