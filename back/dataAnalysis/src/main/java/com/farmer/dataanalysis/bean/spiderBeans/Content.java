package com.farmer.dataanalysis.bean.spiderBeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content{
    private int total;
    private List<mList> list;

}