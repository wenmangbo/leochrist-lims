package cn.edu.buaa.leochrist.actions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.ClassifiedProject;
import cn.edu.buaa.leochrist.model.Degree;
import cn.edu.buaa.leochrist.model.Device;
import cn.edu.buaa.leochrist.model.Lab;
import cn.edu.buaa.leochrist.model.Member;
import cn.edu.buaa.leochrist.model.NormalProject;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Project;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.model.Team;
import cn.edu.buaa.leochrist.model.WorkSheet;
import cn.edu.buaa.leochrist.model.WorkStatus;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;
import cn.edu.buaa.leochrist.service.ClassifiedProjectManager;
import cn.edu.buaa.leochrist.service.DegreeManager;
import cn.edu.buaa.leochrist.service.DeviceManager;
import cn.edu.buaa.leochrist.service.LabManager;
import cn.edu.buaa.leochrist.service.MemberManager;
import cn.edu.buaa.leochrist.service.NormalProjectManager;
import cn.edu.buaa.leochrist.service.PersonManager;
import cn.edu.buaa.leochrist.service.ProjectManager;
import cn.edu.buaa.leochrist.service.RegisterManager;
import cn.edu.buaa.leochrist.service.RoleManager;
import cn.edu.buaa.leochrist.service.TeamManager;
import cn.edu.buaa.leochrist.service.WorkSheetManager;
import cn.edu.buaa.leochrist.service.WorkStatusManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport {

	private String message;

	private Integer teamLeaderId;

	private Integer teamId;

	private Integer personId;

	private Integer memberId;

	private Integer creatorId;

	private Integer projectId;

	private Integer workSheetId;

	private Integer labId;

	private Integer year;

	private Integer month;

	private Integer day;

	private Project project;

	private Register register;

	private Person person;

	private Role role;

	private Degree degree;

	private WorkStatus workStatus;

	private Device device;

	private NormalProject normalProject;

	private ClassifiedProject classifiedProject;

	private Team team;

	private Member member;

	private Lab lab;

	private Person temp;

	private WorkSheet workSheet;

	private WorkStatusManager workStatusManager;

	private RegisterManager registerManager;

	private PersonManager personManager;

	private RoleManager roleManager;

	private DegreeManager degreeManager;

	private NormalProjectManager normalProjectManager;

	private ClassifiedProjectManager classifiedProjectManager;

	private TeamManager teamManager;

	private MemberManager memberManager;

	private LabManager labManager;

	private WorkSheetManager workSheetManager;

	private DeviceManager deviceManager;

	private ProjectManager projectManager;

	private List<Device> devices;

	private List<Degree> degrees;

	private List<Person> persons;

	private List<Integer> memberIds;

	private List<Team> teams;

	private List<Member> members;

	private List<WorkSheet> workSheets;
	
	private List<WorkStatus> workStatuss;

	private List<Lab> labs;

	public String index() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}
		
		this.workSheets = this.workSheetManager.getAll();
		
		Date alert = new Date();
		for(WorkSheet w : workSheets){
			if (w.getDeadline().getTime() - alert.getTime() < 68400000 * 3 && w.getDeadline().getTime() - alert.getTime() > 0) {
				w.setId(0);
			} 
		}
		
		return SUCCESS;
	}

	public String news() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String registe() {
		this.degrees = this.degreeManager.getAll();
		System.out.println(this.degrees.size() + "+++++++++++++++");
		return SUCCESS;
	}

	public String registeSave() {
		if (this.registerManager.isExist(this.register.getUsername().trim())) {
			message = "The user " + this.register.getUsername().trim()
					+ " is exist!";
			System.out.println(message);
			return ERROR;
		}

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String str = this.register.getPassword().trim();
		md5.update(str.getBytes());
		byte[] encodedPassword = md5.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				sb.append("0");
			}
			sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		this.register.setPassword(sb.toString());
		this.register.setIsAvailable(false);
		this.register = this.registerManager.save(this.register);

		this.person.setRegister(this.register);

		Date date = new Date();
		this.person.setRegisteDate(date);
		this.person.setLastModifiedDate(date);
		this.person.setLastLoginDate(date);

		if (null != this.person.getDegree()) {
			this.person.setDegree(this.degreeManager.get(this.person
					.getDegree().getId()));
		}
		this.person = this.personManager.save(this.person);

		this.register.setPerson(this.person);
		this.registerManager.save(this.register);

		this.message = "registe success!";
		return SUCCESS;
	}

	public String login() {
		if (!this.registerManager.isExist(this.register.getUsername().trim())) {
			message = "The user " + this.register.getUsername().trim()
					+ " is not exist!";
			System.out.println(message);
			return ERROR;
		}

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String str = this.register.getPassword().trim();
		md5.update(str.getBytes());
		byte[] encodedPassword = md5.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				sb.append("0");
			}
			sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		this.register.setPassword(sb.toString());

		if (!this.register.getPassword().equals(
				this.registerManager.findByUsername(
						this.register.getUsername().trim()).getPassword())) {

			message = "Password is wrong!";
			System.out.println(message);
			return ERROR;
		}

		this.register = this.registerManager.findByUsername(this.register
				.getUsername().trim());

		System.out.println(this.register.getPerson().getName()
				+ "~~~~~~~~~~~~~~~~`");
		if (!this.register.getIsAvailable()) {
			message = "This account is not available now!";
			System.out.println(message);
			return ERROR;
		}

		this.getRequest().getSession().setAttribute("currentRegister",
				this.register);
		System.out.println("register:" + this.register.getId()
				+ " save in session!");

		return SUCCESS;
	}

	public String logout() {
		this.getRequest().getSession().removeAttribute("currentRegister");
		return SUCCESS;
	}

	public String userView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String userCreateProject() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("isEngage");
		item.setKeyword("0");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.teams = this.teamManager.search(queryItems);

		return SUCCESS;
	}

	public String userProjectSave() {
		if (this.normalProject.getIsClassified()) {
			this.classifiedProject = new ClassifiedProject();
			this.classifiedProject.setName(this.normalProject.getName());
			this.classifiedProject.setIsClassified(true);
			this.classifiedProject.setBudget(this.normalProject.getBudget());
			this.classifiedProject.setInformation(this.normalProject
					.getInformation());
			this.team = this.teamManager.get(teamId);
			this.classifiedProject.setTeam(this.team);
			Date date = new Date();
			this.classifiedProject.setCreateDate(date);
			this.classifiedProject.setLastModifiedDate(date);
			this.classifiedProject = this.classifiedProjectManager
					.save(classifiedProject);

			this.team.setIsEngage(true);
			this.team.setProject(classifiedProject);
			this.team = this.teamManager.save(team);
			this.team = this.teamManager.save(team);
			this.message = "机密项目创建成功";
		} else {
			this.team = this.teamManager.get(teamId);
			this.normalProject.setTeam(this.team);
			Date date = new Date();
			this.normalProject.setCreateDate(date);
			this.normalProject.setLastModifiedDate(date);
			this.normalProject = this.normalProjectManager.save(normalProject);

			this.team.setIsEngage(true);
			this.team.setProject(normalProject);
			this.team = this.teamManager.save(team);
			this.message = "普通项目创建成功";
		}
		return SUCCESS;
	}

	public String userCreateTeam() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("role.roleName");
		item.setKeyword("user");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.persons = this.personManager.search(queryItems);

		return SUCCESS;
	}

	public String userTeamSave() {
		this.team.setIsEngage(false);
		this.team = this.teamManager.save(team);

		for (int mId : memberIds) {
			if (mId != this.teamLeaderId) {
				List<QueryItem> queryItems = new ArrayList<QueryItem>();
				QueryItem item;
				item = new QueryItem();
				item.setFieldName("roleName");
				item.setKeyword("member");// ////////////////////////////////////////
				item.setQueryType(QueryType.EQ);
				queryItems.add(item);
				this.role = this.roleManager.search(queryItems).get(0);

				this.person = this.personManager.get(mId);
				this.person.setRole(this.role);
				this.person = this.personManager.save(person);

				this.member = new Member();
				this.member.setPerson(person);
				this.member.setTeam(this.team);
				this.member.setIsTeamLeader(false);
				this.memberManager.save(member);
			} else {
				List<QueryItem> queryItems = new ArrayList<QueryItem>();
				QueryItem item;
				item = new QueryItem();
				item.setFieldName("roleName");
				item.setKeyword("leader");// ////////////////////////////////////////
				item.setQueryType(QueryType.EQ);
				queryItems.add(item);
				this.role = this.roleManager.search(queryItems).get(0);

				this.person = this.personManager.get(mId);
				this.person.setRole(this.role);
				this.person = this.personManager.save(person);

				this.member = new Member();
				this.member.setPerson(person);
				this.member.setTeam(this.team);
				this.member.setIsTeamLeader(true);
				this.member = this.memberManager.save(member);

				this.team.setTeamLeader(member);
				this.teamManager.save(team);
			}
		}

		return SUCCESS;
	}

	public String userProjectReport() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String userTeamReport() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String userReportDetail() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.workSheet = this.workSheetManager.get(workSheetId);

		this.workStatuss = this.workSheet.getWorkStatuss();

		return SUCCESS;
	}

	public String userTeamReportSave() {

		WorkStatus w = new WorkStatus();

		w.setTitle(this.workStatus.getTitle());

		w.setReport(this.workStatus.getReport());

		w.setStatus(this.workStatus.getStatus());

		this.workSheet = this.workSheetManager.get(workSheetId);

		w.setWorkSheet(workSheet);

		w.setCreateDate(new Date());

		w.setLastModifiedDate(new Date());
		this.workStatusManager.save(w);

		return SUCCESS;
	}

	public String userReportList() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("person.id");
		item.setKeyword(this.person.getId().toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.member = this.memberManager.search(queryItems).get(0);

		this.team = this.member.getTeam();

		queryItems.clear();
		item = new QueryItem();
		item.setFieldName("project.id");
		item.setKeyword(team.getProject().getId().toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.workSheets = this.workSheetManager.search(queryItems);

		return SUCCESS;
	}

	public String userWorkList() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("person.id");
		item.setKeyword(this.person.getId().toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.member = this.memberManager.search(queryItems).get(0);

		this.workSheets = this.member.getWorkSheets();

		return SUCCESS;
	}

	public String userCreateLab() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("role.roleName");
		item.setKeyword("storeman");
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		this.persons = this.personManager.search(queryItems);

		return SUCCESS;
	}

	public String userAddDevice() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.labs = this.labManager.getAll();

		return SUCCESS;
	}

	public String userLabSave() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.person = this.personManager.get(personId);

		this.lab.setPerson(person);
		this.lab.setDeviceNumber(0);
		this.lab.setIsEngage(false);

		this.labManager.save(lab);

		return SUCCESS;
	}

	public String userDeviceSave() {
		System.out.println(this.device.getName());
		this.lab = this.labManager.get(labId);
		this.lab.setDeviceNumber(this.lab.getDeviceNumber() + 1);
		this.lab = this.labManager.save(lab);

		this.device.setStockDate(new Date());
		this.device.setLab(lab);
		this.device.setIsAvailable(true);

		this.deviceManager.save(device);

		return SUCCESS;
	}

	public String userTeamManage() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("team.teamLeader.person.id");
		item.setKeyword(this.person.getId().toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);

		if (0 == this.normalProjectManager.search(queryItems).size()) {
			this.classifiedProject = this.classifiedProjectManager.search(
					queryItems).get(0);
			this.team = classifiedProject.getTeam();
		} else {
			this.normalProject = this.normalProjectManager.search(queryItems)
					.get(0);
			this.team = normalProject.getTeam();
		}

		this.members = team.getMembers();

		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String userWorkSheetSave() {
		this.workSheet.setProject(this.projectManager.get(projectId));
		this.workSheet.setCreator(this.memberManager.get(creatorId));
		this.workSheet.setOwner(this.memberManager.get(memberId));
		Date date = new Date();
		date.setYear(this.year - 1900);
		date.setMonth(this.month - 1);
		date.setDate(this.day);
		this.workSheet.setDeadline(date);
		date = new Date();
		this.workSheet.setCreateDate(date);
		this.workSheet.setLastModifiedDate(date);

		this.workSheetManager.save(workSheet);

		return SUCCESS;
	}

	public String userWorkSheetDetail() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.workSheet = this.workSheetManager.get(workSheetId);

		this.project = this.projectManager.get(this.workSheet.getProject()
				.getId());
		this.member = this.memberManager.get(this.workSheet.getCreator()
				.getId());

		this.temp = this.personManager.get(member.getPerson().getId());

		return SUCCESS;
	}

	public String userLabDevice() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.labs = this.labManager.getAll();

		if (null != labId) {
			List<QueryItem> queryItems = new ArrayList<QueryItem>();
			QueryItem item;
			item = new QueryItem();
			item.setFieldName("lab.id");
			item.setKeyword(labId.toString());
			item.setQueryType(QueryType.EQ);
			queryItems.add(item);

			this.devices = this.deviceManager.search(queryItems);
		}

		return SUCCESS;
	}

	public String userLabDeviceSearch() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public WorkStatus getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(WorkStatus workStatus) {
		this.workStatus = workStatus;
	}

	public WorkStatusManager getWorkStatusManager() {
		return workStatusManager;
	}

	public void setWorkStatusManager(WorkStatusManager workStatusManager) {
		this.workStatusManager = workStatusManager;
	}

	public List<WorkStatus> getWorkStatuss() {
		return workStatuss;
	}

	public void setWorkStatuss(List<WorkStatus> workStatuss) {
		this.workStatuss = workStatuss;
	}

	public Integer getLabId() {
		return labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(Integer teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getWorkSheetId() {
		return workSheetId;
	}

	public void setWorkSheetId(Integer workSheetId) {
		this.workSheetId = workSheetId;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public NormalProject getNormalProject() {
		return normalProject;
	}

	public void setNormalProject(NormalProject normalProject) {
		this.normalProject = normalProject;
	}

	public ClassifiedProject getClassifiedProject() {
		return classifiedProject;
	}

	public void setClassifiedProject(ClassifiedProject classifiedProject) {
		this.classifiedProject = classifiedProject;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public Person getTemp() {
		return temp;
	}

	public void setTemp(Person temp) {
		this.temp = temp;
	}

	public WorkSheet getWorkSheet() {
		return workSheet;
	}

	public void setWorkSheet(WorkSheet workSheet) {
		this.workSheet = workSheet;
	}

	public RegisterManager getRegisterManager() {
		return registerManager;
	}

	public void setRegisterManager(RegisterManager registerManager) {
		this.registerManager = registerManager;
	}

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public DegreeManager getDegreeManager() {
		return degreeManager;
	}

	public void setDegreeManager(DegreeManager degreeManager) {
		this.degreeManager = degreeManager;
	}

	public NormalProjectManager getNormalProjectManager() {
		return normalProjectManager;
	}

	public void setNormalProjectManager(
			NormalProjectManager normalProjectManager) {
		this.normalProjectManager = normalProjectManager;
	}

	public ClassifiedProjectManager getClassifiedProjectManager() {
		return classifiedProjectManager;
	}

	public void setClassifiedProjectManager(
			ClassifiedProjectManager classifiedProjectManager) {
		this.classifiedProjectManager = classifiedProjectManager;
	}

	public TeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(TeamManager teamManager) {
		this.teamManager = teamManager;
	}

	public MemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public LabManager getLabManager() {
		return labManager;
	}

	public void setLabManager(LabManager labManager) {
		this.labManager = labManager;
	}

	public WorkSheetManager getWorkSheetManager() {
		return workSheetManager;
	}

	public void setWorkSheetManager(WorkSheetManager workSheetManager) {
		this.workSheetManager = workSheetManager;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public List<Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<Degree> degrees) {
		this.degrees = degrees;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Integer> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Integer> memberIds) {
		this.memberIds = memberIds;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<WorkSheet> getWorkSheets() {
		return workSheets;
	}

	public void setWorkSheets(List<WorkSheet> workSheets) {
		this.workSheets = workSheets;
	}

	public List<Lab> getLabs() {
		return labs;
	}

	public void setLabs(List<Lab> labs) {
		this.labs = labs;
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