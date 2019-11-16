package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

	List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);//Top6代表前六条,最新
	List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);//Top6代表前六条,推荐
}
