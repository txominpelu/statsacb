<%@page import="statsacb.Partido"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>

    <title>Jugador SuperManager ACB</title>
    <script src="javascript/jquery-1.6.2.min.js" type="text/javascript"></script>
    <script src="javascript/select-elements.js" type="text/javascript"></script>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" >

	<meta content="description" name="description">
	
	<meta content="keywords" name="keywords"> 
	<meta content="author" name="author"> 

</head>

<body>

    <h1>${valPartidos[0].jugador.nombre}</h1>
    
	<ul>
		<li>Posicion: ${valPartidos[0].jugador.posicion}</li>
		<li>Coste: ${valPartidos[0].jugador.precio} </li>
		<li>Val. Media: 14,5</li>
	</ul>
	
	<h2>Últimos tres partidos</h2>
	<table class="sortable" id="anyid">

        <thead>
		<tr>
			<th>Rival</th>
        		
			<th>Valoracion</th>
			
			<th>Minutos</th>
			
			<th>Var. Precio</th>
			
		</tr>
        </thead>
        <tbody>
        
        <g:set var="counter" value="${0}" />
        
        <g:each in="${valPartidos}" var="partido">
        	<tr>
		   		<td>${partido.rival}</td>
		   		
		    	<td>${partido.totalValoracion}</td>
		    	
		   		<td>${partido.minutosJugados}</td>
        	
				<td>+10.000</td>
				
			</tr>
			<g:set var="counter" value="${counter + 1}" />
		</g:each>
		
	</tbody>
	</table>

	<h2>Proximo partido</h2>
	<ul>
		<li>Rival: FCB(1º) +6</li>
		<li>Var. precio estimada: +35.000</li>
		<li>Aleros contra rival: 15.5</li>
		<li>Partidos previos contra rival: 16.5</li>
	</ul>


</body>
</html>
