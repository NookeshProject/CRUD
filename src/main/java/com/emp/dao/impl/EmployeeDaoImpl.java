package com.emp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.emp.dao.EmployeeDao;
import com.emp.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	static SessionFactory factory = new Configuration().configure().buildSessionFactory();

	@Override
	public List<Employee> getAllEmployeesList() {
		Session session = null;
		List<Employee> employeeList=null;
		try {
			session = factory.openSession();
			employeeList = session.createQuery("from Employee").list();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session!=null && session.isOpen()) {
				session.close();
				
			}
		}
		return employeeList;
	}
	
	public List<Employee> getEmployeesBySearchCriteria(int id,String name,double sal) {
		Session session = null;
		List<Employee> employeeList=null;
		try {
			session = factory.openSession();
			StringBuffer sb = new StringBuffer("from Employee where ");
			if(id!=0) {
				sb.append(" eId=:eId ");
			}
			
			if(id!=0 &&(name!=null && !name.equals("") || sal != 0)) {
				sb.append(" and ");
			}
			
			if(name!=null && !name.equals("")) {
				sb.append(" eName=:eName ");
			}
			
			if(name!=null && !name.equals("") && sal != 0) {
				sb.append(" and ");
			}
			if(sal != 0) {
				sb.append(" sal=:eSal ");
			}
			
			Query<Employee> qry = session.createQuery(sb.toString());
			
			if(id!=0) {
				qry.setInteger("eId", id);
			}
			
			if(name!=null && !name.equals("")) {
				qry.setString("eName", name);
			}
			
			if(sal != 0) {
				qry.setDouble("eSal", sal);
			}
			employeeList = qry.list();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session!=null && session.isOpen()) {
				session.close();
				
			}
		}
		return employeeList;
	}
	@Override
    public List<Employee> getEmployeesBySalaryAbove(double salary) {
		Session session = null;
        try  
        {
        	session = factory.openSession();
            Query<Employee> query = session.createQuery("FROM Employee WHERE sal > :salary");
            query.setParameter("salary", salary);	
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
			if (session!=null && session.isOpen()) {
				session.close();
				}
			}
    }

	@Override
	public List<Employee> getEmployeesBySalaryBelow(double salary) {
		Session session = null;
        try  
        {
        	session = factory.openSession();
            Query<Employee> query = session.createQuery("FROM Employee WHERE sal < :salary ORDER BY sal");
            query.setParameter("salary", salary);	
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
			if (session!=null && session.isOpen()) {
				session.close();
			}	
			}
	}

	@Override
	public List<Employee> getEmployeesBySalaryBelowSort(double sal) {
		Session session = null;
        try  
        {
        	session = factory.openSession();
            Query<Employee> query = session.createQuery("FROM Employee WHERE sal < :salary ORDER BY sal");
            query.setParameter("salary", sal);	
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
			if (session!=null && session.isOpen()) {
				session.close();
			}	
			}
	}
	@Override
	public List<Employee> getEmployeesBySalaryAboveSort(double sal) {
		Session session = null;
        try  
        {
        	session = factory.openSession();
            Query<Employee> query = session.createQuery("FROM Employee WHERE sal > :salary ORDER BY sal");
            query.setParameter("salary", sal);	
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
			if (session!=null && session.isOpen()) {
				session.close();
			}	
			}
	}

	
//----------------insert methods-------------------------------------
	
	public void addEmployee(Employee employee) {
	    Session session = null;
	    try {
	        session = factory.openSession();
	        session.beginTransaction();
	        session.save(employee);
	        session.getTransaction().commit();
	    } catch (Exception e) {
	        if (session != null && session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
	}

	
}
