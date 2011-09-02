<tr data-codigo="${jugador.codigoAcb}">
								
	<td><a href="http://www.acb.com/stspartidojug.php?cod_jugador=${jugador.codigoAcb}" target="_blank">${jugador.nombre} </a></td>
     	
    	<td><g:formatNumber number="${jugador.ultimosTres}" format="#.##" /></td>

    	<td><g:formatNumber number="${jugador.valMedia}" format="#.##" /> - ${jugador.partJugados}</td>

    	<g:if test="${jugador.equipo}">
    			<td>${jugador.equipo.nombreCorto}</td>
		</g:if>
    	<g:else>
   				<td> </td>
	</g:else>
    
    <td>${jugador.precio}</td>

</tr>

