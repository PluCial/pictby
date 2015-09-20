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
				<div class="row item-list-row">
					<jsp:include page="/user/pub/include-parts/item_list.jsp" />
					
					<%if(hasNext) { %>
					<div class="col-md-12 col-xs-12 text-center listHasNext">
						<a class="btn btn-default nextLink" href="/kwsnt?keyword=<%=keyword %>&cursor=<%=cursor %>">もっと見る</a>
					</div>
					<%} %>
				</div>
			</div>
			<%}else{ %>
			<div class="container">
			   検索結果が見つかりませんでした。
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
	
	<!-- waiting dialog -->
	<script type="text/javascript" src="/plugins/waiting-dialog/waiting-dialog.js"></script>
	
</body>
</html>
