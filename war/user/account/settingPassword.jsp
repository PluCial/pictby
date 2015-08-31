<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.model.*" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
String entryId = (String) request.getAttribute("entryId");
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/user/account/include-parts/html_head.jsp" />
</head>
<body class="login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="/"><b><%=App.APP_DISPLAY_NAME %></b></a>
		</div><!-- /.login-logo -->
	
		<div class="login-box-body">
			<p class="login-box-msg">パスワードの再設定</p>
			<form action="/user/account/settingPasswordEntry" method="post">
				<div class="form-group ${f:errorClass('password','has-error')}">
					<%if (errors.containsKey("password")){ %>
					<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.password}</label>
					<%} %>
					<input type="password" ${f:text("password")} class="form-control" placeholder="パスワード" />
				</div>
				
				<div class="form-group ${f:errorClass('passwordConfirmation','has-error')}">
					<%if (errors.containsKey("passwordConfirmation")){ %>
					<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.passwordConfirmation}</label>
					<%} %>
					<input type="password" name="passwordConfirmation" class="form-control" placeholder="パスワード確認"/>
				</div>
				
				<div>
              		<button type="submit" class="btn btn-primary btn-block btn-flat">設定してログイン</button>
            	</div><!-- /.col -->
            	<input type="hidden" name="entryId" value="<%=entryId %>">
			</form>

		</div><!-- /.login-box-body -->
	</div><!-- /.login-box -->
		
	<jsp:include page="/user/account/include-parts/html_copyright.jsp" />

	<jsp:include page="/user/account/include-parts/html_script.jsp" />
</body>
</html>
