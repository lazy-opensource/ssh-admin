package com.lzy.jurisdcition.ssh.common.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

//@Transactional
@SuppressWarnings("rawtypes")
public interface BaseDao {
	
	String DEFAULTQUERYCACHEREGION = "defaultDefine";
	
	/**
	 * 通过Id查询
	 * @param c 类型
	 * @param id OID
	 * @return
	 */
	public <T> T get( Class c, Serializable id);
	/**
	 * 新增
	 * @param o 实体对象
	 * @return
	 */
	public Serializable add(Object o);
	/**
	 * 删除
	 * @param c 类型
	 * @param id OID
	 */
	public void delete(Class c, Serializable id);
	/**
	 * 删除
	 * @param o
	 */
	public void delete(Object o);
	
	/**
	 * 批量删除，对使用,分隔的列表执行删除
	 * @param c 类型ls 类型
	 * @param id OIDFieldName 删除条件字段
	 * @param values 语句参数值列表List 要删除的字段列表，使用,分隔
	 * @return
	 */
	 public Integer deleteByValues(Class cls, String idFieldName, String valuesList);
	/**
	 * 修改对象
	 * @param o 实体对象
	 */
	public void update(Object o);
	
	/**
	 * 查询所有
	 * @param c 类型ls
	 * @return
	 */
	public List findAll(Class cls);
	/**
	 * 根据HQL查询
	 * @param hql HQL语句 HQL
	 * @param values 语句参数值列表 参数列表
	 * @return
	 */
	public List find(String hql, Object... values);
	
	/**
	 * 按条件根据HQL查询
	 * @param hql HQL语句 HQL
	 * @param BaseCriteria BaseCriteria条件对象
	 * @return
	 */
	public List find(String hql,BaseCriteria baseCriteria);
	
	/**
	 * 简单查询，按照字段查询
	 * @param c 类型ls
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return
	 */
    public List findByProperty(Class cls, String propertyName, Object value);
    /**
	 * 按照字段查询，不区分大小写
	 * @param c 类型ls
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return
	 */
    public List findByPropertyIgnoreCase(Class cls, String propertyName, String value);
    /**
     * 执行命名查询
     * @param name queryName
     * @param values 语句参数值列表
     * @return
     */
    public List findNamedQuery(String name, Object... values) ;

	/**
	 * 分页查询
	 * @param pageBean
	 * @param values 语句参数值列表
	 */
	public void findByPage(PageBean pageBean,List values);
	
	/**
	 * 分页查询
	 * @param pageBean
	 * @param BaseCriteria 按条件分页查询方法
	 */
	public void findByPage(PageBean pageBean,BaseCriteria baseCriteria);
	
	/**
	 * 分页查询
	 * @param pageBean
	 * @param values 语句参数值列表
	 */
	public void findByPage(PageBean pageBean);
	
	/**
	 * 通过hql语句查询总条数
	 * @param hql HQL语句
	 * @param values 语句参数值列表
	 * @return 总条数
	 */
	public int findCount(String hql, Object... values);
	/**
	 * 通过hql语句查询总条数
	 * @param hql HQL语句
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return 总条数
	 */
	public int findCount(String hql, BaseCriteria baseCriteria);
	
	/**
	 * 查询数据条数，并根据每页条数计算总页数
	 * @param hql 查询条数的语句
	 * @param rowsPerPage 每页显示条数
	 * @param values 语句参数值列表
	 * @return 总页数
	 */
	public int findMaxPage(String hql, int rowsPerPage, Object... values);
	/**
	 * 查询数据条数，并根据每页条数计算总页数
	 * @param hql 查询条数的语句
	 * @param rowsPerPage 每页显示条数
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return 总页数
	 */
	public int findMaxPage(String hql, int rowsPerPage, BaseCriteria baseCriteria);
	
	/**
	 * 查询单个（单行单列）数据，如最大值，最小值等等
	 * @param hql 单个数据查询语句
	 * @param values 语句占位符对应的值
	 * @return 查询到的单个值
	 */
	public <T> T findVal(String hql, Object... values);
	/**
	 * 查询单个（单行单列）数据，如最大值，最小值等等
	 * @param hql 单个数据查询语句
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return 查询到的单个值
	 */
	public <T> T findVal(String hql,  BaseCriteria baseCriteria);
	
	/**
	 * 查询前topCount条数据
	 * @param hql HQL语句
	 * @param topCount
	 * @param values 语句参数值列表
	 * @return
	 */
	public List findTop(String hql,int topCount, Object... values);  
	/**
	 * 查询前topCount条数据
	 * @param hql HQL语句
	 * @param topCount
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return
	 */
	public List findTop(String hql,int topCount,  BaseCriteria bseCriteria);  
	
	/**
	 * 带查询缓存查询，
	 * 查询缓存，使用DEFAULTQUERYCACHEREGION指定区域的缓存，如DEFAULTQUERYCACHEREGION==null或=="",则使用默认标准区域缓存策略
	 * @param hql HQL语句
	 * @param values 语句参数值列表
	 * @return
	 */
	public List findByCache(String hql, Object... values);
	
