package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;


/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = "select * from tb_problem ,tb_pl where id = problemid and labelid = ? order by replaytime desc " ,nativeQuery = true)
    Page<Problem> newList(String labelid, Pageable pageable);

    @Query(value = "select * from tb_problem ,tb_pl where id = problemid and labelid = ? order by replay desc " ,nativeQuery = true)
    Page<Problem> hotList(String labelid, Pageable pageable);


    @Query(value = "select * from tb_problem ,tb_pl where id = problemid and labelid = ? order by creattime desc " ,nativeQuery = true)
    Page<Problem> waitList(String labelid, Pageable pageable);


}
