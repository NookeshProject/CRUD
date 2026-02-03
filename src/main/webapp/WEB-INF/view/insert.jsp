<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Insert Values</title>
 <style>
/* ===== PAGE ===== */
body {
    font-family: "Segoe UI", Arial, sans-serif;
    background: #1f0e62b8;
    margin: 0;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

/* ===== FORM ===== */
.form-container {
    width: 320px;
    background: #ffffff;
    padding: 18px 20px;
    border-radius: 6px;
    box-shadow: 0 4px 14px rgba(0,0,0,0.1);
}

/* ===== TITLE ===== */
.form-container h2 {
    text-align: center;
    font-size: 17px;
    margin-bottom: 14px;
    color: #333;
}

/* ===== INPUTS ===== */
input[type="text"] {
    width: 95%;
    padding: 9px 10px;
    margin-bottom: 10px;
    border: 1px solid #dcdfe3;
    border-radius: 4px;
    font-size: 13px;
}

input[type="text"]:focus {
    outline: none;
    border-color: #4a6cf7;
}

/* ===== CAPTCHA BOX (MEDIUM SIZE) ===== */
.captcha-box {
    background: #f9fafb;
    border: 1px solid #e5e7eb;
    border-radius: 5px;
    padding: 8px 10px;        /* reduced */
    margin-bottom: 12px;
    justify-items: center;
}

/* Row */
.captcha-row {
    display: flex;
    align-items: center;
    gap: 8px;                /* reduced */
    margin-bottom: 8px;
}

/* Canvas */
#captchaCanvas {
    border: 1px solid #cbd5e1;
    border-radius: 4px;
    background: #ffffff;
}

/* Icon buttons */
.captcha-icons {
    display: flex;
    flex-direction: column;
    gap: 4px;                /* reduced */
}

.captcha-icons button {
    width: 30px;             /* reduced */
    height: 30px;
    border-radius: 5px;
    border: 1px solid #cbd5e1;
    background: #eef2ff;
    cursor: pointer;
    font-size: 14px;         /* reduced */
}

.captcha-icons button:hover {
    background: #e0e7ff;
}

/* Input */
.captcha-box input {
    width: 65%;
    padding: 7px;
    border-radius: 4px;
    border: 1px solid #cbd5e1;
    font-size: 13px;
    display: block;
    margin: 6px auto 0 auto;
}


/* ===== BUTTONS ===== */
.refresh-btn {
    width: 100%;
    background: #f1f3f5;
    border: 1px solid #dcdfe3;
    padding: 7px;
    font-size: 12px;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom: 6px;
}

.refresh-btn:hover {
    background: #e6e9ed;
}

button[type="button"] {
    width: 100%;
    padding: 10px;
    background: #4a6cf7;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 14px;
    cursor: pointer;
}

button[type="button"]:hover {
    background: #3f5be0;
}

/* ===== LINK ===== */
a {
    display: block;
    text-align: center;
    margin-top: 12px;
    font-size: 12px;
    color: #4a6cf7;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}

/* ===== PULL REFRESH (MINIMAL) ===== */
#pullRefresh {
    position: fixed;
    top: -50px;
    left: 50%;
    transform: translateX(-50%);
    background: #fff;
    padding: 8px 14px;
    border-radius: 0 0 6px 6px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    font-size: 13px;
    display: flex;
    gap: 8px;
    align-items: center;
    transition: top 0.3s ease;
}

#pullRefresh.show {
    top: 0;
}

