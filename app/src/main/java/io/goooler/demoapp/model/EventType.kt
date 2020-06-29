package io.goooler.demoapp.model

/**
 * 自定义的 EventBus 事件类型
 */

class EventType
/**
 * 自定义的 EventType 成员有三种
 *
 * @param statusCode  状态码，成功或失败
 * @param messageCode 接收消息的通信码
 * @param message     消息主体
 */
    (var statusCode: Int, var messageCode: Int, var message: Any) {
    /**
     * 状态码有两种，成功和失败
     */
    object Status {
        const val SUCCEED = 1
        const val FAILED = 0
    }

    /**
     * @return 比较对象和类的 statusCode 确认是否成功
     */
    val isSuccessful: Boolean
        get() = statusCode == Status.SUCCEED

    /**
     * 定义具体的事件
     */
    interface Message

    /**
     * @param messageCode 用于验证的 messageCode
     * @return 用于验证当前接收方和发送放是否为同一消息
     */
    fun isSameOne(messageCode: Int): Boolean {
        return this.messageCode == messageCode
    }
}
