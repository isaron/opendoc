package com.cloud.platform;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CoreDao extends HibernateDaoSupport implements IDao {
	
	public Object getObject(Class entityClass, String id) {
		return getHibernateTemplate().get(entityClass, id);
	}
	
	public Object getObject(Class entityClass, String hql, Object param) {
		List list = null;
		
		if(param == null) {
			list = getAllByHql(hql); 
		} else {
			list = getAllByHql(hql, param);
		}
		
		try {
			return list.isEmpty() ? entityClass.newInstance() : list.get(0);
		} catch(Exception e) {}
		
		return null;
	}
	
	public Object getObject(Class entityClass, String hql, Object[] params) {
		List list = getAllByHql(hql, params);
		
		try {
			return list.isEmpty() ? entityClass.newInstance() : list.get(0);
		} catch(Exception e) {}
		
		return null;
	}

	public void saveObject(Object entity) {  
        getHibernateTemplate().saveOrUpdate(entity);
    }
	
	public List getAllByHql(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	public List getAllByHql(String hql, Object param) {
		return getHibernateTemplate().find(hql, param);
	}
	
	public List getAllByHql(String hql, Object[] params) {
		return getHibernateTemplate().find(hql, params);
	}
	
	public List getPageByHql(final String hql, SearchVo searchVo) {
		
		// init search vo
		if(searchVo == null) {
			searchVo = new SearchVo();
		}
		
		final int page = searchVo.getPage();
		final int size = searchVo.getPageSize();
		
		// get result count
		List list = getAllByHql(hql);
		
		// if search all, return all list directly
		if(size == -1) {
			return list;
		}
		
		if(list.isEmpty()) {
			searchVo.setPageNum(1);
		} else if(list.size() % searchVo.getPageSize() == 0) {
			searchVo.setPageNum(list.size() / searchVo.getPageSize());
		} else {
			searchVo.setPageNum(list.size() / searchVo.getPageSize() + 1);
		}
		
		// get result page list
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);

				query.setFirstResult((page - 1) * size);
				query.setMaxResults(size);
				
				return query.list();
			}
		});
		
		return list;
	}

    public List getPageByHql(final String hql, final Object[] params, SearchVo searchVo) {

        // init search vo
        if(searchVo == null) {
            searchVo = new SearchVo();
        }

        final int page = searchVo.getPage();
        final int size = searchVo.getPageSize();

        // get result count
        List list = getAllByHql(hql, params);

        // if search all, return all list directly
        if(size == -1) {
            return list;
        }

        if(list.isEmpty()) {
            searchVo.setPageNum(1);
        } else if(list.size() % searchVo.getPageSize() == 0) {
            searchVo.setPageNum(list.size() / searchVo.getPageSize());
        } else {
            searchVo.setPageNum(list.size() / searchVo.getPageSize() + 1);
        }

        // get result page list
        list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);

                for(int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }

                query.setFirstResult((page - 1) * size);
                query.setMaxResults(size);

                return query.list();
            }
        });

        return list;
    }
	
	public void removeObject(Object entity) {
		getHibernateTemplate().delete(entity);
	}
	
	public void removeById(Class entityClass, String id) {
		Object entity = getObject(entityClass, id);
		removeObject(entity);
	}
	
	public void removeByHql(String hql) {
		getHibernateTemplate().bulkUpdate(hql);
	}
	
	public void removeByHql(String hql, Object param) {
		getHibernateTemplate().bulkUpdate(hql, param);
	}
	
	public void removeByHql(String hql, Object[] params) {
		getHibernateTemplate().bulkUpdate(hql, params);
	}
	
	public void updateByHql(String hql) {
		getHibernateTemplate().bulkUpdate(hql);
	}
	
	public void updateByHql(String hql, Object param) {
		getHibernateTemplate().bulkUpdate(hql, param);
	}
	
	public void updateByHql(String hql, Object[] params) {
		getHibernateTemplate().bulkUpdate(hql, params);
	}
}
