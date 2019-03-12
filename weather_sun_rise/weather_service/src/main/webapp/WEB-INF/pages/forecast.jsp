<%--
  Created by IntelliJ IDEA.
  User: IShepelev
  Date: 07.03.2019
  Time: 7:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Погода</title>
</head>
<body>
<h1>В <c:out value="${forecastView.city}"/> на <c:out value="${forecastView.date}"/>
</h1>
<h3>Температура - <c:out value="${forecastView.lowTemp}"/> - <c:out value="${forecastView.highTemp}"/></h3>
<h3>За окном <c:out value="${forecastView.description}"/></h3>
</body>
</html>
