package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.DeviceDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Device;

public class DeviceDaoHibernate extends GenericDaoHibernate<Device, Integer>
		implements DeviceDao {

	public DeviceDaoHibernate() {
		super(Device.class);
	}

}