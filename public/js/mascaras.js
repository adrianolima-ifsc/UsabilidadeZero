
function mascaraCpf(input) {

	var valor = input.val();

	input.attr("maxlength", "14");

	valor = valor.replace(/\D/g,"");
	valor = valor.replace(/(\d{3})(\d)/,"$1.$2");
	valor = valor.replace(/(\d{3})(\d)/,"$1.$2");
    valor = valor.replace(/(\d{3})(\d{1,2})$/,"$1-$2");

	input.val(valor);
}

function mascaraNumCartao(input) {

	var valor = input.val();

	input.attr("maxlength", "19");

	valor = valor.replace(/\D/g,"");
	valor = valor.replace(/(\d{4})(\d)/,"$1 $2");
	valor = valor.replace(/(\d{4})(\d)/,"$1 $2");
	valor = valor.replace(/(\d{4})(\d)/,"$1 $2");

	input.val(valor);
}

function mascaraValidade(input) {

	var valor = input.val();

	input.attr("maxlength", "7");

	valor = valor.replace(/\D/g,"");
	valor = valor.replace(/(\d{2})(\d)/,"$1/$2");

	input.val(valor);
}

