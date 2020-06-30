package io.goooler.demoapp.util

import android.content.Context
import io.goooler.demoapp.main.bean.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    internal fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    /**
     * 存入数据库
     */
    inline fun <reified T> put(bean: T) {
        boxStore.boxFor(T::class.java).put(bean)
    }

    /**
     * 存入数据库
     */
    inline fun <reified T> put(beans: List<T>) {
        boxStore.boxFor(T::class.java).put(beans)
    }

    /**
     * 按照 id 查找
     */
    inline fun <reified T> get(id: Long): T {
        return boxStore.boxFor(T::class.java).get(id)
    }

    /**
     * 按照 id 查找
     */
    inline fun <reified T> get(ids: List<Long>): List<T> {
        return boxStore.boxFor(T::class.java).get(ids)
    }

    /**
     * 查找所有
     */
    inline fun <reified T> getAll(): List<T> {
        return boxStore.boxFor(T::class.java).all
    }
}
