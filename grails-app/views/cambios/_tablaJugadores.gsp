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
		<g:each in="${0..min}" var="num">
			<g:if test="${jugadores.size() > num}">
			<g:set var="jugador" value="${jugadores[num]}"/>

			<tr data-codigo="${jugador.codigoAcb}">
								
				<td><a href="http://www.acb.com/stspartidojug.php?cod_jugador=${jugador.codigoAcb}" target="_blank">${jugador.nombre} </a></td>
        	
			    	<td><g:formatNumber number="${jugador.ultimosTres}" format="#.##" /></td>
	
			    	<td><g:formatNumber number="${jugador.valMedia}" format="#.##" /></td>
			
			    	<g:if test="${jugador.equipo}">
		     			<td>${jugador.equipo.nombreCorto}</td>
					</g:if>
			    	<g:else>
	     				<td> </td>
				</g:else>
			    
			    <td>${jugador.precio}</td>
			
			</tr>
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
	</tbody>
</table>
