$(document).ready(function() {

	var idTarefa = $("#id-tarefa").text();
	var idEvento = $('[name="id-evento"]').val();
	var siglaEvento = $('.sigla').text();

	var codigo = $('[name="codigo"]').val();
	var estudo = codigo.substr(2,1);
	var tarefa = codigo.substr(3,1);

	// Tarefa 1
	$.appear('#inscricao h5');

	$('#inscricao h5').on('appear', function() {
		
		if (tarefa == 1) {

			if(siglaEvento == 'BSB') {

				tarefaConcluida();
			}
		}
	});

	$("#valor").on('click', function() {
		
		if (tarefa == 1) {

			if(siglaEvento == 'BSB') {

				tarefaConcluida();
			}
		}
	});

	//Tarefa 2
	$("#botao-inscricao").click(function(e){

		//e.preventDefault();

		var campos = $("#form-inscricao")
			.find('.ob')
			.serializeArray();
		var error_free = true;

		$.each(campos, function(i, campo) {

			var elemento = $('[name='+campo.name+']');
			var valido = elemento.hasClass("valido");
			var error_element = $("small", $(elemento).parent());

			if (!valido) {
			
				error_element.addClass("error").removeClass("no-error"); 
				error_free = false;
			
			} else {

				error_element.removeClass("error").addClass("no-error");
			}
		})

		if (!error_free) {

			e.preventDefault();
			limparCampos();
			alert("Todos os campos devem ser preenchidos corretamente! Tente novamente.");
		
		} else {

			if (estudo == 1) {
			
				alert("Sua inscrição foi realizada com sucesso!");
			}

			if (tarefa == 2) {

				if(siglaEvento == BRACIS) {

					$.get(jsRoutes.controllers.ControladorEstudos.fazerInscricao());
				}
				
				alert("Sua inscrição foi realizada com sucesso!");
			}
		}
	});

	//Tarefa 3
	$("#botao-certificado").click(function(e){

		//e.preventDefault();
		var dataEvento = $('[name="data-evento"]').val();

		var campos = $("#form-certificado")
			.find('.ob')
			.serializeArray();
		var error_free = true;

		$.each(campos, function(i, campo) {

			var elemento = $('[name='+campo.name+']');
			var valido = elemento.hasClass("valido");
			var error_element = $("small", $(elemento).parent());

			console.log(dataEvento);

			if (!valido) {
			
				error_element.addClass("error").removeClass("no-error"); 
				error_free = false;
			
			} else {

				error_element.removeClass("error").addClass("no-error");
			}
		})

		if (!error_free) {

			e.preventDefault();
			limparCampos();
			alert("Todos os campos devem ser preenchidos corretamente! Tente novamente.");
		
		} else if (dataEvento > 0) {
			
			e.preventDefault();
			alert("Não é possível emitir certificado de evento que ainda não aconteceu!");
		}
	});

	$(document).keypress(function(e){

		if (estudo == 0) {

			if (e.which == '13') e.preventDefault();
		}
	});

	function tarefaConcluida() {
		
		$.get(jsRoutes.controllers.ControladorEstudos.setConcluido(idTarefa));
	}

});

// https://formden.com/blog/validate-contact-form-jquery