.spinner {
    width: 14px;
    height: 14px;
    border: 2px solid #ddd;
    border-top: 2px solid #4a6cf7;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}
</style>

<script>

    function submitForm() {

        var form = document.getElementById("empForm");
        var formData = new FormData(form);

        // 🔎 client validation
        if (!formData.get("id") || formData.get("id") == 0 ||
            !formData.get("name") || !formData.get("salary")) {

            alert("Give appropriate values!");
            return;
        }else if(!formData.get("captcha")) {

            alert("Please enter captcha.");
            return;
        }

        fetch("insert", {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(data => {

            if (data.status === "error") {
                alert(data.message);
            }
            else if (data.status === "success") {
                alert("Employee added successfully!");
                window.location.href = data.redirect;
            }

        })
        .catch(error => {
            alert("Something went wrong. Please try again.");
            console.error(error);
        });
    }
    function drawCaptcha(text) {
        const canvas = document.getElementById("captchaCanvas");
        const ctx = canvas.getContext("2d");

        // clear
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // background
        ctx.fillStyle = "#ffffff";
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // noise dots
        for (let i = 0; i < 80; i++) {
            ctx.fillStyle = "#c7d2fe";
            ctx.beginPath();
            ctx.arc(
                Math.random() * canvas.width,
                Math.random() * canvas.height,
                1.2, 0, Math.PI * 2
            );
            ctx.fill();
        }

        // text
        ctx.font = "bold 24px Arial";
		ctx.fillText(text, 16, 30);
        ctx.fillStyle = "#465c98";
        ctx.setTransform(1, 0.1, -0.1, 1, 0, 0);
        ctx.setTransform(1, 0, 0, 1, 0, 0);

        // curve
        drawRandomCurve(ctx, canvas.width, canvas.height);
    }

    function drawRandomCurve(ctx, width, height) {
        ctx.strokeStyle = "#8b9cf5";
        ctx.lineWidth = 2;

        ctx.beginPath();

        let x = 0;
        let y = height / 2 + (Math.random() * 10 - 5);
        ctx.moveTo(x, y);

        while (x < width) {
            x += 10;
            y += Math.random() * 10 - 5;
            ctx.lineTo(x, y);
        }

        ctx.stroke();
    }
    /* Play audio */
    function playCaptcha() {
        const audio = document.getElementById("captchaAudio");
        audio.currentTime = 0;
        audio.play();
    }

    /* Refresh both image & audio */
    function refreshCaptcha() {
        fetch("refresh-captcha")
            .then(res => res.text())
            .then(text => {
                drawCaptcha(text);
                const audio = document.getElementById("captchaAudio");
                audio.src = "audio-captcha?ts=" + Date.now();
                audio.load();
            });
    }

    /* Draw on page load */
    window.onload = function () {
        const captchaText = "<%= session.getAttribute("AUDIO_CAPTCHA") %>";
        if (captchaText && captchaText !== "null") {
            drawCaptcha(captchaText);
        }
    };
    
</script>
</head>
<body>
<div id="pullRefresh">
    <div class="spinner"></div>
    <span>Release to refresh</span>
</div>

    <div class="form-container">
        <h2>ADD YOUR DETAILS</h2>
        <form id="empForm">
		    <input type="text" name="id" placeholder="Employee ID">
		    <input type="text" name="name" placeholder="Name">
		    <input type="text" name="designation" placeholder="Designation">
		    <input type="text" name="salary" placeholder="Salary">
		    
		    <div class="captcha-box">
			    <div class="captcha-row">
			        <!-- Captcha Image -->
			        <canvas id="captchaCanvas" width="140" height="42"></canvas>
			
			        <!-- Controls -->
			        <div class="captcha-icons">
			            <button type="button" title="Play audio" onclick="playCaptcha()">🔊</button>
			            <button type="button" title="Refresh captcha" onclick="refreshCaptcha()">↻</button>
			        </div>
			    </div>
			
			    <!-- Input -->
			    <input type="text" name="captcha" placeholder="Enter Captcha">
			
			    <!-- Hidden audio -->
			    <audio id="captchaAudio">
			        <source src="audio-captcha?ts=<%=System.currentTimeMillis()%>" type="audio/wav">
			    </audio>
			
			</div>


	        <!-- 🔐 AUDIO CAPTCHA END -->

            <button type="button" onclick="submitForm()">Submit</button>
        </form>
        <a href="index.jsp"> Home page</a>
    </div>
<script type="text/javascript">
let lastScrollTop = 0;
let refreshTriggered = false;
const refreshBar = document.getElementById("pullRefresh");

window.addEventListener("scroll", function () {
    const currentScrollTop = window.pageYOffset || document.documentElement.scrollTop;

    // Detect scroll UP
    if (currentScrollTop < lastScrollTop) {

        // User reached top
        if (currentScrollTop === 0 && !refreshTriggered) {
            refreshTriggered = true;

            // Show animation
            refreshBar.classList.add("show");

            // Delay to let animation play
            setTimeout(() => {
                location.reload();
            }, 900);
        }
    }

    lastScrollTop = currentScrollTop <= 0 ? 0 : currentScrollTop;
});

</script>
</body>
</html>
