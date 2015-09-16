<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pictby.App" %>

		<div class="lockscreen-footer text-center">
        Copyright © <b><%=App.APP_DISPLAY_NAME %></b><br>
        All rights reserved
      	</div>
      	
      	<jsp:include page="/include-parts/dialog_modal.jsp">
			<jsp:param name="modelId" value="preparingModal" />
		</jsp:include>

	