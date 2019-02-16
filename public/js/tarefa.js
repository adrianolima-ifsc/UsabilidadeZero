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

$("#concluir-tarefa").click(function() {

	var valor = $("#valor").val();

	var tarefa = {
		id: idTarefa,
		valor: valor
	}

	var tarefaJson = JSON.parse(tarefa);

	console.log(tarefa);

	$.post(jsRoutes.controllers.ControladorEstudos.concluirTarefa(), tarefaJson, function() {
		console.log("Concluir");
	});
})

$("#desistir-tarefa").click(function() {
	console.log("Desistir");
})