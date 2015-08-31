<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pictby.App" %>
	<section id="page-top" style="background-image:url(/images/ciel.jpg)">
 		<div class="parallax-overlay"></div>
		<div class="inner"><!-- spot-image -->          
			<div class="detail">
				<h1>
					<a href="/"><%=App.APP_DISPLAY_NAME %></a>
				</h1>
				<h3>FREE TO USE THE CREATOR'S PICTURES</h3>
				<div class="search-box text-center">
					<form id="search" action="/kws" method="get">
						<div class="input-group">
                    		<span class="input-group-addon"><i class="fa fa-search"></i></span>
                    		<input type="text" class="form-control" placeholder="Keywords" name="keyword">
                    		<input type="submit" value="検索" style="display: none;">
                  		</div>
                 		</form>
				</div>
			</div>
		</div><!-- spot-catch end-->
	</section>