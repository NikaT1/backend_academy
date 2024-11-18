package backend.academy.stat;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StatisticNames {

    // common statistic
    public static final String COMMON_STATISTIC_NAME = "Общая информация";
    public static final String METRIC_HEADER = "Метрика";
    public static final String VALUE_HEADER = "Значение";
    public static final String START_DATE_PARAM = "Начальная дата";
    public static final String FINISH_DATE_PARAM = "Конечная дата";
    public static final String COUNT_OF_REQUEST_METRIC = "Количество запросов";
    public static final String RESPONSE_AVG_METRIC = "Средний размер ответа";
    public static final String RESPONSE_95P_METRIC = "95p размера ответа";
    public static final String VALUE_PREFIX = "b";

    // resource statistic
    public static final String RESOURCE_STATISTIC_NAME = "Запрашиваемые ресурсы";
    public static final String RESOURCE_HEADER = "Ресурс";

    // http codes statistic
    public static final String CODE_STATISTIC_NAME = "Коды ответа";
    public static final String CODE_HEADER = "Код";

    // http codes statistic
    public static final String USER_AGENT_STATISTIC_NAME = "User agent";
    public static final String USER_AGENT_HEADER = "Agent";

    public static final String COUNT_HEADER = "Количество";
}
