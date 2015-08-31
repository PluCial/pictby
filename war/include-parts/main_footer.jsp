<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pictby.App" %>
<%@ page import="com.pictby.model.*" %>
<%@ page import="com.pictby.enums.*" %>
	<section id="footer" class="footer service-footer">
		<div class="container">
		
			<div class="text-center">
				<div class="footer-widget">
					<h3 class="widget-title"><%=App.APP_DISPLAY_NAME %></h3>

					<ul class="unstyled">
						<li><a href="/info/agreement">利用規約</a></li>
						<li><a href="/info/privacy">プライバシーポリシー</a></li>
						<li>
							<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#preparingModal" 
								href="/preparing.html">
								運用会社</a>
						</li>
						<li>
							<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#preparingModal" 
								href="/preparing.html">よくある質問</a>
						</li>
						<li>
							<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#preparingModal" 
								href="/preparing.html">お問い合わせ</a>
						</li>
					</ul>
				</div>

			</div><!--/ Row end -->
			
			<div class="row">
				<div class="col-md-12 text-center">
							
					<div class="copyright-info">
         			&copy; Copyright <%=App.APP_DISPLAY_NAME %> <span>All Rights Reserved</span>
        			</div>
				</div>
			</div><!--/ Row end -->
		   <div id="back-to-top" data-spy="affix" data-offset-top="10" class="back-to-top affix">
				<button class="btn btn-primary2" title="Back to Top"><i class="fa fa-angle-double-up"></i></button>
			</div>
		</div><!--/ Container end -->
		
	</section>
	
	<jsp:include page="/user/dialog_modal.jsp">
		<jsp:param name="modelId" value="preparingModal" />
	</jsp:include>
	<jsp:include page="/user/dialog_modal.jsp">
		<jsp:param name="modelId" value="searchModal" />
	</jsp:include>