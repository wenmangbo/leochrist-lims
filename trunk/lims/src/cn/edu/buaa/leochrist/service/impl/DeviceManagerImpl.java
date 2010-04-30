package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.DeviceDao;
import cn.edu.buaa.leochrist.model.Device;
import cn.edu.buaa.leochrist.service.DeviceManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class DeviceManagerImpl extends GenericManagerImpl<Device, Integer>
		implements DeviceManager {

	DeviceDao deviceDao;

	public DeviceManagerImpl(DeviceDao deviceDao) {
		super(deviceDao);
		this.deviceDao = deviceDao;
	}

}