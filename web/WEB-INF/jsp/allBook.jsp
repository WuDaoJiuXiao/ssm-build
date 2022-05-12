<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示页面</title>
    <%--BootStrap美化界面--%>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h4 style="text-align: center">
                    <strong>全部书籍列表</strong>
                </h4>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/allBook">全部书籍</a>
                <span style="color: red;font-weight: bolder;font-size: 12px">${error}</span>
            </div>
            <div class="col-md-6 column">
                <%--查询书籍功能--%>
                <form action="${pageContext.request.contextPath}/book/queryBook" method="post" style="float: right">
                    <label>
                        <input type="text" placeholder="请输入要查询书籍的名称..." class="form-control" name="queryBookName">
                    </label>
                    <input type="submit" value="查询" class="btn btn-primary">
                </form>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th style="text-align: center">书籍编号</th>
                    <th style="text-align: center">书籍名称</th>
                    <th style="text-align: center">书籍数量</th>
                    <th style="text-align: center">书籍描述</th>
                    <th style="text-align: center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="book" items="${bookList}">
                    <tr>
                        <td style="text-align: center">${book.bookId}</td>
                        <td style="text-align: center">${book.bookName}</td>
                        <td style="text-align: center">${book.bookCount}</td>
                        <td style="text-align: center">${book.detail}</td>
                        <td style="text-align: center">
                            <a href="${pageContext.request.contextPath}/book/toUpdateBook?bookId=${book.bookId}">修改</a>
                            &nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="${pageContext.request.contextPath}/book/deleteBook/${book.bookId}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
