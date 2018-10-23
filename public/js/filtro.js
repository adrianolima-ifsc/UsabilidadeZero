$(document).ready(function(){
	$("#myInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#myDIV *").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

var count = 0;

$(document).click(function(){
	count++;
	$("p").text("Cliques: " + count);
});

