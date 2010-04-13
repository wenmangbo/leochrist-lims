package cn.edu.buaa.leochrist.dao.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.edu.buaa.leochrist.model.generic.Page;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.Result;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 * 
 * <p>
 * Extend this interface if you want typesafe (no casting necessary) DAO's for
 * your domain objects.
 * 
 * @author Leo Christ
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public interface GenericDao<T, PK extends Serializable> {

	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	List<T> getAll();

	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	List<T> getAll(boolean cachable);

	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	public List<T> getAll(Class clazz, int firstResult, int maxResults,
			String order);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to get
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	T get(PK id);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to get
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	T get(PK id, boolean cachable);

	/**
	 * Checks for existence of an object of type T using the id arg.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return - true if it exists, false if it doesn't
	 */
	boolean exists(PK id);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object
	 *            the object to save
	 * @return the persisted object
	 */
	T save(T object);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object
	 *            the object to save
	 * @return the persisted object
	 */
	List<T> saveAll(List<T> objects);

	void flush();

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to remove
	 */
	void remove(PK id);

	/**
	 * Gets all records without duplicates.
	 * <p>
	 * Note that if you use this method, it is imperative that your model
	 * classes correctly implement the hashcode/equals methods
	 * </p>
	 * 
	 * @return List of populated objects
	 */
	List<T> getAllDistinct();

	/**
	 * Find a list of records by using a named query
	 * 
	 * @param queryName
	 *            query name of the named query
	 * @param queryParams
	 *            a map of the query names and the values
	 * @return a list of the records found
	 */
	List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

	/**
	 * Find a list of records by using a named query
	 * 
	 * @param queryName
	 *            query name of the named query
	 * @return a list of the records found
	 */
	List<T> findByNamedQuery(String queryName);

	/**
	 * Find a list of records by using a luncene query
	 * 
	 * @param luncene
	 *            query
	 * @return a list of the records found
	 */
	Result<T> search(Class<T> clazz, List<QueryItem> queryItems,
			int firstResult, int maxResults, String... orders);

	/**
	 * Index records
	 */
	void index();

	/**
	 * Index records
	 */
	void index(T element);

	/**
	 * 实现分页 orders: 结果排序 例: "name desc", "age asc"
	 * 
	 * @param page
	 * @return
	 */
	Page getNextPage(Page page);

	/**
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	// Page<T> mapQuery(Map<String, Object> params, Page<T> page);
	/**
	 * 
	 * @param queryItems
	 * @param page
	 * @return
	 */
	Page<T> search(List<QueryItem> queryItems, Page<T> page);

	/**
	 * 
	 * @param queryItems
	 * @param page
	 * @return
	 */
	Page<T> search(List<QueryItem> queryItems, Page<T> page, boolean cachable);

	/**
	 * 
	 * @param queryItem
	 * @param page
	 * @return
	 */
	Page<T> search(QueryItem queryItem, Page<T> page);

	/**
	 * 
	 * @param example
	 * @param firstResult
	 * @param maxResults
	 * @param orders
	 * @return
	 */
	Result<T> search(T example, int firstResult, int maxResults,
			String... orders);

	List<T> findByNativeSql(String sql);

	Page<T> findByNativeSql(String sql, String select, String count,
			Page<T> page);
}