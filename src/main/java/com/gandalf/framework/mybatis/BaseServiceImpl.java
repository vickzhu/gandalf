package com.gandalf.framework.mybatis;

import java.util.List;

public abstract class BaseServiceImpl<M, E> implements BaseService<M, E> {

    protected abstract BaseMapper<M, E> getMapper();

    @Override
    public int countByExample(E example) {
        return getMapper().countByExample(example);
    }

    @Override
    public int deleteByExample(E example) {
        return getMapper().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long primaryKey) {
        return getMapper().deleteByPrimaryKey(primaryKey);
    }

    @Override
    public int insert(M record) {
        return getMapper().insert(record);
    }

    @Override
    public int insertSelective(M record) {
        return getMapper().insertSelective(record);
    }

    @Override
    public List<M> selectByExample(E example) {
        return getMapper().selectByExample(example);
    }

    @Override
    public List<M> selectByExampleWithBLOBs(E example) {
        return getMapper().selectByExampleWithBLOBs(example);
    }

    @Override
    public M selectByPrimaryKey(Long primaryKey) {
        return getMapper().selectByPrimaryKey(primaryKey);
    }

    @Override
    public int updateByExampleSelective(M record, E example) {
        return getMapper().updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(M record, E example) {
        return getMapper().updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(M record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(M record) {
        return getMapper().updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(M record) {
        return getMapper().updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByExampleWithBLOBs(M record, E example) {
        return getMapper().updateByExampleWithBLOBs(record, example);
    }

}
