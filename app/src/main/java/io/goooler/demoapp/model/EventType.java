package io.goooler.demoapp.model;

/**
 * 自定义的 EventBus 事件类型
 */

public class EventType {

    /**
     * 状态码有两种，成功和失败
     */
    public static final int SUCCEED = 1;
    public static final int FAILED = 0;

    /**
     * 具体的一些 messageCode 如下
     */

    // Service 回调给 MainOverallFragment 的数据，用于刷新数据总览
    public static final int SERVICE_TO_OVERALL = 100;
    // MainOverallFragment 下拉刷新触发请求，交给 Service 处理
    public static final int OVERALL_TO_SERVICE = 101;
    //从首页总览数据跳转到详情页面查看统计图
    public static final int OVERALL_TO_DETAIL = 102;
    //从首页总览数据跳转到 MainActivity
    public static final int OVERALL_TO_MAIN = 103;

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
     * @return 比较对象和类的 statusCode 确认是否成功
     */
    public boolean isSuccessful() {
        return statusCode == SUCCEED;
    }

    /**
     * @param messageCode 用于验证的 messageCode
     * @return 用于验证当前接收方和发送放是否为同一消息
     */
    public boolean isSameOne(int messageCode) {
        return this.messageCode == messageCode;
    }
}
