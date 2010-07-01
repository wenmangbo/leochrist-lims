package cn.edu.buaa.leochrist.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Production;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Result;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;
import cn.edu.buaa.leochrist.service.PersonManager;
import cn.edu.buaa.leochrist.service.ProductionManager;
import cn.edu.buaa.leochrist.service.RegisterManager;
import cn.edu.buaa.leochrist.service.ResultManager;
import cn.edu.buaa.leochrist.service.RoleManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AdminAction extends ActionSupport {

	private Result result;

	private ResultManager resultManager;

	private Integer year;

	private Integer month;

	private Integer day;

	private Production production;

	private Production p;

	private ProductionManager productionManager;

	private Register register;

	private Register tempRegister;

	private Register searchRegister;

	private Person person;

	private Person searchPerson;

	private Role role;

	private RegisterManager registerManager;

	private RoleManager roleManager;

	private PersonManager personManager;

	private List<Register> registers;

	private List<Role> roles;

	public String adminView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String adminProductionSave() {
		this.production = new Production();

		this.production.setName(p.getName());
		this.production.setOwner(p.getOwner());
		this.production.setInformation(p.getInformation());
		this.production.setValidity(p.getValidity());
		this.production.setType(p.getType());
		this.production.setEnable(true);
		this.production.setCou(p.getCou());
		this.production.setNumber(p.getNumber());
		Date date = new Date();
		date.setYear(this.year - 1900);
		date.setMonth(this.month - 1);
		date.setDate(this.day);
		this.production.setDate(date);

		this.productionManager.save(production);

		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String adminResultSave() {
		Date date = new Date();
		date.setYear(this.year - 1900);
		date.setMonth(this.month - 1);
		date.setDate(this.day);
		this.result.setDate(date);

		this.resultManager.save(result);

		return SUCCESS;
	}

	public String adminRegisterInfo() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.roles = this.roleManager.getAll();

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("isAvailable");
		item.setKeyword("0");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.registers = this.registerManager.search(queryItems);

		return SUCCESS;
	}

	public String adminSaveRegister() {
		this.person = this.personManager.get(this.person.getId());
		this.role = this.roleManager.get(this.role.getId());
		this.register = this.person.getRegister();
		this.person.setRole(this.role);
		this.register.setIsAvailable(true);

		this.personManager.save(this.person);
		this.registerManager.save(this.register);
		return SUCCESS;
	}

	public String adminProduction() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String adminRegisterDetail() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.roles = this.roleManager.getAll();

		this.searchRegister = this.registerManager.get(this.searchRegister
				.getId());
		return SUCCESS;
	}

	public String adminSearchView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String adminRegisterSearch() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.roles = this.roleManager.getAll();

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("isAvailable");
		item.setKeyword("1");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		if (null != this.searchRegister
				&& null != this.searchRegister.getUsername()) {
			item = new QueryItem();
			item.setFieldName("username");
			item.setKeyword(this.searchRegister.getUsername().trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (null != this.searchPerson && null != this.searchPerson.getName()) {
			item = new QueryItem();
			item.setFieldName("person.name");
			item.setKeyword(this.searchPerson.getName().trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (null != this.searchPerson && null != this.searchPerson.getdCard()) {
			item = new QueryItem();
			item.setFieldName("person.dCard");
			item.setKeyword(this.searchPerson.getdCard().trim());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);
		}

		this.registers = this.registerManager.search(queryItems);

		return SUCCESS;
	}

	public String adminChangeRegister() {
		this.person = this.personManager.get(this.person.getId());
		this.role = this.roleManager.get(this.role.getId());
		this.register = this.person.getRegister();
		this.person.setRole(this.role);

		if (null != this.tempRegister) {
			this.register.setIsAvailable(this.tempRegister.getIsAvailable());
		} else {
			this.register.setIsAvailable(true);
		}

		this.personManager.save(this.person);
		this.registerManager.save(this.register);
		return SUCCESS;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ResultManager getResultManager() {
		return resultManager;
	}

	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public Production getP() {
		return p;
	}

	public void setP(Production p) {
		this.p = p;
	}

	public ProductionManager getProductionManager() {
		return productionManager;
	}

	public void setProductionManager(ProductionManager productionManager) {
		this.productionManager = productionManager;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Register getTempRegister() {
		return tempRegister;
	}

	public void setTempRegister(Register tempRegister) {
		this.tempRegister = tempRegister;
	}

	public Register getSearchRegister() {
		return searchRegister;
	}

	public void setSearchRegister(Register searchRegister) {
		this.searchRegister = searchRegister;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getSearchPerson() {
		return searchPerson;
	}

	public void setSearchPerson(Person searchPerson) {
		this.searchPerson = searchPerson;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RegisterManager getRegisterManager() {
		return registerManager;
	}

	public void setRegisterManager(RegisterManager registerManager) {
		this.registerManager = registerManager;
	}

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public List<Register> getRegisters() {
		return registers;
	}

	public void setRegisters(List<Register> registers) {
		this.registers = registers;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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