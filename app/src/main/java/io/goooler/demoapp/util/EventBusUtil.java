package io.goooler.demoapp.util;

import org.greenrobot.eventbus.EventBus;

import io.goooler.demoapp.model.EventType;

/**
 * EventBus 的简单封装，加入统一的判空等操作
 */

public class EventBusUtil {

    /**
     * 注册 EventBus 之前先判断是否注册
     *
     * @param subscriber 要注册的页面
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 注销 EventBus 之前先判断是否注册
     *
     * @param subscriber 要注销的页面
     */
    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    /**
     * 传递消息
     *
     * @param eventType 消息类型为自定义的 EventType
     */
    public static void post(EventType eventType) {
        EventBus.getDefault().post(eventType);
    }
}