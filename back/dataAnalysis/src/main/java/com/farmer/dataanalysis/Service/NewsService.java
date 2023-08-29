package com.farmer.dataanalysis.Service;


import com.farmer.dataanalysis.Pojo.News;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    public List<News> getNewsInformation();
    public List<News> getDetailedNews();
}
