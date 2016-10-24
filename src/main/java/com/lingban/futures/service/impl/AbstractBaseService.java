package com.lingban.futures.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

@Service
public abstract class AbstractBaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    public int save(T entity){
        return mapper.insert(entity);
    }

    public int delete(T entity){
        return mapper.deleteByPrimaryKey(entity);
    }
    public List<T> select(T entity){
        return mapper.select(entity);
    }
    public List<T> selectAll(){
    	return mapper.selectAll();
    }

//    /**
//     * 单表分页查询
//     * 
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    public List<T> selectPage(int pageNum,int pageSize){
//        PageHelper.startPage(pageNum, pageSize);
//        return mapper.selectAll();
//    }
//    
//    public List<T> selectPage(int pageNum,int pageSize, Example example){
//    	PageHelper.startPage(pageNum, pageSize);
//    	return mapper.selectByExample(example);
//    }
}