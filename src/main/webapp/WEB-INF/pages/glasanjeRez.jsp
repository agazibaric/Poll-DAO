<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
table {
	border-collapse: collapse;
	border-spacing: 0;
}

table.rez td {
	text-align: center;
}

</style>
</head>

<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Ime</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="option" items="${pollOptions}">
				<tr>
					<td>${option.optionTitle}</td>
					<td>${option.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}"
		width="500" height="300" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Linkovi pobjednika ankete:</p>
	<ul>
		<c:forEach var="option" items="${winners}">
			<li><a href="${option.optionLink}" target="_blank">${option.optionTitle}</a></li>
		</c:forEach>
	</ul>

</body>
</html>
