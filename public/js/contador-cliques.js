var idTarefa = $('input[name="id"]').val();
var cliquesTarefa = $('input[name="cliques"]').val();

$("#contador").text(cliquesTarefa);

$(document).click(function(){

	cliquesTarefa++;

	$('input[name="cliques"]').val(cliquesTarefa);
	$("#contador").text(cliquesTarefa);
	
	$.get(jsRoutes.controllers.ControladorEstudos.atualizaCliquesTarefa(idTarefa, cliquesTarefa))
});