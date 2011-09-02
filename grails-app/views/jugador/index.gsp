<%@page import="statsacb.Partido"%>
<%@page import="statsacb.ValoracionPartido"%>
<%@ page contentType="text/html; charset=UTF-8" %>

    <h1 data-codigo="${valPartidos[0].jugador.codigoAcb}">${valPartidos[0].jugador.nombre} - ${valPartidos[0].jugador.posicion}</h1>
    
	<ul>
		<li>Coste: ${valPartidos[0].jugador.precio} </li>
		<li>Val. Media: <g:formatNumber number="${jugador.valMedia }" format="#.##" /></li>
	</ul>
	
	<h2>Últimos tres partidos</h2>
        
    <g:set var="counter" value="${0}" />
    <ul>
    	<g:each in="${valPartidos}" var="partido">
      
			<li>vs ${partido.getEquipoRival().nombreCorto}, <g:formatNumber number="${partido.getValoracionSM()}" format="#.##" /> Valoracion, ${partido.minutosJugados}'.</li>

		</g:each>
	</ul>

	<h2>Proximo partido</h2>
	<table>
			<tr>
				<td>Rival: FCB(1º) +6</td>
				<td>Var. precio: <g:formatNumber number="${jugador.varPrecio }" format="#.#" /></td>
			</tr>
			<tr>
				<td>Aleros vs rival: 15.5</td>
				<td>Previos vs rival: 16.5</td>
			</tr>
	</table>
	
	<p id="boton-sustituir"> Sustituir </p>

