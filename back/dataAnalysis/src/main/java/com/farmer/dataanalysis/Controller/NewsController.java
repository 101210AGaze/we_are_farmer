package com.farmer.dataanalysis.Controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.farmer.dataanalysis.Pojo.News;

import java.util.List;

@RestController
@CrossOrigin
public class NewsController {
    @RequestMapping("/newsInformation")
    public List<News> getNewsInformation(){

        return null;
    }
    @RequestMapping("/detailedNews")
    public List<News> getDetailedNews(){

        return null;
    }

}
