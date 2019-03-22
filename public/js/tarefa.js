$(document).ready(function() {

	var idTarefa = $("#id-tarefa").text();
	var idEvento = $('[name="id-evento"]').val();

	// Tarefa 1
	var tarefa = $('[name="codigo"]').val();

	$("#valor").change(function() {
		
		$(this).removeClass("invalido").addClass("valido");
		
		if (tarefa == "EC01" || tarefa == "EC11") {

			if(idEvento == 1) {

				$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
			}
		}
	});

	//Tarefa 2
	$("#botao-inscricao").click(function(e){

		//e.preventDefault();

		var campos = $("#form-inscricao")
			.find(':input')
			.not('[type="hidden"]')
			.serializeArray();
		var error_free = true;

		$.each(campos, function(i, campo) {

			var elemento = $('[name='+campo.name+']');
			var valido = elemento.hasClass("valido");
			var error_element = $("span", $(campo).parent());

			console.log(campo.name);
			console.log(valido);

			if (error_element.length == 0) {

				// console.log(error_element);
				// console.log("Está vazia!");
			}

			if (!valido) {
			
				error_element.removeClass("error").addClass("error_show"); 
				error_free = false;
			
			} else {

				error_element.removeClass("error_show").addClass("error");
			}
		})

		console.log(tarefa);
		console.log(idEvento);

		if (!error_free) {

			e.preventDefault();
			alert("Todos os campos devem ser preenchidos!") ;
		
		} else {

			if (tarefa == "EC11" || tarefa == "EC12" || tarefa == "EC11") {
			
				alert("Sua inscrição foi realizada com sucesso!");
			}

			if (tarefa == "EC02" || tarefa == "EC12") {

				if(idEvento == 2) {

					$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
				}
			}
		}

	});

	function testarValidade(entrada, elemento) {
		
		if(entrada) {
		
			elemento.removeClass("invalido").addClass("valido");
		
		} else {

			elemento.removeClass("valido").addClass("invalido");
		}
	}

	$('#nome').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#email').on('input', function() {
		
		var input = $(this);
		var re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
		var is_email = re.test(input.val());
		
		testarValidade(is_email, input);
	});


	$('#fone').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#cpf').on('input', function() {
		
		var input = $(this);
		var re = /[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}/;
		var is_cpf = re.test(input.val());
		
		testarValidade(is_cpf, input);

	});

	$('#endereco').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#cidade').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#numCartao').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#titularCartao').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#validade').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#codigoSeguranca').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

	$('#email').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarValidade(is_name, input);
	});

});

// https://formden.com/blog/validate-contact-form-jquery