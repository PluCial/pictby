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
<%@ page import="com.pictby.utils.*" %>
<%@ page import="java.util.UUID" %>
<%
User user = (User) request.getAttribute("user");
Item item = (Item) request.getAttribute("item");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));
String token = UUID.randomUUID().toString();
String tagsinput = "";
if(item.getTagsList() != null && item.getTagsList().size() > 0) {
	tagsinput = item.getTagsList().toString().replaceAll("^\\[", "").replaceAll("\\]$", "");
}
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
<%if(isOwner) { %>
	<link rel="stylesheet" href="/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css">
<style>
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

.tt-suggestion:hover,.tt-suggestion:focus,.tt-suggestion.tt-cursor {
	color: #ffffff;
	text-decoration: none;
	outline: 0;
	background-color: #428bca;
}
</style>
<%} %>
</head>
	
<body>
	<div class="body-inner">
		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->

		<!-- sub_page_top -->
 		<jsp:include page="/include-parts/sub_page_top.jsp" />
    	<!--/ sub_page_top -->


		<!-- Portfolio item start -->
		<section id="portfolio-item">
			<div class="container">
				<!-- Portfolio item row start -->
				<div class="row">
					<!-- Portfolio item slider start -->
					<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
						<div class="post-content">
							<div class="post-image-wrapper">
								<img src="<%=item.getOriginalImageUrl() + "=s800" %>" alt="" class="img-responsive" style="<%=isLocal ? "width:100%;height:auto;" : "" %>">
							</div>
							<div class="post-header clearfix">
								<h2 class="post-title">
									<span id="<%=item.getNameResourcesKey() %>"><%=item.getName() %></span>
									<%if(isOwner) { %>
									<a data-toggle="modal" 
										data-backdrop="static"
										data-target="#textResourcesModal" 
										style="color:#323232;"
										href="/user/secure/editTextItemResources?itemId=<%=item.getKey().getName() %>&resourcesKey=<%=item.getNameResourcesKey() %>">
										<i class="fa fa-pencil-square-o edit-mode"></i>
									</a>
									<%} %>
								</h2>
							</div>
							<div class="post-body">
								<p>
									<span id="<%=item.getDetailResourcesKey() %>"><%=Utils.getJspDisplayString(item.getDetail()) %></span>
									<%if(isOwner) { %>
									<a data-toggle="modal" 
										data-backdrop="static"
										data-target="#textResourcesModal" 
										style="color:#323232;"
										href="/user/secure/editTextItemResources?itemId=<%=item.getKey().getName() %>&resourcesKey=<%=item.getDetailResourcesKey() %>">
										<i class="fa fa-pencil-square-o edit-mode"></i>
									</a>
									<%} %>
								</p>
							</div>
						</div>
					</div>
					<!-- Portfolio item slider end -->

					<!-- sidebar start -->
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="sidebar">
						
							<div class="image-info widget">
								<table class="table table-bordered table-striped">
									<tbody style="text-align: center;">
										<tr>
											<th style="text-align: center;">Type</th>
											<th style="text-align: center;">width</th>
											<th style="text-align: center;">height</th>
										</tr>
										<tr>
											<td>
												<b><%=item.getOriginalImageContentType().replace("image/", "") %></b>
											</td>
											<td>
												<span><b><%=item.getOriginalImageWidth() %></b> px</span>
											</td>
											<td>
												<span><b><%=item.getOriginalImageHeight() %></b> px</span>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						
							<div class="widget widget-tags">
								<h4 class="widget-title"><i class="fa fa-tags"></i> Tags</h4>
				    	        <%if(isOwner) { %>
				        	    <div class="tagsinput-box">
									<input name="tagsinput" value="<%=tagsinput %>" class="tagsinput" type="text" />
								</div>
								<%}else{ %>
								<ul class="unstyled clearfix">
									<%for(String tag: item.getTagsList()) { %>
					              	<li><a href="/<%=user.getUserId() %>/tag/<%=tag %>"><%=tag %></a></li>
					              	<%} %>
					            </ul>
								<%} %>
							</div>
						
							<div class="widget">
								<h4><i class="fa fa-user"></i> Creator</h4>
								<div class="author-img">
									<img class="pull-left" src="<%=user.getIconImageResources() != null ? user.getIconImageUrl() + "=s100" : "/user/pub/images/icon-user-default.png" %>">
									<h4 class=""><a href="/<%=user.getUserId() %>"><%=user.getName() %></a></h4>
									<p><%=user.getCatchCopy() %></p>
								</div>
							</div>
						
							<%if(isOwner) { %>
							<div class="widget" style="padding-top: 50px;">
								<div class="text-center" style="border-top: 1px solid #ddd;border-bottom: 1px solid #ddd;padding: 20px 0;">
									<a data-toggle="modal" 
										data-backdrop="static" 
										data-target="#itemDeleteModal" 
										href="/user/secure/itemDelete?itemId=<%=item.getKey().getName() %>" 
										class="btn btn-danger"><i class="fa fa-trash"></i> このアイテムを削除する
									</a>
								</div>
							</div>
							<%} %>
						
						</div>
					</div>
					<!-- sidebar end -->
				</div><!-- Portfolio item row end -->
			</div><!-- Container end -->
		</section><!-- Portfolio item end -->
		
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
	<!-- text_resources_edit_modal start -->
	<%if(isOwner) { %>
	<jsp:include page="/user/dialog_modal.jsp">
		<jsp:param name="modelId" value="textResourcesModal" />
	</jsp:include>
	<jsp:include page="/user/dialog_modal.jsp">
		<jsp:param name="modelId" value="itemDeleteModal" />
	</jsp:include>
	<!-- waiting dialog -->
	<script type="text/javascript" src="/plugins/waiting-dialog/waiting-dialog.js"></script>
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

		
		$('input.tagsinput').on('itemAdded', function(event) {
				console.log('item added : ' + event.item);

				waitingDialog.show();
				
				var changeUrl = "/user/secure/tagAddEntry?itemId=<%=item.getKey().getName() %>&tag=" + event.item.trim();

				$.ajax({
					type : "GET",
					url : changeUrl,
					dataType : "json",
					success : function(data) {
						if (data.status != "OK") {
							return;
						}
					},
					error : function(data) {
						return;
					},
					complete : function(data) {
						waitingDialog.hide();
						return;
					}
				});
			});
		

		$('input.tagsinput').on('itemRemoved', function(event) {
				console.log('item removed : ' + event.item);

				waitingDialog.show();

				var changeUrl = "/user/secure/tagDeleteEntry?itemId=<%=item.getKey().getName() %>&tag=" + event.item.trim();

				$.ajax({
					type : "GET",
					url : changeUrl,
					dataType : "json",
					success : function(data) {
						if (data.status != "OK") {
							return;
						}
					},
					error : function(data) {
						return;
					},
					complete : function(data) {
						waitingDialog.hide();
						return;
					}
				});
			});
		});
	</script>
	<%} %>
	<!-- text_resources_edit_modal end -->
</body>
</html>
