<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.model.*" %>
<%@ page import="com.pictby.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%
User user = (User) request.getAttribute("loginUser");
String itemId = (String) request.getAttribute("itemId");
String resourcesKey = (String) request.getAttribute("resourcesKey");
UserTextRes textResources = (UserTextRes) request.getAttribute("textResources");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<style>
		.modal-body input, .modal-body textarea {
			color: #555;font-size: 16px;
		}
	</style>
</head>
<body>
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-pencil-square-o"></i> <%=textResources != null ? textResources.getRole().getName() : "" %></h4>
	</div><!-- /modal-header -->
	
    <form id="resources-form" action="#">
		<div class="modal-body">
			
			<%if(textResources != null) { %>
			<%if(!textResources.getRole().isLongText()) { %>
			<input type="text" name="content" class="form-control" style="height: 40px;" value="<%=textResources.getContent().getValue() %>" />
			<%}else { %>
			<textarea name="content" class="form-control" rows="10"><%=textResources.getContent().getValue() %></textarea>
			<%} %>
			<%} %>
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
			<button type="button" id="text-resources-submit-button" class="btn btn-info">変更</button>
		</div>	<!-- /modal-footer -->
		<input type="hidden" name="userId" value="<%=user.getUserId() %>" />
		<input type="hidden" name="itemId" value="<%=itemId == null ? "" : itemId %>" />
		<input type="hidden" name="resourcesKey" value="<%=resourcesKey %>" />
	</form>
</body>
</html>