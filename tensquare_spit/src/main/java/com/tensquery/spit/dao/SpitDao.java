package com.tensquery.spit.dao;

import com.tensquery.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitDao extends MongoRepository<Spit,String> {
    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
