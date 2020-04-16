<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page Not Found</title>
    <style>
        body{
            margin: 0;
            padding: 0;
        }
        #main_area{
            width: 100%;
            height: 100%;
            background-image: url("static/error.png");
            background-repeat: no-repeat;
            overflow: hidden;
            background-position: center;
        }
        button{
            position: fixed;
            bottom: 80px;
            margin-left: 46%;
            width: 100px;
            height: 40px;
            background-color: #5077D2;
            border: none;
            border-radius: 5px;
            font-size: 20px;
            color: white;
        }
    </style>
</head>
<body>
<div id="main_area">
    <a href="/"><button><b>HOME</b></button></a>
</div>
</body>
</html>