	/**
	 * 查询缓存,需要指定具体的缓存策略区域
	 * @param hql HQL语句
	 * @param queryCacheRegion 指定查询缓存区域
	 * @param values 语句参数值列表
	 * @return
	 */
	public List findByCache(String hql, String queryCacheRegion, Object... values);
	
	/**
	 * 带查询缓存查询，
	 * 查询缓存，使用DEFAULTQUERYCACHEREGION指定区域的缓存，如DEFAULTQUERYCACHEREGION==null或=="",则使用默认标准区域缓存策略
	 * @param hql HQL语句
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return
	 */
	public List findByCache(String hql, BaseCriteria baseCriteria);
	
	/**
	 * 查询缓存,需要指定具体的缓存策略区域
	 * @param hql HQL语句
	 * @param queryCacheRegion 指定查询缓存区域
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return
	 */
	public List findByCache(String hql, String queryCacheRegion, BaseCriteria baseCriteria);

	
	/**
	 * 通过hql语句进行批量修改（更新，删除）
	 * @param hql HQL语句
	 * @param values 语句参数值列表
	 * @return
	 */
	public int updateByHql(String hql,Object... values);
	
	
	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param values 语句参数值列表
	 * @return 查询的结果，多列时转换为Object[]
	 */
	public List findBySQL(String sql,Object... values);  
	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return 查询的结果，多列时转换为Object[]
	 */
	public List findBySQL(String sql,BaseCriteria baseCriteria);  
	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param transformers Hibernate结果转换器，支持Transformers.ALIAS_TO_ENTITY_MAP和Transformers.TO_LIST
	 * @param values 语句参数值列表
	 * @return 查询的结果封装为transformers指定的类型
	 */
	public List findBySQL(String sql,ResultTransformer transformers, Object... values);  
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param transformers Hibernate结果转换器，支持Transformers.ALIAS_TO_ENTITY_MAP和Transformers.TO_LIST
	 * @param BaseCriteria BaseCriteria条件对象 
	 * @return 查询的结果封装为transformers指定的类型
	 */
	public List findBySQL(String sql,ResultTransformer transformers,BaseCriteria baseCriteria);  
	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param c 类型 封装的类型
	 * @param values 语句参数值列表
	 * @return 查询的结果封装为c指定的实体
	 */
	public List findBySQL(String sql, Class c,Object... values);  
	
	/**
	 * 使用JDBC执行原生的增删改SQL语句
	 * @param sql SQL语句
	 * @param values 语句参数值列表
	 */
	public void updateBySQL(String sql,Object...values); 
	/**
	 * 使用JDBC执行原生的批量SQL，多参数语句值列表
	 * @param sql SQL语句
	 * @param values 多参数语句值列表
	 */
	public void batchUpdateSQL(final String sql, final Object[][] values);
	
	/**
	 * 使用JDBC执行原生的批量SQL，单参数语句值列表
	 * @param sql SQL语句
	 * @param values 单参数语句值列表
	 */
	public void batchUpdateSQL(final String sql, final Object[] values);
	
	/**
	 * 自定义Hibernate回调函数，无需关闭Session
	 * @param hibernateCallback
	 */
	public void execute(HibernateCallback hibernateCallback);
	
	
	/**
	 * 获取sessionFactory，主要用来手动清除缓存
	 * @return
	 */
	public SessionFactory getSessionFactory(); 
	/**
	 * 获取Session
	 * @return
	 */
	public Session getCurrentSession();
	
	/**
	 * 初始化关联的对象
	 * @param proxy
	 */
	public void initialize(Object proxy) ;

	/**
	 * 将集合中的对象深度初始化
	 * @param collection
	 */
	public void initializeDeep(Collection collection);
	
	/**
	 * 清除默认区域的查询缓存策略，如果默认为空，则清除所有标准区域查询缓存策略
	 */
	public void evictQuery();
	/**
	 * 清除指定区域策略的查询缓存
	 * @param queryCacheRegion 指定查询缓存区域
	 */
	public void evictQuery(String queryCacheRegion);
	
	/**
	 * 清除二级缓存中所有的c对象
	 * @param c 类型
	 */
	public void evictEntity(Class c);
	/**
	 * 清除二级缓存中OID为id的c对象
	 * @param c 类型
	 * @param id OID
	 */
	public void evictEntity(Class c,Serializable id);
	/**
	 * 清除二级缓存中指定类的集合属性
	 * @param collectionRegion 查询缓存区域
	 */
	public void evictCollectionRegion(String collectionRegion);
	/**
	 * 清除二级缓存中指定id类的集合属性
	 * @param collectionRegion 查询缓存区域
	 * @param id OID
	 */
	public void evictCollectionRegion(String collectionRegion,Serializable id);
	
}
