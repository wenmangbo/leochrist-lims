package cn.edu.buaa.leochrist.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;
import cn.edu.buaa.leochrist.service.PersonManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SearchAction extends ActionSupport {

	private String name;

	private Register register;

	private Person person;

	private Role role;

	private PersonManager personManager;

	private List<Person> persons;

	public String searchView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

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

	public String searchAllPerson() {
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("register.isAvailable");
		item.setKeyword("1");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.persons = this.personManager.search(queryItems);
		return SUCCESS;
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
