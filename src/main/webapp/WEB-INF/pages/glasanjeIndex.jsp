<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

</head>

<body>
  	<h1>${poll.title}</h1>
	<p>${poll.message}</p> 

	<ol>
		<c:forEach var="option" items="${pollOptions}">
			<li><a href="glasanje-glasaj?pollID=${poll.id}&optionID=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>