package io.goooler.demoapp.base.model

import androidx.lifecycle.MutableLiveData
import io.goooler.demoapp.base.util.MkUtil
import io.goooler.demoapp.base.util.SpUtil

/**
 * 自定义一些类型，例如某类的简称、别名等
 */

typealias MutableBooleanLiveData = MutableLiveData<Boolean>

typealias MutableIntLiveData = MutableLiveData<Int>

typealias MutableLongLiveData = MutableLiveData<Long>

typealias MutableDoubleLiveData = MutableLiveData<Double>

typealias MutableFloatLiveData = MutableLiveData<Float>

typealias MutableStringLiveData = MutableLiveData<String>

typealias MutableListLiveData<T> = MutableLiveData<List<T>>

typealias PrefUtil = SpUtil

typealias MMKVUtil = MkUtil