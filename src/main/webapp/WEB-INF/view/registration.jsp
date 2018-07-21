<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<html>
<head>
<title></title>
</head>
<body>
	<form:form action="${pageContext.request.contextPath}/registration"
		method="POST" class="form-horizontal">

		<!-- Place for messages: error, alert etc ... -->
		<div class="form-group">
			<div class="col-xs-15">
				<div>
					<!-- Validate login -->
					<c:if test="${param.error != null}">
						<div class="alert alert-danger col-xs-offset-1 col-xs-10">
							Invalid username and password.</div>
					</c:if>

					<c:if test="${param.logout != null}">
						<div class="alert alert-success col-xs-offset-1 col-xs-10">
							You have been logged out.</div>
					</c:if>

				</div>
			</div>
		</div>

		<!-- User name -->
		<div style="margin-bottom: 25px" class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input type="text"
				name="firstName" placeholder="firstname" class="form-control">
		</div>
		
		<div style="margin-bottom: 25px" class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input type="text"
				name="lastName" placeholder="lastname" class="form-control">
		</div>
		
		<div style="margin-bottom: 25px" class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input type="text"
				name="email" placeholder="email" class="form-control">
		</div>

		<!-- Password -->
		<div style="margin-bottom: 25px" class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-lock"></i></span> <input type="password"
				name="password" placeholder="password" class="form-control">
		</div>
		<div style="margin-bottom: 25px" class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-lock"></i></span> <input type="password"
				name="confirmPassword" placeholder="confirmpassword" class="form-control">
		</div>

		<!-- Login/Submit Button -->
		<div style="margin-top: 10px" class="form-group">
			<div class="col-sm-6 controls">
				<button type="submit" class="btn btn-success">Register</button>
			</div>
		</div>

	</form:form>
</body>
</html>