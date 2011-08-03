<%@ page contentType="text/html; charset=UTF-8" %>
<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//ES"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>

<head>

    <title>Tabla ordenada SuperManager ACB</title>
    <link rel="StyleSheet" href="../css/table-style.css" type="text/css"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="../js/select-elements.js"></script>

<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="description" content="description"/>

<meta name="keywords" content="keywords"/> 
<meta name="author" content="author"/> 
<link rel="stylesheet" type="text/css" href="../css/default.css" media="screen"/>
</head>

<body>


<div class="container">
	
	<div class="main">
		

<div align="center">


        <h1 align="center">Listado de jugadores</h1>
    	<br/>

<div>
	<div class="tablas-alineadas">
    	<table align="center"class="sortable" id="anyid" cellpadding="0" cellspacing="0">

        <thead>
		<tr>
			<th>Nombre</th>
        		
			<th>Ult. 3</th>
			
			<th>Val Media</th>
			
			<th>Val Jornada</th>
			
			<th>Equipo</th>
			
		</tr>
        </thead>
        
		<tr>
			<td><a href="http://www.acb.com/stspartidojug.php?cod_jugador=B9H">Vujanic, Milos</a></td>
        	
		    <td>21.33</td>
		
		    <td>14.08</td>
		
		    <td>32.0</td>
		
		    <td>MUR</td>
		
		</tr>

        
		
	</table>

</div>   

    <div class="datos-jugador">
	<h1>Juan Tal Cual</h1>
	<ul>
		<li>Posición: Alero</li>
		<li>Coste: 350.000</li>
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
		<tr>
		   	<td>TAU</td>
        	
		    	<td>21.33</td>

		    	<td>22'</td>

			<td>+10.000</td>
		</tr>
		<tr>
		   	<td>TAU</td>
        	
		    	<td>21.33</td>

		    	<td>22'</td>

			<td>+10.000</td>
		</tr>
		<tr>
		   	<td>TAU</td>
        	
		    	<td>21.33</td>

		    	<td>22'</td>

			<td>+10.000</td>
		</tr>
	</tbody>
	</table>

	<h2>Proximo partido</h2>
	<ul>
		<li>Rival: FCB(1º) +6</li>
		<li>Var. precio estimada: +35.000</li>
		<li>Aleros contra rival: 15.5</li>
		<li>Partidos previos contra rival: 16.5</li>
	</ul>
    	<br>
	
    </div>
</div>

	<div id="mercado" class="tablas-alineadas">
	
	<table >

	<thead>
		<tr>
			<th>Nombre</th>
        		
			<th>Ult. 3</th>
			
			<th>Val Media</th>
			
			<th>Val Jornada</th>

			<th>Equipo</th>
			
		</tr>
        </thead>
        
        <g:each in="${jugadores}" var="jugador">
        
        	<tr data-codigo="${jugador.codigo}">
				
				<td><a href="http://www.acb.com/stspartidojug.php?cod_jugador=BCE">${jugador.nombre} </a></td>
        	
			    <td>${jugador.ultimosTres}</td>
	
			
			    <td>${jugador.valMedia}</td>
			
			    <td>${jugador.valJornada}</td>
			
			    <td>${jugador.equipo}</td>
				
			</tr>
		</g:each>
		
			
		

        
		
        
    </table>   
    </div>  
    <div class="datos-jugador" id="datos-jugador-panel2">
	<h1>Juan Tal Cual</h1>
	<ul>
		<li>Posición: Alero</li>
		<li>Coste: 350.000</li>
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
		<tr>
		   	<td>TAU</td>
        	
		    	<td>21.33</td>

		    	<td>22'</td>

			<td>+10.000</td>
		</tr>
		<tr>
		   	<td>TAU</td>
        	
		    	<td>21.33</td>

		    	<td>22'</td>

			<td>+10.000</td>
		</tr>
		<tr>
		   	<td>TAU</td>
        	
		    	<td>21.33</td>

		    	<td>22'</td>

			<td>+10.000</td>
		</tr>
	</tbody>
	</table>

	<h2>Proximo partido</h2>
	<ul>
		<li>Rival: FCB(1º) +6</li>
		<li>Var. precio estimada: +35.000</li>
		<li>Aleros contra rival: 15.5</li>
		<li>Partidos previos contra rival: 16.5</li>
	</ul>
    	<br>
	
    </div>

</div>
</div>

	
	
		

	</div>

	<div class="footer">&copy; 2006 <a href="index.html">Website.com</a>. Valid <a href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a> &amp; <a href="http://validator.w3.org/check?uri=referer">XHTML</a>. Template design by <a href="http://templates.arcsin.se">Arcsin</a>
	</div>

</div>

</body>

</html>
