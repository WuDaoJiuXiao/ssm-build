<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加书籍页面</title>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h4 style="text-align: center">
                    <strong>新增书籍</strong>
                </h4>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/addBook" method="post">
        <div class="form-group">
            <label for="bookName">书籍名称:</label>
            <input type="text" class="form-control" id="bookName" name="bookName" required>
        </div>
        <div class="form-group">
            <label for="bookCount">书籍数量:</label>
            <input type="text" class="form-control" id="bookCount" name="bookCount" required>
        </div>
        <div class="form-group">
            <label for="bookDetail">书籍描述:</label>
            <input type="text" class="form-control" id="bookDetail" name="detail" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="提交信息" style="background: #45e054">
        </div>
    </form>
</div>
</body>
</html>
