
function testarCampoVazio(input) {

	var valor = input.val();
	
	if(valor) {
	
		input.removeClass("invalido").addClass("valido");
	
	} else {

		input.removeClass("valido").addClass("invalido");
	}
}
