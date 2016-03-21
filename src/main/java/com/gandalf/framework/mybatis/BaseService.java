package com.gandalf.framework.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseService<M,E> {
	
	int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Long primaryKey);

    int insert(M record);

    int insertSelective(M record);

    List<M> selectByExample(E example);
    
    List<M> selectByExampleWithBLOBs(E example);
    
    M selectByPrimaryKey(Long primaryKey);

    int updateByExampleSelective(@Param("record") M record, @Param("example") E example);

    int updateByExample(@Param("record") M record, @Param("example") E example);

    int updateByPrimaryKeySelective(M record);

    int updateByPrimaryKey(M record);
    
    
}
