package com.lingban.futures.service;

import java.util.List;


public interface BaseService<T> {

    public int save(T entity);
    public int delete(T entity);
    public List<T> select(T entity);
    public List<T> selectAll();
    public List<T> selectPage(int pageNum,int pageSize);
}