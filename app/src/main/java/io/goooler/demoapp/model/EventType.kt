package io.goooler.demoapp.model

/**
 * 自定义的 EventBus 事件类型
 * @param messageCode 接收消息的通信码
 * @param message     消息主体
 */
class EventType(val messageCode: Int, val message: Any) {

    /**
     * @param messageCode 用于验证的 messageCode
     * @return 用于验证当前接收方和发送放是否为同一消息
     */
    fun isSameOne(messageCode: Int): Boolean {
        return this.messageCode == messageCode
    }
}
