<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pictby.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
User user = (User) request.getAttribute("loginUser");
Errors errors =(Errors) request.getAttribute("errors");
String detail = (String) request.getAttribute("detail");
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
	<link href="/plugins/cropper/cropper.min.css" rel="stylesheet">
	<link rel="stylesheet" href="/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css">
	<style>
.form-group,.portfolio-slider {
	margin-bottom: 25px;
}

.has-error label.control-label {
	color: #ee3b24;
}

input.form-control,textarea.form-control {
	color: #000;
}

.icheckbox_square-blue {
	background-position: 0 0;
}

.icheckbox_square-blue,.iradio_square-blue {
	display: inline-block;
	vertical-align: middle;
	margin: 0;
	padding: 0;
	width: 22px;
	height: 22px;
	background: url(/plugins/iCheck/square/blue.png) no-repeat;
	border: none;
	cursor: pointer;
}

.icheckbox_square-blue.checked {
	background-position: -48px 0;
}

.img-container {
	min-height: 300px;
	max-height: 300px;
	width: 100%;
	max-width: 750px;
	margin-bottom: 15px;
	box-shadow: inset 0 0 5px rgba(0, 0, 0, .25);
	background-color: #e5ecf9;
	overflow: hidden;
	background-image: url(/user/pub/images/image-png.png);
	background-repeat: no-repeat;
	background-position: center;
}

.avatar-upload label {
	display: block;
	float: left;
	clear: left;
	width: 100px;
}

.avatar-upload input {
	display: block;
	margin-left: 110px;
}

.image-info input {
	padding: 5px;
    margin: 0px;
    height: 30px;
    text-align: right;
}
.post-header {
margin-top: 30px;
}
#crop-btn-group {
	    display: inline-block;
}
#crop-btn-group,#clear-button {
	display: none;
}

.btn-primary2.solid {
	margin-left:15px;
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

		<form action="/user/secure/changeProfileImageEntry" method="post" enctype="multipart/form-data">
		
			<!-- Portfolio item start -->
			<section id="portfolio-item" style="padding-bottom: 0;">
				<div class="container">
								
					<!-- Portfolio item row start -->
					<div class="row">
						<!-- Portfolio item slider start -->
						<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 col-lg-offset-3 col-md-offset-3" style="margin-bottom: 30px;">
							<div class="post-content">
								<h3>新しいアイコン写真を選択してください。</h3>
								<%if (errors.containsKey("uploadImage")){ %>
								<div class="${f:errorClass('uploadImage','has-error')}">
									<label class="control-label has-error" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.uploadImage}</label>
								</div>
								<%} %>
								<div class="avatar-wrapper"></div>
								<div class="img-container">
									<img src="">
								</div>

								<label class="btn btn-primary btn-upload" for="inputImage" title="Upload image file">
									<input class="sr-only" id="inputImage" name="uploadImage" type="file" accept="image/*">
									<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="Import image with Blob URLs">
									<i class="fa fa-upload"></i> Import image</span>
								</label>

							</div>
						</div>
						<!-- Portfolio item slider end -->
						<!-- sidebar end -->
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
	
			<input type="hidden" name="imageX" id="imageX" value="" />
			<input type="hidden" name="imageY" id="imageY" value="" />
			<input type="hidden" name="imageHeight" id="imageHeight" value="" />
			<input type="hidden" name="imageWidth" id="imageWidth" value="" />
			<input type="hidden" name="resourcesKey" value="<%=user.getIconImageResources() != null ? user.getIconImageResources().getKey().getName() :"" %>" />
		</form>
		
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<script src="/plugins/cropper/cropper.min.js" type="text/javascript"></script>
    <script src="/user/pub/js/change-profile-image.js" type="text/javascript"></script>
	<!-- javaScript end -->
</body>
</html>
