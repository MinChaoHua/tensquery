package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    LabelDao labelDao;

    @Autowired
    IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(String.valueOf(idWorker.nextId()));
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(specification(label));
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
         Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(specification(label),pageable);
    }

    public  Specification specification(Label label){
        return (Specification<Label>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                Predicate predicate = criteriaBuilder.like(root.get("criteriaBuilder").as(String.class),"%"+label.getLabelname()+"%");
                list.add(predicate);
            }
            if(label.getState()!=null && !"".equals(label.getState())){
                Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class),label.getState());
                list.add(predicate);
            }
            Predicate[] predicate = new Predicate[list.size()];
            list.toArray(predicate);
             return criteriaBuilder.and(predicate);
            };
    }
}
