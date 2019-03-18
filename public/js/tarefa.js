var idTarefa = $("#id-tarefa").text();
var cliquesTarefa = 0;

//Adiciona cliques
$(document).click(function() {

	cliquesTarefa++;

	$('input[name="cliques"]').val(cliquesTarefa);
	$("#contador").text(cliquesTarefa);
	
	$.get(jsRoutes.controllers.ControladorEstudos.adicionaCliquesTarefa(idTarefa))
});

$("#valor").change(function() {

	let valor = $('[name="valor"]').val();

	$('[name="concluidoReal"]').val(valor);
});