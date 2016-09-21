
function checkcpf() {
	var cpf = $("#cpf").val();
	var cpf1 = $("#cpfrepetido").val();
	var submit = $("#cadastro");
	var aviso = $(".aviso");

	if (cpf == cpf1) {
		$("#check").attr("class", "glyphicon glyphicon-ok green");
		match2 = true;
		checkaviso();
	} else {
		$("#check").attr("class", "glyphicon glyphicon-remove red");
		submit.attr("type", "button");
		match2 = false;
		checkaviso();
	}
}

function checkem() {
	var em = $("#email").val();
	var em1 = $("#emailrepetir").val();
	var submit = $("#cadastro");
	var aviso = $(".aviso");

	if (em == em1) {
		$("#checkem").attr("class", "glyphicon glyphicon-ok green");
		match1 = true;
		checkaviso();
	} else {
		$("#checkem").attr("class", "glyphicon glyphicon-remove red");
		submit.attr("type", "button");
		match1 = false;
		checkaviso();
	}
}

var match1 = false;
var match2 = false;
function checkaviso() {
	aviso = $("#helpBlock");
	submit = $(".cadastro");

	if (match1 == true && match2 == true) {
		submit.attr("type", "submit");
		aviso.attr("hidden", "hidden");
	} else {
		submit.attr("type", "button");
		aviso.removeAttr("hidden");
	}
}
