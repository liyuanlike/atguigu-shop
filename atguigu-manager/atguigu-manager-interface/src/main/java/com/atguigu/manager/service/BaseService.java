package com.atguigu.manager.service;

import com.github.abel533.mapper.MapperProvider;

import java.util.List;

public interface BaseService<T> {

    T selectOne(T record);

    List<T> select(T record);

    int selectCount(T record);

    T selectByPrimaryKey(Object key);

    int insert(T record);

    int insertSelective(T record);

    int delete(T record);

    int deleteByPrimaryKey(Object key);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    List<T> selectByPage(int pageNum, int pageSize, T record);

    int deleteByPrimaryKeys(List<Object> keys);

    /*int selectCountByExample(Object example);

    int deleteByExample(Object example);

    List<T> selectByExample(Object example);

    int updateByExampleSelective(T record, Object example);

    int updateByExample(T record, Object example);*/

}
