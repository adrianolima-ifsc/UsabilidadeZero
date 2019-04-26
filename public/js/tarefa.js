$(document).ready(function() {

	var idTarefa = $("#id-tarefa").text();
	var idEvento = $('[name="id-evento"]').val();

	var codigo = $('[name="codigo"]').val();
	var estudo = codigo.substr(2,1);
	var tarefa = codigo.substr(3,1);

	// Tarefa 1
	$.appear('#inscricao h5');

	$('#inscricao h5').on('appear', function() {
		
		if (tarefa == 1) {

			if(idEvento == 1) {

				tarefaConcluida();
			}
		}
	});

	$("#valor").on('click', function() {
		
		$(this).removeClass("invalido").addClass("valido");
		
		if (tarefa == 1) {

			if(idEvento == 1) {

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

			console.log(campo.name);
			console.log(campo);
			console.log(valido);
			console.log(error_element);
			console.log(elemento);
			console.log(elemento.parent());
			console.log(error_free);

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
			alert("Todos os campos devem ser preenchidos!. Tente novamente.");
		
		} else {

			if (estudo == 1) {
			
				alert("Sua inscrição foi realizada com sucesso!");
			}

			if (tarefa == 2) {

				if(idEvento == 2) {

					$.get(jsRoutes.controllers.ControladorEstudos.fazerInscricao());
				}
			}
		}
	});

	$(document).keypress(function(e){

		if (estudo == 0) {

			if (e.which == '13') e.preventDefault();
		}
	});

	function tarefaConcluida() {
		
		$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
	}

});

// https://formden.com/blog/validate-contact-form-jquery