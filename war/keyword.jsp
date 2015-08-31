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
List<Item> itemList =(List<Item>) request.getAttribute("itemList");
String keyword = (String)request.getAttribute("keyword");
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


		<!-- item-list start -->
		<section class="portfolio portfolio-box">
			<div class="container">
				
				<div class="row">
					<div class="col-md-12 heading">
						<span class="title-icon classic pull-left"><i class="fa fa-search"></i></span>
						<h2 class="title classic">キーワード: <%=keyword %></h2>
					</div>
				</div>
			</div>
			
			<%if(itemList.size() > 0) { %>
			<div class="container">
				<div class="row">
					<%for(Item item: itemList) {
						User user = item.getUserRef().getModel();
					%>
					<div class="col-md-4 col-xs-12 item-box wow fadeInUp animated">
						<figure class="" data-wow-duration="500ms" data-wow-delay="0ms">
							<div class="img-wrapper" style="background-image: url(<%=item.getOriginalImageUrl() %>)">
								<a href="/<%=user.getUserId() %>/item/<%=item.getKey().getName() %>"></a>
							</div>
							<figcaption>
                                <h4>
                                	<a href="/<%=user.getUserId() %>/item/<%=item.getKey().getName() %>"><%=item.getName() %></a>
                                </h4>
                                <p><b><%=item.getOriginalImageContentType().replace("image/", "") %></b> | <%=item.getOriginalImageWidth() %>px × <%=item.getOriginalImageHeight() %>px</p>
                             </figcaption>
							<div class="sort-bar">
								<i class="fa fa-ellipsis-v"></i>
								<i class="fa fa-ellipsis-v"></i>
							</div>
						</figure>
					</div>
					<%} %>
				</div>
			</div>
			<%} %>
		</section>
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
