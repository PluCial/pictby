<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
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
	    		<div class="col-md-12">
					お問い合わせありがとうございました。
	    		</div>
	    	</div>

		</div><!--/ container end -->

	</section><!--/ Main container end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
</body>
</html>
