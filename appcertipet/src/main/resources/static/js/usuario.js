

function checkcpf() {
	var cpf = $("#cpf").val();
	var cpf1 = $("#cpfrepetido").val();
	var submit = $("#cadastro");
	var aviso = $(".aviso");

	if (cpf == cpf1) {
		$("#check").attr("class", "glyphicon glyphicon-ok green");
		submit.attr("type", "submit");
		aviso.attr("hidden", "hidden");
	} else {
		$("#check").attr("class", "glyphicon glyphicon-remove red");
		submit.attr("type", "button");
		aviso.attr("hidden", "false");
	}
}

function checkem() {
	var em = $("#email").val();
	var em1 = $("#emailrepetir").val();
	var submit = $("#cadastro");
	var aviso = $(".aviso");

	if (em == em1) {
		$("#checkem").attr("class", "glyphicon glyphicon-ok green");
		submit.attr("type", "submit");
		aviso.attr("hidden", "hidden");
	} else {
		$("#checkem").attr("class", "glyphicon glyphicon-remove red");
		submit.attr("type", "button");
		aviso.attr("hidden", "false");
	}
}
