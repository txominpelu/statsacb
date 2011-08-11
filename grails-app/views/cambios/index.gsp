<html>
	<head>	
	</head>

	<body>

		<div class="main-panel">
			<header><p>Tu equipo</p></header>
			<div class="grupo-tabla">

				<div id="tabla-equipo">

					<g:render template="tablaJugadores" model="[jugadores : session.equipoSM.jugadores, min:11]" />
				</div>

				<div class="player-data-container" id="datos-jugador-1">
					 
				</div>

			</div>
		</div>
		<div id="panel-mercado">
			<header><p>Mercado</p></header>
		</div>
		<div class="main-panel">
			<div class="grupo-tabla">
		
				<div id="tabla-mercado">
					<g:render template="tablaJugadores" model="[jugadores : jugadoresMercado, min: jugadoresMercado.size()]" />
				</div>
			
				<div class="player-data-container" id="datos-jugador-2">
					
				</div>

			
			</div>
		</div>

	</body>

</html>
