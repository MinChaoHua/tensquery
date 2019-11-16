package com.tensquery.spit.controller;

import com.tensquery.spit.pojo.Spit;
import com.tensquery.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    SpitService spitService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("spitId") String id){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable("spitId") String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("spitId") String id){
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable("parentId") String id,@PathVariable("page") int page,@PathVariable("size") int size){
        Page<Spit> pageSpit = spitService.findByParentid(id, page, size);
        return new Result(true, StatusCode.OK,"查找成功",new PageResult<Spit>(pageSpit.getTotalElements(),pageSpit.getContent()));
    }

    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.GET)
    public Result thumbup(@PathVariable("spitId") String spitId){
        String userId = "222";
        //判断用户是否重复点赞
        if(redisTemplate.opsForValue().get("thumbup_"+userId)!=null){
            return new Result(false, StatusCode.REPERROR,"不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userId,new Date());
        return new Result(true, StatusCode.OK,"点赞成功");

    }

}
