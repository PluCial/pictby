<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
String message =(String) request.getAttribute("message");
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
    	
    	<!-- Main container start -->

	<section id="main-container">
		<div class="container">
			
			<div class="row">
				<!-- Map start here -->
				<div id="map-wrapper" class="no-padding">
					<div class="map" id="map"></div>
				</div><!--/ Map end here -->	

			</div><!-- Content row  end -->

			<div class="gap-40"></div>

			<div class="row">
	    		<div class="col-md-7">
	    			<form id="contact-form" action="/info/contactEntry" method="post" role="form">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group ${f:errorClass('name','has-error')}">
									<%if (errors.containsKey("name")){ %>
									<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.name}</label>
									<%} %>
									<input class="form-control" ${f:text("name")} placeholder="名前" type="text" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group ${f:errorClass('name','has-error')}">
									<%if (errors.containsKey("email")){ %>
									<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.email}</label>
									<%} %>
									<input class="form-control" ${f:text("email")} placeholder="メール" type="email" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group ${f:errorClass('subject','has-error')}">
									<%if (errors.containsKey("subject")){ %>
									<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.subject}</label>
									<%} %>
									<input class="form-control" ${f:text("subject")} placeholder="件名" required>
								</div>
							</div>
						</div>
						<div class="form-group ${f:errorClass('message','has-error')}">
							<%if (errors.containsKey("message")){ %>
							<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.message}</label>
							<%} %>
							<textarea class="form-control" name="message" id="message" placeholder="本文" rows="10" required><%=message == null ? "" : message %></textarea>
						</div>
						<div class="text-right"><br>
							<button class="btn btn-primary solid blank" type="submit">Send Message</button> 
						</div>
					</form>
	    		</div>
	    		<div class="col-md-5">
	    			<div class="contact-info">
		    			<h3>Contact Details</h3>
			    		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eget erat magna. Pellentesque justo ante</p>
			    		<br>
			    		<p><i class="fa fa-home info"></i>  1102 Saint Marys, Junction City, KS </p>
						<p><i class="fa fa-phone info"></i>  +(785) 238-4131 </p>
						<p><i class="fa fa-envelope-o info"></i>  info@bizcraft.com</p>
						<p><i class="fa fa-globe info"></i>  www.bizcraft.com</p>
    				</div>
	    		</div>
	    	</div>

		</div><!--/ container end -->

	</section><!--/ Main container end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<script type="text/javascript">
	$("#map").gmap3({
        map:{
            options:{
               center:[-37.8152065,144.963937],
               zoom: 14,
               scrollwheel: false
            }
        },
        marker:{
          values:[
            {address:"Corner Swanston St & Flinders St, Melbourne VIC 3000, Australia", data:" Welcome To bizCraft ! ! ", 
             options:{icon: "http://themewinter.com/html/marker.png"}}
          ],
          options:{
            draggable: false
          },
          events:{
            mouseover: function(marker, event, context){
              var map = $(this).gmap3("get"),
                infowindow = $(this).gmap3({get:{name:"infowindow"}});
              if (infowindow){
                infowindow.open(map, marker);
                infowindow.setContent(context.data);
              } else {
                $(this).gmap3({
                  infowindow:{
                    anchor:marker, 
                    options:{content: context.data}
                  }
                });
              }
            },
            mouseout: function(){
              var infowindow = $(this).gmap3({get:{name:"infowindow"}});
              if (infowindow){
                infowindow.close();
              }
            }
          }
        }
      });
	</script>
	<!-- javaScript end -->
	
</body>
</html>
