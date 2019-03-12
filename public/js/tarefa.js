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

var botaoConcluir = $("#concluir-tarefa");
var botaoDesistir = $("#desistir-tarefa");

botaoConcluir.click(function() {

	let valor = $('[name="valor"]').val();

	let tarefa = {
		id: idTarefa,
		valor: valor
	}

	$.post(jsRoutes.controllers.ControladorEstudos.concluirTarefa(), tarefa, function(data) {

		$('#conteudo').html(data);

		$('#modal-relatorio').modal({
			keyboard: false,
			backdrop: 'static'});
		$('#modal-relatorio').modal('toggle');
	});

	botaoConcluir.attr("disabled", true);
	botaoDesistir.attr("disabled", true);

	console.log(this);
})

$("#desistir-tarefa").click(function() {
	console.log("Desistir");
})