<%--
  Created by IntelliJ IDEA.
  User: IShepelev
  Date: 07.03.2019
  Time: 7:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ошибка</title>
</head>
<body>
<h1>Не удалось получить данные!</h1>

<p><c:out value="${errorView.text}"/></p>
</body>
</html>
