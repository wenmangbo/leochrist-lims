package cn.edu.buaa.leochrist.service.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.edu.buaa.leochrist.model.generic.Page;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.Result;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericManager<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> getAll(Class clazz,int firstResult,int maxResults,String order);

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    T save(T object);
    
    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    List<T> saveAll(List<T> object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    /**
     * Find a list of records by using a luncene query
     * @param luncene query
     * @return a list of the records found
     */
    Result<T> search(Class<T> clazz,
    	       List<QueryItem> queryItems,int firstResult,int maxResults,String... orders);

    /**
     * 实现分页
     * orders: 结果排序 例: "name desc", "age asc"
     * @param page
     * @return
     */
    Page getNextPage(Page page);

    /**
     *
     * @param params
     * @param page
     * orders: 结果排序 例: "name desc", "age asc"
     * @return
     */
    //Page<T> mapQuery(Map<String, Object> params,Page<T> page);


    /**
     *
     * @param params
     * @param page
     * orders: 结果排序 例: "name desc", "age asc"
     * @return
     */
    List<T> search(List<QueryItem> queryItems);

    /**
     *
     * @param queryItems
     * @param page
     * @return
     */
    Page<T> search(List<QueryItem> queryItems, Page<T> page) ;
    
    /**
    *
    * @param queryItems
    * @param page
    * @return
    */
   Page<T> search(QueryItem queryItem, Page<T> page) ;
}
