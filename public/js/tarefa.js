var idTarefa = $("#id-tarefa").text();
var idEvento = $('[name="id-evento"]').val();

// Tarefa 1
var tarefa = $('[name="codigo"]').val();

$("#valor").change(function() {
	
	if (tarefa === "EC01" || tarefa === "EC11") {

		if(idEvento == 1) {

			$.get(jsRoutes.controllers.ControladorEstudos.setConcluidoReal(idTarefa));
		}
	}
});

//Tarefa 2
$("#inscricao").on("click", function() {

	if (tarefa === "EC02" || tarefa === "EC12") {

		console.log(tarefa);
	}
});
