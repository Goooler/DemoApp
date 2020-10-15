@file:Suppress("unused")

package io.goooler.demoapp.test.util

import io.goooler.demoapp.test.type.BaseObjectBoxEntity
import io.objectbox.BoxStore
import kotlin.reflect.KClass

/**
 * ObjectBox 使用简单封装
 */
object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(body: () -> BoxStore) {
        boxStore = body()
    }

    /**
     * 存入数据库
     * @param bean .
     */
    inline fun <reified T : BaseObjectBoxEntity> put(bean: T) {
        boxStore.boxFor(T::class.java).put(bean)
    }

    /**
     * 存入数据库
     * @param beans .
     */
    inline fun <reified T : BaseObjectBoxEntity> put(beans: Collection<T>) {
        boxStore.boxFor(T::class.java).put(beans)
    }

    /**
     * 按照 id 查找
     * @param id .
     */
    inline fun <reified T : BaseObjectBoxEntity> get(id: Long): T {
        return boxStore.boxFor(T::class.java).get(id)
    }

    /**
     * 按照 id 查找
     * @param ids .
     */
    inline fun <reified T : BaseObjectBoxEntity> get(ids: Collection<Long>): List<T> {
        return boxStore.boxFor(T::class.java).get(ids)
    }

    /**
     * 查找所有
     */
    inline fun <reified T : BaseObjectBoxEntity> getAll(): List<T> {
        return boxStore.boxFor(T::class.java).all
    }

    /**
     * 删除
     * @param bean .
     */
    inline fun <reified T : BaseObjectBoxEntity> remove(bean: T) {
        boxStore.boxFor(T::class.java).remove(bean)
    }

    /**
     * 删除
     * @param beans .
     */
    inline fun <reified T : BaseObjectBoxEntity> remove(beans: Collection<T>) {
        boxStore.boxFor(T::class.java).remove(beans)
    }

    /**
     * 删除
     * @param id .
     */
    inline fun <reified T : BaseObjectBoxEntity> remove(id: Long) {
        boxStore.boxFor(T::class.java).remove(id)
    }

    /**
     * 删除所有
     */
    inline fun <reified T : BaseObjectBoxEntity> removeAll() {
        boxStore.boxFor(T::class.java).removeAll()
    }
}

inline fun <reified T : BaseObjectBoxEntity> T.putIntoBox() {
    ObjectBox.put(this)
}

inline fun <reified T : BaseObjectBoxEntity> Collection<T>.putIntoBox() {
    return ObjectBox.put(this)
}

inline fun <reified T : BaseObjectBoxEntity> KClass<T>.getAllFromBox(): List<T> {
    return ObjectBox.getAll()
}
