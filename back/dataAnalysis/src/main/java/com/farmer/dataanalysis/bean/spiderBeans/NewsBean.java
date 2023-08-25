package com.farmer.dataanalysis.bean.spiderBeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsBean {
    String code;
    String msg;
    NewsData data;
}
