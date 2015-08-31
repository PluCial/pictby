<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.enums.*" %>
<%@ page import="com.pictby.service.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pictby.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="java.util.Map" %>
<%
User user = (User) request.getAttribute("loginUser");
Map<String,SocialLink> socialLinkMap = user.getSocialLinkMap();
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
	<link href="/plugins/cropper/cropper.min.css" rel="stylesheet">
	<link rel="stylesheet" href="/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css">
	<style>
.input-group {
	margin-bottom: 30px;
	width: 100%;
}

h4 {
	margin-bottom: 0;
	text-transform: none;
}

.btn-primary2.solid {
	margin-left: 15px;
}

.btn-primary2.solid:hover {
	color: #323232;
}
</style>
</head>
	
<body>

	<div class="body-inner">
		<!-- Header start -->
		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->

		<!-- sub_page_top -->
 		<jsp:include page="/include-parts/sub_page_top.jsp" />
    	<!--/ sub_page_top -->

		<form action="/user/secure/settingSocialEntry" method="post">
		
			<!-- Portfolio item start -->
			<section id="portfolio-item" style="padding-bottom: 0;">
				<div class="container">
				
				
					<!-- Portfolio item row start -->
					<div class="row">
						<!-- Portfolio item slider start -->
						<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 col-lg-offset-3 col-md-offset-3" style="margin-bottom: 30px;">
							<div class="post-content">
								<%for(Social social: Social.values()) { %>
								<h4><%=social.getSocialName() %></h4>
								<div class="input-group">
									<%if(!StringUtil.isEmpty(social.getBaseUrl())) { %>
									<span class="input-group-addon"><%=social.getBaseUrl() %></span>
									<%} %>
								
									<input type="text" name="<%=social.toString() %>" class="form-control" value="<%=socialLinkMap.containsKey(social.toString()) ? socialLinkMap.get(social.toString()).getUrlPath() : "" %>" />
								</div>
								<%} %>
							</div>
						</div>
					</div><!-- Portfolio item row end -->
				
				</div><!-- Container end -->
				
			</section><!-- Portfolio item end -->
		
			<section style="background: #f5f5f5;">
				<div class="container">
					<div class="row">
						<div class="col-lg-12 text-center">
							<p>
								<a href="/<%=user.getUserId() %>" class="btn btn-primary2">キャンセル</a>
								<button type="submit" class="btn btn-primary2 solid">保存して追加</button>
							</p>
						</div>
					</div>
				</div><!-- Container end -->
    		</section>
	
		</form>
		
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
</body>
</html>
