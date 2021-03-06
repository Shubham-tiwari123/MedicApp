<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        body{
            margin: 0;
            padding: 0;
        }

        #div1{
            float: left;
            width: 19%;
            height: 100vh;
            background-image: url("static/navbar.jpg");
            position: fixed;
            overflow: hidden;
            box-shadow: 10px 5px 15px rgba(65, 65, 65, 0.69);
        }

        #div2{
            float: right;
            width: 80%;
            height: 100vh;
            overflow-y: auto;
            overflow-x: hidden;
        }

        #company-name{
            width: 100%;
            height: 20%;
            background-color: #F8FAFB;
        }

        #side-nav{
            width: 100%;
            height: 80%;
        }

        #name{
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

        #upper-bar{
            width: 100%;
            height: 25%;
        }

        #form-area{
            width: 100%;
            height: fit-content;
            margin-top: 10px;
            padding-bottom: 20px;
        }
        #box{
            width: 250px;
            height: 130px;
            float: left;
            margin-left: 60px;
            margin-top: 20px;
            border-radius: 20px;
        }
        .img1{
            background-image: url("static/pic2.png");
            background-size: 500px 500px;
            background-position: center;
        }
        .img2{
            background-image: url("static/pic3.png");
            background-size: 500px 500px;
            background-position: center;
        }
        .img3{
            background-image: url("static/pic1.png");
            background-size: 500px 500px;
            background-position: center;
        }
        #hospital-key{
            font: bold 25px Arial, Helvetica, sans-serif;
            color: #37A1F6;
            padding-left: 20px;
        }
        #show-key{
            width: 95%;
            height: 65px;
            border-radius: 40px;
            margin-top: 20px;
            background-color: white;
            margin-left: 20px;
            box-shadow: 5px 5px 10px rgba(65, 65, 65, 0.69);
            border: 1px solid rgba(65, 65, 65, 0.69);
        }
    </style>
</head>
<body>
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
    <div id="upper-bar">
        <div id="box" class="img1">

        </div>
        <div id="box" class="img2">

        </div>
        <div id="box" class="img3">

        </div>
    </div>
    <div id="form-area">
        <p id="hospital-key">Hospital Key</p>
        <div id="show-key">

        </div>
        <div id="show-key">

        </div>
        <div id="show-key">

        </div>
        <div id="show-key">

        </div>
        <div id="show-key">

        </div>
        <div id="show-key">

        </div>

    </div>
</div>
</body>
</html>