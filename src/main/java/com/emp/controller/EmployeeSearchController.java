package com.emp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.emp.constants.EmpSeviceConstants;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;
import com.emp.service.ManagerFactory;

@Controller
public class EmployeeSearchController {

	
//------search by searchcriteria methods---------
	//new line
	
	@GetMapping("/search")
	public String homePage(HttpServletRequest request) {

		EmployeeService service = null;
		List<Employee> list = null;
		try{
			service =(EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);
		    
			String id = (request.getParameter("Id")!=null && !request.getParameter("Id").equals(""))?request.getParameter("Id"):"0";
			String name= (request.getParameter("Name")!=null && !request.getParameter("Name").equals(""))?request.getParameter("Name"):"";
			String sal =  (request.getParameter("Sal")!=null && !request.getParameter("Sal").equals(""))?request.getParameter("Sal"):"0";
			
			
			list = service.getEmployeesBySearchCriteria(Integer.valueOf(id), name, Double.valueOf(sal));
		    request.setAttribute("empList", list);
		    request.setAttribute("empId", id);
		    request.setAttribute("name", name);
		    request.setAttribute("sal", sal);
		}
		catch (Exception e) {
		e.printStackTrace();	
		}
		return "empHome";
	}
	
//----------------------------------------------------------------------------------
	
//-------search by salary greaterthan or lessthan methods--------

//----------------------------------------------------------------------------------	
	
	@GetMapping("/searchBySalAbove")
    public String searchEmployeesBySalaryAbove(HttpServletRequest request) {
        EmployeeService service = null;
        List<Employee> list = null;
        try {
            service = (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").equals("")) ? request.getParameter("Sal") : "0";

            list = service.getEmployeesBySalaryAbove(Double.valueOf(sal));
            request.setAttribute("empList", list);
		    request.setAttribute("sal", sal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "empHome";
    }
	
	@GetMapping("/searchBySalBelow")
    public String searchEmployeesBySalaryBelow(HttpServletRequest request) {
        EmployeeService service = null;
        List<Employee> list = null;
        try {
            service = (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").equals("")) ? request.getParameter("Sal") : "0";

            list = service.getEmployeesBySalaryBelow(Double.valueOf(sal));
            request.setAttribute("empList", list);
		    request.setAttribute("sal", sal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "empHome";
    }
//----------------------------------------------------------------------------------
	
// -----Sorting Order methods----------	

	//----------------------------------------------------------------------------------
	
	@GetMapping("/sortwithAboveSal")
	public String searchEmployeesBySalaryAboveSort(HttpServletRequest request) {
        EmployeeService service = null;
        List<Employee> list = null;
        try {
            service = (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").equals("")) ? request.getParameter("Sal") : "0";

            list = service.getEmployeesBySalaryAboveSort(Double.valueOf(sal));
            request.setAttribute("empList", list);
		    request.setAttribute("sal", sal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "empHome";
    }
	@GetMapping("/sortwithBelowSal")
    public String searchEmployeesBySalaryBelowSort(HttpServletRequest request) {
        EmployeeService service = null;
        List<Employee> list = null;
        try {
            service = (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").equals("")) ? request.getParameter("Sal") : "0";

            list = service.getEmployeesBySalaryBelowSort(Double.valueOf(sal));
            request.setAttribute("empList", list);
		    request.setAttribute("sal", sal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "empHome";
    }
	
//------------------------------------------------------------------------------
//---------------ineserting methods--------------------------------------------
//------------------------------------------------------------------------------

	@GetMapping("/inserting")
    public String navigateToPage2() {
        return "insert"; // This is the logical view name for page2.jsp
    }
	
	 @PostMapping("/insert")
	    public String insertEmployee(HttpServletRequest request) {
	        EmployeeService service = null;
	        try {
	            service = (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

	            String id = request.getParameter("id");
	            String name = request.getParameter("name");
	            String designation = request.getParameter("designation");
	            String salary = request.getParameter("salary");
	            if (id == null || id.equals("0") || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
	                request.setAttribute("errorMessage", "Failed to insert employee. Please give appropriate values.");
	                return "insert";
	            } else {


	            Employee employee = new Employee();
	            employee.seteId(Integer.parseInt(id));
	            employee.seteName(name);
	            employee.setDesignation(designation);
	            employee.setSal(Double.parseDouble(salary));

	            boolean success = service.addEmployee(employee);
	            if (success) {
	                return "redirect:/empHome"; // Redirect to the employee search page
	            } else {
	                
	                request.setAttribute("errorMessage", "Failed to insert employee. Please try again.");
	                return "empHome";
	            }
	           }
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	            request.setAttribute("errorMessage", "An error occurred while processing your request.");
	            return "empHome";
	        }
	        
	    }

}
