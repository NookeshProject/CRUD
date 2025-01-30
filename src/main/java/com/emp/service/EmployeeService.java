package com.emp.service;

import java.util.List;

import com.emp.model.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployeesList();
	public List<Employee> getEmployeesBySearchCriteria(int id,String name,double sal);
	public List<Employee> getEmployeesBySalaryAbove(double sal);
	public List<Employee> getEmployeesBySalaryBelow(double sal);
	public List<Employee> getEmployeesBySalaryBelowSort(double sal);
	public List<Employee> getEmployeesBySalaryAboveSort(double sal);
	public boolean addEmployee(Employee employee);
	
}
