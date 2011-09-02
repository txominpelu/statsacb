
/**
 * Actualiza en la interfaz el dinero disponible del equipo.
 */
function actualizarDineroDisponible(){
	$.getJSON('equipojson', function(data) {
	
		$("#dinero-disponible").html(data.dineroDisponible + "€");
	
	});
}

/**
 * Muestra un panel con informacion del jugador
 * en el lugar indicado por destino
 * 
 * @param jugador codigo del jugador
 * @param destino donde se pintara la informacion del jugador
 */
function mostrarInfoJugador(jugador, destino){
	destino.load("/statsacb/jugador","codigo=" + jugador);
}

/**
 * Ficha un jugador y actualiza la interfaz tras
 * haber fichado al jugador.
 * 
 * @param jugador codigo del jugador a fichar
 */
function ficharJugador(jugador){
	 $.get("sustituir", "jugFinal=" + jugador,
		  function(html){
		 	$('#tabla-equipo').html(html);
		    actualizarDineroDisponible();
		    actualizarMercado();
	 	  });
	 
}

/**
 * Actualiza la tabla del mercado.
 * 
 * @returns
 */
function actualizarMercado(){
	$('#tabla-mercado').load("mercado");
}

$(document).ready(function() {
	

	$('tr').live('click', function () {
	  $(this).siblings('tr').removeClass('hilite');
	  $(this).toggleClass('hilite');
	  var codigo = $(this).data("codigo")
	  var destino = $(this).parents('div').children('div.player-data-container')
	  mostrarInfoJugador(codigo, destino)
	});  
	
	$('#boton-sustituir').live('click', function () {
		  var jugFinal = $('#datos-jugador-2 h1').data("codigo")
		  ficharJugador(jugFinal)
		 
	});
	
	$('#posicion-select').change( function () {
		$('#tabla-mercado').load("mercado", "posicion=" + $(this).attr('value'));
		  
		 
	});
	
	actualizarMercado();

});
