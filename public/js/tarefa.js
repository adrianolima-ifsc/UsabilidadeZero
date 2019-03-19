// Tarefa 1
$("#valor").change(function() {

	let tarefa = $('[name="codigo"]').val();
	
	if (tarefa === "EC01" || tarefa === "EC11") {

		let idEvento = $('[name="id-evento"]').val();
		if(idEvento == 1) {
		console.log(tarefa);

			let valor = $('[name="valor"]').val();

			$('[name="concluidoReal"]').val(valor);
		}
	}
});