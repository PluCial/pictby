<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.utils.*" %>
<%@ page import="com.pictby.model.*" %>
<%
User user = (User) request.getAttribute("user");
Item item = (Item) request.getAttribute("item");
String domein = (String) request.getAttribute("domein");
String linkUrl = domein + "/" + user.getUserId();
int imageWidth = item.getOriginalImageWidth() >= App.EMBED_MAX_IMAGE_WIDTH ? App.EMBED_MAX_IMAGE_WIDTH : item.getOriginalImageWidth();
%>
	
	<figure style="position: relative;margin: 0;padding: 0;">
		<div style="margin: 0;padding: 0;">
				<img src="<%=item.getOriginalImageUrl() %>=s<%=imageWidth %>" alt="<%=item.getName() %>" style="display: block;width: 100%;opacity: 1;" />
		</div>
		<figcaption style="margin: 0;padding: 0;position: absolute;bottom: 0;left: 0;display: block;width: 100%;font-size: 0.8em;">

				<a href="<%=linkUrl %>" 
					style="
						color: #fff;
 						display: block;
						text-decoration: none;
						padding: 5px;
						position: relative;
						text-align: right;">
					<span style="padding-right: 10px;text-shadow: 1px 1px 3px rgba(0,0,0,.5),1px 1px 5px rgba(0,0,0,.3);">PICTBY</span>
					<span style="text-shadow: 1px 1px 3px rgba(0,0,0,.5),1px 1px 5px rgba(0,0,0,.3);"><%=user.getName() %></span>
				</a>

		</figcaption>
	</figure>
