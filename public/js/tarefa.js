$(document).ready(function() {

	var idTarefa = $("#id-tarefa").text();
	var idEvento = $('[name="id-evento"]').val();

	var codigo = $('[name="codigo"]').val();
	var estudo = codigo.substr(2,1);
	var tarefa = codigo.substr(3,1);

	// Tarefa 1
	$("#valor").change(function() {
		
		$(this).removeClass("invalido").addClass("valido");
		
		if (tarefa == 1) {

			if(idEvento == 1) {

				$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
			}
		}
	});

	//Tarefa 2
	$("#botao-inscricao").click(function(e){

		//e.preventDefault();

		var campos = $("#form-inscricao")
			.find('.form-control')
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

			if (!valido) {
			
				error_element.addClass("error").removeClass("text-muted"); 
				error_free = false;
			
			} else {

				error_element.removeClass("error").addClass("text-muted");
			}
		})

		if (!error_free) {

			e.preventDefault();
			alert("Todos os campos devem ser preenchidos!") ;
		
		} else {

			if (estudo == 1) {
			
				alert("Sua inscrição foi realizada com sucesso!");
			}

			if (tarefa == 2) {

				if(idEvento == 2) {

					//$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
				}
			}
		}
	});

	$(document).keypress(function(e){

		if (estudo == 0) {

			if (e.which == '13') e.preventDefault();
		}
	});

});

// https://formden.com/blog/validate-contact-form-jquery