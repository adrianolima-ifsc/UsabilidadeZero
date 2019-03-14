var idTarefa = $("#id-tarefa").text();
var cliquesTarefa = 0;

//$("#contador").text(cliquesTarefa);

$(document).click(adicionaCliques);

//$(window).onbeforeunload = adicionaCliques;

function adicionaCliques() {

	cliquesTarefa++;

	$('input[name="cliques"]').val(cliquesTarefa);
	$("#contador").text(cliquesTarefa);
	
	$.get(jsRoutes.controllers.ControladorEstudos.adicionaCliquesTarefa(idTarefa))
}

$("#valor").change(function() {

	let valor = $('[name="valor"]').val();

	$('[name="concluidoReal"]').val(valor);
});