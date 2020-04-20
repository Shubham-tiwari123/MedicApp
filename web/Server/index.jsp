<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <style>
        body {
            margin: 0px;
            overflow: hidden;
            background-color: #F8FAFB;
        }

        .div1 {
            overflow: hidden;
            background-color: transparent;
            height: 10vh;
            width: 100%;
        }

        .div3 {
            float: left;
            background-color: transparent;
            height: 100%;
            width: 60%;
        }

        .div4 {
            height: 100%;
            width: 30%;
            float: right;
            background-color: white;
        }

        .div2 {
            background-color: white;
            height: 90vh;
            width: 100%;
        }

        #company-name {
            width: 20%;
            height: fit-content;
            padding-top: 18px;
            padding-left: 60px;
            float: left;
            color: #37A1F6;
            font: bold 35px Arial, Helvetica, sans-serif;
        }

        #login-form {
            width: 90%;
            height: fit-content;
            padding-bottom: 40px;
            background-color: white;
            margin-top: 55px;
            border-radius: 25px;
            border: none;
        }

        #welcome-msg {
            height: fit-content;
            width: 300px;
            color: #656667;
            margin-top: 60px;
            padding-bottom: 20px;
            font: bold 25px Arial, Helvetica, sans-serif;
        }

        #user_input {
            height: fit-content;
            width: inherit;
            margin-left: 10px;
        }

        #form_fill {
            width: 280px;
            margin-top: 25px;
            border-radius: 30px;
            height: 45px;
            border: 1px solid #6332cc;
            background-color: #E3E8FE;
        }

        #submitBtn {
            width: 280px;
            height: 43px;
            border-radius: 30px;
            background-color: #6332cc;
            border: none;
            color: white;
            font: bold 20px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            padding-top: 14%;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.2);
        }

        /* Modal Content */
        .modal-content {
            background-color: white;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 25%;
            height: 120px;
            border-radius: 20px;
        }

        #popup-text{
            font:bold 24px Arial, Helvetica, sans-serif;
            color: rgba(92, 93, 94, 0.78);
            text-align: center;
            width: 100%;
            height: fit-content;
            margin-top: 30px;
        }

        #confirm-btn{
            background-color: #57B846;
            border: #57B846;
            color: white;
            margin-left: 40%;
            height: 30px;
            width: 60px;
            border-radius: 40px;
            font:bold 16px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
        }
    </style>
</head>
<body>
<div id="popup-msg" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <p id="popup-text" >Patient ID: 58452945</p>
        <button id="confirm-btn">OK</button>
    </div>
</div>

<div class="div1">
    <div id="company-name">
        <b>Medic</b>
    </div>
</div>
<div class="div2">
    <div class="div3">
        <img src="static/bg.png" width="750px" height="550px" style="margin-top: 3%;margin-left: 30px">
        <!--<img src="static/bg1.jpg" width="700px" height="420px" style="margin-top: 40px; margin-left: 60px">-->
    </div>
    <div class="div4">
        <div id="login-form">
            <div id="welcome-msg" align="center">
                <b>Administrator Login</b>
            </div>
            <div id="user_input">
                <div id="form_fill">
                    <img src="static/person.png" style="float: left; height:30px; width: 35px;
                        margin-top: 6px; margin-left: 6px">
                    <input type="text" id="userEmail" placeholder="Username" name="userEmail" style=" width: 225px;
                        height: 100%;border: none; background-color: transparent;float: right;
                        margin-right: 10px;color: #616265; font: bold 16px Arial, Helvetica, sans-serif;">
                </div>

                <div id="form_fill">
                    <img src="static/pass.png" style="float: left; height:28px; width: 35px;
                        margin-top: 10px; margin-left: 6px">
                    <input type="password" id="userPass" placeholder="Password" style=" width: 225px; height: 100%;
                        border: none; background-color: transparent;float: right; margin-right: 10px;
                        color: #616265; font: bold 16px Arial, Helvetica, sans-serif;" name="userPass">
                </div>
                <div style=" height: fit-content; width: 100%; margin-top: 40px;">
                    <a href="#" style="color: inherit;text-decoration: none; margin-left: 5px">
                        <button id="submitBtn" onclick="sendResponse()">Sign In</button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.min.js "></script>
<script>
    let modal = document.getElementById("popup-msg");
    let conform_btn = document.getElementById("confirm-btn");

    conform_btn.onclick = function(){
        document.getElementById("popup-text").style.color = "rgba(92, 93, 94, 0.78)";
        modal.style.display = "none";

    };

    window.onload = function(){
        console.log("Loading window");
        document.getElementById("userEmail").value = "";
        document.getElementById("userPass").value = "";
    };

    async function sendResponse() {
        const email = document.getElementById("userEmail").value;
        const pass = document.getElementById("userPass").value;

        let flag = await validateForm(email,pass);

        if(flag) {
            modal.style.display = "block";
            document.getElementById("popup-text").innerText = "Checking credentials...";
            document.getElementById("confirm-btn").style.visibility="hidden";

            console.log("values:",email,pass);

            let response = $.post('/admin_login', {
                email: email,
                pass: pass
            });

            setTimeout(function () {
                response.success(function (result) {
                    document.getElementById("userEmail").value = "";
                    document.getElementById("userPass").value = "";
                    const resultObj = jQuery.parseJSON(result);
                    if(resultObj.statusCode===200){
                        // forward to login page
                        console.log("iffff");
                        window.location.replace('/admin_dashboard');
                    }else{
                        document.getElementById("popup-text").innerText = "Wrong credentials";
                        document.getElementById("popup-text").style.color = "#BA0606";
                        document.getElementById("confirm-btn").style.visibility ="visible";
                    }

                });

                response.error(function (jqXHR, textStatus, errorThrown) {
                    document.getElementById("popup-text").innerText = "Server Error";
                    document.getElementById("popup-text").style.color = "#BA0606";
                    document.getElementById("confirm-btn").style.visibility ="visible";
                })
            }, 2000);

        }
    }


    function validateForm(email,pass) {
        console.log("validating form");
        if(email===""){
            alert("Please provide email");
            document.getElementById("userEmail").focus();
            return false;
        }
        if(email!==""){
            let atpos = email.indexOf("@");
            let dotpos = email.indexOf(".");
            if(atpos<1 || (dotpos-atpos)<2){
                alert("Please provide valid email");
                document.getElementById("userEmail").focus();
                return false;
            }
        }
        if(pass ===""){
            alert("Please provide password");
            document.getElementById("userPass").focus();
            return false;
        }
        return true;
    }
</script>

</body>
</html>