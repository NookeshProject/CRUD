package com.emp.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.emp.dao.EmployeeDao;
import com.emp.dao.impl.EmployeeDaoImpl;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public List<Employee> getAllEmployeesList() {
		EmployeeDao dao = null;
		List<Employee> employeeList = null;
		try {
		dao = new EmployeeDaoImpl();
		employeeList =  dao.getAllEmployeesList();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return employeeList;
	}

	@Override
	public List<Employee> getEmployeesBySearchCriteria(int id, String name, double sal) {
		EmployeeDao dao = new EmployeeDaoImpl();
		try {
			
			return  dao.getEmployeesBySearchCriteria(id, name, sal);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	
	}

	@Override
	public List<Employee> getEmployeesBySalaryAbove(double sal) {
	    EmployeeDao dao = new EmployeeDaoImpl();
	    try {
	        return dao.getEmployeesBySalaryAbove(sal);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<Employee> getEmployeesBySalaryBelow(double sal) {
		EmployeeDao dao = new EmployeeDaoImpl();
	    try {
	        return dao.getEmployeesBySalaryBelow(sal);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<Employee> getEmployeesBySalaryBelowSort(double sal) {
		EmployeeDao dao = new EmployeeDaoImpl();
	    try {
	        return dao.getEmployeesBySalaryBelowSort(sal);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<Employee> getEmployeesBySalaryAboveSort(double sal) {
		EmployeeDao dao = new EmployeeDaoImpl();
	    try {
	        return dao.getEmployeesBySalaryAboveSort(sal);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	@Override
	public boolean addEmployee(Employee employee) {
		EmployeeDao dao = new EmployeeDaoImpl();
	    try {
	        dao.addEmployee(employee);
	    } catch (Exception e) {
	        e.printStackTrace();
	       
	    }
		return true;
	}
	
		
	
}
	

