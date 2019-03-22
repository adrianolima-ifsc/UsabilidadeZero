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

			var element = $("#contact_" + form_data[input]['name']);
			var valido = element.hasClass("valido");
			var error_element = $("span", element.parent());

			if (error_element.length == 0) {

				console.log(error_element);
				console.log("Está vazia!");
			}

			if (!valido) {
			
				error_element.removeClass("error").addClass("error_show"); 
				error_free = false;
			
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
		
			input.removeClass("invalido").addClass("valido");
		
		} else {

			input.removeClass("valido").addClass("invalido");
		}
	});

	$('#email').on('input', function() {

	})

	$('#fone').on('input', function() {

	})

	$('#cpf').on('input', function() {

	})

	$('#endereco').on('input', function() {

	})

	$('#cidade').on('input', function() {

	})

	$('#numCartao').on('input', function() {

	})

	$('#titularCartao').on('input', function() {

	})

	$('#validade').on('input', function() {

	})

	$('#condigoSeguranca').on('input', function() {

	})

	$('#email').on('input', function() {

	})

});