$(document).ready(function() {

	var idTarefa = $("#id-tarefa").text();
	var idEvento = $('[name="id-evento"]').val();

	// Tarefa 1
	var tarefa = $('[name="codigo"]').val();

	$("#valor").change(function() {
		
		if (tarefa === "EC01" || tarefa === "EC11") {

			if(idEvento == 1) {

				$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
			}
		}
	});

	//Tarefa 2
	$("#botao-inscricao").click(function(event){

		var form_data = $("#form-inscricao").serializeArray();
		var error_free = true;

		for (var input in form_data) {

			var element = $("#contact_"+form_data[input]['name']);
			var valid = element.hasClass("valid");
			var error_element = $("span", element.parent());

			if (error_element.length == 0) {

				console.log(error_element);
				console.log("Está vazia!");
			}

			if (!valid) {
			
				error_element.removeClass("error").addClass("error_show"); 
				error_free=false;
			
			} else {

				error_element.removeClass("error_show").addClass("error");
			}
		}

		if (!error_free) {

			event.preventDefault(); 
		}
	});

	$('#email').on('input', function() {
		
		var input = $(this);
		var re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
		var is_email = re.test(input.val());
		
		if(is_email) {
		
			input.removeClass("invalid").addClass("valid");
		
		} else {

			input.removeClass("valid").addClass("invalid");
		}
	});
});