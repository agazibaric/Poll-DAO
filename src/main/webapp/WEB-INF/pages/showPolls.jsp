<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body>

	<h1>Odaberite anketu:</h1>
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>

</body>
</html>