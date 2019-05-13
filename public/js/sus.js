$(document).ready(function() {

	$("#botao-enviar").click(function(e){

		e.preventDefault();

		var campos = $("#form-sus")
			.serializeArray();
		var error_free = true;

		$.each(campos, function(i, campo) {

			var elemento = $('[name='+campo.name+']');
			var valido = elemento.hasClass("valido");
			var error_element = $("small", $(elemento).parent());

			console.log(elemento.val());

			if (!valido) {
			
				error_element.addClass("error").removeClass("no-error"); 
				error_free = false;
			
			} else {

				error_element.removeClass("error").addClass("no-error");
			}
		});

		if (!error_free) {

			e.preventDefault();
			alert("Todos os campos devem ser preenchidos corretamente! Tente novamente.");
		
		} else {

			if (tarefa == 2) {

				if(siglaEvento == BRACIS) {

					$.get(jsRoutes.controllers.ControladorEstudos.fazerInscricao());
				}
				
				alert("Sua inscrição foi realizada com sucesso!");
			}
		}
	});
})