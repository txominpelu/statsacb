<%@page import="statsacb.Posicion"%>

<g:set var="jugadores" value="${session.equipoSM.getJugadores(posicion)}"/>

<th colspan="5">${ posicion }S</th>

<g:each in="${0..(posicion.getMaxJugadores()-1)}" var="num">
		<g:if test="${jugadores.size() > num}">
			
			<g:render template="filaJugador" model="[jugador : jugadores[num] ]" />
			
		</g:if>
		<g:else>
	     	<tr>
				<td>&ltSin Jugador&gt</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>	
		</g:else>
</g:each>

