<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%
String cursor = null;
boolean hasNext = false;
if (request.getAttribute("cursor") != null && request.getAttribute("hasNext") != null) {
	cursor = (String) request.getAttribute("cursor");
	hasNext = Boolean.valueOf((String) request.getAttribute("hasNext"));
}
%>
					<jsp:include page="/user/pub/include-parts/item_list.jsp" />
				
					<%if(hasNext) { %>
					<div class="col-md-12 col-xs-12 text-center listHasNext">
						<a class="btn btn-default" href="/portfolioNext?cursor=<%=cursor %>">もっと見る</a>
					</div>
					<%} %>
