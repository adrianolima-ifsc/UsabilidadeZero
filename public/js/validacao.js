
function validarCampo(input, valor) {

	if(valor) {
	
		input.removeClass("invalido").addClass("valido");
	
	} else {

		input.removeClass("valido").addClass("invalido");
	}
}

function testarNome(input) {

	var regex = /[a-zA-Z]{3,}/;
	return regex.test(input.val());
}

function testarEmail(input) {

	var regex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
	return regex.test(input.val());
}

function testarCpf(input) {

	var regex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
	return regex.test(input.val());
}

function testarValidade(input) {

	var regex = /^[0-1]\d\/2\d{3}$/;
	return regex.test(input.val());
}