package com.tensquery.search.controller;

import com.tensquery.search.pojo.Article;
import com.tensquery.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> pageList = articleService.findByKey(key,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(pageList.getTotalElements(),pageList.getContent()));
    }
}
