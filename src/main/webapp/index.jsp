<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Home page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: radial-gradient(skyblue,black);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
           
        }
        .container {
            text-align: center;
        }
        .container h1 {
            margin-bottom: 20px;
        }
        .button-group {
            margin-top: 20px;
        }
        .button-group a {
            display: inline-block;
            text-decoration: none;
            padding: 10px 20px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            margin-right: 10px;
            transition: background-color 0.3s;
        }
        .button-group a:hover {
            background-color: #45a049;
            box-shadow: rgb(255, 255, 255)0px 3px 20px;
            
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Choose your option</h1>
        <div class="button-group">
            <a href="${contextPath}/home" title="Employee table">Fetch employee</a>
            <a href="${contextPath}/inserting" title="To add details into table">Insert Employee</a>
            
        </div>
    </div>
</body>
</html>
