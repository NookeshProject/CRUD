<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Insert Values</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
    		padding: 0;

			
           /* background-image: linear-gradient(90deg, orange, gray, maroon);*/
            
     }
        .form-container {
            width: 300px;
            margin: 50px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            animation: fadeIn 0.5s ease forwards;
        }
        input[type="text"] {
            width: 100%;
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        button[type="submit"] {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #4CAF50;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button[type="submit"]:hover {
            background-color: #45a049;
        }

        /* Keyframe animations */
        @keyframes slideInDown {
            from {
                transform: translateY(-50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
        @keyframes slideInLeft {
            from {
                transform: translateX(-50px);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
        @keyframes slideInRight {
            from {
                transform: translateX(50px);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
        
        a{
	   padding: 10px;
    cursor: pointer;
    background-color: maroon;
    text-decoration:none;
    color: white;
    border: none;
    border-radius: 5px;
    margin: 25px 16px 11px 100px;
    position: absolute;
    
}
h2{
 text-align: center;
}
    </style>
    <script>
        function showSuccessPopup() {
            
            var id = document.forms["form"]["id"].value;
            var name = document.forms["form"]["name"].value;
            var salary = document.forms["form"]["salary"].value;

            if (id === ""||id==0 || name === "" ||salary === "") {
                
                return alert("give appropiate values!");
            } else {
                return alert("Employee added successfully!");
            }
        }
    </script>
</head>
<body>
    <div class="form-container">
        <h2>ADD YOUR DETAILS</h2>
        <form name="form" action="insert" method="post" onsubmit="showSuccessPopup()">
            <input type="text" name="id" placeholder="Employee ID" required="required">
            <input type="text" name="name" placeholder="Name" required="required">
            <input type="text" name="designation" placeholder="Designation" required="required" >
            <input type="text" name="salary" placeholder="Salary" required="required">
            
            <div class="captcha-box">

	            <audio controls>
				    <source src="audio-captcha?ts=<%=System.currentTimeMillis()%>" type="audio/wav">
				    Your browser does not support audio.
				</audio>
	
	            <button type="button" class="refresh-btn" onclick="refreshCaptcha()">
	                🔄 Refresh Audio
	            </button>
	
	            <input type="text"
	                   name="captcha"
	                   placeholder="Enter what you hear"
	                   required>
	
	        </div>
	        <!-- 🔐 AUDIO CAPTCHA END -->

            <button type="submit">Submit</button>
        </form>
        <a href="index.jsp"> Home page</a>
    </div>
</body>
</html>
