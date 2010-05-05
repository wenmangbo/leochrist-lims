package cn.edu.buaa.leochrist.actions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.Degree;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.service.DegreeManager;
import cn.edu.buaa.leochrist.service.PersonManager;
import cn.edu.buaa.leochrist.service.RegisterManager;
import cn.edu.buaa.leochrist.service.RoleManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class InitializeAction extends ActionSupport {

	private Person person;

	private Register register;

	private List<Role> roles;

	private List<Degree> degrees;

	private PersonManager personManager;

	private RegisterManager registerManager;

	private RoleManager roleManager;

	private DegreeManager degreeManager;

	public String initialize() {
		Role role = new Role();
		role.setRoleName("admin");
		role = this.roleManager.save(role);

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
		this.register.setIsAvailable(true);
		this.register = this.registerManager.save(this.register);

		this.person.setRegister(this.register);
		this.person.setRole(role);
		Date date = new Date();
		this.person.setRegisteDate(date);
		this.person.setLastModifiedDate(date);
		this.person.setLastLoginDate(date);
		this.person = this.personManager.save(this.person);

		this.register.setPerson(this.person);
		this.registerManager.save(this.register);

		for (int n = 0; n < roles.size() / 2; n++) {
			if (null != roles.get(n + roles.size() / 2).getRoleName()) {
				Role r = new Role();
				r.setRoleName(roles.get(n + roles.size() / 2).getRoleName());
				r.setDescription(roles.get(n).getDescription());
				this.roleManager.save(r);
			}
		}

		for (int n = 0; n < degrees.size() / 2; n++) {
			if (null != degrees.get(n).getDegreeName()) {
				Degree d = new Degree();
				d.setDegreeName(degrees.get(n).getDegreeName());
				d.setDescription(degrees.get(n + degrees.size() / 2)
						.getDescription());
				this.degreeManager.save(d);
			}
		}

		this.register = this.registerManager.findByUsername(this.register
				.getUsername().trim());

		this.getRequest().getSession().setAttribute("currentRegister",
				this.register);
		System.out.println("register:" + this.register.getId()
				+ " save in session!");

		return SUCCESS;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<Degree> degrees) {
		this.degrees = degrees;
	}

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
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

	public DegreeManager getDegreeManager() {
		return degreeManager;
	}

	public void setDegreeManager(DegreeManager degreeManager) {
		this.degreeManager = degreeManager;
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
