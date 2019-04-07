$(document).ready(function(){

	var grid = $('.grid');

	grid.isotope({
		itemSelector: '#myDIV',
		layoutMode: 'fitRows',
		getSortData: {
			sigla: '.sigla',
			nome: '.nome',
			local: '.local',
			dataEvento: '.data-evento'
		}
	});

	$('#input-filtro').on("keyup", function() {

		var value = $(this).val().toLowerCase();

		grid.isotope({

			filter: function() {

				return $(this).text().toLowerCase().indexOf(value) > -1;
			}
		})
	})

	// bind sort button click
	$('.categorias').on( 'click', 'button', function() {

		var sortValue = $(this).attr('data-sort-value');
		grid.isotope({ sortBy: sortValue });

		console.log(sortValue);
	});
});


