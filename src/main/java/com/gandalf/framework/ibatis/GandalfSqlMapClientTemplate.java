package com.gandalf.framework.ibatis;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class GandalfSqlMapClientTemplate extends SqlMapClientTemplate {

    /**
     * 分页查询
     * 
     * @param countStatementName 统计记录总数的StatementName
     * @param queryStatementName 查询记录的StatementName
     * @param query 查询条件（需要设置currentPage和PageSize值，或是startRow和endRow值）
     * @return
     */
    public PaginationQueryList queryForPagination(String countStatementName, String queryStatementName, BaseQuery query) {
        PaginationQueryList paginationQueryList = new PaginationQueryList(query);

        int totalCount = ((Integer) queryForObject(countStatementName, query)).intValue();
        paginationQueryList.setTotalItem(totalCount);

        if (totalCount > 0) {
            List items = queryForList(queryStatementName, query);

            if (items != null) {
                paginationQueryList.addAll(items);
            }
        }

        return paginationQueryList;
    }

    /**
     * 批量插入
     * 
     * @param statementName
     * @param parameterObjects
     * @return
     * @throws DataAccessException
     */
    public Integer batchInsert(final String statementName, final Collection<? extends Object> parameterObjects)
                                                                                                               throws DataAccessException {
        return batchAction(statementName, parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                         throws SQLException {
                executor.insert(statementName, parameterObject);
            }
        });
    }

    /**
     * 批量更新
     * 
     * @param statementName
     * @param parameterObjects
     * @return 成功执行的数量
     * @throws DataAccessException
     */
    public Integer batchUpdate(final String statementName, final Collection<? extends Object> parameterObjects)
                                                                                                               throws DataAccessException {
        return batchAction(statementName, parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                         throws SQLException {
                executor.update(statementName, parameterObject);
            }
        });
    }

    /**
     * 批量删除
     * 
     * @param statementName
     * @param parameterObjects
     * @return 成功执行的数量
     * @throws DataAccessException
     */
    public Integer batchDelete(final String statementName, final Collection<? extends Object> parameterObjects)
                                                                                                               throws DataAccessException {
        return batchAction(statementName, parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                         throws SQLException {
                executor.delete(statementName, parameterObject);
            }
        });
    }

    /**
     * 可以批量指定的动作
     * 
     * @param statementName
     * @param parameterObjects
     * @return 成功执行的数量
     * @throws DataAccessException
     */
    private Integer batchAction(final String statementName, final Collection<? extends Object> parameterObjects,
                                final BatchAction batchAction) throws DataAccessException {
        Object ret = execute(new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                if (parameterObjects == null || parameterObjects.isEmpty()) {
                    return new Integer(0);
                } else {
                    executor.startBatch();
                    for (Object parameterObject : parameterObjects) {
                        batchAction.doAction(executor, statementName, parameterObject);
                    }
                    return new Integer(executor.executeBatch());
                }
            }
        });

        //
        if (ret instanceof Integer) {
            return (Integer) ret;
        } else {
            return new Integer(0);
        }
    }

    @Override
    public void delete(String statementName, Object parameterObject, int requiredRowsAffected)
                                                                                              throws DataAccessException {
        super.delete(statementName, parameterObject, requiredRowsAffected);
    }

    @Override
    public int delete(String statementName, Object parameterObject) throws DataAccessException {
        return super.delete(statementName, parameterObject);
    }

    @Override
    public int delete(String statementName) throws DataAccessException {
        return super.delete(statementName);
    }

    @Override
    public Object insert(String statementName, Object parameterObject) throws DataAccessException {
        return super.insert(statementName, parameterObject);
    }

    @Override
    public Object insert(String statementName) throws DataAccessException {
        return super.insert(statementName);
    }

    @Override
    public void update(String statementName, Object parameterObject, int requiredRowsAffected)
                                                                                              throws DataAccessException {
        super.update(statementName, parameterObject, requiredRowsAffected);
    }

    @Override
    public int update(String statementName, Object parameterObject) throws DataAccessException {
        return super.update(statementName, parameterObject);
    }

    @Override
    public int update(String statementName) throws DataAccessException {
        return super.update(statementName);
    }
}

/**
 * 可以批量执行的动作接口
 * 
 * @author bob.panl
 */
interface BatchAction {

    public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                 throws SQLException;
}
