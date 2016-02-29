package com.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;

/**
 * @function 增删改查、翻页效果
 * @author hxy
 * @version 2013-3-31 17:13:50
 */

public abstract class BaseDAO extends HibernateDaoSupport {

	// 通过注解将sessionFactory注入到setSuperHibernateDaoSupport的参数中
	@Resource(name = "sessionFactory")
	public void setSuperHibernateDaoSupport(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

	}

	/**
	 * @function 创建一个对象
	 * @version 2012-7-13 13:34:35
	 */
	public void create(Object entity) {
		try {

			getHibernateTemplate().save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 更新一个对象
	 * @version 2012-7-13 13:34:35
	 */
	public void update(Object entity) {
		try {
			getHibernateTemplate().update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 删除一个对象
	 * @version 2012-7-13 13:34:35
	 */
	public void delete(Object entity) {
		try {
			getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 根据条件查找单个对象
	 * @version 2012-7-13 13:34:35
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object loadByKey(final Class clazz, final String key,
			final Object val) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz, "clazz");
				criteria.add(Restrictions.eq("clazz." + key, val));
				List result = criteria.list();
				if (result != null && result.size() > 0) {
					return result.get(0);
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * @function 无条件查询所有结果集
	 * @version 2012-7-13 13:34:35
	 */
	@SuppressWarnings("rawtypes")
	protected List loadAll(Class clazz) {
		List result = getHibernateTemplate().find("from " + clazz.getName());
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;

		}
	}

	/**
	 * @function 根据HQL查询结果集
	 * @version 2015-3-11 23:20:16
	 */
	@SuppressWarnings("rawtypes")
	protected List queryByHQL(String hql) {
		List result = getHibernateTemplate().find(hql);
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;

		}
	}
	
	/**
	 * @function 根据HQL查询结果集，带分页效果
	 * @version 2015-3-11 23:19:55
	 */
	@SuppressWarnings("rawtypes")
	public PageBean queryPageByHQL(Page page, String hql) {

		boolean b = hql.contains("limit") || hql.contains("LIMIT");
		if (!b) {
			int currentPage = page.getCurrentPage();
			int pageSize = page.getPageSize();
			hql += " limit " + currentPage + ", " + pageSize;
		}

		List result = getHibernateTemplate().find(hql);
		if (result != null && result.size() > 0) {
			PageBean pageBean = new PageBean(page, result);
			return pageBean;
		} else {
			return null;
		}
	}

	/**
	 * @function 根据单一条件返回查询结果集
	 * @version 2012-7-13 13:34:35
	 */
	@SuppressWarnings("rawtypes")
	protected List loadAllByKey(Class clazz, String keyName, Object keyValue) {
		List result = getHibernateTemplate().find(
				"from " + clazz.getName() + " clazz" + " where " + "clazz."
						+ keyName + "=" + "'" + keyValue + "'");
		if (result != null && result.size() > 0) {
			return result;

		} else {
			return null;
		}
	}

	/**
	 * @function 删除所有对象
	 * @version 2012-7-13 13:34:35
	 */
	@SuppressWarnings("rawtypes")
	protected void deleteAll(Class clazz) {
		getHibernateTemplate().deleteAll(
				getHibernateTemplate().find("from " + clazz.getName()));
	}

	/**
	 * @function 删除所有对象
	 * @version 2012-7-13 13:34:35
	 */
	@SuppressWarnings("rawtypes")
	protected void deleteAllList(List list) {
		getHibernateTemplate().deleteAll(list);
	}

	/**
	 * @function 无条件翻页查找全部
	 * @version 2013-3-31 13:45:56
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PageBean loadAllPage(final Class clazz, final Page page) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz);
				criteria.addOrder(Order.desc("id"));
				page.setTotalItems(criteria.list().size());
				criteria.setFirstResult(page.getBeginItem());
				criteria.setMaxResults(page.getPageSize());
				return new PageBean(page, criteria.list());
			}
		});
	}

	/**
	 * @function 有条件查询翻页效果所有结果集
	 * @param clazz
	 * @param page
	 * @param map
	 * @return PageBean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PageBean loadAllByKeyPage(final Class clazz, final Page page,
			final Map<String, Object> map) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz, "clazz");
				criteria.add(Restrictions.allEq(map));
				criteria.addOrder(Order.desc("id"));
				page.setTotalItems(criteria.list().size());
				criteria.setFirstResult(page.getBeginItem());
				criteria.setMaxResults(page.getPageSize());
				return new PageBean(page, criteria.list());
			}
		});
	}

	/**
	 * @function 根据多个条件查询单个对象
	 * @version 2013-3-31 15:02:08
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object loadByKey(final Class clazz, final Map<String, Object> map) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz, "clazz");
				criteria.add(Restrictions.allEq(map));
				criteria.addOrder(Order.desc("id"));
				List result = criteria.list();
				if (result != null && result.size() > 0) {
					return result.get(0);
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * @function 根据多个查询条件返回结果集
	 * @version 2013-3-31 15:02:02
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List loadAllByKey(final Class clazz, final Map<String, Object> map) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz, "clazz");
				criteria.add(Restrictions.allEq(map));
				criteria.addOrder(Order.desc("id"));
				List result = criteria.list();
				if (null == result || 0 == result.size()) {
					return null;
				} else {
					//System.out.println("查询结果：" + result.size());
					return result;
				}

			}
		});

	}

	/**
	 * @function 根据多个查询条件返回结果集
	 * @version 2013-3-31 15:02:02
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List loadAllByKeyOrByKey(final Class clazz,
			final Map<String, Object> map, final Map<String, Object> map2) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz, "clazz");
				criteria.addOrder(Order.desc("id"));
				criteria.add(Restrictions.or(Restrictions.allEq(map),
						Restrictions.allEq(map2)));
				List result = criteria.list();
				if (null == result || 0 == result.size()) {
					//System.out.println("查询结果为空");
					return null;
				} else {
					//System.out.println("或者查询结果：" + result.size());
					return result;
				}

			}
		});

	}

	/**
	 * @function 根据map条件返回搜有的and 和 or条件成立的数据 map存储and条件 map1存储or条件
	 * @param clazz
	 * @param map
	 * @param map2
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected PageBean loadAllByKeyOr(final Class clazz, final Page page,
			final Map<String, Object> map, final Map<String, Object> map1) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String key = null;
				String hql = "from " + clazz.getName() + " clazz" + " where ";

				Iterator<String> iter = map.keySet().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					hql += "clazz." + key + " like " + "'%" + map.get(key)
							+ "%' ";
					if (iter.hasNext())
						hql += " and ";
				}

				iter = map1.keySet().iterator();
				if (map != null && map.keySet() != null
						&& map.keySet().size() != 0) {
					if (iter.hasNext())
						hql += " and ";
				}
				while (iter.hasNext()) {
					key = iter.next();
					hql += "clazz." + key + " like " + "'%" + map1.get(key)
							+ "%' ";
					if (iter.hasNext())
						hql += " or ";
				}
				hql += "order by clazz.id desc";
				System.out.println(hql);
				Query query = session.createQuery(hql);
				List result = query.list();
				if (null != query.list()) {

					page.setTotalItems(result.size());
					query.setFirstResult(page.getBeginItem());
					query.setMaxResults(page.getPageSize());
					return new PageBean(page, query.list());
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * @function 或者两个条件查询翻页效果所有结果集
	 * @param clazz
	 * @param page
	 * @param map
	 * @return PageBean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PageBean loadAllByKeyOrByKeyPage(final Class clazz,
			final Page page, final Map<String, Object> map,
			final Map<String, Object> map2) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(clazz, "clazz");
				criteria.add(Restrictions.or(Restrictions.allEq(map),
						Restrictions.allEq(map2)));
				criteria.addOrder(Order.desc("id"));
				if (null != criteria.list()) {

					page.setTotalItems(criteria.list().size());
					criteria.setFirstResult(page.getBeginItem());
					criteria.setMaxResults(page.getPageSize());
					return new PageBean(page, criteria.list());
				} else {
					return null;
				}

			}
		});
	}

}
