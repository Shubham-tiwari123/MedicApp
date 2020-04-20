<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Record</title>
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

        #form-box {
            margin-left: 50px;
            margin-top: 95px;
            width: 90%;
            height: 80%;
            border: 1px solid rgba(65, 65, 65, 0.69);
            box-shadow: 5px 10px 15px rgba(65, 65, 65, 0.69);
            border-radius: 30px;
            background-color: white;
            color: #034377;
            font: 25px Arial, Helvetica, sans-serif;

        }

        #input-boxtext {
            float: left;
            margin-left: 30px;
            margin-top: 10px;
            color: #034377;
            font: bold 24px Arial, Helvetica, sans-serif;
        }

        #submitBtn {
            width: 80px;
            height: 33px;
            border-radius: 10px;
            background-color: #57B846;
            border: #57B846;
            color: white;
            font: bold 18px Arial, Helvetica, sans-serif;
            float: right;
            margin-right: 50px;
            margin-top: 8px;
        }

        #form-input-div {
            width: 100%;
            height: 60px;
            margin-top: 20px;
        }

        .text-box {
            text-align: center;
            float: left;
            margin-left: 15px;
            background-color: #F7F7F7;
            width: 210px;
            border-radius: 10px;
            height: 38px;
            border: 1px solid rgba(65, 65, 65, 0.69);
            /*box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);*/
            font: bold 16px Arial, Helvetica, sans-serif;
            color: rgba(65, 65, 65, 0.69);
        }

        #form-text-set {
            width: 65%;
            height: 25px;
            margin-top: 20px;
            margin-left: 50px;
        }

        #view-recordbox {
            background-color: #F7F7F7;
            width: 92%;
            height:78%;
            border: 1px solid rgba(65, 65, 65, 0.69);;
            margin-left: 30px;
            /*box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);*/
            border-radius: 20px;
            margin-top: 10px;
            overflow-x: hidden;
            overflow-y: auto;
        }

        #insideview-box {
            width: auto;
            margin-left: 30px;
            margin-top: 28px;
            height: 12%;
            border: 1px solid black;
            border-radius: 20px;
            background-color: white;
            margin-right: 30px;

        }

        #insidebox-content {
            width: 60%;
            float: left;
            height: fit-content;
            margin-top: 10px;
            color: lightslategray;
            border-radius: 20px 0 0 20px;
            font: 23px Arial, Helvetica, sans-serif;
        }

        #get-record{
            width: 120px;
            height: 35px;
            border-radius: 10px;
            background-color: #57B846;
            border: #57B846;
            color: white;
            font: bold 18px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 8px rgba(65, 65, 65, 0.69);
            float: right;
            margin-right: 90px;
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
                    Patient Id
                </div>
                <input type="text" class="text-box" id="patientID">
                <button id="get-record" onclick="getRecord()">Get Record</button>
            </div>
            <div id="view-recordbox">
                <%--<div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>
                <div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>
                <div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>
                <div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>
                <div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>
                <div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>
                <div id="insideview-box">
                    <div id="insidebox-content">
                        <b style="float: left; margin-left: 50px;">24-02-2019</b>
                        <b style="float: left; margin-left: 150px; ">DY Patil</b>
                    </div>
                    <div style="float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;">
                        <button id="submitBtn">View</button>
                    </div>
                </div>--%>
            </div>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js "></script>
<script>
    let record;
    let modal = document.getElementById("popup-msg");
    let conform_btn = document.getElementById("confirm-btn");

    conform_btn.onclick = function(){
        document.getElementById("popup-text").style.color = "rgba(92, 93, 94, 0.78)";
        modal.style.display = "none";
    };

    window.onload = function(){
        console.log("Loading window");
        document.getElementById("patientID").value = "";
    };

    function validateForm(patientID) {
        console.log("validating form");
        if (patientID === "") {
            alert("Please provide patient ID");
            document.getElementById("patientID").focus();
            return false;
        }
        if(isNaN(patientID)){
            alert("ID should only contain numbers");
            document.getElementById("patientID").focus();
            return false;
        }
        return true;
    }

    function detailView(jsonValue){
        localStorage.setItem("jsonValue",record[jsonValue]);
        window.location.replace("/detail_view")
    }

    async function getRecord() {
        const patientID = document.getElementById("patientID").value;
        let flag = await validateForm(patientID);

        if(flag) {
            modal.style.display = "block";
            document.getElementById("popup-text").innerText = "Getting record...";
            document.getElementById("confirm-btn").style.visibility="hidden";

            console.log("values:",patientID);

            let response = $.post('/get_user_info', {
                patientID: patientID
            });

            setTimeout(function () {
                response.success(function (result) {
                    const resultObj = jQuery.parseJSON(result);

                    if(resultObj.statusCode===200){
                        document.getElementById("patientID").value="";
                        console.log(resultObj.medicalRecord)
                        record = resultObj.medicalRecord;
                        var html="";
                        for(var i=1;i<record.length;i++){
                            var json = jQuery.parseJSON(record[i]);
                            console.log("json:::",json);
                            html +=("<div id=\"insideview-box\">\n" +
                                "                    <div id=\"insidebox-content\">\n" +
                                "                        <b style=\"float: left; margin-left: 50px;\">"+json.date+"</b>\n" +
                                "                        <b style=\"float: left; margin-left: 100px; \">"+json.hospitalName+"</b>\n" +
                                "                    </div>\n" +
                                "                    <div style=\"float: right; height: 100%; width: 30%;border-radius: 0 20px 20px 0;\">\n" +
                                "                        <button id=\"submitBtn\" onclick=\"detailView(\'"+i+"'\)\">View</button>\n" +
                                "                    </div>\n" +
                                "                </div>")
                        }
                        // set the values
                        $("#view-recordbox").append(html);
                        modal.style.display = "none";
                    }else{
                        document.getElementById("popup-text").innerText = "Server error...";
                        document.getElementById("popup-text").style.color = "#BA0606";
                        document.getElementById("confirm-btn").style.visibility ="visible";
                    }

                });

                response.error(function (jqXHR, textStatus, errorThrown) {
                    document.getElementById("popup-text").innerText = "Server error...";
                    document.getElementById("popup-text").style.color = "#BA0606";
                    document.getElementById("confirm-btn").style.visibility ="visible";
                })
            }, 2000);

        }
    }

</script>
</body>
</html>
