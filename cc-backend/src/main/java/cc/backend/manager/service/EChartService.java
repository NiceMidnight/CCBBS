package cc.backend.manager.service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/16
 */
public interface EChartService {
    Long getCountOfPostForTFP(int topicId);
    Long getCountOfUserForDict(int dictTypeId);
    Long getCountOfJobForTFJ(int topicId);
    Long getCountOfArticleForTFA(int topicId);
    Long getCountOfTodayDailyUniqueVisitorsLog();
    Long getCountOfYesterdayDailyUniqueVisitorsLog();
    Long getCountOfCurrentMonthDailyUniqueVisitorsLog();
    Long getCountOfLastMonthDailyUniqueVisitorsLog();
    Long getCountOfCurrentMonthArticle();
    Long getCountOfLathMonthArticle();
    Long getCountOfCurrentWeekPost();
    Long getCountOfLastWeekPost();
    Long getCountOfCurrentMonthJob();
    Long getCountOfLastMonthJob();
    Long getCountOfCurrentWeekFeedback();
    Long getCountOfLastWeekFeedback();
}
