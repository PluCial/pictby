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
<%@ page import="java.util.UUID" %>
<%
User user = (User) request.getAttribute("loginUser");
Errors errors =(Errors) request.getAttribute("errors");
String detail = (String) request.getAttribute("detail");
String token = UUID.randomUUID().toString();
String tagsinput = (String) request.getAttribute("tagsinput");
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
	max-height: 500px;
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

.bootstrap-tagsinput {
    width: 100%;
}
.bootstrap-tagsinput input {
font-size: 75%;
}

.tt-menu {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: none;
  float: left;
  min-width: 160px;
  padding: 5px 0;
  margin: 2px 0 0;
  list-style: none;
  font-size: 14px;
  background-color: #ffffff;
  border: 1px solid #cccccc;
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 4px;
  -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
  background-clip: padding-box;
}
.tt-suggestion {
  display: block;
  padding: 3px 20px;
  clear: both;
  font-weight: normal;
  line-height: 1.428571429;
  color: #333333;
  white-space: nowrap;
}
.tt-suggestion:hover,
.tt-suggestion:focus,
.tt-suggestion.tt-cursor {
  color: #ffffff;
  text-decoration: none;
  outline: 0;
  background-color: #428bca;
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

		<form action="/user/secure/itemAddEntry" method="post" enctype="multipart/form-data">
		
			<!-- Portfolio item start -->
			<section id="portfolio-item" style="padding-bottom: 0;">
				<div class="container">
				
				
					<!-- Portfolio item row start -->
					<div class="row">
						<!-- Portfolio item slider start -->
						<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12" style="margin-bottom: 30px;">
							<div class="post-content">
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
						
								<div id="crop-btn-group">
									<button class="btn btn-primary" id="enable-button" type="button">
										<i class="fa fa-crop"></i> 切り取り
									</button>
									<button class="btn btn-primary" id="clear-button" type="button">
										<i class="fa fa-remove"></i> キャンセル
									</button>
								</div>
							
								<div class="post-header clearfix">
									<div class="form-group ${f:errorClass('itemName','has-error')}">
										<%if (errors.containsKey("itemName")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.itemName}</label>
										<%} %>
										<input type="text" class="form-control" ${f:text("itemName")} placeholder="アイテム名"/>
									</div>
								</div>
							
								<div class="post-body">
									<div class="form-group ${f:errorClass('detail','has-error')}">
										<%if (errors.containsKey("detail")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.detail}</label>
										<%} %>
										<textarea name="detail" class="form-control" rows="10" placeholder="アイテム説明"><%=detail == null ? "" : detail %></textarea>
									</div>
								</div>
							</div>
						</div>
						<!-- Portfolio item slider end -->

						<!-- sidebar start -->
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<div class="sidebar">
									
								<div class="image-info widget">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr>
												<th style="text-align: center;">width</th>
												<th style="text-align: center;">height</th>
											</tr>
											<tr>
												<td>
													<div class="input-group">
														<input type="text" name="displayWidth" id="displayWidth" class="form-control" value="0" disabled="disabled" />
														<span class="input-group-addon">px</span>
													</div>
												</td>
												<td>
													<div class="input-group">
														<input type="text" name="displayHeight" id="displayHeight" class="form-control" value="0" disabled="disabled" />
														<span class="input-group-addon">px</span>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
									
								<div class="widget widget-tags">
									<h4 class="widget-title"><i class="fa fa-tags"></i> Tags</h4>
									<div class="tagsinput-box">
										<input name="tagsinput" value="<%=tagsinput == null ? "" : tagsinput %>" class="tagsinput" type="text" />
									</div>
								</div>
							
 								<div class="widget hidden-sm">
									<h4><i class="fa fa-user"></i> Author</h4>
									<div class="author-img">
										<img class="pull-left" src="<%=user.getIconImageResources() != null ? user.getIconImageUrl() + "=s100" : "/user/pub/images/icon-user-default.png" %>">
										<h4 class=""><a href="/<%=user.getUserId() %>"><%=user.getName() %></a></h4>
										<p><%=user.getCatchCopy() %></p>
									</div>
								</div>
									
								<input type="hidden" name="imageX" id="imageX" value="" />
								<input type="hidden" name="imageY" id="imageY" value="" />
 								<input type="hidden" name="imageHeight" id="imageHeight" value="" />
								<input type="hidden" name="imageWidth" id="imageWidth" value="" />
								<input type="hidden" name="itemTags" id="itemTags" value="" />

							</div>
						</div><!-- sidebar end -->
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
	<script src="/plugins/cropper/cropper.min.js" type="text/javascript"></script>
    <script src="/user/pub/js/item-add.js" type="text/javascript"></script>
    <script src="/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
    <script src="/plugins/bootstrap-tagsinput/bootstrap-tagsinput-angular.js"></script>
    <script src="/plugins/typeahead.js/dist/typeahead.bundle.js"></script>
	<script>
	jQuery(function($) {
		var bloodHound = new Bloodhound({
			datumTokenizer : function(d) {
				return Bloodhound.tokenizers.whitespace(d.name);
			},
			queryTokenizer : Bloodhound.tokenizers.whitespace,
			limit : 10,
			prefetch : {
				ttl : 1,
				url : '/user/userTags/<%=user.getUserId() %>/<%=token %>',
				filter : function(list) {
					return $.map(list, function(cityname) {
						return {
							name : cityname
						};
					});
				}
			}
		});
		bloodHound.clearPrefetchCache();
		bloodHound.initialize();

		$('input.tagsinput').tagsinput({
			typeaheadjs : {
				name : 'bloodHound',
				displayKey : 'name',
				valueKey : 'name',
				source : bloodHound.ttAdapter()
			}
		});
	});
	</script>
	<!-- javaScript end -->
</body>
</html>
