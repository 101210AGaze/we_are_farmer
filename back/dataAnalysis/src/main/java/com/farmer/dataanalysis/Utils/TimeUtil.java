package com.farmer.dataanalysis.Utils;

import org.apache.commons.lang3.time.FastDateFormat;

public abstract class TimeUtil {
    /**
     * 时间工具类
     * @param timestamp，时间戳
     * @param pattern，展示格式
     * @return 按提供的展示格式返回字符串类型的时间
     */
    public static String format(Long timestamp,String pattern){
        return FastDateFormat.getInstance(pattern).format(timestamp);
    }
}
