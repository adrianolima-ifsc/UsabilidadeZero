var idTarefa = $("#id-tarefa").text();
var cliquesTarefa = 0;

//Adiciona cliques
function adicionaClique() {

	cliquesTarefa++;

	$('input[name="cliques"]').val(cliquesTarefa);
	$("#contador").text(cliquesTarefa);
	
	$.get(jsRoutes.controllers.ControladorEstudos.adicionaCliquesTarefa(idTarefa));
}

$(document).click(function(e) {

	adicionaClique();
});

$('body').backDetect(function(){

	adicionaClique();
});