package com.lzy.jurisdcition.ssh.common.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.lzy.jurisdcition.ssh.common.base.dao.BaseDao;
import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

@Repository("baseDao")
@SuppressWarnings({"rawtypes" , "unchecked"})
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	private Logger logs = Logger.getLogger(BaseDaoImpl.class);
	
	@Resource(name = "sessionFactory")
	private SessionFactory mySessionFacotry;
	@PostConstruct
	public void injectSessionFactory() {
		//mySessionFacotry.getCurrentSession().setFlushMode(FlushMode.COMMIT);;
		super.setSessionFactory(mySessionFacotry);
	}

	public <T> T get( Class c, Serializable id) {
		logs.info("开始调用get");
		return (T)getHibernateTemplate().get(c, id);
	}

	public Serializable add(Object o) {
		try{
            getCurrentSession().setFlushMode(FlushMode.AUTO);
            Serializable row = getHibernateTemplate().save(o);
            getCurrentSession().flush();
            //getCurrentSession().close();
            return row;
		}catch(RuntimeException e){
			logs.info("调用    save  出错了");
			throw e;
		}
	}

	public void delete(Class c, Serializable id) {
		getHibernateTemplate().delete(getHibernateTemplate().get(c, id));

	}

	public void delete(Object o) {
		getHibernateTemplate().delete(o);

	}

	public Integer deleteByValues(Class cls, String idFieldName, String valuesList) {
		try{
			valuesList = valuesList==null ? "" : valuesList.trim();
			if("".equals(valuesList)){
				return 0;
			}else{
				valuesList = "'"+valuesList.replaceAll(",", "','")+"'";
			}
			StringBuffer sb = new StringBuffer("delete from ")
					     .append(cls.getSimpleName()).append(" where ")
					     .append(idFieldName).append(" in(").append(valuesList).append(")");
			
			final String hql = sb.toString();
			return (Integer)getHibernateTemplate().execute(
					new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException {
					/*
					 * 示例delete from User where id in('1,2,3'); 
					 */
					return session.createQuery(hql).executeUpdate();
				}
				
			});
		}catch(RuntimeException e){
			throw e;
		}
	}

	public void update(Object o) {
        getCurrentSession().setFlushMode(FlushMode.COMMIT);
        getHibernateTemplate().update(o);
        getCurrentSession().flush();
        //getCurrentSession().close();
        //getCurrentSession().clear();
	}

	public List findAll(Class cls) {
		try{
			String hql = "from "+cls.getSimpleName();
			List list = getHibernateTemplate().find(hql);
			initialize(list);//强制加载
			return list;
		}catch(RuntimeException e){
			throw e;
		}
	}

	public List find(String hql, Object... values) {
		/**
		 * 示例：String hql= "from bean.User u where u.name=? and u.password=?"

               this.getHibernateTemplate().find(hql, new String[]{"test", "123"});
		 */
		return getHibernateTemplate().find(hql, values);
	}

	public List find(String hql, BaseCriteria baseCriteria) {
		
		if(baseCriteria !=null){
			return getHibernateTemplate().find(hql+" "+baseCriteria.getCondition(), baseCriteria.getValues());
		}else{
			return getHibernateTemplate().find(hql);
		}
	}

	public List findByProperty(Class cls, String propertyName, Object value) {
		try{
			StringBuffer sb = new StringBuffer("from ")
					.append(cls.getSimpleName()+" as model where model.")
					.append(propertyName).append("= ?");
			/**
			 * 示例：this.getHibernateTemplate().find("from bean.User u where u.name=?", "test");
			 */
			return getHibernateTemplate().find(sb.toString(), value);
		}catch(RuntimeException e){
			throw e;
		}
	}

	public List findByPropertyIgnoreCase(Class cls, String propertyName, String value) {
		try{
			StringBuffer sb = new StringBuffer("from ")
					/**
					 * lower 忽略大小写
					 */
					.append(cls.getSimpleName()).append(" as model where lower(")
					.append(propertyName).append(")= ?");
			
			return getHibernateTemplate().find(sb.toString(), value.toLowerCase());
		}catch(RuntimeException e){
			throw e;
		}
	}

	public List findNamedQuery(final String name, final Object... values) {
		
		return super.getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session)
					throws HibernateException {
				List list = null;
				if(name == null || "".equals(name)){
					return list;
				}
				Query query = session.getNamedQuery(name.trim());
				if(values != null && values.length > 0){
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				
				return query.list();
			}
			
		});
	}

	public void findByPage(final PageBean pageBean, final List values) {
		final String hql = pageBean.getHql() != null ? pageBean.getHql() : pageBean.getAutoHql();

		super.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				/**
				 * hibernate 分页
				 */
				query.setFirstResult((pageBean.getPageNo()-1)*(pageBean.getRowsPerPage()));
		        query.setMaxResults(pageBean.getRowsPerPage());
		        if (values != null && values.size() > 0) {
					for (int i = 0; i < values.size(); i++) {
						query.setParameter(i, values.get(i));
					}
				}
		        
		        List list = query.list();
		        pageBean.setData(list);
		        String queryString = "";
		        int end = hql.length();
		        if(hql.indexOf("order by") != -1){
		        	end = hql.indexOf("order by");
		        }
		        if(hql.toUpperCase().indexOf("SELECT") != -1){
		        	int start = hql.toUpperCase().indexOf("FROM");
		        	queryString = "select count(*) "+hql.substring(start,end);
		        }else{
		        	queryString = "select count(*) "+hql.substring(0,end);
		        }
		        int deleteOrderBy = queryString.toUpperCase().indexOf("ORDER");
		        if(deleteOrderBy != -1){
		        	queryString = queryString.substring(0,deleteOrderBy);
		        }
		        //创建查询总行数
		        Query qry = session.createQuery(queryString);
		        if (values != null) {
					for (int i = 0; i < values.size(); i++) {
						qry.setParameter(i, values.get(i));
					}
				}
		        int maxRow = (Integer.valueOf(qry.iterate().next()
						.toString())).intValue();
				pageBean.setRowsCount(maxRow);
				
				return pageBean;
			}
			
		});
	}

	public void findByPage(PageBean pageBean, BaseCriteria baseCriteria) {
		if(baseCriteria != null){
			pageBean.setCondition(baseCriteria.getCondition());
			this.findByPage(pageBean,baseCriteria.getValues());
		}else{
			this.findByPage(pageBean);
		}

	}

	public void findByPage(PageBean pageBean) {
		this.findByPage(pageBean,new ArrayList());

	}

	public int findCount(String hql, Object... values) {
		/**
		 * select count(*) from User u where u.name=? and u.pw = ?
		 */
		return Integer.valueOf(this.getHibernateTemplate().find(hql, values)
				.get(0).toString());
	}

	public int findCount(String hql, BaseCriteria baseCriteria) {
		
		if(baseCriteria != null){
			return Integer.valueOf(this.getHibernateTemplate().find(hql+" "+
					baseCriteria.getCondition(), baseCriteria.getValues()).get(0).toString());
		}
		return Integer.valueOf(this.getHibernateTemplate().find(hql).get(0).toString());
	}

	public int findMaxPage(String hql, int rowsPerPage, Object... values) {
		
		return (this.findCount(hql, values) - 1) / rowsPerPage + 1;
	}

	public int findMaxPage(String hql, int rowsPerPage, BaseCriteria baseCriteria) {
		
		//总行数-1   /  每页大小 + 1   =   最大页数
		return (this.findCount(hql, baseCriteria) - 1) / rowsPerPage + 1;
	}

	public <T> T findVal(String hql, Object... values) {
		
		List list = this.find(hql, values);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public <T> T findVal(String hql, BaseCriteria baseCriteria) {
		
		List list = this.find(hql, baseCriteria);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public List findTop(final String hql, final int topCount, final Object... values) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setFetchSize(0);
				query.setMaxResults(topCount);
				if(values != null && values.length > 0){
					for (int i = 0 ; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.list();
			}
			
		});
	}

	public List findTop(final String hql,final int topCount,final BaseCriteria baseCriteria) {
		
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

			public List doInHibernate(Session session) throws HibernateException {
				String hql2 = hql;
				if(baseCriteria != null){
					hql2 = hql+" "+baseCriteria.getCondition();
				}
				Query q = session.createQuery(hql2);
				q.setFirstResult(0);
				q.setMaxResults(topCount);
				if(baseCriteria.getValues() != null){
				    Object[] o = baseCriteria.getValues().toArray();
				    if(o != null && o.length > 0){
				    	for(int i=0;i<o.length;i++){
				    		q.setParameter(i, o[i]);
				    	}
				    }
				}
				return q.list();
			}
			
		});
	}

	public List findByCache(String hql, Object... values) {
		
		//设置二级缓存
	    getHibernateTemplate().setCacheQueries(true);
		if(null != DEFAULTQUERYCACHEREGION || (!"".equals(DEFAULTQUERYCACHEREGION))){
			getHibernateTemplate().setQueryCacheRegion(DEFAULTQUERYCACHEREGION);
		}
		
		List list = getHibernateTemplate().find(hql, values);
		getHibernateTemplate().setCacheQueries(false);//用完立即释放
		return list;
	}

	public List findByCache(String hql, String queryCacheRegion, Object... values) {
		getHibernateTemplate().setCacheQueries(true); // 开启
		getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
		List list = super.getHibernateTemplate().find(hql, values);
		getHibernateTemplate().setCacheQueries(false);// 用完后立即关闭，防止其他查询被缓存
		return list;
	}

	public List findByCache(String hql, BaseCriteria baseCriteria) {
		getHibernateTemplate().setCacheQueries(true);
		if(null != DEFAULTQUERYCACHEREGION || (!"".equals(DEFAULTQUERYCACHEREGION))){
			getHibernateTemplate().setQueryCacheRegion(DEFAULTQUERYCACHEREGION);
		}
		List list = null;
		if(baseCriteria != null){
			if(baseCriteria.getValues() != null){
				list = getHibernateTemplate().find(hql+" "+baseCriteria.getCondition(), baseCriteria.getValues());
				getHibernateTemplate().setCacheQueries(false);
				return list;
			}
			list = getHibernateTemplate().find(hql+" "+baseCriteria.getCondition());
			getHibernateTemplate().setCacheQueries(false);
			return list;
		}
		list = getHibernateTemplate().find(hql);
		return list;
	}

	public List findByCache(String hql, String queryCacheRegion, BaseCriteria baseCriteria) {
		getHibernateTemplate().setCacheQueries(true); // 开启
		getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
		List ret=new ArrayList();
		if(baseCriteria!=null){
			ret = super.getHibernateTemplate().find(hql+" "+baseCriteria.getCondition(), baseCriteria.getValues());
		}else{
			ret = super.getHibernateTemplate().find(hql);
		}
		return ret;
	}

	public int updateByHql(String hql, Object... values) {
		return this.getHibernateTemplate().bulkUpdate(hql, values);
	}

	public List findBySQL(final String sql, final Object... values) {
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
				if(values != null && values.length > 0){
					for(int i=0;i<values.length;i++){
						q.setParameter(i, values[i]);
					}
				}
				return q.list();
			}
			
		});
	}

	public List findBySQL(final String sql,final BaseCriteria baseCriteria) {
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				if(baseCriteria != null){
					String sql2 = sql+" "+baseCriteria.getCondition();
					SQLQuery qry = session.createSQLQuery(sql2);
					if(baseCriteria.getValues() != null && baseCriteria.getValues().toArray().length > 0){
						Object[] o = baseCriteria.getValues().toArray();
						for(int i=0; i>o.length; i++){
							qry.setParameter(i, o[i]);
						}
						return qry.list();
					}
					return qry.list();
				}
				SQLQuery qry1 = session.createSQLQuery(sql);
				return qry1.list();
			}
			
		});
	}

	public List findBySQL(String sql, ResultTransformer transformers, Object... values) {
		final String sql1 = sql;
		final ResultTransformer transformers1 = transformers;
		final Object[] values1 = values;
		return getHibernateTemplate().execute(new HibernateCallback<List>() {

			public List doInHibernate(Session session) throws HibernateException {
				SQLQuery qry = session.createSQLQuery(sql1);
				if(values1 != null){
					for(int i=0;i<values1.length;i++){
						qry.setParameter(i,values1[i]);
					}
				}
				qry.setResultTransformer(transformers1);
				return qry.list();
			}
			
		});
	}

	public List findBySQL(String sql, ResultTransformer transformers, BaseCriteria baseCriteria) {
		final String sql1 = sql;
		final ResultTransformer transformers1 = transformers;
		final BaseCriteria baseCriteria1 = baseCriteria;
		return getHibernateTemplate().execute(new HibernateCallback<List>() {

			public List doInHibernate(Session session) throws HibernateException {
				if(baseCriteria1 != null){
					String str = sql1 +" "+baseCriteria1.getCondition();
					SQLQuery qry = session.createSQLQuery(str);
					for(int i=0;i<baseCriteria1.getValues().size();i++){
						qry.setParameter(i,baseCriteria1.getValues().get(i) );
					}
				}
				SQLQuery qry = session.createSQLQuery(sql1);
				qry.setResultTransformer(transformers1);
				return qry.list();
			}
			
		});
	}

	public List findBySQL(final String sql, final Class c, final Object... values) {
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				SQLQuery qry = session.createSQLQuery(sql);
				if(values != null && values.length > 0){
					for(int i=0;i<values.length;i++){
						qry.setParameter(i, values[i]);
					}
				}
				qry.addEntity(c);
				return qry.list();
			}
		});
	}

	public void updateBySQL(final String sql, final Object... values) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Boolean>() {
			public Boolean doInHibernate(Session session) throws HibernateException {
				Transaction tx = session.beginTransaction();
				
				SQLQuery qry = session.createSQLQuery(sql);
				if(values != null & values.length > 0){
					for(int i=0;i<values.length;i++){
						qry.setParameter(i, values[i]);
					}
				}
				qry.executeUpdate();
				tx.commit();
				if(session.isOpen()){
					session.close();
				}
				return true;
			}
			
		});

	}

	public void batchUpdateSQL(final String sql, final Object[][] values) {
		final Session session = this.getSessionFactory().openSession();
		session.doWork(new Work(){
			public void execute(Connection connection) throws SQLException {
				PreparedStatement pst = null;
				connection.setAutoCommit(false);//不自动提交
				try{
					 pst = connection.prepareStatement(sql);//预编译
		                if(values != null){
		                	for(Object[] arr : values){
		                		for(int i=0;i<arr.length;i++){
		                			pst.setObject(i+1, arr[i]);
		                		}
		                		pst.addBatch();//添加到批量中
		                	}
		                }
		                pst.executeBatch();//批量执行
		                connection.commit();
				}catch(Exception e){
					e.printStackTrace();		
				}finally{
					if(pst != null){
						pst.close();
					}
					if(connection != null){
						connection.close();
					}
					if(session.isOpen()){
						session.close();
					}
				}
			}
			
		});

	}

	public void batchUpdateSQL(final String sql, final Object[] values) {
		final Session session = this.getSessionFactory().openSession();
		session.doWork(new Work(){
			public void execute(Connection connection) throws SQLException {
				PreparedStatement pst = null;
				connection.setAutoCommit(false);//不自动提交
				try{
					 pst = connection.prepareStatement(sql);//预编译
		                if(values != null){
		                	for(int i=0;i<values.length;i++){
		                	    pst.setObject(i+1, values[i]);
		                		pst.addBatch();//添加到批量中
		                	}
		                }
		                pst.executeBatch();//批量执行
		                connection.commit();
				}catch(Exception e){
					e.printStackTrace();		
				}finally{
					if(pst != null){
						pst.close();
					}
					if(connection != null){
						connection.close();
					}
					if(session.isOpen()){
						session.close();
					}
				}
			}
			
		});

	}

	public void execute(HibernateCallback hibernateCallback) {
		this.getHibernateTemplate().execute(hibernateCallback);
	}

	public Session getCurrentSession() {
		
		return super.getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

	public void initialize(Object proxy) {
		if(!Hibernate.isInitialized(proxy)){//判断是否存在代理
			Hibernate.initialize(proxy);//Session关闭后，可以正常访问游离对象
		}

	}

	public void initializeDeep(Collection collection) {
		if(collection == null){
			return;
		}
		for(Object obj : collection){
			Method[] methods = obj.getClass().getMethods();
			if(methods != null){
				for(int i=0;i<methods.length;i++){
					String getName = methods[i].getName();
					String getFix = null , setFix = null;
					if(getName.length()>3 && getName.startsWith("get")){
						getFix = getName.substring(3);
						for(int j=0;j<methods.length;j++){
							String setName = methods[j].getName();
							if(setName.length()>3 && setName.startsWith("set")){
								setFix = setName.substring(3);
								if(getFix.equals(setFix)){
									Object o;
									try {
										o = methods[i].invoke(obj,
												new Object[0]);
										if (o != null) {
											Hibernate.initialize(o);
											methods[j].invoke(obj, o);
										}
										break;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}

	}

	public void evictQuery() {
		if (null != DEFAULTQUERYCACHEREGION || (!"".equals(DEFAULTQUERYCACHEREGION))) {
			getHibernateTemplate().getSessionFactory().getCache().evictQueryRegion(DEFAULTQUERYCACHEREGION);
		} else {
			getHibernateTemplate().getSessionFactory().getCache().evictQueryRegions();
		}

	}

	public void evictQuery(String queryCacheRegion) {
		getHibernateTemplate().getSessionFactory().getCache().evictQueryRegion(queryCacheRegion);

	}

	public void evictEntity(Class c) {
		//清除二级缓存中某个类型
		this.getSessionFactory().getCache().evictEntityRegion(c);

	}

	public void evictEntity(Class c, Serializable id) {
		//清除二级缓存中某个类型的实体对象
		this.getSessionFactory().getCache().evictEntity(c, id);

	}

	public void evictCollectionRegion(String collectionRegion) {
		//清除二级缓存中指定类的集合属性
		this.getSessionFactory().getCache().evictCollectionRegion(collectionRegion);

	}

	public void evictCollectionRegion(String collectionRegion, Serializable id) {
		this.getSessionFactory().getCache().evictCollection(collectionRegion, id);
	}

}
