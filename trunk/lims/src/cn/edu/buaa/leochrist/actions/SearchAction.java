package cn.edu.buaa.leochrist.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.Dissertation;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;
import cn.edu.buaa.leochrist.service.DissertationManager;
import cn.edu.buaa.leochrist.service.PersonManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SearchAction extends ActionSupport {

	private Integer personId;

	private String name;

	private Register register;

	private Person person;

	private Person p;

	private Role role;

	private DissertationManager dissertationManager;

	private PersonManager personManager;

	private List<Person> persons;

	private List<Dissertation> dissertations;

	public String searchView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String searchResult() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.p = this.personManager.get(personId);

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("uploader.id");
		item.setKeyword(personId.toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.dissertations = this.dissertationManager.search(queryItems);

		return SUCCESS;
	}

	public String searchPerson() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}
	
	public String searchDissertation() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}
	
	public String searchDissertationByName() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		if (!name.equals("")) {
			List<QueryItem> queryItems = new ArrayList<QueryItem>();
			QueryItem item;
			item = new QueryItem();
			item.setFieldName("author");
			item.setKeyword(name.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);

			this.dissertations = this.dissertationManager.search(queryItems);
		}
		return SUCCESS;
	}
	
	public String searchDissertationAll() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.dissertations = this.dissertationManager.getAll();
		
		return SUCCESS;
	}
	

	public String searchPersonByName() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		if (!name.equals("")) {
			List<QueryItem> queryItems = new ArrayList<QueryItem>();
			QueryItem item;
			item = new QueryItem();
			item.setFieldName("register.isAvailable");
			item.setKeyword("1");
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);

			item = new QueryItem();
			item.setFieldName("role.roleName");
			item.setKeyword("admin");
			item.setQueryType(QueryType.NOT_EQ);
			queryItems.add(item);

			item = new QueryItem();
			item.setFieldName("name");
			item.setKeyword(name.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);

			this.persons = this.personManager.search(queryItems);
		}
		return SUCCESS;
	}

	public String searchPersonAll() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("register.isAvailable");
		item.setKeyword("1");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		item = new QueryItem();
		item.setFieldName("role.roleName");
		item.setKeyword("admin");
		item.setQueryType(QueryType.NOT_EQ);
		queryItems.add(item);

		this.persons = this.personManager.search(queryItems);
		return SUCCESS;
	}

	public DissertationManager getDissertationManager() {
		return dissertationManager;
	}

	public void setDissertationManager(DissertationManager dissertationManager) {
		this.dissertationManager = dissertationManager;
	}

	public List<Dissertation> getDissertations() {
		return dissertations;
	}

	public void setDissertations(List<Dissertation> dissertations) {
		this.dissertations = dissertations;
	}

	public Person getP() {
		return p;
	}

	public void setP(Person p) {
		this.p = p;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	/**
	 * Convenience method to get the request
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * Convenience method to get the session. This will create a session if one
	 * doesn't exist.
	 * 
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

}
