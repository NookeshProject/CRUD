<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Details</title>


<style>

	body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #9dd8d6;
}

#h1div{
	text-align: center;
	font-family: monospace;
	color: black;
	box-shadow: #718eb2 2px;
	border:1px solid #0ed7ce;
	
background-color: antiquewhite;
	width: 25%;
	margin: 20px auto;
	border-radius: 28px;
}
.div1 {
    width: 80%;
    margin: 20px auto;
    background-color: antiquewhite;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 2px 2px  10px rgba(0, 0, 0, 0.868);
}

#error {
    color: #d9534f;
    margin-bottom: 10px;
}


.div2 {
    margin-bottom: 20px;
}

.div2 table {
    border-collapse: separate;
    border-spacing: 10px;
}

.div2 table td,
.div2 table th {
    padding: 8px;
    border: 0px solid white;
}

.div2 input[type="text"] {
    width: calc(100% - 10px);
    padding: 10px;
    box-sizing: border-box;
    border: 1px solid #718eb2;
    border-radius: 5px;
    
}
#eSal{
	width: 40%;
}
.button1{
	padding: 10px 20px;
    cursor: pointer;
    background-color: #5cb85c;
    color: white;
    border: none;
    border-radius: 5px;
}
.div2 input[type="button"] {
    padding: 10px 20px;
    cursor: pointer;
    background-color: #5cb85c;
    color: white;
    border: none;
    border-radius: 5px;
    margin-top:20px;
}


.empTable {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 10px;
}

.empTable th,
.empTable td {
    padding: 10px;
    text-align: left;
    border: 1px solid #be7f18;
}

.empTable th {
    background-color: #e1ca7c;
}

.empTable tr:nth-child(even) {
    background-color:   #8ca19c47;
}

.empTable tr:hover {
    background-color:#99969661; ;
}

a{
	padding:5px;
    cursor: pointer;
    background-color: maroon;
    text-decoration:none;
    color: white;
    border: none;
    border-radius: 5px;
    
}

	</style>
	
	
	
</head>



<body >
<div id = "h1div"><h1>EMPLOYEE DETAILS</h1></div>

<div class="div1">
	<div id="error" style="color: red"></div>
		<div class="div2">
			<form name="form" id="form1">
		Search here:
		<table >

			<tbody>
				<tr>
					<c:out value='${id}' />
					<td><label>Employee Id :<input type="text" name="Id" placeholder="eId"
						id="empId" value="<c:out value='${empId}'/>" /></td>
					<td><label>Employee Name</label> : <input type="text" placeholder="eName"
						name="Name" id="empName" value="<c:out value='${name}'/>" /></td>
					<td><label>Employee Sal</label> : <input type="text" placeholder="sal"
						name="Sal" id="empSal" value="<c:out value='${sal}'/>" /></td>
					<td><input type="button" value="Search"
						onclick="javascript:searchEmployee();" /></td>

				</tr>
			</tbody>
		
		</table>
		</form>
		
			<form name="form2" id="form2">
        	<input type="text" name="Sal" id="eSal" placeholder="Search by Salary" value="<c:out value='${sal}'/>"/>
        	<button type="button" onclick="searchEmployeeBySalary(true);" class="button1">Above Salary</button>
			<button type="button" onclick="searchEmployeeBySalary(false);" class="button1">Below Salary</button>
			<button type="submit" onclick="sortSal(true);" class="button1" id="sort1"  >Sort AboveSal</button>
			<button type="submit" onclick="sortSal(false);" class="button1" id="sort2"  >Sort BelowSal</button>

    		</form>
		
		
	</div>

		<table class="empTable">
			<thead>
				<tr>
					<th>Employee Id</th>
					<th>Employee Name</th>
					<th>Employee Designation</th>
					<th>Employee Salary</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty empList}">
						<c:forEach var="employee" items="${empList}">
							<tr>
								<td><c:out value="${employee.eId}" /></td>
								<td><c:out value="${employee.eName}" /></td>
								<td><c:out value="${employee.designation}" /></td>
								<td><c:out value="${employee.sal}" /></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4">No records found</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
			
					
		</table>
		<a href="index.jsp"> Home page</a>
	</div>
	
	
	
	
	
	
	<script type="text/javascript">
	function searchEmployee() {
		document.getElementById("error").innerHTML = "";
		var name = document.getElementById("empName").value;
		var id = document.getElementById("empId").value;
		var sal = document.getElementById("empSal").value;
		if (name == '' && id == '' && sal == '') {
			document.getElementById("error").innerHTML = "please enter any one value";
		} else {
			document.form.action = "search";
			document.form.submit();
		}
	}
	function searchEmployeeBySalary(isAbove) {
        var sal = document.getElementById("eSal").value.trim();
        if (sal === '') {
            document.getElementById("error").innerHTML = "Please enter a value for salary.";
        } else {
            document.getElementById("error").innerHTML = ""; // Clear error message
            document.form2.action = isAbove ? "searchBySalAbove" : "searchBySalBelow";
            document.form2.submit();
            document.getElementById("hideBut").style.display = "block"; // Show the "Sort Salary" button
        }
    }
	function sortSal(isSort) {
		var sal = document.getElementById("eSal").value.trim();
        if (sal === '') {
            document.getElementById("error").innerHTML = "Please enter a value for salary.";
        } else {
            document.getElementById("error").innerHTML = ""; // Clear error message
            document.form2.action = isSort ? "sortwithAboveSal" : "sortwithBelowSal";
            document.form2.submit();
            
        }
           
    }
	</script>
	
	
	
	
	
</body>
</html>
