$(document).ready(function(){

	// Iniciando isotope
	var gridProximos = $('.grid-proximos');
	var gridPassados = $('.grid-passados');

	iniciaAnimacao(gridProximos);
	iniciaAnimacao(gridPassados);

	function iniciaAnimacao(grid) {

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
	}

	// Filtrando eventos
	$('#input-filtro').on("keyup", function() {

		filtrar(this, gridProximos);
		filtrar(this, gridPassados);
	})

	function filtrar(input, grid) {

		var value = $(input).val().toLowerCase();

		grid.isotope({

			filter: function() {

				return $(this).text().toLowerCase().indexOf(value) > -1;
			}
		})
	}

	// Ordenando eventos
	$('.categorias-proximos').on( 'click', 'button', function() {

		ordena(this, gridProximos);
	});

	$('.categorias-passados').on( 'click', 'button', function() {

		ordena(this, gridPassados);
	});

	function ordena(categorias, grid) {

		var sortValue = $(categorias).attr('data-sort-value');
		grid.isotope({ sortBy: sortValue });

		console.log(sortValue);
	}
});


