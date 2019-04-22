$(document).ready(function() {

	var codigo = $('[name="codigo"]').val();
	var estudo = codigo.substr(2,1);
	var tarefa = codigo.substr(3,1);

	$('#nome').on('input', function() {

		var input = $(this);
		var valor = testarNome(input);

		validarCampo(input, valor);		
	});

	$('#email').on('input', function() {
		
		var input = $(this);
		var valor = testarEmail(input);

		validarCampo(input, valor);
	});

	$('#cpf').on('input', function() {
		
		var input = $(this);

		if (estudo == 1) mascaraCpf(input);

		var valor = testarCpf(input);

		validarCampo(input, valor);

	});

	$('#numCartao').on('input', function() {
		
		var input = $(this);

		if (estudo == 1) mascaraNumCartao(input);
		
		var valor = testarNumCartao(input);

		validarCampo(input, valor);
	});

	$('#titularCartao').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		validarCampo(input, valor);
	});

	$('#validade').on('input', function() {

		var input = $(this);

		if(estudo == 1) mascaraValidade(input);
		
		var valor = testarValidade(input);
		
		validarCampo(input, valor);
	});

	$('#codigoSeguranca').on('input', function() {

		var input = $(this);
		var is_name = input.val();
		
		validarCampo(input, valor);
	});
})

function goBack() {
	
  window.history.back();
}

function limparCampos() {

	var campos = $("#form-inscricao")
		.find('.campo')
		.serializeArray();

	$.each(campos, function(i, campo) {

		var elemento = $('[name='+campo.name+']');
		elemento.removeClass("valido invalido");
		elemento.val("");
	})
}