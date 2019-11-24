package com.tensquery.search.service;

import com.tensquery.search.dao.ArticleDao;
import com.tensquery.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

   // @Autowired
    //private IdWorker idWorker;

    public void save(Article article){
        //article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }
}
