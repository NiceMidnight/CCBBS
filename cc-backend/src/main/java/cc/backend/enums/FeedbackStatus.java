package cc.backend.enums;

/**
 * @Description 反馈信息的状态
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
public enum FeedbackStatus {
    /**
     * 待处理
     */
    Pending,
    /**
     * 处理中
     */
    InProgress,
    /**
     * 已处理，还需要跟进观察
     */
    Processed,
    /**
     * 已关闭，无需跟进
     */
    Closed
}
