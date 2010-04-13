package cn.edu.buaa.leochrist.service.generic;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edu.buaa.leochrist.dao.generic.GenericDao;
import cn.edu.buaa.leochrist.model.generic.Page;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.Result;
import cn.edu.buaa.leochrist.service.generic.GenericManager;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 *
 * @author Leo christ
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
// @Transactional
public class GenericManagerImpl<T, PK extends Serializable> implements
		GenericManager<T, PK> {
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * GenericDao instance, set by constructor of this class
	 */
	protected GenericDao<T, PK> genericDao;

	/**
	 * Public constructor for creating a new GenericManagerImpl.
	 *
	 * @param genericDao
	 *            the GenericDao to use for persistence
	 */
	public GenericManagerImpl(final GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll() {
		return genericDao.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class clazz, int firstResult, int maxResults,
			String order) {
		return genericDao.getAll(clazz, firstResult, maxResults, order);
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(PK id) {
		return genericDao.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(PK id) {
		return genericDao.exists(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public T save(T object) {
		return genericDao.save(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id) {
		genericDao.remove(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Result<T> search(Class<T> clazz, List<QueryItem> queryItems,
			int firstResult, int maxResults, String... orders) {
		return this.genericDao.search(clazz, queryItems, firstResult,
				maxResults, orders);
	}

	/**
	 * 实现分页 orders: 结果排序 例: "name desc", "age asc"
	 *
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page getNextPage(Page page) {
		return this.genericDao.getNextPage(page);
	}

	/**
	 *
	 * @param params
	 * @param page
	 * @return
	 */
	// public Page<T> mapQuery(Map<String, Object> params, Page<T> page) {
	// return this.genericDao.mapQuery(params, page);
	// }
	/**
	 *
	 * @param params
	 * @param page
	 * @return
	 */
	// public List<T> mapQuery(Map<String, Object> params) {
	//
	// Page<T> page = new Page<T>();
	//
	// page.setSize(1000);
	//
	// return this.genericDao.mapQuery(params, page).getResults();
	// }
	/**
	 *
	 * @param queryItems
	 * @param page
	 * @return
	 */
	public Page<T> search(List<QueryItem> queryItems, Page<T> page) {
		return this.genericDao.search(queryItems, page);
	}

	public List<T> search(List<QueryItem> queryItems) {

		Page<T> page = new Page<T>();

		page.setSize(10000);

		page = search(queryItems, page);

		return page.getResults();
	}

	public Page<T> search(QueryItem queryItem, Page<T> page) {
		return this.genericDao.search(queryItem, page);
	}

	public List<T> saveAll(List<T> objects) {
		return this.genericDao.saveAll(objects);

	}

}
