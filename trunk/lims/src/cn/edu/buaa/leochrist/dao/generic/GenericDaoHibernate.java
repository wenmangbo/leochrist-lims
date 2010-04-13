package cn.edu.buaa.leochrist.dao.generic;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.buaa.leochrist.model.generic.Page;
import cn.edu.buaa.leochrist.model.generic.Result;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * 
 * @author Leo Christ
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public class GenericDaoHibernate<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDao<T, PK> {

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	private Class<T> persistentClass;

	/**
	 * Constructor that takes in a class to see which type of entity to persist
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll() {
		return this.getAll(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(boolean cachable) {
		boolean oldCachable = super.getHibernateTemplate().isCacheQueries();

		super.getHibernateTemplate().setCacheQueries(cachable);

		Collection elements = new LinkedHashSet(super.getHibernateTemplate()
				.loadAll(this.persistentClass));
		super.getHibernateTemplate().setCacheQueries(oldCachable);

		return new ArrayList(elements);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class clazz, int firstResult, int maxResults,
			String order) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.addOrder(Order.asc(order));
		return this.getHibernateTemplate().findByCriteria(criteria,
				firstResult, maxResults);

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAllDistinct() {
		Collection result = new LinkedHashSet(getAll());
		return new ArrayList(result);
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(PK id) {
		if (id != null) {
			T entity = (T) this.get(id, true);
			return entity;
		}
		return null;

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id, boolean cachable) {

		boolean oldCachable = super.getHibernateTemplate().isCacheQueries();

		super.getHibernateTemplate().setCacheQueries(cachable);

		T entity = (T) super.getHibernateTemplate().get(this.persistentClass,
				id);

		super.getHibernateTemplate().setCacheQueries(oldCachable);

		if (entity == null) {
			log.warn("Uh oh, '" + this.persistentClass + "' object with id '"
					+ id + "' not found...");
			// throw new ObjectRetrievalFailureException(this.persistentClass,
			// id);
		}

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(PK id) {
		T entity = (T) super.getHibernateTemplate().get(this.persistentClass,
				id);
		return entity != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T save(T object) {
		log.debug("save ");

		/* try { */
		return (T) super.getHibernateTemplate().merge(object);
		/*
		 * } catch (Exception e) {
		 * 
		 * log.debug("Exception: " + e.getMessage()); return null; }
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> saveAll(List<T> objects) {
		List<T> savedObjects = new ArrayList<T>();
		for (T object : objects) {
			object = (T) super.getHibernateTemplate().merge(object);
			savedObjects.add(object);
		}
		return savedObjects;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id) {
		/*
		 * Session session = this.getSession();
		 * session.delete(session.get(this.persistentClass, id));
		 * session.flush();
		 */
		super.getHibernateTemplate().delete(this.get(id, false));

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		String[] params = new String[queryParams.size()];
		Object[] values = new Object[queryParams.size()];
		int index = 0;
		Iterator<String> i = queryParams.keySet().iterator();
		while (i.hasNext()) {
			String key = i.next();
			params[index] = key;
			values[index++] = queryParams.get(key);
		}

		getHibernateTemplate().setCacheQueries(true);
		List<T> results = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, params, values);
		return results;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> findByNamedQuery(String queryName) {
		Map<String, Object> queryParams = new HashMap<String, Object>(0);

		return this.findByNamedQuery(queryName, queryParams);

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Result<T> search(Class<T> clazz, List<QueryItem> queryItems,
			int firstResult, int maxResults, String... orders) {
		Result<T> result = new Result<T>();

		try {
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery bq = new BooleanQuery();

			FullTextSession s = Search.createFullTextSession(getSession());

			BooleanQuery.setMaxClauseCount(10000);

			for (QueryItem queryItem : queryItems) {

				String keyword = queryItem.getKeyword();

				if (StringUtils.isNotBlank(keyword)) {
					keyword = keyword.trim();
					String fieldName = queryItem.getFieldName();

					QueryParser parser = new QueryParser(fieldName, analyzer);

					Query query = parser.parse(keyword);

					if (QueryType.AND.equals(queryItem.getQueryType())) {
						bq.add(query, BooleanClause.Occur.MUST);
					} else if (QueryType.OR.equals(queryItem.getQueryType())) {
						bq.add(query, BooleanClause.Occur.SHOULD);
					} else if (QueryType.NOT.equals(queryItem.getQueryType())) {
						bq.add(query, BooleanClause.Occur.MUST_NOT);
					} else if (QueryType.BIGGER
							.equals(queryItem.getQueryType())) {
						RangeQuery rq = new RangeQuery(new Term(fieldName,
								NumberTools.longToString(Double
										.valueOf(keyword).longValue())),
								new Term(fieldName,
										NumberTools.MAX_STRING_VALUE), false);
						bq.add(rq, BooleanClause.Occur.MUST);

					} else if (QueryType.SMALLER.equals(queryItem
							.getQueryType())) {
						;
						RangeQuery rq = new RangeQuery(new Term(fieldName,
								NumberTools.MIN_STRING_VALUE), new Term(
								fieldName, NumberTools.longToString(Double
										.valueOf(keyword).longValue())), false);
						bq.add(rq, BooleanClause.Occur.MUST);
					} else if (QueryType.BIGGER_OR_EQ.equals(queryItem
							.getQueryType())) {

						RangeQuery rq = new RangeQuery(new Term(fieldName,
								NumberTools.longToString(Double
										.valueOf(keyword).longValue())),
								new Term(fieldName,
										NumberTools.MAX_STRING_VALUE), true);
						bq.add(rq, BooleanClause.Occur.MUST);

					} else if (QueryType.SMALLER_OR_EQ.equals(queryItem
							.getQueryType())) {

						RangeQuery rq = new RangeQuery(new Term(fieldName,
								NumberTools.MIN_STRING_VALUE), new Term(
								fieldName, NumberTools.longToString(Double
										.valueOf(keyword).longValue())), true);
						bq.add(rq, BooleanClause.Occur.MUST);

					} else if (QueryType.EQ.equals(queryItem.getQueryType())) {
						bq.add(query, BooleanClause.Occur.MUST);
					} else if (QueryType.START_WITH.equals(queryItem
							.getQueryType())) {
						bq.add(query, BooleanClause.Occur.MUST);

					} else if (QueryType.CONTAINS.equals(queryItem
							.getQueryType())) {
						BooleanQuery bq1 = new BooleanQuery();

						String[] keys = keyword.split(",");

						for (String subKey : keys) {
							subKey = subKey.replaceAll("'", "");

							Query phraseQuery = parser.parse(subKey);

							bq1.add(phraseQuery, BooleanClause.Occur.SHOULD);

						}

						bq.add(bq1, BooleanClause.Occur.MUST);
					}

					log
							.debug(queryItem.getFieldName()
									+ queryItem.getQueryType()
									+ queryItem.getKeyword());
				}
			}

			Query luceneQuery = bq;

			Sort sort = new Sort();

			if (orders == null || orders.length == 0) {
				// sort = new Sort();
			} else {
				SortField[] sortFields = new SortField[orders.length];

				for (int i = 0; i < sortFields.length; i++) {
					String[] splits = orders[i].split(" ");
					if (splits.length == 1)
						sortFields[i] = new SortField(splits[0], false);
					else {
						if ("desc".equalsIgnoreCase(splits[1].trim()))
							sortFields[i] = new SortField(splits[0], true);
						else
							sortFields[i] = new SortField(splits[0], false);
					}
				}

				sort = new Sort(sortFields);
			}

			FullTextQuery fullTextQuery = s.createFullTextQuery(luceneQuery,
					clazz).setFirstResult(firstResult)
					.setMaxResults(maxResults).setSort(sort);
			fullTextQuery.setCacheable(true);

			Iterator iterator = fullTextQuery.iterate();

			result.setIterator(iterator);

			result.setTotal(fullTextQuery.getResultSize());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void index() {

		Session session = getSession();
		/*
		 * boolean isTransactional =
		 * SessionFactoryUtils.isSessionTransactional(session,
		 * this.getSessionFactory());
		 * 
		 * System.out.println(" ss Transactional="+isTransactional);
		 */
		int batchSize = 100;

		int count = 1;

		FullTextSession fullTextSession = Search.createFullTextSession(session);

		org.hibernate.ScrollableResults results = fullTextSession
				.createCriteria(persistentClass).setFetchSize(batchSize)
				.scroll(org.hibernate.ScrollMode.FORWARD_ONLY);

		long beginTime = System.currentTimeMillis() / 1000;
		long endTime = beginTime;
		log.info("begin to index at " + beginTime);

		while (results.next()) {
			T element = (T) results.get(0);
			fullTextSession.index(element);
			/*
			 * if(count%5000==0){ session.clear(); }
			 */
			log.info("index at " + count++);

		}

		endTime = System.currentTimeMillis() / 1000;

		log.info("end index at " + endTime + " used " + (endTime - beginTime));

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void index(T element) {
		FullTextSession fullTextSession = Search
				.createFullTextSession(getSession());

		element = (T) fullTextSession.merge(element);

		fullTextSession.index(element);

	}

	@SuppressWarnings("unchecked")
	public Page getNextPage(Page page) {

		if (page == null) {
			return new Page();
		}

		// String[] orders = new String[0];

		Session session = getSession();

		// super.getHibernateTemplate().loadAll(entityClass)
		String countHql = "select count(*) from "
				+ this.persistentClass.getName();

		String hql = "from " + this.persistentClass.getName() + " entity ";

		if (page.getOrders() != null) {
			hql += " order by " + page.getOrders();
		}

		org.hibernate.Query query = session.createQuery(countHql);

		Long num = (Long) query.uniqueResult();

		page.setRecordNum(num.longValue());

		int index = page.getIndex();

		int size = page.getSize();

		int firstResult = index * size;

		query = session.createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(size);

		List<T> results = query.list();

		page.setResults(results);

		return page;
	}

	public Page<T> search(List<QueryItem> queryItems, Page<T> page) {
		return this.search(queryItems, page, true);
	}

	@SuppressWarnings("unchecked")
	public Page<T> search(List<QueryItem> queryItems, Page<T> page,
			boolean cacheable) {
		String queryString = "select entity from  "
				+ this.persistentClass.getName() + " entity ";

		String criteriaString = "";

		String countHql = "select count(*) from "
				+ this.persistentClass.getName() + " entity ";

		if (queryItems != null) {

			if (!queryItems.isEmpty()) {

				int flag = 0;

				for (QueryItem queryItem : queryItems) {

					if (queryItem != null) {

						String key = queryItem.getFieldName();

						if (!key.startsWith("entity")) {
							key = "entity." + key;
						}

						String value = queryItem.getKeyword();

						if (StringUtils.isNotBlank(value)) {

							value = value.trim().toLowerCase();

							if (flag == 1) {

								criteriaString += " and ";
							} else if (flag == 0) {
								criteriaString += " where ";
							}

							if (QueryType.EQ.equals(queryItem.getQueryType())) {
								criteriaString += " " + key + "='" + value
										+ "' ";
							} else if (QueryType.NOT_EQ.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + "<>'" + value
										+ "' ";
								flag = 1;
							} else if (QueryType.IS.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + " is " + value
										+ " ";
							} else if (QueryType.NOT.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + " is not "
										+ value + " ";
							} else if (QueryType.BIGGER.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + " > '" + value
										+ "' ";
							} else if (QueryType.SMALLER_OR_EQ.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + " <= '" + value
										+ "' ";
							} else if (QueryType.BIGGER_OR_EQ.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + " >= '" + value
										+ "' ";
							} else if (QueryType.SMALLER.equals(queryItem
									.getQueryType())) {
								criteriaString += " " + key + " < '" + value
										+ "' ";
							} else if (QueryType.LIKE.equals(queryItem
									.getQueryType())) {
								criteriaString += " lower(" + key + ") like '%"
										+ value + "%' ";
							} else if (QueryType.AND.equals(queryItem
									.getQueryType())) {
								criteriaString += " lower(" + key + ") like '%"
										+ value + "%' ";
							} else if (QueryType.START_WITH.equals(queryItem
									.getQueryType())) {
								criteriaString += " lower(" + key + ") like '"
										+ value + "%' ";
							} else if (QueryType.END_WITH.equals(queryItem
									.getQueryType())) {
								criteriaString += " lower(" + key + ") like '%"
										+ value + "' ";
							} else if (QueryType.CONTAINS.equals(queryItem
									.getQueryType())) {
								criteriaString += " lower(" + key + ") in ("
										+ value + ")";
							} else if (QueryType.NOT_LIKE.equals(queryItem
									.getQueryType())) {
								criteriaString += " lower(" + key
										+ ") not like '%" + value + "%'";
							}
							flag = 1;

						}
					}

				}
			}
		}

		if (StringUtils.isNotBlank(page.getGroupby())) {
			criteriaString += " group by " + page.getGroupby();
		}

		if (StringUtils.isNotBlank(page.getOrders())) {
			criteriaString += " order by " + page.getOrders();
		}

		countHql += " " + criteriaString;
		queryString += " " + criteriaString;

		log.debug(queryString);

		List numResult = this.getHibernateTemplate().find(countHql);
		Long num = 0l;

		if (!numResult.isEmpty() && !StringUtils.isNotBlank(page.getGroupby())) {
			num = (Long) numResult.get(0);
		} else if (!numResult.isEmpty()
				&& StringUtils.isNotBlank(page.getGroupby())) {
			num = Long.valueOf(numResult.size());
		}

		int index = page.getIndex();

		int size = page.getSize();

		int firstResult = index * size;

		page.setRecordNum(num.longValue());

		org.hibernate.Query query = getSession().createQuery(queryString);

		query.setFirstResult(firstResult);
		query.setMaxResults(size);

		query.setCacheable(cacheable);

		List<T> results = query.list();

		page.setResults(results);

		return page;
	}

	private String generateCriteriaString(List<QueryItem> queryItems) {
		String criteriaString = "";

		if (queryItems != null) {

			if (!queryItems.isEmpty()) {

				int flag = 0;

				for (QueryItem queryItem : queryItems) {

					String key = queryItem.getFieldName();

					if (StringUtils.isNotBlank(key)
							&& !key.startsWith("entity")) {
						key = "entity." + key;
					}

					String value = queryItem.getKeyword();

					if (StringUtils.isNotBlank(key)
							&& StringUtils.isNotBlank(value)) {

						value = value.trim().toLowerCase();

						if (flag == 1) {
							criteriaString += " and ";
						} else {
							flag = 1;
						}

						if (QueryType.EQ.equals(queryItem.getQueryType())) {
							criteriaString += " " + key + "='" + value + "' ";
							flag = 1;
						} else if (QueryType.NOT_EQ.equals(queryItem
								.getQueryType())) {
							criteriaString += " " + key + "<>'" + value + "' ";
							flag = 1;
						} else if (QueryType.IS
								.equals(queryItem.getQueryType())) {
							criteriaString += " " + key + " is " + value + " ";
							flag = 1;
						} else if (QueryType.NOT.equals(queryItem
								.getQueryType())) {
							criteriaString += " " + key + " is not " + value
									+ " ";
							flag = 1;
						} else if (QueryType.BIGGER.equals(queryItem
								.getQueryType())) {
							criteriaString += " " + key + " > '" + value + "' ";
							flag = 1;
						} else if (QueryType.SMALLER_OR_EQ.equals(queryItem
								.getQueryType())) {
							criteriaString += " " + key + " <= '" + value
									+ "' ";
							flag = 1;
						} else if (QueryType.BIGGER_OR_EQ.equals(queryItem
								.getQueryType())) {
							criteriaString += " " + key + " >= '" + value
									+ "' ";
							flag = 1;
						} else if (QueryType.SMALLER.equals(queryItem
								.getQueryType())) {
							criteriaString += " " + key + " < '" + value + "' ";
							flag = 1;
						} else if (QueryType.LIKE.equals(queryItem
								.getQueryType())) {
							criteriaString += " lower(" + key + ") like '%"
									+ value + "%' ";
							flag = 1;
						} else if (QueryType.AND.equals(queryItem
								.getQueryType())) {
							criteriaString += " lower(" + key + ") like '%"
									+ value + "%' ";

							flag = 1;
						}
					} else {
						if (QueryType.AND.equals(queryItem.getQueryType())) {
							String subCriteriaString = this
									.generateCriteriaString(queryItem
											.getQueryItems());

							if (StringUtils.isNotBlank(subCriteriaString)) {
								if (flag == 1) {
									criteriaString += " and ";
								} else {
									flag = 1;
								}

								criteriaString += " (" + subCriteriaString
										+ ") ";
							}

						} else if (QueryType.OR
								.equals(queryItem.getQueryType())) {
							String subCriteriaString = this
									.generateCriteriaString(queryItem
											.getQueryItems());

							if (StringUtils.isNotBlank(subCriteriaString)) {

								if (flag == 1) {
									criteriaString += " or ";
								} else {
									flag = 1;
								}

								criteriaString += " (" + subCriteriaString
										+ ") ";
							}
						}
					}
				}
			}
		}

		return criteriaString;
	}

	@SuppressWarnings("unchecked")
	public Page<T> search(QueryItem topQueryItem, Page<T> page) {

		String queryString = "select entity from  "
				+ this.persistentClass.getName() + " entity ";

		String criteriaString = "";

		String countHql = "select count(*) from "
				+ this.persistentClass.getName() + " entity ";

		List<QueryItem> queryItems = topQueryItem.getQueryItems();

		criteriaString = this.generateCriteriaString(queryItems);

		if (StringUtils.isNotBlank(criteriaString)) {
			criteriaString = " where " + criteriaString;
		}

		if (StringUtils.isNotBlank(page.getGroupby())) {
			criteriaString += " group by " + page.getGroupby();
		}

		if (StringUtils.isNotBlank(page.getOrders())) {
			criteriaString += " order by " + page.getOrders();
		}

		countHql += " " + criteriaString;
		queryString += " " + criteriaString;

		log.debug(queryString + " limit " + page.getIndex());

		List numResult = this.getHibernateTemplate().find(countHql);
		Long num = 0l;

		if (!numResult.isEmpty() && !StringUtils.isNotBlank(page.getGroupby())) {
			num = (Long) numResult.get(0);
		} else if (!numResult.isEmpty()
				&& StringUtils.isNotBlank(page.getGroupby())) {
			num = Long.valueOf(numResult.size());
		}

		int index = page.getIndex();

		int size = page.getSize();

		int firstResult = index * size;

		page.setRecordNum(num.longValue());

		org.hibernate.Query query = getSession().createQuery(queryString);

		query.setFirstResult(firstResult);
		query.setMaxResults(size);

		List<T> results = query.list();

		page.setResults(results);

		return page;

	}

	@SuppressWarnings("unchecked")
	public Result<T> search(T example, int firstResult, int maxResults,
			String... orders) {
		Result<T> result = new Result<T>();

		List<T> elements = this.getHibernateTemplate().findByExample(example,
				firstResult, maxResults);

		result.setElements(elements);

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNativeSql(String sql) {
		List<T> results = this.getSession().createSQLQuery(sql).addEntity(
				persistentClass).list();
		return results;
	}

	@SuppressWarnings("unchecked")
	public Page<T> findByNativeSql(String sql, String select, String count,
			Page<T> page) {
		int index = page.getIndex();
		int size = page.getSize();
		int firstResult = index * size;

		if (count == null)
			count = "*";
		String countsql = "select count(" + count + ") " + sql;
		String selectsql = "select " + select + " " + sql;
		selectsql += " limit " + firstResult + ", " + size;

		long num = ((BigInteger) (getSession().createSQLQuery(countsql)
				.uniqueResult())).longValue();

		List<T> results = this.getSession().createSQLQuery(selectsql)
				.addEntity(persistentClass).list();
		page.setRecordNum(num);
		page.setResults(results);

		return page;
	}

	public void flush() {
		this.getHibernateTemplate().flush();
	}
}
