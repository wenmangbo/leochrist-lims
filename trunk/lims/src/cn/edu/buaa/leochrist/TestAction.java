package cn.edu.buaa.leochrist;

import java.util.Date;

import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.model.Storeman;
import cn.edu.buaa.leochrist.service.RegisterManager;
import cn.edu.buaa.leochrist.service.StoremanManager;

import com.opensymphony.xwork2.Action;

public class TestAction implements Action {

	private Storeman storeman;

	private Register register;

	private StoremanManager storemanManager;

	private RegisterManager registerManager;

	@Override
	public String execute() throws Exception {
		this.register = new Register();
		this.register.setUsername("leochrist");
		this.register.setPassword("123");
		this.register.setIsAvailable(true);
		this.register = this.registerManager.save(register);
		
		this.storeman = new Storeman();
		this.storeman.setRegister(this.register);
		this.storeman.setdCard("610321198505172114");
		this.storeman.setRegisteDate(new Date());
		this.storeman.setLastModifiedDate(new Date());
		this.storeman.setLastLoginDate(new Date());
		this.storeman = this.storemanManager.save(storeman);
		
		this.register.setPerson(storeman);
		this.registerManager.save(register);

		return SUCCESS;
	}

	public Storeman getStoreman() {
		return storeman;
	}

	public void setStoreman(Storeman storeman) {
		this.storeman = storeman;
	}

	public StoremanManager getStoremanManager() {
		return storemanManager;
	}

	public void setStoremanManager(StoremanManager storemanManager) {
		this.storemanManager = storemanManager;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public RegisterManager getRegisterManager() {
		return registerManager;
	}

	public void setRegisterManager(RegisterManager registerManager) {
		this.registerManager = registerManager;
	}

}
