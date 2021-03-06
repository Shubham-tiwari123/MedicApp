<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
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

        .div2 {
            background-color: transparent;
            height: 90vh;
            width: 100%;
        }

        .div3 {
            float: left;
            background-color: transparent;
            height: 100%;
            width: 60%;
        }

        .div4 {
            height: 110%;
            width: 40%;
            float: right;
            background-color: transparent;
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

        #login-form {
            width: 70%;
            height: fit-content;
            padding-bottom: 20px;
            background-color: white;
            margin-top: 20px;
            margin-left: 80px;
            border-radius: 25px;
            border: 1px solid rgba(122, 122, 122, 0.63);
            box-shadow: 15px 15px 10px rgba(122, 122, 122, 0.63);
        }

        #welcome-msg {
            height: fit-content;
            width: 100%;
            color: #37A1F6;
            margin-top: 20px;
            font: bold 28px Arial, Helvetica, sans-serif;
        }

        #form_fill {
            width: 88%;
            border-radius: 10px;
            height: 35px;
            border: 1px solid rgba(65, 65, 65, 0.69);
            /*box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);*/
            margin-top: 12px;
            margin-left: 20px;
        }

        #form_fill input{
            width: 94%;
            height: inherit;
            border: none;
            background-color: transparent;
            margin-right: 10px;
            margin-left: 10px;
            color: #464646;
            font: bold 16px Arial, Helvetica, sans-serif;
        }

        #form_fill input:focus{
            outline: none;
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
        <b style="margin-right: 20px"><a href="/" style="color: #37A1F6;
              text-decoration: none">HOME</a></b>
        <b style="margin-right: 20px"><a href="#" style="color: #37A1F6;
              text-decoration: none">ABOUT</a></b>
        <b style="margin-right: 20px"><a href="#" style="color: #37A1F6;
              text-decoration: none">TEAM</a></b>
        <b style="margin-right: 20px"><a href="/sign_in" style="color: #37A1F6;text-decoration: none">LOGIN</a></b>
    </div>
</div>
<div class="div2">
    <div class="div3">
        <img src="static/bgpic.png" width="100%" height="100%">
    </div>
    <div class="div4">
        <div id="login-form">
            <div id="welcome-msg" style="padding-left: 30px">
                <b>Let's Connect :)</b>
            </div>
            <div style="width: 100%;height: fit-content; margin-top: 10px">
                <div id="form_fill" style="margin-top: 20px">
                    <input type="text" id="hospitalName" placeholder="Hospital Name" onfocus="this.placeholder = ''"
                           onblur="this.placeholder='Hospital Name'" name="hospitalName" >
                </div>
                <div id="form_fill">
                    <input type="text" id="address" placeholder="Address" onfocus="this.placeholder = ''"
                           onblur="this.placeholder='Address'" name="Address">
                </div>
                <%--<div style="width: 100%; height: 35px; margin-top: 18px">--%>
                    <div id="form_fill">
                        <input type="text" id="state" placeholder="State" onfocus="this.placeholder = ''"
                               onblur="this.placeholder='State'" name="state">
                    </div>
                    <div id="form_fill">
                        <input type="text" id="city" placeholder="City" onfocus="this.placeholder = ''"
                               onblur="this.placeholder='City'" name="city">
                    </div>
                <%--</div>--%>
                <div id="form_fill">
                    <input type="text" id="phone" placeholder="Phone Number" onfocus="this.placeholder = ''"
                           onblur="this.placeholder='Phone Number'" name="Phone">
                </div>
                <div id="form_fill">
                    <input type="email" id="userEmail" placeholder="Email" onfocus="this.placeholder = ''"
                           onblur="this.placeholder='Email'" name="userEmail" >
                </div>
                <div id="form_fill">
                    <input type="password" id="userPass" placeholder="Password" onfocus="this.placeholder = ''"
                           onblur="this.placeholder='Password'" name="userPass">
                </div>
                <div style=" height: fit-content; width: 100%; margin-top: 20px;">
                    <a href="#" style="color: inherit;text-decoration: none; margin-left: 33%" >
                        <button id="submitBtn" style="width: 115px" onclick="sendResponse()">REGISTER</button>
                    </a>
                </div>
                <div style=" height: fit-content; width: 100%; margin-top: 10px;">
                    <a href="/sign_in" style="color: #37A1F6;text-decoration: none; margin-left: 42%">
                        <b style="font-size: 16px">Login</b>
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
    let status = false;

    conform_btn.onclick = function(){
        modal.style.display = "none";
        if (status){
            window.location.replace('/sign_in');
        }
    };

    window.onload = function(){
        console.log("Loading window");
        document.getElementById("hospitalName").value = "";
        document.getElementById("address").value = "";
        document.getElementById("phone").value = "";
        document.getElementById("userEmail").value = "";
        document.getElementById("userPass").value = "";
        document.getElementById("city").value = "";
        document.getElementById("state").value = "";
    };

    async function sendResponse() {
        const name = document.getElementById("hospitalName").value;
        const state = document.getElementById("state").value;
        const address = document.getElementById("address").value;
        const phoneNumber = document.getElementById("phone").value;
        const city = document.getElementById("city").value;
        const email = document.getElementById("userEmail").value;
        const pass = document.getElementById("userPass").value;

        let flag = await validateForm(name,state,address,phoneNumber,city,email,pass);

        if(flag) {
            modal.style.display = "block";
            document.getElementById("popup-text").innerText = "Waiting for response...";
            document.getElementById("confirm-btn").style.visibility="hidden";

            console.log("values:",name,state,address,phoneNumber,city,email,pass);

            let response = $.post('/register_hospital', {
                name: name,
                state: state,
                address: address,
                phoneNumber: Number(phoneNumber),
                city: city,
                email: email,
                pass: pass
            });

            setTimeout(function () {
                response.success(function (result) {
                    document.getElementById("hospitalName").value = "";
                    document.getElementById("address").value = "";
                    document.getElementById("phone").value = "";
                    document.getElementById("userEmail").value = "";
                    document.getElementById("userPass").value = "";
                    document.getElementById("city").value = "";
                    document.getElementById("state").value = "";
                    const resultObj = jQuery.parseJSON(result);
                    if(resultObj.statusCode===200){
                        // forward to login page
                        status = true;
                        document.getElementById("popup-text").innerText = "Registered...";
                        document.getElementById("popup-text").style.color = "#44a706";
                        document.getElementById("confirm-btn").style.visibility ="visible";
                    }else{
                        document.getElementById("popup-text").innerText = "Username already exists...";
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


    function validateForm(name,state,address,phoneNumber,city,email,pass) {
        console.log("validating form");
        if(name===""){
            alert("Please provide hospital name");
            document.getElementById("patientName").focus();
            return false;
        }
        if(address===""){
            alert("Please provide address");
            document.getElementById("patientAddress").focus();
            return false;
        }
        if(state===""){
            alert("Please provide state");
            document.getElementById("state").focus();
            return false;
        }
        if(city===""){
            alert("Please provide city");
            document.getElementById("city").focus();
            return false;
        }
        if(phoneNumber===""||phoneNumber.length!==10){
            alert("Please provide valid number");
            document.getElementById("phone").focus();
            return false;
        }
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