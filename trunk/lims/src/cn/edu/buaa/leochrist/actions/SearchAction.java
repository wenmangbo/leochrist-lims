package cn.edu.buaa.leochrist.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.Degree;
import cn.edu.buaa.leochrist.model.Dissertation;
import cn.edu.buaa.leochrist.model.Member;
import cn.edu.buaa.leochrist.model.NormalProject;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Production;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Result;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.model.Team;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;
import cn.edu.buaa.leochrist.service.DegreeManager;
import cn.edu.buaa.leochrist.service.DissertationManager;
import cn.edu.buaa.leochrist.service.MemberManager;
import cn.edu.buaa.leochrist.service.NormalProjectManager;
import cn.edu.buaa.leochrist.service.PersonManager;
import cn.edu.buaa.leochrist.service.ProductionManager;
import cn.edu.buaa.leochrist.service.ResultManager;
import cn.edu.buaa.leochrist.service.TeamManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SearchAction extends ActionSupport {

	private List<Result> results;

	private Result result;

	private Integer productionId;

	private Production production;

	private ResultManager resultManager;

	private String number;

	private String owner;

	private Integer dType;

	private Integer year;

	private Integer month;

	private Integer day;

	private Integer sexType;

	private Integer personId;

	private Integer degreeId;

	private Integer dId;

	private Integer teamId;

	private Integer type;

	private Integer projectId;

	private String name;

	private String keyword;

	private String clc;

	private String title;

	private Register register;

	private Person person;

	private Person p;

	private Role role;

	private Team team;

	private Dissertation dissertation;

	private NormalProject normalProject;

	private DegreeManager degreeManager;

	private ProductionManager productionManager;

	private DissertationManager dissertationManager;

	private NormalProjectManager normalProjectManager;

	private PersonManager personManager;

	private MemberManager memberManager;

	private TeamManager teamManager;

	private List<Person> persons;

	private List<Dissertation> dissertations;

	private List<NormalProject> normalProjects;

	private List<Degree> degrees;

	private List<Member> members;

	private List<Production> productions;

	public String searchProject() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.normalProjects = this.normalProjectManager.getAll();
		System.out.println(this.normalProjects.size());
		return SUCCESS;
	}

	public String searchProjectById() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.normalProject = this.normalProjectManager.get(projectId);
		this.team = this.teamManager.get(normalProject.getTeam().getId());
		return SUCCESS;
	}

	public String searchTeamById() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.team = this.teamManager.get(teamId);

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("team.id");
		item.setKeyword(teamId.toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.members = this.memberManager.search(queryItems);
		return SUCCESS;
	}

	public String searchView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String searchProduction() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String personViewD() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.dissertation = dissertationManager.get(dId);

		return SUCCESS;
	}

	public String orderByType() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		if (0 != type) {
			List<QueryItem> queryItems = new ArrayList<QueryItem>();
			QueryItem q = new QueryItem();
			q.setFieldName("type");
			q.setKeyword(type.toString());
			q.setQueryType(QueryType.EQ);
			queryItems.add(q);
			productions = productionManager.search(queryItems);
		} else {
			productions = productionManager.getAll();
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

	public String searchResultAll() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.results = this.resultManager.getAll();

		return SUCCESS;
	}

	public String searchProductionById() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.production = this.productionManager.get(productionId);

		return SUCCESS;
	}

	public String searchPerson() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.degrees = this.degreeManager.getAll();

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

	@SuppressWarnings("deprecation")
	public String searchDissertationByName() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;

		if (!title.equals("")) {
			item = new QueryItem();
			item.setFieldName("title");
			item.setKeyword(title.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (!name.equals("")) {
			item = new QueryItem();
			item.setFieldName("author");
			item.setKeyword(name.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (!keyword.equals("")) {
			item = new QueryItem();
			item.setFieldName("keyword");
			item.setKeyword(keyword.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (!clc.equals("")) {
			item = new QueryItem();
			item.setFieldName("clc");
			item.setKeyword(clc.trim());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);
		}

		if (0 != queryItems.size()) {
			this.dissertations = this.dissertationManager.search(queryItems);

			if (null != year && null != month && null != day) {
				Date date = new Date();
				date.setYear(this.year - 1900);
				date.setMonth(this.month - 1);
				date.setDate(this.day);
				if (1 == dType) {
					for (Dissertation d : this.dissertations) {
						if (d.getPubDate().after(date)) {
							d.setId(0);
						}
					}
				} else {
					for (Dissertation d : this.dissertations) {
						if (d.getPubDate().before(date)) {
							d.setId(0);
						}
					}
				}
			}
		} else {
			if (!year.equals("") && !month.equals("") && !day.equals("")) {
				this.dissertations = this.dissertationManager.getAll();
				Date date = new Date();
				date.setYear(this.year - 1900);
				date.setMonth(this.month - 1);
				date.setDate(this.day);
				if (1 == dType) {
					for (Dissertation d : this.dissertations) {
						if (d.getPubDate().after(date)) {
							d.setId(0);
						}
					}
				} else {
					for (Dissertation d : this.dissertations) {
						if (d.getPubDate().before(date)) {
							d.setId(0);
						}
					}
				}
			}

		}
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String searchProductionBy() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;

		if (!name.equals("")) {
			item = new QueryItem();
			item.setFieldName("name");
			item.setKeyword(name.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (!number.equals("")) {
			item = new QueryItem();
			item.setFieldName("number");
			item.setKeyword(number.trim());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);
		}

		if (0 != queryItems.size()) {
			this.productions = this.productionManager.search(queryItems);

			if (null != year && null != month && null != day) {
				Date date = new Date();
				date.setYear(this.year - 1900);
				date.setMonth(this.month - 1);
				date.setDate(this.day);
				if (1 == dType) {
					for (Production d : this.productions) {
						if (d.getDate().after(date)) {
							d.setId(0);
						}
					}
				} else {
					for (Production d : this.productions) {
						if (d.getDate().before(date)) {
							d.setId(0);
						}
					}
				}
			}
		} else {
			if (!year.equals("") && !month.equals("") && !day.equals("")) {
				this.productions = this.productionManager.getAll();
				Date date = new Date();
				date.setYear(this.year - 1900);
				date.setMonth(this.month - 1);
				date.setDate(this.day);
				if (1 == dType) {
					for (Production d : this.productions) {
						if (d.getDate().after(date)) {
							d.setId(0);
						}
					}
				} else {
					for (Production d : this.productions) {
						if (d.getDate().before(date)) {
							d.setId(0);
						}
					}
				}
			}

		}
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String searchResultBy() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;

		if (!title.equals("")) {
			item = new QueryItem();
			item.setFieldName("title");
			item.setKeyword(title.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (!owner.equals("")) {
			item = new QueryItem();
			item.setFieldName("owner");
			item.setKeyword(owner.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (!clc.equals("")) {
			item = new QueryItem();
			item.setFieldName("clc");
			item.setKeyword(clc.trim());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);
		}

		if (!keyword.equals("")) {
			item = new QueryItem();
			item.setFieldName("keyword");
			item.setKeyword(keyword.trim());
			item.setQueryType(QueryType.LIKE);
			queryItems.add(item);
		}

		if (0 != queryItems.size()) {
			this.results = this.resultManager.search(queryItems);

			if (null != year && null != month && null != day) {
				Date date = new Date();
				date.setYear(this.year - 1900);
				date.setMonth(this.month - 1);
				date.setDate(this.day);
				if (1 == dType) {
					for (Result r : this.results) {
						if (r.getDate().after(date)) {
							r.setId(0);
						}
					}
				} else {
					for (Result r : this.results) {
						if (r.getDate().before(date)) {
							r.setId(0);
						}
					}
				}
			}
		} else {
			if (!year.equals("") && !month.equals("") && !day.equals("")) {
				this.results = this.resultManager.getAll();
				Date date = new Date();
				date.setYear(this.year - 1900);
				date.setMonth(this.month - 1);
				date.setDate(this.day);
				if (1 == dType) {
					for (Result r : this.results) {
						if (r.getDate().after(date)) {
							r.setId(0);
						}
					}
				} else {
					for (Result r : this.results) {
						if (r.getDate().before(date)) {
							r.setId(0);
						}
					}
				}
			}

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

	public String searchProductionAll() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.productions = this.productionManager.getAll();

		return SUCCESS;
	}

	public String searchPersonByName() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;

		if (!name.equals("")) {
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
		}

		if (-1 != sexType) {
			if (1 == sexType) {
				item = new QueryItem();
				item.setFieldName("isMale");
				item.setKeyword("1");
				item.setQueryType(QueryType.EQ);
				queryItems.add(item);
			}
			if (0 == sexType) {
				item = new QueryItem();
				item.setFieldName("isMale");
				item.setKeyword("0");
				item.setQueryType(QueryType.EQ);
				queryItems.add(item);
			}
		}

		if (-1 != degreeId) {
			item = new QueryItem();
			item.setFieldName("degree.id");
			item.setKeyword(degreeId.toString());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);
		}

		if (!queryItems.isEmpty()) {
			this.persons = this.personManager.search(queryItems);
		}

		return SUCCESS;
	}

	public String searchPersonById() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		if (!personId.equals("")) {
			List<QueryItem> queryItems = new ArrayList<QueryItem>();
			QueryItem item;
			item = new QueryItem();
			item.setFieldName("id");
			item.setKeyword(personId.toString());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);
			this.persons = this.personManager.search(queryItems);
		}
		return SUCCESS;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getdType() {
		return dType;
	}

	public void setdType(Integer dType) {
		this.dType = dType;
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

	public Integer getProductionId() {
		return productionId;
	}

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getClc() {
		return clc;
	}

	public void setClc(String clc) {
		this.clc = clc;
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

	public Integer getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}

	public Integer getSexType() {
		return sexType;
	}

	public void setSexType(Integer sexType) {
		this.sexType = sexType;
	}

	public Dissertation getDissertation() {
		return dissertation;
	}

	public void setDissertation(Dissertation dissertation) {
		this.dissertation = dissertation;
	}

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public ProductionManager getProductionManager() {
		return productionManager;
	}

	public void setProductionManager(ProductionManager productionManager) {
		this.productionManager = productionManager;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Production> getProductions() {
		return productions;
	}

	public void setProductions(List<Production> productions) {
		this.productions = productions;
	}

	public MemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public TeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(TeamManager teamManager) {
		this.teamManager = teamManager;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public NormalProject getNormalProject() {
		return normalProject;
	}

	public void setNormalProject(NormalProject normalProject) {
		this.normalProject = normalProject;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public DegreeManager getDegreeManager() {
		return degreeManager;
	}

	public void setDegreeManager(DegreeManager degreeManager) {
		this.degreeManager = degreeManager;
	}

	public List<Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<Degree> degrees) {
		this.degrees = degrees;
	}

	public NormalProjectManager getNormalProjectManager() {
		return normalProjectManager;
	}

	public void setNormalProjectManager(
			NormalProjectManager normalProjectManager) {
		this.normalProjectManager = normalProjectManager;
	}

	public List<NormalProject> getNormalProjects() {
		return normalProjects;
	}

	public void setNormalProjects(List<NormalProject> normalProjects) {
		this.normalProjects = normalProjects;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
