<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Patients</title>
    <style>
        body {
            margin: 0;
            padding: 0;
        }

        #div1 {
            float: left;
            width: 19%;
            height: 100vh;
            background-image: url("static/navbar.jpg");
            position: fixed;
            overflow: hidden;
            box-shadow: 10px 5px 15px rgba(65, 65, 65, 0.69);
        }

        #div2 {
            float: right;
            width: 80%;
            height: 100vh;
            overflow-y: auto;
            overflow-x: hidden;
        }

        #company-name {
            width: 100%;
            height: 20%;
            background-color: #F8FAFB;
        }

        #side-nav {
            width: 100%;
            height: 80%;
        }

        #name {
            color: #37A1F6;
            font: bold 50px Arial, Helvetica, sans-serif;
            width: 100%;
            height: fit-content;
            text-align: center;
            padding-top: 30px;
        }

        #menu-bar {
            width: 200px;
            height: fit-content;
            margin-left: 40px;
            font: bold 25px Arial, Helvetica, sans-serif;
            margin-top: 80px;
        }

        #form-area {
            width: 100%;
            height: fit-content;
            margin-top: 10px;
            padding-bottom: 20px;
        }

        #show-key {
            width: 95%;
            height: 65px;
            border-radius: 40px;
            margin-top: 20px;
            background-color: white;
            margin-left: 20px;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
            border: 1px solid rgba(65, 65, 65, 0.69);
        }

        #hospital-details {
            float: left;
            width: 24%;
            height: 100%;
            color: #6A51C9;
            font: bold 23px Arial, Helvetica, sans-serif;
        }

        .switch {
            position: relative;
            display: inline-block;
            width: 80px;
            height: 30px;
            float: right;
            margin-right: 50px;
            margin-top: 15px;
        }

        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #B5B5B5;
            -webkit-transition: .4s;
            transition: .4s;
        }

        .slider:before {
            position: absolute;
            content: "";
            height: 26px;
            width: 26px;
            left: 4px;
            bottom: 2px;
            background-color: white;
            -webkit-transition: .4s;
            transition: .4s;
        }

        input:checked + .slider {
            background-color: #6A51C9;
        }

        input:focus + .slider {
            box-shadow: 0 0 1px #6A51C9;
        }

        input:checked + .slider:before {
            -webkit-transform: translateX(46px);
            -ms-transform: translateX(46px);
            transform: translateX(46px);
        }

        /* Rounded sliders */
        .slider.round {
            border-radius: 34px;
        }

        .slider.round:before {
            border-radius: 50%;
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
            background-color: rgb(0, 0, 0);
            background-color: rgba(0, 0, 0, 0.2);
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

        #popup-text {
            font: bold 24px Arial, Helvetica, sans-serif;
            color: rgba(92, 93, 94, 0.78);
            text-align: center;
            width: 100%;
            height: fit-content;
            margin-top: 30px;
        }

        #confirm-btn {
            float: left;
            background-color: #57B846;
            border: #57B846;
            color: white;
            height: 30px;
            width: 60px;
            border-radius: 40px;
            font: bold 18px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
            margin-left: 80px;
        }

        #buttons {
            width: 100%;
            height: 40px;
        }

        #cancel-btn {
            float: left;
            background-color: #EC3030;
            border: #57B846;
            color: white;
            height: 30px;
            width: 60px;
            border-radius: 40px;
            font: bold 18px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
            margin-left: 40px;
        }

    </style>
</head>
<body>

<div id="popup-msg" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <p id="popup-text">Deactivate Patients</p>
        <div id="buttons">
            <button id="confirm-btn">OK</button>
            <button id="cancel-btn">NO</button>
        </div>

    </div>
</div>

<div id="div1">
    <div id="company-name">
        <div id="name">
            <b>Medic</b>
        </div>
    </div>
    <div id="side-nav">
        <%@include file="sidenave.jsp" %>
    </div>
</div>
<div id="div2">
    <div id="form-area">

    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js "></script>
<script>

    let modal = document.getElementById("popup-msg");
    let conform_btn = document.getElementById("confirm-btn");
    let cancel_btn = document.getElementById("cancel-btn");


     function popup(patientID) {
        let value = document.getElementById(patientID).checked;
        console.log("value",value);
         conform_btn.style.visibility = "visible";
         cancel_btn.style.visibility = "visible";
         if(value){
            console.log("if value", value);
            modal.style.display = "block";
            document.getElementById("popup-text").innerText = "Deativate Patient";
            console.log("patientID", patientID, typeof patientID);
            cancel_btn.onclick = function () {
                modal.style.display = "none";
                document.getElementById(patientID).checked = true;
            };

            conform_btn.onclick = function () {
                modal.style.display = "none";
                document.getElementById(patientID).checked = false;
            };
        }else{
            console.log("else value", value);
            modal.style.display = "block";
            document.getElementById("popup-text").innerText = "Activate Patient";

            console.log("patientID", patientID, typeof patientID);

            cancel_btn.onclick = function () {
                modal.style.display = "none";
                document.getElementById(patientID).checked = false;
            };

            conform_btn.onclick = function () {
                modal.style.display = "none";
                document.getElementById(patientID).checked = true;
            };
        }
    }


    modal.style.display =  "block";
    document.getElementById("popup-text").innerText = "Getting patients..";
    conform_btn.style.visibility = "hidden";
    cancel_btn.style.visibility = "hidden";

    let response = $.get('/get_patient');

    setTimeout(function () {
        response.success(function (result) {
            const resultObj = jQuery.parseJSON(result);

            if(resultObj.statusCode===200){
                var record = resultObj.result;
                // set the values
                var html="";
                for(var i=0;i<record.length;i++){
                    var json = jQuery.parseJSON(record[i]);
                    html +=("<div id=\"show-key\">\n" +
                        "            <div id=\"hospital-details\" style=\"border-radius: 30px 0 0 30px; margin-left: 10px; text-align: center;\n" +
                        "            border-right: 2px solid gray\">\n" +
                        "                <p>"+json.patientID+"</p>\n" +
                        "            </div>\n" +
                        "            <div id=\"hospital-details\" style=\"margin-left: 7px; text-align: center; border-right: 2px solid gray\">\n" +
                        "                <p>"+json.name+"</p>\n" +
                        "            </div>\n" +
                        "            <div id=\"hospital-details\" style=\"margin-left: 7px; text-align: center; border-right: 2px solid gray\">\n" +
                        "                <p>"+json.phoneNumber+"</p>\n" +
                        "            </div>\n" +
                        "            <div id=\"hospital-details\" style=\"margin-left: 7px; border-radius: 0 30px 30px 0\">\n" +
                        "                <label class=\"switch\">\n" +
                        "                    <input type=\"checkbox\" id=\'"+json.patientID+"'\" checked>\n" +
                        "                    <span class=\"slider round\" onclick=\"popup("+json.patientID+")\"></span>\n" +
                        "                </label>\n" +
                        "\n" +
                        "            </div>\n" +
                        "        </div>")
                }
                $("#form-area").append(html);
                setTimeout(function () {
                    modal.style.display =  "none";
                },1000)
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
</script>
</body>
</html>