package com.farmer.dataanalysis.bean.spiderBeans;
import lombok.*;


@Getter
@Setter
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


@Override
    public String toString(){
    return getMarketName()+","+getProvinceName()+","+getAreaName()+","+getMinimumPrice()+","+getMiddlePrice()
            +","+getHighestPrice()+","+getVarietyId()+","+getVarietyName();
}
}


