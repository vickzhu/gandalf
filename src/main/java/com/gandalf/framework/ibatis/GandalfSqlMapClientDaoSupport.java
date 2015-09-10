package com.gandalf.framework.ibatis;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

public class GandalfSqlMapClientDaoSupport extends DaoSupport {

    private GandalfSqlMapClientTemplate sqlMapClientTemplate = new GandalfSqlMapClientTemplate();

    private boolean                     externalTemplate     = false;

    /**
     * 构造函数
     */
    public GandalfSqlMapClientDaoSupport() {
        sqlMapClientTemplate = (GandalfSqlMapClientTemplate) new SqlMapClientInterceptor().proxy(new GandalfSqlMapClientTemplate());
    }

    /**
     * Set the JDBC DataSource to be used by this DAO. Not required: The SqlMapClient might carry a shared DataSource.
     * 
     * @see #setSqlMapClient
     */
    public final void setDataSource(DataSource dataSource) {
        this.sqlMapClientTemplate.setDataSource(dataSource);
    }

    /**
     * Return the JDBC DataSource used by this DAO.
     */
    public final DataSource getDataSource() {
        return (this.sqlMapClientTemplate != null ? this.sqlMapClientTemplate.getDataSource() : null);
    }

    /**
     * Set the iBATIS Database Layer SqlMapClient to work with. Either this or a "sqlMapClientTemplate" is required.
     * 
     * @see #setSqlMapClientTemplate
     */
    @Autowired
    public final void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
    }

    /**
     * Return the iBATIS Database Layer SqlMapClient that this template works with.
     */
    public final SqlMapClient getSqlMapClient() {
        return this.sqlMapClientTemplate.getSqlMapClient();
    }

    /**
     * Set the SqlMapClientTemplate for this DAO explicitly, as an alternative to specifying a SqlMapClient.
     * 
     * @see #setSqlMapClient
     */
    public final void setSqlMapClientTemplate(GandalfSqlMapClientTemplate sqlMapClientTemplate) {
        if (sqlMapClientTemplate == null) {
            throw new IllegalArgumentException("Cannot set sqlMapClientTemplate to null");
        }
        this.sqlMapClientTemplate = sqlMapClientTemplate;
        this.externalTemplate = true;
    }

    /**
     * Return the SqlMapClientTemplate for this DAO, pre-initialized with the SqlMapClient or set explicitly.
     */
    public final GandalfSqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }

    protected final void checkDaoConfig() {
        if (!this.externalTemplate) {
            this.sqlMapClientTemplate.afterPropertiesSet();
        }
    }
}
