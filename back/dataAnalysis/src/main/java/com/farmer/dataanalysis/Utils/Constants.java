package com.farmer.dataanalysis.Utils;

import java.util.ArrayList;
import java.util.List;

public interface Constants {
    //声明本项目分页的规则
    int PAGE_SIZE =2;

    String LeafVegetables[] = {
            "大白菜","甘蓝"
    };
    List<String[]>vegetables = new ArrayList<>(){
        {
            add(LeafVegetables);

        }
    };
    public static void main(String[] args) {
        System.out.println(vegetables.get(0)[0]);
    }
}

