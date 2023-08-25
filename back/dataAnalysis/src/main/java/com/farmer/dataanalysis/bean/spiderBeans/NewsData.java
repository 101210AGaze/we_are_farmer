package com.farmer.dataanalysis.bean.spiderBeans;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsData {
    List<NewsRecord> records;
    String author;
    String source;
    String title;
    String content;
    String publishDate;
    String total;
    @Override
    public String toString(){
        return getAuthor()+","+getPublishDate()+","+getSource()+","+getTitle()+","+getContent();
    }
}
