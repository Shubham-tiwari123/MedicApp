<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
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

        #border-image {
            margin-top: 80px;
            width: 80%;
            height: 48%;
            border: 1px solid transparent;
            margin-left: 10%;
        }

        #server-img {
            float: left;
            width: 180px;
            height: 180px;
            margin-top: 40px;
            margin-left: 20px;
            border: 3px solid grey;
            border-radius: 50%;
        }

        #submitBtn {
            width: 130px;
            height: 40px;
            border-radius: 40px;
            background-color: #57B846;
            border: #57B846;
            color: white;
            font: bold 18px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
            float: left;
            margin-top: 120px;
            margin-left: 100px;
        }

        #client-img {
            float: right;
            width: 180px;
            height: 180px;
            margin-top: 45px;
            margin-right: 80px;
            border: 3px solid grey;
            border-radius: 50%;
        }

        #text2 {
            width: 900px;
            height: 50px;
            margin-top: 240px;
        }

        #table1 {
            height: fit-content;
            width: 92%;
            margin-left: 4%;
            margin-top: 30px;
        }

        #table2, th {
            width: 15%;
            height: 40px;
            background-color: #57B846;
            color: white;
            font: bold 20px Arial, Helvetica, sans-serif;
            border-radius: 20px 0 0 0;
        }

        #table3, th {
            width: 80%;
            height: 40px;
            background-color: #57B846;
            color: white;
            font: bold 21px Arial, Helvetica, sans-serif;
            border-radius: 0 20px 0 0;
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
            background-color: #57B846;
            border: #57B846;
            color: white;
            margin-left: 40%;
            height: 30px;
            width: 60px;
            border-radius: 40px;
            font: bold 16px Arial, Helvetica, sans-serif;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
        }
    </style>
</head>
<body>
<div id="popup-msg" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <p id="popup-text">Patient ID: 58452945</p>
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
        <div id="border-image">
            <img src="static/server.png" id="server-img">
            <button id="submitBtn" onclick="connectToServer()">CONNECT</button>
            <img src="static/client.png" id="client-img">
            <div id="text2">
                <b style="float: left;  font: bold 25px Arial, Helvetica, sans-serif; margin-left: 40px;
                    color: #37A1F6;">Medic Server</b>
                <b style="float: right; font: bold 25px Arial, Helvetica, sans-serif;
                    margin-right: 21%; color: #37A1F6;">Hospital</b>
            </div>
        </div>
        <div id="table1">
            <table style="border-collapse: collapse; width: 100%; box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
            border-radius: 20px">
                <tr>
                    <th id="table2" style="border-right: 2px solid gray;">PC</th>
                    <th id="table3">Public Keys</th>
                </tr>
                <tr>
                    <td style="width: 40px; height: 45px;font: bold 20px Arial, Helvetica, sans-serif;
                         color:#034377; padding-left:35px;border-right: 2px solid gray;">Hospital
                    </td>

                    <td id="pc-key" style="width: 100px; height: 45px;font: bold 20px Arial, Helvetica, sans-serif;
                          color:#034377; padding-left: 45px; border:1px solid  lightgray">Hospital Public key
                    </td>
                </tr>
                <tr>
                    <td style="width: 40px; height: 45px;font: bold 20px Arial, Helvetica, sans-serif;
                        color:#034377;background-color:lightgrey;padding-left: 45px;border-right: 2px solid gray;
                        border-bottom-left-radius:20px;">Server
                    </td>

                    <td id="server-key" style="width: 100px; height: 45px;font: bold 20px Arial, Helvetica, sans-serif;
                         color:#034377;background-color:lightgrey;padding-left: 45px; border-bottom-right-radius:20px;">
                        Server Public key
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js "></script>
<script>

    let modal = document.getElementById("popup-msg");
    modal.style.display = "block";
    document.getElementById("popup-text").innerText = "Getting Keys...";
    document.getElementById("confirm-btn").style.visibility="hidden";

    let conform_btn = document.getElementById("confirm-btn");
    conform_btn.onclick = function(){
        document.getElementById("popup-text").style.color = "rgba(92, 93, 94, 0.78)";
        modal.style.display = "none";

    };

    let response = $.get('/get_keys');

    setTimeout(function () {
        response.success(function (result) {
            let obj = jQuery.parseJSON(result);
            if(obj.statusCode===200){
                document.getElementById("popup-text").innerText = "Setting Keys";
                document.getElementById("pc-key").innerText = obj.PC;
                document.getElementById("server-key").innerText = obj.Server;
                document.getElementById("submitBtn").disabled = true;
                document.getElementById("submitBtn").style.backgroundColor = "grey";
            }
            modal.style.display = "none";
        });

        response.error(function (jqXHR, textStatus, errorThrown) {
            document.getElementById("popup-text").innerText = "Server error... refresh page";
            document.getElementById("popup-text").style.color = "#BA0606";
            document.getElementById("confirm-btn").style.visibility ="visible";
        })
    },2000);

    function connectToServer() {
        modal.style.display = "block";
        document.getElementById("popup-text").innerText = "Connecting to server";
        document.getElementById("confirm-btn").style.visibility="hidden";
        console.log("called");
        let res = $.post('/connect_server');

        setTimeout(function () {
            res.success(function (result) {
                console.log("parse",result);
                var obj = jQuery.parseJSON(result);
                console.log("obj",obj);
                document.getElementById("popup-text").innerText = "Setting keys";
                document.getElementById("pc-key").innerText = obj.PC;
                document.getElementById("server-key").innerText = obj.Server;
                document.getElementById("submitBtn").disabled = true;
                document.getElementById("submitBtn").style.backgroundColor = "grey";
                setTimeout(function () {
                    modal.style.display = "none";
                },1000);
            });

            res.error(function (jqXHR, textStatus, errorThrown) {
                document.getElementById("popup-text").innerText = "Server error... try again";
                document.getElementById("popup-text").style.color = "#BA0606";
                document.getElementById("confirm-btn").style.visibility ="visible";
            })

        },2000)
    }
</script>
</body>
</html>
