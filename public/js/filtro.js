$(document).ready(function(){

	var grid = $('.grid');

	grid.isotope({
		itemSelector: '#myDIV',
		layoutMode: 'fitRows'
	});

	$("#myInput").on("keyup", function() {
	
	 	var value = $(this).val().toLowerCase();
	
		grid.isotope({

			filter: function() {

				return $(this).text().toLowerCase().indexOf(value) > -1;
			}
		})
	})
});


