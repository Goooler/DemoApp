package io.goooler.demoapp.model;

/**
 * 自定义的 EventBus 事件类型
 */

public class EventType {

    public int statusCode;
    public int messageCode;
    public Object message;

    /**
     * 自定义的 EventType 成员有三种
     *
     * @param statusCode  状态码，成功或失败
     * @param messageCode 接收消息的通信码
     * @param message     消息主体
     */
    public EventType(int statusCode, int messageCode, Object message) {
        this.statusCode = statusCode;
        this.messageCode = messageCode;
        this.message = message;
    }

    /**
     * 状态码有两种，成功和失败
     */
    public interface Status {
        int SUCCEED = 1;
        int FAILED = 0;
    }

    /**
     * 定义具体的事件
     */
    public interface Message {

    }

    /**
     * @return 比较对象和类的 statusCode 确认是否成功
     */
    public boolean isSuccessful() {
        return statusCode == Status.SUCCEED;
    }

    /**
     * @param messageCode 用于验证的 messageCode
     * @return 用于验证当前接收方和发送放是否为同一消息
     */
    public boolean isSameOne(int messageCode) {
        return this.messageCode == messageCode;
    }
}
