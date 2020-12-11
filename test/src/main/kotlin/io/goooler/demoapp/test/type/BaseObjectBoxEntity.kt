package io.goooler.demoapp.test.type

import androidx.annotation.Keep
import io.objectbox.annotation.BaseEntity

@Keep
@BaseEntity
interface BaseObjectBoxEntity {
  var id: Long
}
