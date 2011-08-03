$(document).ready(function() {

	$('tr').click(function () {
	  $(this).siblings('tr').removeClass('hilite');
	  $(this).toggleClass('hilite');
	  var codigo = $(this).data("codigo")
	  $.ajax({
		  url: "http://localhost:9090/statsacb/jugador/index?codigo=" + codigo,
		  cache: false,
		  success: function(html){
		    $("#datos-jugador-panel2").html(html);
		  }
		});
	});  

});
