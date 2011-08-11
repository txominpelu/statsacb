$(document).ready(function() {

	$('tr').live('click', function () {
	  $(this).siblings('tr').removeClass('hilite');
	  $(this).toggleClass('hilite');
	  var codigo = $(this).data("codigo")
	  var destino = $(this).parents('div').children('div.player-data-container')
	  $.ajax({
		  url: "http://localhost:9090/statsacb/jugador/index?codigo=" + codigo,
		  cache: false,
		  success: function(html){
		    destino.html(html);
		  }
		});
	});  
	
	$('#boton-sustituir').live('click', function () {
		  var jugFinal = $('#datos-jugador-2 h1').data("codigo")
		  
		  $.ajax({
			  url: "http://localhost:9090/statsacb/cambios/sustituir",
			  data: "jugFinal=" + jugFinal,
			  cache: false,
			  success: function(html){
			    $("#tabla-equipo").html(html);
			  }
			});
	});

});
