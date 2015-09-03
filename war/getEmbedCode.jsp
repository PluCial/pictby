<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pictby.model.*" %>
<%
String domein = (String) request.getAttribute("domein");
String itemId = (String) request.getAttribute("itemId");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-code"></i> 画像の埋め込み方法</h4>
	</div><!-- /modal-header -->
	
		<div class="modal-body">
			<h3>ステップ１</h3>
			<p>次のコードをページの&lt;head&gt; 〜 &lt;/head&gt;の中、もしくは &lt;/body&gt;の直前に貼付けてください。</p>
			<pre>
				&lt;script src=&quot;//code.jquery.com/jquery-2.1.4.min.js&quot;&gt;&lt;/script&gt;
				&lt;script src=&quot;<%=domein %>/emJs&quot;&gt;&lt;/script&gt;
			</pre>
			<p style="font-size:0.8em">※他の画像の埋め込みで、既に貼付けてある場合はこの手順をスキップしてください。</p>
			
			<h3 style="margin-top: 50px;">ステップ２</h3>
			<p>画像を表示したい位置に次のタグを貼り付けてください。</p>
			<pre>
				&lt;div data-rel=&quot;pictby&quot; data-pictby-item=&quot;<%=itemId %>&quot;&gt;&lt;/div&gt;
			</pre>
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
		</div>	<!-- /modal-footer -->
</body>
</html>
