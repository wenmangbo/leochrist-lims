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

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport {

	private String message;

	private Register register;

	private Person person;

	private Role role;

	private RegisterManager registerManager;

	private PersonManager personManager;

	private DegreeManager degreeManager;

	private List<Degree> degrees;

	public String index() {
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
		System.out.println(this.degrees.size()+"+++++++++++++++");
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
		
		System.out.println(this.person.getName()+"~~~~~~~~~~~~~!!!!!");
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

		System.out.println(this.register.getPerson().getName()+"~~~~~~~~~~~~~~~~`");
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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