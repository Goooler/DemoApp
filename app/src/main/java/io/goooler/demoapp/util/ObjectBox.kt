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
     * @param bean .
     */
    inline fun <reified T> put(bean: T) {
        boxStore.boxFor(T::class.java).put(bean)
    }

    /**
     * 存入数据库
     * @param beans .
     */
    inline fun <reified T> put(beans: Collection<T>) {
        boxStore.boxFor(T::class.java).put(beans)
    }

    /**
     * 按照 id 查找
     * @param id .
     */
    inline fun <reified T> get(id: Long): T {
        return boxStore.boxFor(T::class.java).get(id)
    }

    /**
     * 按照 id 查找
     * @param ids .
     */
    inline fun <reified T> get(ids: Collection<Long>): List<T> {
        return boxStore.boxFor(T::class.java).get(ids)
    }

    /**
     * 查找所有
     */
    inline fun <reified T> getAll(): List<T> {
        return boxStore.boxFor(T::class.java).all
    }

    /**
     * 删除
     * @param bean .
     */
    inline fun <reified T> remove(bean: T) {
        boxStore.boxFor(T::class.java).remove(bean)
    }

    /**
     * 删除
     * @param beans .
     */
    inline fun <reified T> remove(beans: Collection<T>) {
        boxStore.boxFor(T::class.java).remove(beans)
    }

    /**
     * 删除
     * @param id .
     */
    inline fun <reified T> remove(id: Long) {
        boxStore.boxFor(T::class.java).remove(id)
    }

    /**
     * 删除所有
     */
    inline fun <reified T> removeAll() {
        boxStore.boxFor(T::class.java).removeAll()
    }
}
