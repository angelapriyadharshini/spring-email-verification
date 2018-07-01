<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Access Denied</title>
</head>
<body>
<h2>Access Denied - You are not authorized to access this resource.</h2>

<hr>

<a href="${pageContext.request.contextPath}/">Back</a>
<hr>
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">

		<input type="submit" value="Logout" />

	</form:form>

</body>
</html>