package com.farmer.dataanalysis.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class mList {
    private String marketName;
    private String provinceName;
    private String areaName;
    private String minimumPrice;
    private String middlePrice;
    private String highestPrice;
    private String varietyId;
    private String varietyName;
}


