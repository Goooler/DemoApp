package io.goooler.demoapp.util

import android.app.Application
import io.goooler.demoapp.base.BaseObjectBoxEntity
import io.goooler.demoapp.main.bean.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    internal fun init(application: Application) {
        boxStore = MyObjectBox.builder()
            .androidContext(application)
            .build()
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
