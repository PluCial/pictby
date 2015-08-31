<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%
String modelId = (String)request.getParameter("modelId");
%>
	<div class="modal fade" id="<%=modelId %>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
        	<div class="modal-content">
        	</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div>
	
	
	<script>
		jQuery(function($) {
			
 			<%if(modelId.equals("textResourcesModal")) { %>
			/* ----------------------------------------------------------- */
			/*  text resources Edit
			/* ----------------------------------------------------------- */
			$('#textResourcesModal').on('hidden.bs.modal', function () {
				$('#textResourcesModal').removeData('bs.modal');
			});
	      
			$('#textResourcesModal').on('loaded.bs.modal', function () {
				var submitButton = $(this).find('#text-resources-submit-button');
				var submitform = $(this).find('#resources-form');
				var resourcesKey = submitform.find('[name=resourcesKey]').val();
	  		
				// submit
				submitButton.bind('click', function(e) {
	  			
					var formData = submitform.serialize();
					var newContent = submitform.find('[name=content]').val();
	  			
					$.ajax({
						type: "POST",
						url: "/user/secure/editTextResourcesEntry",
						data: formData,
						dataType: "json",
						success: function(data) {
							if(data.status == "OK") {
	  						
								var resourcesTarget = $('#' + resourcesKey);
	  						
								$('#textResourcesModal').modal('hide');
	  						
								resourcesTarget.html(newContent.replace(/\r?\n/g, '<br>'));
								resourcesTarget.css({"display":"none"});
								resourcesTarget.animate({ opacity: 'show'},{ duration: 1500, easing: 'swing'});
							}
						},
						complete: function(data) {
							console.log(data);
							button.attr("disabled", false);
						}
					});
	  			
				});
			});
			
			<%}else if(modelId.equals("itemDeleteModal")) { %>
			
			/* ----------------------------------------------------------- */
			/*  item delete
			/* ----------------------------------------------------------- */
			$('#itemDeleteModal').on('hidden.bs.modal', function () {
				$('#itemDeleteModal').removeData('bs.modal');
			});
	      
			$('#itemDeleteModal').on('loaded.bs.modal', function () {
				var submitButton = $(this).find('#item-delete-submit-button');
				var submitform = $(this).find('#item-delete-submit-form');
				var resourcesKey = submitform.find('[name=resourcesKey]').val();
	  		
				// submit
				submitButton.bind('click', function(e) {
					submitform.submit();
				});
			});
			
			<%}else if(modelId.equals("preparing")) { %>
			
			/* ----------------------------------------------------------- */
			/*  Search
			/* ----------------------------------------------------------- */
			$('#searchModal').on('hidden.bs.modal', function () {
				$('#searchModal').removeData('bs.modal');
			});
			
			$('#searchModal').on('loaded.bs.modal', function () {
				var submitButton = $(this).find('#search-button');
				var submitform = $(this).find('#search-form');
				var resourcesKey = submitform.find('[name=resourcesKey]').val();
	  		
				// submit
				submitButton.bind('click', function(e) {
					submitform.submit();
				});
			});
			
			
			<%}else if(modelId.equals("preparing")) { %>
			
			/* ----------------------------------------------------------- */
			/*  preparing
			/* ----------------------------------------------------------- */
			$('#preparingModal').on('hidden.bs.modal', function () {
				$('#preparingModal').removeData('bs.modal');
			});
			
			<%} %>
			
		});
	</script>
	