<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.model.*" %>
<%
User loginUser =(User) request.getAttribute("loginUser");
boolean isLogged = Boolean.valueOf((String) request.getAttribute("isLogged"));
%>
	<!-- Header start -->
	<header id="header" class="navbar-fixed-top header6" role="banner">
		<div class="container">
			<div class="row">
				<!-- Logo start -->
				<div class="navbar-header">
				    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				        <span class="sr-only">Toggle navigation</span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				    </button>
				    <div class="navbar-brand">
 				    	<a class="logo-image" href="/"><span><%=App.APP_DISPLAY_NAME %></span></a>
				    </div>
				    <div class="navbar-brand">
				    	<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#preparingModal" 
								style="font-size: 18px;height: 50px;border-radius: 0px;display: inline-block;padding-left: 20px;padding-right: 20px;background-color: rgba(22, 26, 30, 0.8);"
								class=""
								href="/kwsb"><i class="fa fa-search" style="color: #fff;"></i>
						</a>
				    </div>
				</div><!--/ Logo end -->
				<nav class="collapse navbar-collapse clearfix" role="navigation">
					<ul class="nav navbar-nav navbar-right">
						<%if(isLogged) { %>
						<li><a href="/<%=loginUser.getUserId() %>"><i class="fa fa-user"></i> <%=loginUser.getName() %></a></li>
						<li><a href="/user/secure/logout"><i class="fa fa-sign-out"></i> ログアウト</a></li>
						<%}else { %>
	                    <li><a href="/user/account/register"><i class="fa fa-plus"></i> クリエイター登録</a></li>
            			<li><a href="/user/account/login"><i class="fa fa-sign-in"></i> ログイン</a></li>
            			<%} %>
                    </ul>
				</nav><!--/ Navigation end -->
			</div><!--/ Row end -->
		</div><!--/ Container end -->
	</header><!--/ Header end -->