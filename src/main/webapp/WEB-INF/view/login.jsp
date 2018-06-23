<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<title>Login Page</title>

<style>
.failed {
	color: red;
}
</style>

</head>
<body>
	<h3>Login Page</h3>
	<form:form
		action="${pageContext.request.contextPath}/authenticateLogin"
		method="POST">

		<!-- Validate login -->
		<c:if test="${param.error!=null}">
			<i class="failed">Invalid username/password</i>
		</c:if>

		<p>
			User name: <input type="text" name="username" />
		</p>

		<p>
			Password: <input type="password" name="password" />
		</p>
		<input type="submit" value="Login" />
	</form:form>
</body>
</html>
