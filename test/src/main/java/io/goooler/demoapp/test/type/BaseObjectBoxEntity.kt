package io.goooler.demoapp.test.type

import androidx.annotation.Keep
import io.objectbox.annotation.BaseEntity

@BaseEntity
@Keep
interface BaseObjectBoxEntity {
    var id: Long
}