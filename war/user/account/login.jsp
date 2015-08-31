<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pictby.App" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
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
				<p class="login-box-msg">ログイン</p>
				<form action="/user/account/loginEntry" method="post">
					<div class="form-group ${f:errorClass('email','has-error')}">
						<%if (errors.containsKey("email")){ %>
						<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.email}</label>
						<%} %>
						<input type="email" ${f:text("email")} class="form-control" placeholder="メールアドレス" />
					</div>
					<div class="form-group ${f:errorClass('password','has-error')}">
						<%if (errors.containsKey("password")){ %>
						<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.password}</label>
						<%} %>
						<input type="password" ${f:text("password")} class="form-control" placeholder="パスワード" />
					</div>
					
					<div>
              			<button type="submit" class="btn btn-primary btn-block btn-flat">ログイン</button>
            		</div><!-- /.col -->	
				</form>

				<div class="social-auth-links text-center">
					<p>- もしくは -</p>
					<a class="btn btn-block btn-social btn-facebook btn-flat"
					 	data-toggle="modal" 
						data-backdrop="static"
						data-target="#preparingModal" 
						href="/preparing.html">
					<i class="fa fa-facebook"></i> Facebook アカウントで始める</a>
					
					<a class="btn btn-block btn-social btn-google-plus btn-flat"
					 	data-toggle="modal" 
						data-backdrop="static"
						data-target="#preparingModal" 
						href="/preparing.html">
					<i class="fa fa-google-plus"></i> Google+ アカウントで始める</a>
				</div>

				<a href="/user/account/resetPassword">パスワードをお忘れですか?</a><br>
				<a href="/user/account/register" class="text-center">まだアカウントをお持ちでない方はこちら</a>

			</div><!-- /.login-box-body -->
		</div><!-- /.login-box -->
		
		<jsp:include page="/user/account/include-parts/html_copyright.jsp" />

		<jsp:include page="/user/account/include-parts/html_script.jsp" />
	</body>
</html>
