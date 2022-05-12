<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网站首页</title>
    <style>
        .body, h1, h2, h3, h4, p, a{
            margin: 0 0 0 0;
            border: 0;
            padding: 0 0 0 0;
        }
        a {
            text-decoration: none;
            color: black;
            font-size: 17px;
        }
        a:hover{
            color: orange;
        }
        h3 {
            width: 130px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            background: deepskyblue;
            border-radius: 7px;
            margin-top: 5px;
            margin-bottom: 5px;
        }
        #allInfo {
            width: 130px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            background: #00ff37;
            border-radius: 7px;
            margin-top: 5px;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>

<p id="allInfo">功能列表</p>

<h3><a href="${pageContext.request.contextPath}/book/allBook">查询所有书籍</a></h3>

</body>
</html>
