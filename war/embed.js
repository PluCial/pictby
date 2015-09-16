$(function () {

	'use strict';

	(function($) { "use strict";
		$(document).ready(function(){

			$("*[data-rel^='pictby']").each(function(){
				var embedTag = $(this);
				var embedUrl =  embedTag.data('pictbyItem');
				
				$.ajax({
					type: 'GET',
					url: embedUrl,
					dataType: 'html',
					success: function(data) {
						embedTag.append(data);
						var figureTag = embedTag.children("figure");
						var figcaptionTag = figureTag.children("figcaption")
						
						figureTag.hover(
							function () {
								figcaptionTag.show();
							},
							function () {
								figcaptionTag.hide();
							}
						);
					}
				});
			});
		});
	})(jQuery);

});
