<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Patient</title>
    <style>
        body {
            margin: 0px;
            overflow: hidden;
            background-color: #F8FAFB;
        }

        .div1 {
            height: 10vh;
            width: 100%;
            background-color: #e2f0ec;
            box-shadow: 0px 5px 15px rgba(122, 122, 122, 0.63);
            position: fixed;
        }

        .div2 {
            background-color: transparent;
            height: 100vh;
            width: 100%;
        }

        .div3 {
            float: left;
            height: inherit;
            width: 20%;
            background-color: white;
            box-shadow: 0px 20px 20px rgba(122, 122, 122, 0.63);
        }

        .div4 {
            float: right;
            background-color: transparent;
            height: 100%;
            width: 80%;
            align-content: center;
        }

        #company-name {
            width: 199px;
            height: auto;
            padding-top: 12px;
            padding-left: 60px;
            float: left;
            color: #37A1F6;
            font: bold 35px Arial, Helvetica, sans-serif;
            background-color: transparent;;
        }

        #patient-image {
            width: 50px;
            height: 50px;
            margin-top: 10px;
            float: right;
            margin-right: 60px;
        }

        #menu-bar {
            background-color: transparent;
            width: 200px;
            height: fit-content;
            margin-left: 40px;
            font: bold 22px Arial, Helvetica, sans-serif;
            margin-top: 200px;
        }

        #form-box {
            margin-left: 250px;
            margin-top: 100px;
            width: 500px;
            height: 510px;
            border: 1px solid rgba(65, 65, 65, 0.69);
            box-shadow: 5px 10px 15px rgba(65, 65, 65, 0.69);
            border-radius: 30px;
            background-color: white;
            text-align: center;
            color: #034377;
            font: bold 25px Arial, Helvetica, sans-serif;
        }

        #input-boxtext {
            float: left;
            margin-left: 30px;
            margin-top: 15px;
        }

        .input-box {
            float: right;
            margin-top: 5px;
            margin-right: 30px;
            background-color: #F7F7F7;
            width: 240px;
            border-radius: 10px;
            height: 40px;
            border: 1px solid rgba(65, 65, 65, 0.69);
            /*box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);*/
            font: bold 16px Arial, Helvetica, sans-serif;
            color: rgba(65, 65, 65, 0.69);
            text-align: center;
        }

        #submitBtn {
            width: 110px;
            height: 35px;
            border-radius: 10px;
            background-color: #57B846;
            border: #57B846;
            color: white;
            font: bold 18px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
        }

        #form-input-div {
            width: 100%;
            height: 50px;
            margin-top: 30px;
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
            font:bold 18px Arial, Helvetica, sans-serif;
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
    <div id="patient-image">
        <img src="static/person.png"
             style="height: 40px; width: 40px; border: 2px solid rgba(65, 65, 65, 0.69);border-radius: 50%;">
    </div>
</div>
<div class="div2">
    <div class="div3">
        <%@include file="sidenav.jsp" %>
    </div>
    <div class="div4">
        <div id="form-box">
            <div id="form-input-div">
                <div id="input-boxtext">
                    Name
                </div>
                <div id="form-fill">
                    <input type="text" id="patientName" class="input-box">
                </div>
            </div>

            <div id="form-input-div">
                <div id="input-boxtext">
                    Age
                </div>
                <div id="form-fill">
                    <input type="number" id="patientAge" class="input-box">
                </div>
            </div>

            <div id="form-input-div">
                <div id="input-boxtext">
                    Gender
                </div>
                <div id="form-fill">
                    <select id="patientGender" class="input-box" style="height: 45px">
                        <option>Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Transgender">Transgender</option>
                    </select>
                    <!--<input type="text" id="patientGender" class="input-box">-->
                </div>
            </div>

            <div id="form-input-div">
                <div id="input-boxtext">
                    Phone Number
                </div>
                <div id="form-fill">
                    <input type="text" id="patientNumber" class="input-box">
                </div>
            </div>

            <div id="form-input-div">
                <div id="input-boxtext">
                    Address
                </div>
                <div id="form-fill">
                    <input type="text" id="patientAddress" class="input-box">
                </div>
            </div>

            <div id="form-input-div">
                <a href="#" style="color: inherit;text-decoration: none">
                    <button id="submitBtn" onclick="sendResponse()">SUBMIT</button>
                </a>
            </div>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js "></script>
<script>
    let modal = document.getElementById("popup-msg");
    let conform_btn = document.getElementById("confirm-btn");

    conform_btn.onclick = function(){
        modal.style.display = "none";
        window.location.replace("/register_patient")
    };

    window.onload = function(){
        console.log("Loading window");
        document.getElementById("patientName").value = "";
        document.getElementById("patientAge").value = "";
        document.getElementById("patientAddress").value = "";
        document.getElementById("patientNumber").value = "";
        document.getElementById("patientGender").value = "Select Gender";
    };

    async function sendResponse() {
        const name = document.getElementById("patientName").value;
        const age = document.getElementById("patientAge").value;
        const address = document.getElementById("patientAddress").value;
        const phoneNumber = document.getElementById("patientNumber").value;
        const gender = document.getElementById("patientGender").value;

        let flag = await validateForm(name,age,address,phoneNumber,gender);

        if(flag) {
            modal.style.display = "block";
            document.getElementById("popup-text").innerText = "Waiting for response...";
            document.getElementById("confirm-btn").style.visibility="hidden";

            console.log("values:",name,age,address,phoneNumber,gender);

            let response = $.post('/register_new_patient', {
                name: name,
                age: age,
                address: address,
                phoneNumber: phoneNumber,
                gender: gender,
            });

            setTimeout(function () {
                response.success(function (result) {
                    const resultObj = jQuery.parseJSON(result);
                    if(resultObj.statusCode===200){
                        // forward to login pageServer Error
                        console.log("iffff");
                        document.getElementById("popup-text").innerText = "Patient ID:   "+resultObj.patientID;
                        document.getElementById("popup-text").style.color = "#57B846";
                        document.getElementById("confirm-btn").style.visibility ="visible";
                    }else{
                        document.getElementById("popup-text").innerText = "Cannot register...try again";
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


    function validateForm(name,age,address,phoneNumber,gender) {
        console.log("validating form");
        if(name===""){
            alert("Please provide name");
            document.getElementById("patientName").focus();
            return false;
        }
        if(age===""){
            alert("Please provide age");
            document.getElementById("patientAge").focus();
            return false;
        }
        if(Number(age)>99){
            alert("Age must be between 1-99");
            document.getElementById("patientAge").focus();
            return false;
        }
        if(gender==="Select Gender"){
            alert("Please provide valid gender");
            document.getElementById("patientGender").focus();
            return false;
        }
        if(phoneNumber===""||phoneNumber.length!==10){
            alert("Please provide valid number");
            document.getElementById("patientNumber").focus();
            return false;
        }
        if(address===""){
            alert("Please provide address");
            document.getElementById("patientAddress").focus();
            return false;
        }
        return true;
    }
</script>

</body>
</html>
