package com.emp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.emp.constants.EmpSeviceConstants;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;
import com.emp.service.ManagerFactory;

@Controller
public class EmployeeSearchController {

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
    public String navigateToPage2() {
        System.out.println("➡️ Navigating to insert page");
        return "insert";
    }

    @PostMapping("/insert")
    public String insertEmployee(HttpServletRequest request) {

        System.out.println("➡️ Entered /insert");

        try {
            EmployeeService service =
                    (EmployeeService) ManagerFactory.getManagerInstance(EmpSeviceConstants.EMPLOYEE_SERVICE);

            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String designation = request.getParameter("designation");
            String salary = request.getParameter("salary");

            System.out.println("📥 Insert Params → id=" + id +
                    ", name=" + name +
                    ", designation=" + designation +
                    ", salary=" + salary);

            // 🔐 CAPTCHA VALIDATION
            HttpSession session = request.getSession(false);
            String userCaptcha = request.getParameter("captcha");
            String sessionCaptcha = (String) session.getAttribute("AUDIO_CAPTCHA");

            System.out.println("🔐 Captcha → user=" + userCaptcha +
                    ", session=" + sessionCaptcha);

            session.removeAttribute("AUDIO_CAPTCHA");

            if (sessionCaptcha == null || userCaptcha == null ||
                    !sessionCaptcha.equals(userCaptcha)) {

                System.out.println("❌ CAPTCHA FAILED");
                request.setAttribute("errorMessage", "Invalid audio captcha. Please try again.");
                return "insert";
            }

            if (id == null || id.equals("0") || id.trim().isEmpty()
                    || name == null || name.trim().isEmpty()) {

                System.out.println("❌ Validation failed");
                request.setAttribute("errorMessage",
                        "Failed to insert employee. Please give appropriate values.");
                return "insert";
            }

            Employee employee = new Employee();
            employee.seteId(Integer.parseInt(id));
            employee.seteName(name);
            employee.setDesignation(designation);
            employee.setSal(Double.parseDouble(salary));

            boolean success = service.addEmployee(employee);

            System.out.println("💾 Insert result: " + success);

            if (success) {
                return "redirect:/index.jsp";
            } else {
                request.setAttribute("errorMessage",
                        "Failed to insert employee. Please try again.");
                return "insert";
            }

        } catch (Exception e) {
            System.out.println("❌ Exception in /insert");
            e.printStackTrace();
            request.setAttribute("errorMessage",
                    "An error occurred while processing your request.");
            return "insert";
        }
    }
}
