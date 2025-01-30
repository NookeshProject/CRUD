package com.emp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.emp.constants.EmpSeviceConstants;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;
import com.emp.service.ManagerFactory;

@Controller
public class HomeController {

	
//direct employee details on the home page
	@GetMapping("/home")
	public String homePage(HttpServletRequest request) {

		EmployeeService service = null;
		List<Employee> list = null;
		try{
			service =(EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);
		    list = service.getAllEmployeesList();
		    request.setAttribute("empList", list);
		}
		catch (Exception e) {
		e.printStackTrace();	
		}
		return "empHome";
	}
	
	

	
}
