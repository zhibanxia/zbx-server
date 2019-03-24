package cn.zhibanxia.zbxserver.dao;

import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;

public abstract class BaseDao {

    @Resource(name = "zhibanxiaSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 获取合适的sqlSessionTemplate
     *
     * @return
     */
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    /**
     * @param statement
     * @return
     */
    public int insert(String statement) {
        return getSqlSessionTemplate().insert(addNameSpace(statement));
    }

    /**
     * @param statement
     * @return
     */
    public int update(String statement) {
        return getSqlSessionTemplate().update(addNameSpace(statement));
    }

    /**
     * @param statement
     * @param parameter
     * @return
     */
    public <T> T selectOne(String statement, Object parameter) {
        return getSqlSessionTemplate().selectOne(addNameSpace(statement), parameter);
    }

    /**
     * @param statement
     * @return
     */
    public <T> T selectOne(String statement) {
        return getSqlSessionTemplate().selectOne(addNameSpace(statement));
    }


    /**
     * @param statement
     * @param parameter
     * @return
     */
    public int insert(String statement, Object parameter) {
        return getSqlSessionTemplate().insert(addNameSpace(statement), parameter);
    }

    /**
     * @param statement
     * @param parameter
     * @return
     */
    public int update(String statement, Object parameter) {
        return getSqlSessionTemplate().update(addNameSpace(statement), parameter);
    }

    /**
     * @param statement
     * @param parameter
     * @return
     */
    public <E> List<E> selectList(String statement, Object parameter) {
        return getSqlSessionTemplate().selectList(addNameSpace(statement), parameter);
    }

    /**
     * 获取mybatis命名空间
     *
     * @param method
     * @return
     */
    protected String addNameSpace(String method) {
        return getClass().getName() + "." + method;
    }

}
