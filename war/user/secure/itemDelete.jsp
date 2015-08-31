<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.model.*" %>
<%@ page import="com.pictby.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pictby.model.UserTextRes" %>
<%
String itemId = (String) request.getAttribute("itemId");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-trash"></i> アイテムの削除</h4>
	</div><!-- /modal-header -->
    <form action="/user/secure/itemDeleteEntry" id="item-delete-submit-form" method="post">
		<div class="modal-body">
			<p>一度削除すると元に戻すことはできません。<br />このアイテムを本当に削除してよろしいですか？</p>
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
			<button id="item-delete-submit-button" type="button" class="btn btn-danger">削除する</button>
		</div>	<!-- /modal-footer -->
		<input type="hidden" name="itemId" value="<%=itemId == null ? "" : itemId %>" />
	</form>
</body>
</html>