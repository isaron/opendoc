package com.cloud.platform;

import java.util.List;

public interface IDao {
	
	public Object getObject(Class entityClass, String id);
	
	public Object getObject(Class entityClass, String hql, Object param);
	
	public Object getObject(Class entityClass, String hql, Object[] params);

    public void saveObject(Object entity);
    
    public List getAllByHql(String hql);
    
    public List getAllByHql(String hql, Object param);
    
    public List getAllByHql(String hql, Object[] params);
    
    public List getPageByHql(String hql, SearchVo searchVo);

    public List getPageByHql(String hql, Object[] params, SearchVo searchVo);
    
    public void removeObject(Object entity);
    
    public void removeById(Class entityClass, String id);
    
    public void removeByHql(String hql);
    
    public void removeByHql(String hql, Object param);
    
    public void removeByHql(String hql, Object[] params);
    
    public void updateByHql(String hql);
    
    public void updateByHql(String hql, Object param);
    
    public void updateByHql(String hql, Object[] params);
}
