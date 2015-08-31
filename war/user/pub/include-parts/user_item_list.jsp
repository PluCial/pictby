<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pictby.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
User user =(User) request.getAttribute("user");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
List<Item> itemList =(List<Item>) request.getAttribute("itemList");
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
%>
		<div class="container">
			<div class="row <%=isOwner && isEditPage ? "connectedSortable" :""  %>">
				
				<%for(Item item: itemList) {%>
				<div class="col-md-4 col-xs-12 item-box wow fadeInUp animated" 
					data-user-id="<%=user.getUserId() %>" 
					data-item-id="<%=item.getKey().getName() %>" 
					data-order="<%=item.getSortOrder() %>" 
					data-sort-max-order="<%=user.getItemCount() %>">
						<figure class="" data-wow-duration="500ms" data-wow-delay="0ms">
							<div class="img-wrapper" style="background-image: url(<%=item.getOriginalImageUrl() %>)">
								<a href="/<%=user.getUserId() %>/item/<%=item.getKey().getName() %>"></a>
							</div>
							<figcaption>
                                <h4 class="text-ellipsis">
                                	<a href="/<%=user.getUserId() %>/item/<%=item.getKey().getName() %>"><%=item.getName() %></a>
                                </h4>
                                <p><b><%=item.getOriginalImageContentType().replace("image/", "") %></b> | <%=item.getOriginalImageWidth() %>px × <%=item.getOriginalImageHeight() %>px</p>
                             </figcaption>
							<div class="sort-bar">
								<i class="fa fa-ellipsis-v"></i>
								<i class="fa fa-ellipsis-v"></i>
							</div>
						</figure>
				</div><!--/ item 1 end -->
				<%} %>
					
			</div><!-- Content row end -->
		</div><!-- Container end -->