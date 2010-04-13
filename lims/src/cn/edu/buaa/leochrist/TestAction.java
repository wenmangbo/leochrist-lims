package cn.edu.buaa.leochrist;

import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.service.RegisterManager;

import com.opensymphony.xwork2.Action;

public class TestAction implements Action {

	private Register register;

	private RegisterManager registerManager;

	@Override
	public String execute() throws Exception {
		this.register = new Register();
		this.register.setUsername("leochrist");
		this.register.setPassword("leochrist");
		this.register.setIsAvailable(true);
		this.registerManager.save(register);
		return SUCCESS;
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
