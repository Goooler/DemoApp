package io.goooler.demoapp.common.type

import io.goooler.demoapp.base.http.BaseBean
import io.objectbox.annotation.BaseEntity

@BaseEntity
interface BaseObjectBoxEntity : BaseBean {
    var id: Long
}