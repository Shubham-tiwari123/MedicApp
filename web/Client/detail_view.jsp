<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Detail View</title>
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

        .div5 {
            border-radius: 20px;
            font-size: 22px;
            width: 800px;
            height: 510px;
            border: 1px solid black;
            margin-top: 120px;
            margin-left: 200px;
            color: #034377;
        }

        #form-box {
            position: fixed;
            margin-left: 70px;
            margin-top: 8%;
            width: 70%;
            height: auto;
            border: 1px solid rgba(65, 65, 65, 0.69);
            box-shadow: 5px 10px 15px rgba(65, 65, 65, 0.69);
            border-radius: 30px;
            background-color: white;
        }
        .input-box {
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
            width: 100px;
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
            background-color: transparent;
        }

        #left-div{
            float: left;
            width: 50%;
            height: 100%;
            background-color: transparent;
        }
        #right-div{
            float: right;
            width: 50%;
            height: 100%;
            background-color: transparent;
        }
        #input-box-text{
            float: left;
            background-color: transparent;
            height: fit-content;
            margin-top: 10px;
            width: 40%;
            text-align: center;
            color: #034377;
            font: bold 24px Arial, Helvetica, sans-serif;
        }
        #form-fill{
            float: right;
            background-color: transparent;
            height: 100%;
            width: 60%;
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
    <script>
        var json = localStorage.getItem("jsonValue");
        if (!json){
            window.location.replace("/view_record")
        }
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
                <div id="left-div">
                    <div id="input-box-text">
                        Patient ID
                    </div>
                    <div id="form-fill">
                        <input type="text" name="patientID" class="input-box" id="patientID" disabled>
                    </div>
                </div>
                <div id="right-div">
                    <div id="input-box-text">
                        Doctor Name
                    </div>
                    <div id="form-fill">
                        <input type="text" name="doctorName" class="input-box" id="doctorName" disabled>
                    </div>
                </div>
            </div>

            <div id="form-input-div">
                <div id="left-div">
                    <div id="input-box-text">
                        Speciality
                    </div>
                    <div id="form-fill">
                        <input type="text" name="speciality" class="input-box" id="speciality" disabled>
                    </div>
                </div>
                <div id="right-div">
                    <div id="input-box-text">
                        Date
                    </div>
                    <div id="form-fill">
                        <input type="text" name="date" class="input-box" id="date" disabled>
                    </div>
                </div>
            </div>

            <div id="form-input-div" style="height: 260px; margin-bottom: 15px">
                <div id="caption" style="height: auto; width: 100%; margin-bottom: 5px;">
                    <div style="color: #034377;font: bold 24px Arial, Helvetica, sans-serif; height: fit-content;
                    width: 40%; margin-left: 40px">
                        Prescription
                    </div>
                </div>
                <div style="width: 100%; height: 80%">
                    <textarea style="max-width: 89%;max-height: 196px; min-height: 196px; min-width: 89%;
                     background-color: #F7F7F7;; margin-left: 40px; border-radius: 10px;
                     font: bold 16px Arial, Helvetica, sans-serif; color: rgba(65, 65, 65, 0.69);
                     border: 1px solid rgba(65, 65, 65, 0.69); padding: 10px;" id="prescription" disabled></textarea>
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

    modal.style.display = "block";
    document.getElementById("popup-text").innerText = "Setting record...";
    document.getElementById("confirm-btn").style.visibility="hidden";

    setTimeout(function () {
        var json = localStorage.getItem("jsonValue");
        console.log("json...",json);
        var jsonParse = jQuery.parseJSON(json);
        document.getElementById("patientID").value = jsonParse.patientId;
        document.getElementById("doctorName").value = jsonParse.doctorName;
        document.getElementById("speciality").value = jsonParse.specialistType;
        document.getElementById("date").value = jsonParse.date;
        document.getElementById("prescription").innerText = jsonParse.prescription;
        modal.style.display = "none";
    },2000);

    window.onload = function () {

    }
</script>
</body>
</html>
