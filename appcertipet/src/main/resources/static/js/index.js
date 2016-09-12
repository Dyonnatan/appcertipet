$(".cpf").focus(function(){
  $(".cpf-help").slideDown(500);
}).blur(function(){
  $(".cpf-help").slideUp(500);
});
$(document).ready(function(){
	$(".rodape").mouseover(function(){
        $(this).css("top", "73%");
    });
	$(".rodape").mouseout(function(){
        $(this).css("top", "90%");
    });
 });