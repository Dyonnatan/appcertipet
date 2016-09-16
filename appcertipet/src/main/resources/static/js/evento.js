$(function() {
	$('[rel="tooltip"]').tooltip();

	$('.js-atualizar-status').on(
			'click',
			function(event) {
				event.preventDefault();

				var botaoReceber = $(event.currentTarget);
				var urlReceber = botaoReceber.attr('href');

				var response = $.ajax({
					url : urlReceber,
					type : 'PUT'
				});

				response.done(function(e) {
					var codigo = botaoReceber.data('codigo');
					$('[data-role=' + codigo + ']').html(
							'<span class="label label-success">' + e
									+ '</span>');
					botaoReceber.hide();
				});

				response.fail(function(e) {
					console.log(e);
					alert('Erro marcando presença');
				});

			});
});

function checkcpf() {
	var cpf = $("#cpf").val();
	var cpf1 = $("#cpfrepetido").val();
	var submit = $("#cadastro");
	var aviso = $(".aviso");

	if (cpf == cpf1) {
		$("#check").attr("class", "glyphicon glyphicon-ok");
		submit.attr("type", "submit");
		aviso.attr("hidden", "hidden");
	} else {
		$("#checkem").attr("class", "");
		$("#check").attr("class", "");
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
		$("#checkem").attr("class", "glyphicon glyphicon-ok");
		submit.attr("type", "submit");
		aviso.attr("hidden", "hidden");
	} else {
		$("#checkem").attr("class", "");
		$("#check").attr("class", "");
		submit.attr("type", "button");
		aviso.attr("hidden", "false");
	}
}
