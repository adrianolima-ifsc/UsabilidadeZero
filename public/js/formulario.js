$(document).ready(function() {

	var codigo = $('[name="codigo"]').val();
	var estudo = codigo.substr(2,1);
	var tarefa = codigo.substr(3,1);

	$('#nome').on('input', function() {

		var input = $(this);
		testarCampoVazio(input);

		var re = /[a-z]{3,}$/;
		var is_name = re.test(input.val());
		
	});

	$('#email').on('input', function() {
		
		var input = $(this);
		var re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
		var is_email = re.test(input.val());
		
		testarCampoVazio(input);
	});


	$('#fone').on('input', function() {

		var input = $(this);
		
		testarCampoVazio(input);
	});

	$('#cpf').on('input', function() {
		
		var input = $(this);
		var valor = input.val();

		if (estudo == 1) {

			mascaraCpf(input);
		}

		var re = /\d{3}.\d{3}.\d{3}-\d{2}/;
		var is_cpf = re.test(valor);
		
		testarCampoVazio(input);

	});

	$('#endereco').on('input', function() {

		var input = $(this);
		
		testarCampoVazio(input);
	});

	$('#cidade').on('input', function() {

		var input = $(this);
		
		testarCampoVazio(input);
	});

	$('#numCartao').on('input', function() {
		
		var input = $(this);
		var valor = input.val();

		if (estudo == 1) {

			mascaraCartao(input);
		}

		testarCampoVazio(input);
	});

	$('#titularCartao').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarCampoVazio(input);
	});

	$('#validade').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarCampoVazio(input);
	});

	$('#codigoSeguranca').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		testarCampoVazio(input);
	});
})