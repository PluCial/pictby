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
String tag = (String) request.getAttribute("tag");
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
								<img src="<%=user.getIconImageResources() != null ? user.getIconImageUrl() + "=s100" : "/user/pub/images/icon-user-default.png" %>">
							</div>
							<div class="author-bio">
								<h3><a href="/<%=user.getUserId() %>"><%=user.getName() %></a></h3>
								<p><span id="<%=user.getCatchCopyResourcesKey() %>"><%=user.getCatchCopy() %></span></p>
									
								<div class="team-social">
									<%for(Map.Entry<String,SocialLink> linkEntry: socialLinkMap.entrySet()) {
										SocialLink socialLink = linkEntry.getValue();
										Social social = socialLink.getSocial();
										String baseUrl = StringUtil.isEmpty(social.getBaseUrl()) ? "" : social.getBaseUrl();
									%>
									<a class="<%=social.getLinkStyleClass() %>" href="<%=baseUrl + socialLink.getUrlPath() %>"><i class="<%=social.getIconStyleClass() %>"></i></a>
									<%} %>
										
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-7">
					<div class="author-detail">
						<h3>About Me</h3>
						<p><span id="<%=user.getDetailResourcesKey() %>"><%=Utils.getJspDisplayString(user.getDetail()) %></span></p>
					</div>
				</div>
			</div>
    	</section>


		<!-- item-list start -->
		<%if(itemList.size() > 0) { %>
		<section class="portfolio portfolio-box">
			<div class="container">
				
				<div class="row">
					<div class="col-md-12 heading text-center">
						<h2 class="title3">Portfolio
						</h2>
					</div>
				</div>
				
				<div class="row text-center">
					<div class="isotope-nav" data-isotope-nav="isotope">
						<ul>
							<li><a href="/<%=user.getUserId() %>">All</a></li>
							<%
								for(ItemTag itemTag : user.getItemTagList()) {
									if(itemTag.getItemCount() > 0) {
							%>
							<li><a class="<%=tag.equals(itemTag.getTagName()) ? "active" : "" %>" href="/<%=user.getUserId() %>/tag/<%=itemTag.getTagName() %>"><%=itemTag.getTagName() %></a></li>
							<%	} %>
							<%} %>
						</ul>
					</div>
				</div>
				
			</div>
			<jsp:include page="/user/pub/include-parts/user_item_list.jsp">
				<jsp:param name="isEditPage" value="false" />
			</jsp:include>
		</section>
		<%} %>
		<!-- item-list end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
</body>
</html>
