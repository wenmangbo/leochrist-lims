package cn.edu.buaa.leochrist.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.buaa.leochrist.model.Degree;
import cn.edu.buaa.leochrist.model.Dissertation;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.model.generic.QueryItem;
import cn.edu.buaa.leochrist.model.generic.QueryItem.QueryType;
import cn.edu.buaa.leochrist.service.DegreeManager;
import cn.edu.buaa.leochrist.service.DissertationManager;
import cn.edu.buaa.leochrist.service.PersonManager;
import cn.edu.buaa.leochrist.service.RegisterManager;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PersonAction extends ActionSupport {

	private File file;

	private Integer delId;

	private Dissertation dissertation;

	private Integer degreeId;

	private String password;

	private Integer year;

	private Integer month;

	private Integer day;

	private Register register;

	private Person person;

	private Person p;

	private Role role;

	private Degree degree;

	private DegreeManager degreeManager;

	private PersonManager personManager;

	private RegisterManager registerManager;

	private DissertationManager dissertationManager;

	private List<Degree> degrees;

	private List<Dissertation> dissertations;

	public String personView() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	public String personDel() {
		this.dissertationManager.remove(delId);
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String personEdit() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.degrees = this.degreeManager.getAll();

		year = person.getBirthday().getYear() + 1900;
		month = person.getBirthday().getMonth() + 1;
		day = person.getBirthday().getDate();

		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String personEditSave() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		this.person.setName(p.getName());
		
		this.person.setNativePlace(p.getNativePlace());

		this.person.setdCard(p.getdCard());

		this.person.setIntroduction(p.getIntroduction());
		
		this.person.setMail(p.getMail());

		Date date = new Date();

		this.person.setLastModifiedDate(date);
		date.setYear(this.year - 1900);
		date.setMonth(this.month - 1);
		date.setDate(this.day);
		this.person.setBirthday(date);

		this.degree = this.degreeManager.get(degreeId);

		this.person.setDegree(degree);

		this.register = this.person.getRegister();

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String str = password.trim();
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
		this.register = this.registerManager.save(this.register);

		this.person.setRegister(this.register);

		this.personManager.save(person);

		return SUCCESS;
	}

	public String personUpload() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String personUploadSave() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		String newFileName = new Date().toString() + ".pdf";
		File newFile = new File(
				"/home/leochrist/workspace/lims/WebContent/file/" + newFileName);

		try {
			FileOutputStream fos = new FileOutputStream(newFile);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			Dissertation d = new Dissertation();
			d.setAuthor(this.dissertation.getAuthor());
			d.setFile(newFileName);
			d.setClc(this.dissertation.getClc());
			d.setKeyword(this.dissertation.getKeyword());
			d.setMagazine(this.dissertation.getMagazine());
			d.setYearsVolume(this.dissertation.getYearsVolume());
			d.setInformation(this.dissertation.getInformation());
			Date date = new Date();
			d.setTitle(this.dissertation.getTitle());
			d.setUploadDate(date);
			date.setYear(this.year - 1900);
			date.setMonth(this.month - 1);
			date.setDate(this.day);
			d.setPubDate(date);
			d.setUploader(person);

			this.dissertationManager.save(d);
			System.out.println("文件上传成功");
		} catch (Exception e) {
			System.out.println("文件上传失败");
		}

		return SUCCESS;
	}

	public String personAllFile() {
		this.register = (Register) this.getRequest().getSession().getAttribute(
				"currentRegister");

		if (null != this.register) {
			this.person = this.register.getPerson();
			this.role = this.person.getRole();
		}

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		QueryItem item;
		item = new QueryItem();
		item.setFieldName("uploader.id");
		item.setKeyword(person.getId().toString());
		item.setQueryType(QueryType.EQ);
		queryItems.add(item);
		
		this.dissertations = this.dissertationManager.search(queryItems);

		return SUCCESS;
	}

	public Integer getDelId() {
		return delId;
	}

	public void setDelId(Integer delId) {
		this.delId = delId;
	}

	public List<Dissertation> getDissertations() {
		return dissertations;
	}

	public void setDissertations(List<Dissertation> dissertations) {
		this.dissertations = dissertations;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Dissertation getDissertation() {
		return dissertation;
	}

	public void setDissertation(Dissertation dissertation) {
		this.dissertation = dissertation;
	}

	public DissertationManager getDissertationManager() {
		return dissertationManager;
	}

	public void setDissertationManager(DissertationManager dissertationManager) {
		this.dissertationManager = dissertationManager;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
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

	public Person getP() {
		return p;
	}

	public void setP(Person p) {
		this.p = p;
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
