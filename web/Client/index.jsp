<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body{
            margin: 0px;
            overflow: hidden;
            background-color: #F8FAFB;
        }
        .div1 {
            overflow: hidden;
            background-color:transparent;
            height: 10vh;
            width: 100%;
        }

        .div3 {
            float: left;
            background-color: transparent;
            height: 100%;
            width: 60%;
        }

        .div4{
            height: 100%;
            width: 40%;
            float: right;
            background-color: transparent;
        }
        .div2 {
            background-color: transparent;
            height: 90vh;
            width: 100%;
        }
        #login-form{
            width: 60%;
            height: fit-content;
            padding-bottom: 40px;
            background-color: white;
            margin-top: 55px;
            margin-left: 120px;
            border-radius: 25px;
            border: 1px solid rgba(122, 122, 122, 0.63);
            box-shadow: 15px 15px 10px rgba(122, 122, 122, 0.63);
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

        #menu-bar {
            width: 70%;
            height: fit-content;
            margin-top: 25px;
            padding-right: 60px;
            float: right;
            color: grey;
            text-align: right;
            font: bold 20px Arial, Helvetica, sans-serif;
        }

        #welcome-msg {
            height: fit-content;
            width: 100%;
            color: #37A1F6;
            margin-top: 60px;
            padding-bottom: 40px;
            font: bold 28px Arial, Helvetica, sans-serif;
        }

        #user_input {
            height: fit-content;
            width: inherit;
            margin-left: 35px;
        }

        #form_fill {
            width: 240px;
            margin-top: 15px;
            border-radius: 10px;
            height: 40px;
            border: 1px solid rgba(65, 65, 65, 0.69);
            /*box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);*/
        }

        #submitBtn {
            width: 100px;
            height: 30px;
            border-radius: 10px;
            background-color: #57B846;
            border: none;
            color: white;
            font: bold 16px Arial, Helvetica, sans-serif;
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

    <script src="http://code.jquery.com/jquery-latest.min.js "></script>
    <script>
        let response = $.get('/check_login');
        response.success(function (result) {
            let obj = jQuery.parseJSON(result);
            if(obj.statusCode===200){
                window.location.replace("/dashboard");
            }
        });
    </script>
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
    <div id="menu-bar">
        <b style="margin-right: 20px"><a href="#" style="color: #37A1F6;
        text-decoration: none">ABOUT</a></b>
        <b style="margin-right: 20px"><a href="#" style="color: #37A1F6;
        text-decoration: none">TEAM</a></b>
        <b style="margin-right: 20px"><a href="/sign_up" style="color: #37A1F6;
        text-decoration: none">SIGN UP</a></b>
    </div>
</div>
<div class="div2">
    <div class="div3">
        <img src="static/bgpic.png" width="100%" height="100%"  >
    </div>
    <div class="div4" >
        <div id="login-form">
            <div id="welcome-msg" align="center">
                <b>Welcome Back :)</b>
            </div>
            <div id="user_input">
                <div id="form_fill">
                    <img src="static/icons8-email-52.png" style="float: left; height:18px; width: 18px;
                        margin-top: 12px; margin-left: 5px">
                    <input type="text" id="userEmail" placeholder="Email" name="userEmail" style=" width: 200px;
                        height: 100%;border: none; background-color: transparent;float: right;
                        margin-right: 10px;color: #464646; font: bold 16px Arial, Helvetica, sans-serif;">
                </div>

                <div id="form_fill">
                    <img src="static/icons8-password-52.png" style="float: left; height:18px; width: 18px;
                        margin-top: 12px; margin-left: 5px">
                    <input type="password" id="userPass" placeholder="Password" style=" width: 200px; height: 100%;
                        border: none; background-color: transparent;float: right; margin-right: 10px;
                        color: #464646; font: bold 16px Arial, Helvetica, sans-serif;" name="userPass">
                </div>
                <div style=" height: fit-content; width: 100%; margin-top: 20px;">
                    <a href="#" style="color: inherit;text-decoration: none; margin-left: 75px">
                        <button id="submitBtn" onclick="sendResponse()">LOGIN</button>
                    </a>
                </div>
                <div style=" height: fit-content; width: 100%; margin-top: 12px;">
                    <a href="/sign_up" style="color: #37A1F6;text-decoration: none; margin-left: 90px">
                        <b>Sign Up</b>
                    </a>

                </div>
            </div>
        </div>
    </div>
</div>


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

            let response = $.post('/login', {
                email: email,
                pass: pass
            });

            setTimeout(function () {
                response.success(function (result) {
                    document.getElementById("userEmail").value = "";
                    document.getElementById("userPass").value = "";
                    const resultObj = jQuery.parseJSON(result);
                    if(resultObj.statusCode===200){
                        // forward to dashboard page
                        console.log("iffff");
                        window.location.replace('/dashboard');
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