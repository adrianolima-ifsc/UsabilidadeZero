var idTarefa = $("#id-tarefa").text();
var idEvento = $('[name="id-evento"]').val();

$(function(){
	$("form").validate();
})
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
$('[type="submit"]').on("click", function(e) {

	if (tarefa === "EC02" || tarefa === "EC12") {

		$("form").validate({
			rules : {
				nome:{
					minlength:3
				}
			},
       		messages:{
            	nome:{
                    minlength:"O nome deve ter pelo menos 3 caracteres"
            	}
            }
		})
	e.preventDefault();
	}

});
