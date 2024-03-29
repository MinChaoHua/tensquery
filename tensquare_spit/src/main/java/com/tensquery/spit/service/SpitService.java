package com.tensquery.spit.service;

import com.tensquery.spit.dao.SpitDao;
import com.tensquery.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpitService {
    @Autowired
    SpitDao spitDao;
    @Autowired
    IdWorker idWorker;

    @Autowired
    MongoTemplate mongoTemplate;

    public void thumbup(String spitId) {
        // 1) 效率问题
        // Spit spit = spitDao.findById(spitId).get();
        // spit.setThumbup((spit.getThumbup()==null?0:spit.getThumbup())+1);
        // spitDao.save(spit);
        // 2) 使用原生的mongodb命令
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setThumbup(0);
        spit.setPublishtime(new Date());
        spit.setComment(0);//回复数
        spit.setShare(0);
        spit.setState("1");
        spit.setVisits(0);
        //如果当前的添加吐槽有父节点，则父节点的吐槽回复数要加1
        if(spit.getParentid()!=null && "".equals(spit.getParentid())){
            Query query = new Query();
            Update update  = new Update();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }
    public void update(Spit spit){
        spitDao.save(spit);
    }
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentid,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentid,pageable);
    }
}
