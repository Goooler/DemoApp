@file:Suppress("unused")

package io.goooler.demoapp.common.util

import io.goooler.demoapp.common.type.EventType
import org.greenrobot.eventbus.EventBus

/**
 * EventBus 的简单封装，加入统一的判空等操作
 */

object EventBusUtil {

    /**
     * 注册 EventBus 之前先判断是否注册
     *
     * @param subscriber 要注册的页面
     */
    fun register(subscriber: Any) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber)
        }
    }

    /**
     * 注销 EventBus 之前先判断是否注册
     *
     * @param subscriber 要注销的页面
     */
    fun unregister(subscriber: Any) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

    /**
     * 传递消息
     *
     * @param eventType 消息类型为自定义的 EventType
     */
    fun post(eventType: EventType) {
        EventBus.getDefault().post(eventType)
    }
}