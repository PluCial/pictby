<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
String accessDomein =(String) request.getAttribute("accessDomein");
String detail = (String) request.getAttribute("detail");
String entryId = (String) request.getAttribute("entryId");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/user/account/include-parts/html_head.jsp" />
		<style>
			.container {
				min-height: 100vh;
			}
			h3 {
				  text-align: center;
				  margin-bottom: 2em;
			}
			.box {
				  background: #fff;
				  padding: 20px;
			}
			.content {
				margin: 5% auto;
				max-width: 600px;
			}
			.checkbox {
				margin-top: 2em;
			}
		</style>
	</head>
	<body class="skin-blue sidebar-collapse">
		<div class="wrapper">
      
      
			<div class="content-wrapper">
				<div class="container">

					<section class="content">
						<h3>アカウントの作成</h3>
			
						<div class="box box-primary">
                
							<!-- form start -->
							<form action="/user/account/addAccountEntry" method="post">
								<div class="box-body">
								
									<div class="form-group ${f:errorClass('userId','has-error')}">
										<%if (errors.containsKey("userId")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.userId}</label>
										<%}else { %>
										<label for="exampleInputEmail1">ユーザーID</label>
										<%} %>
										<div class="input-group">
											<span class="input-group-addon" style="color: #333;"><b><%=accessDomein %>/+</b></span>
											<input type="text" ${f:text("userId")} class="form-control" placeholder="ユーザーID">
										</div>
									</div>
									
									<div class="form-group ${f:errorClass('catchCopy','has-error')}">
										<%if (errors.containsKey("catchCopy")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.catchCopy}</label>
										<%}else { %>
										<label for="exampleInputEmail1">キャッチコピー</label>
										<%} %>
										<input type="text" ${f:text("catchCopy")} class="form-control" placeholder="キャッチコピー">
									</div>
									
									<div class="form-group ${f:errorClass('detail','has-error')}">
										<%if (errors.containsKey("detail")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.detail}</label>
										<%}else { %>
										<label for="exampleInputEmail1">自己紹介</label>
										<%} %>
										<textarea class="form-control" name="detail" rows="5" placeholder="特徴・説明"><%=detail == null ? "" : detail %></textarea>
									</div>
									
									<input type="hidden" name="entryId" value="<%=entryId %>" />

								</div><!-- /.box-body -->

								<div class="box-footer">
									<button type="submit" class="btn btn-primary pull-right">登録する</button>
								</div>
							</form>
						</div>
            
					</section>
          			<jsp:include page="/user/account/include-parts/html_copyright.jsp" />
				</div><!-- /.container -->
			</div><!-- /.content-wrapper -->

		</div><!-- ./wrapper -->

		<jsp:include page="/user/account/include-parts/html_script.jsp" />

	</body>
</html>

