package com.tensquery.search.dao;

import com.tensquery.search.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article,String> {
}
