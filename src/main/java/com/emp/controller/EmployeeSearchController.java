package com.emp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emp.captchaService.CaptchaService;
import com.emp.constants.EmpSeviceConstants;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;
import com.emp.service.ManagerFactory;

@Controller
public class EmployeeSearchController {
	@Autowired
    private CaptchaService captchaService;

    // ================= SEARCH BY CRITERIA =================

    @GetMapping("/search")
    public String homePage(HttpServletRequest request) {

        System.out.println("➡️ Entered /search");

        EmployeeService service = null;
        List<Employee> list = null;

        try {
            service = (EmployeeService) ManagerFactory
                    .getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String id = (request.getParameter("Id") != null && !request.getParameter("Id").isEmpty())
                    ? request.getParameter("Id") : "0";
            String name = (request.getParameter("Name") != null && !request.getParameter("Name").isEmpty())
                    ? request.getParameter("Name") : "";
            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").isEmpty())
                    ? request.getParameter("Sal") : "0";

            System.out.println("🔍 Search Params → id=" + id + ", name=" + name + ", sal=" + sal);

            list = service.getEmployeesBySearchCriteria(
                    Integer.valueOf(id), name, Double.valueOf(sal));

            System.out.println("✅ Employees found: " + (list != null ? list.size() : 0));

            request.setAttribute("empList", list);
            request.setAttribute("empId", id);
            request.setAttribute("name", name);
            request.setAttribute("sal", sal);

        } catch (Exception e) {
            System.out.println("❌ Error in /search");
            e.printStackTrace();
        }

        return "empHome";
    }

    // ================= SEARCH BY SALARY =================

    @GetMapping("/searchBySalAbove")
    public String searchEmployeesBySalaryAbove(HttpServletRequest request) {

        System.out.println("➡️ Entered /searchBySalAbove");

        try {
            EmployeeService service =
                    (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").isEmpty())
                    ? request.getParameter("Sal") : "0";

            System.out.println("🔍 Salary Above: " + sal);

            List<Employee> list = service.getEmployeesBySalaryAbove(Double.valueOf(sal));

            System.out.println("✅ Employees found: " + (list != null ? list.size() : 0));

            request.setAttribute("empList", list);
            request.setAttribute("sal", sal);

        } catch (Exception e) {
            System.out.println("❌ Error in /searchBySalAbove");
            e.printStackTrace();
        }

        return "empHome";
    }

    @GetMapping("/searchBySalBelow")
    public String searchEmployeesBySalaryBelow(HttpServletRequest request) {

        System.out.println("➡️ Entered /searchBySalBelow");

        try {
            EmployeeService service =
                    (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").isEmpty())
                    ? request.getParameter("Sal") : "0";

            System.out.println("🔍 Salary Below: " + sal);

            List<Employee> list = service.getEmployeesBySalaryBelow(Double.valueOf(sal));

            System.out.println("✅ Employees found: " + (list != null ? list.size() : 0));

            request.setAttribute("empList", list);
            request.setAttribute("sal", sal);

        } catch (Exception e) {
            System.out.println("❌ Error in /searchBySalBelow");
            e.printStackTrace();
        }

        return "empHome";
    }

    // ================= SORT METHODS =================

    @GetMapping("/sortwithAboveSal")
    public String searchEmployeesBySalaryAboveSort(HttpServletRequest request) {

        System.out.println("➡️ Entered /sortwithAboveSal");

        try {
            EmployeeService service =
                    (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").isEmpty())
                    ? request.getParameter("Sal") : "0";

            System.out.println("🔄 Sorting Above Salary: " + sal);

            List<Employee> list = service.getEmployeesBySalaryAboveSort(Double.valueOf(sal));

            request.setAttribute("empList", list);
            request.setAttribute("sal", sal);

        } catch (Exception e) {
            System.out.println("❌ Error in /sortwithAboveSal");
            e.printStackTrace();
        }

        return "empHome";
    }

    @GetMapping("/sortwithBelowSal")
    public String searchEmployeesBySalaryBelowSort(HttpServletRequest request) {

        System.out.println("➡️ Entered /sortwithBelowSal");

        try {
            EmployeeService service =
                    (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String sal = (request.getParameter("Sal") != null && !request.getParameter("Sal").isEmpty())
                    ? request.getParameter("Sal") : "0";

            System.out.println("🔄 Sorting Below Salary: " + sal);

            List<Employee> list = service.getEmployeesBySalaryBelowSort(Double.valueOf(sal));

            request.setAttribute("empList", list);
            request.setAttribute("sal", sal);

        } catch (Exception e) {
            System.out.println("❌ Error in /sortwithBelowSal");
            e.printStackTrace();
        }

        return "empHome";
    }

    // ================= INSERT =================

    @GetMapping("/inserting")
    public String navigateToPage2(HttpSession session) {

        // Generate captcha on first load
        String captcha = captchaService.generateCaptcha();

        session.setAttribute("AUDIO_CAPTCHA", captcha);

        System.out.println("🔐 Captcha generated on page load: " + captcha);

        return "insert";
    }


    @PostMapping("/insert")
    @ResponseBody
    public Map<String, Object> insertEmployee(HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        try {
            EmployeeService service =
                (EmployeeService) ManagerFactory.getManagerInstance(
                        EmpSeviceConstants.EMPLOYEE_SERVICE);

            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String designation = request.getParameter("designation");
            String salary = request.getParameter("salary");

            // 🔐 CAPTCHA
            HttpSession session = request.getSession(false);
            String userCaptcha = request.getParameter("captcha");
            String sessionCaptcha = (String) session.getAttribute("AUDIO_CAPTCHA");
            session.removeAttribute("AUDIO_CAPTCHA");

            if (sessionCaptcha == null || userCaptcha == null ||
                !sessionCaptcha.equals(userCaptcha)) {

                result.put("status", "error");
                result.put("message", "Invalid audio captcha. Please try again.");
                return result;
            }

            if (id == null || id.equals("0") || id.trim().isEmpty()
                    || name == null || name.trim().isEmpty()) {

                result.put("status", "error");
                result.put("message", "Please give appropriate values.");
                return result;
            }

            Employee employee = new Employee();
            employee.seteId(Integer.parseInt(id));
            employee.seteName(name);
            employee.setDesignation(designation);
            employee.setSal(Double.parseDouble(salary));

            boolean success = service.addEmployee(employee);

            if (success) {
                result.put("status", "success");
                result.put("redirect", "empHome");
            } else {
                result.put("status", "error");
                result.put("message", "Failed to insert employee.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Server error occurred.");
        }

        return result;
    }
}
