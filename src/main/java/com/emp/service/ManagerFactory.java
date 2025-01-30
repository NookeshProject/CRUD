package com.emp.service;

public class ManagerFactory {
	
	public static Object getManagerInstance(String managerBean) {
		Object myClass = null;
		try {
			myClass = Class.forName(managerBean).newInstance();
		} catch (Exception e) {
		}
		return myClass;
	}

}
