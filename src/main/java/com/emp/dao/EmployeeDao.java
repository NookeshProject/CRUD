package com.emp.dao;

import java.util.List;

import com.emp.model.Employee;

public interface EmployeeDao {
	public List<Employee> getAllEmployeesList();
	public List<Employee> getEmployeesBySearchCriteria(int id,String name,double sal);
	public List<Employee> getEmployeesBySalaryAbove(double salary);
	public List<Employee> getEmployeesBySalaryBelow(double salary);
	public List<Employee> getEmployeesBySalaryBelowSort(double sal);
	public List<Employee> getEmployeesBySalaryAboveSort(double sal);
	public void addEmployee(Employee employee);
}
