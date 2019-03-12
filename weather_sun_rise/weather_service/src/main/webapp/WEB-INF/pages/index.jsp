<%--
  Created by IntelliJ IDEA.
  User: IShepelev
  Date: 05.03.2019
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Начало</title>
</head>
<body>
<h2>Погодный брокер (User)</h2>
<form:form action="weather/submit" method="get" modelAttribute="data">
    <div class="form-group">
        <label>Date</label>
        <form:input type= "date" path="date"/>
    </div>
    <div class="form-group">
        <label>City</label>
        <form:input type="text" path="city"/>
    </div>

    <button type="submit">Запрос</button>
</form:form>
</body>
</html>
