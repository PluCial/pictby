<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%
String domein = (String) request.getAttribute("domein");
%>
$(function () {

	'use strict';

	(function($) { "use strict";
		$(document).ready(function(){

			$("*[data-rel^='pictby']").each(function(){
				var embedTag = $(this);
				var itemId =  embedTag.data('pictbyItem');
				var embedUrl =  '<%=domein %>/embed/' + itemId
				
				$.ajax({
					type: 'GET',
					url: embedUrl,
					dataType: 'html',
					success: function(data) {
						embedTag.append(data);
					}
				});
			});
		});
	})(jQuery);

});