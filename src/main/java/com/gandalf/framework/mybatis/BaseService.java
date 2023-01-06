package com.gandalf.framework.mybatis;

import java.util.List;

import com.gandalf.framework.web.tool.Page;

public interface BaseService<M, E> {

    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Long primaryKey);

    int insert(M record);

    int insertSelective(M record);

    List<M> selectByExample(E example);

    List<M> selectByExampleWithBLOBs(E example);

    M selectByPrimaryKey(Long primaryKey);

    int updateByExampleSelective(M record, E example);

    int updateByExample(M record, E example);

    int updateByPrimaryKeySelective(M record);

    int updateByPrimaryKey(M record);

    int updateByPrimaryKeyWithBLOBs(M record);

    int updateByExampleWithBLOBs(M record, E example);
    
    /**
     * <pre>
     * 注意：example需要预先设置offset和rows两个参数，这两个参数可以从page中获取
     * example.setOffset(page.getOffset());
     * example.setRows(page.getPageSize());
     * </pre>
     * @param example
     * @param page
     */
    void selectByPagination(E example, Page<M> page);
    
}
