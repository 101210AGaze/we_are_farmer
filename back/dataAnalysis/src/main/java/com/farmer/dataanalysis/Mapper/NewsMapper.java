package com.farmer.dataanalysis.Mapper;


import com.farmer.dataanalysis.Pojo.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public class NewsMapper {
    @Select("")
    public List<News> getNewsInformation(){
        return null;
    }

    @Select("")
    public News getDetailedNews(){
        return null;
    }
}
