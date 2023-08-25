package com.farmer.dataanalysis.bean.spiderBeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author HWJ
 * @DESC 第一层数据
 * @since 2023/8/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class msgBean {
    private int code;
    private String message;
    private Content content;
}
