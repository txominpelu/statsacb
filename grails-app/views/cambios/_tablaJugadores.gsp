<%@page import="statsacb.Posicion"%>
<table class="tabla-jugadores">
	<thead>
		<tr>
			<th>Nombre</th>
			<th>Ultimos 3</th>
			<th>Val. Media</th>
			<th>Equipo</th>
			<th>Precio</th>
		</tr>
	</thead>
	
	<tbody>
		<g:if test="${equipo}">
		
			<g:each in="${Posicion.values()}" var="posicion">
	
				<g:render template="filasPosicion" model="[posicion : posicion]" />
				
			</g:each>
		</g:if>
		<g:else>
			<g:each in="${jugadores}" var="jugador">
	
				<g:render template="filaJugador" model="[jugador : jugador ]" />
	
			</g:each>
		</g:else>
	</tbody>
</table>
