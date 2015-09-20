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
<%@ page import="com.pictby.utils.*" %>
<%@ page import="java.util.Map" %>
<%
User user = (User) request.getAttribute("user");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
List<Item> itemList =(List<Item>) request.getAttribute("itemList");
Map<String,SocialLink> socialLinkMap = user.getSocialLinkMap();

String cursor = null;
boolean hasNext = false;
if (request.getAttribute("cursor") != null && request.getAttribute("hasNext") != null) {
	cursor = (String) request.getAttribute("cursor");
	hasNext = Boolean.valueOf((String) request.getAttribute("hasNext"));
}
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
</head>
	
<body>

	<div class="body-inner">
		<!-- Header start -->
 		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->

		<!-- sub_page_top -->
 		<jsp:include page="/include-parts/sub_page_top.jsp" />
    	<!--/ sub_page_top -->
    	
    	<section id="user-about">
    		<div class="container">
				<div class="col-md-5">
					<div class="author widget">
						 <div class="author-body text-center">
							<div class="author-img">
								<img src="<%=user.getIconImageResources() != null ? user.getIconImageUrl() + "=s150" : "/user/pub/images/icon-user-default.png" %>">
								<%if(isOwner) { %>
								<div class="parallax-overlay">
									<a href="/user/secure/changeProfileImage"><i class="fa fa-camera"></i></a>
								</div>
								<%} %>
							</div>
							<div class="author-bio">
								<h3>
									<span id="<%=user.getNameResourcesKey() %>"><%=user.getName() %></span>
									<%if(isOwner) { %>
									<a data-toggle="modal" 
										data-backdrop="static"
										data-target="#textResourcesModal" 
										style="color:#555"
										href="/user/secure/editTextUserResources?resourcesKey=<%=user.getNameResourcesKey() %>">
										<i class="fa fa-pencil-square-o edit-mode"></i>
									</a>
									<%} %>
								</h3>
								<p><span id="<%=user.getCatchCopyResourcesKey() %>"><%=user.getCatchCopy() %></span>
									<%if(isOwner) { %>
									<a data-toggle="modal" 
										data-backdrop="static"
										data-target="#textResourcesModal" 
										style="color:#555"
										href="/user/secure/editTextUserResources?userId=<%=user.getUserId() %>&resourcesKey=<%=user.getCatchCopyResourcesKey() %>">
										<i class="fa fa-pencil-square-o edit-mode"></i>
									</a>
									<%} %>
								</p>
									
								<div class="team-social">
									<%for(Map.Entry<String,SocialLink> linkEntry: socialLinkMap.entrySet()) {
										SocialLink socialLink = linkEntry.getValue();
										Social social = socialLink.getSocial();
										String baseUrl = StringUtil.isEmpty(social.getBaseUrl()) ? "" : social.getBaseUrl();
									%>
									<a class="<%=social.getLinkStyleClass() %>" href="<%=baseUrl + socialLink.getUrlPath() %>" target="_blank"><i class="<%=social.getIconStyleClass() %>"></i></a>
									<%} %>
										
									<%if(isOwner && socialLinkMap.size() > 0) { %>
									<a href="/user/secure/settingSocial" 
										style="background-color: transparent;color: #555;">
										<i class="fa fa-cogs"></i>
									</a>
									<%} %>
								</div>
							
								<div>
									<%if(isOwner && socialLinkMap.size() <= 0) { %>
									<a href="/user/secure/settingSocial">
										<i class="fa fa-link"></i> リンクの追加
									</a>
									<%} %>
								</div>
							</div>
						</div>
					</div>
				</div>
					
				<div class="col-md-7">
					<div class="author-detail">
						<h3>About Me</h3>
						<p><span id="<%=user.getDetailResourcesKey() %>"><%=Utils.getJspDisplayString(user.getDetail()) %></span>
							<%if(isOwner) { %>
							<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#textResourcesModal" 
								style="color:#555"
								href="/user/secure/editTextUserResources?spotId=<%=user.getUserId() %>&resourcesKey=<%=user.getDetailResourcesKey() %>">
								<i class="fa fa-pencil-square-o edit-mode"></i>
							</a>
							<%} %>
						</p>
					</div>
				</div>
			</div>
    	</section>


		<!-- item-list section start -->
		<%if(isOwner || itemList.size() > 0) { %>
		<section class="portfolio portfolio-box">
			<div class="container">
				
				<div class="row">
					<div class="col-md-12 heading text-center">
						<h2 class="title3">Portfolio <span style="font-size: 20px;">(<%=user.getItemCount() %>枚)</span>
						</h2>
					</div>
				</div>
				
				<%if(isOwner) { %>
				<div class="row text-center" style="margin-top:-10px;margin-bottom:20px;">
					<div class="col-md-12">
						<a class="btn btn-primary" href="/user/secure/itemAdd?userId=<%=user.getUserId() %>">
							<i class="fa fa-plus"></i> アイテムを追加
						</a>
					</div>
				</div>
				<%} %>
				
				<!-- tag-list -->
				<%if(itemList.size() > 0) { %>
				<div class="row text-center">
					<div class="isotope-nav" data-isotope-nav="isotope">
						<ul>
							<li><a href="/<%=user.getUserId() %>" class="active" data-filter="*">All</a></li>
							<%
								for(ItemTag itemTag : user.getItemTagList()) {
									if(itemTag.getItemCount() > 0) {
							%>
							<li><a href="/<%=user.getUserId() %>/tag/<%=itemTag.getTagName() %>"><%=itemTag.getTagName() %> (<%=itemTag.getItemCount() %>枚)</a></li>
							<%} %>
							<%} %>
						</ul>
					</div>
				</div>
				<%} %>
			</div><!-- tag-list end -->
			
			<!-- item-list -->
			<div class="container">
				<div class="row item-list-row <%=isOwner ? "connectedSortable" :""  %>">
				
					<jsp:include page="/user/pub/include-parts/item_list.jsp" />
				
					<%if(hasNext) { %>
					<div class="col-md-12 col-xs-12 text-center listHasNext">
						<a class="btn btn-default nextLink" href="/<%=user.getUserId() %>/itemListNext?cursor=<%=cursor %>">もっと見る</a>
					</div>
					<%} %>
					
				</div>
			</div><!-- item-list end -->
			
		</section><!-- item-list section end -->
		<%} %>
		

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
	<!-- dialog_modal start -->
	<%if(isOwner) { %>
	<jsp:include page="/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="textResourcesModal" />
	</jsp:include>
	<%} %>
	<!-- dialog_modal end -->
	
	<!-- waiting dialog -->
	<script type="text/javascript" src="/plugins/waiting-dialog/waiting-dialog.js"></script>
	
</body>
</html>
