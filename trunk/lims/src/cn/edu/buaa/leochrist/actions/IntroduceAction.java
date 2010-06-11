package cn.edu.buaa.leochrist.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.NormalProject;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.service.NormalProjectManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class IntroduceAction extends ActionSupport {

	private Register register;

	private Person person;

	private Role role;

	private NormalProject normalProject;

	private NormalProjectManager normalProjectManager;

	private List<NormalProject> normalProjects;

	public String introduceView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String introduceProject() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.normalProjects = this.normalProjectManager.getAll();

		return SUCCESS;
	}

	public NormalProject getNormalProject() {
		return normalProject;
	}

	public void setNormalProject(NormalProject normalProject) {
		this.normalProject = normalProject;
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
