<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-search"></i> キーワード検索</h4>
	</div><!-- /modal-header -->
    <form id="search-form" action="/kws" method="get">
		<div class="modal-body">
			<input type="text" class="form-control" placeholder="Keywords" name="keyword">
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
			<button id="search-button" type="button" class="btn btn-primary">検索する</button>
		</div>	<!-- /modal-footer -->
	</form>
</body>
</html>
