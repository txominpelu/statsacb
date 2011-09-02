<html>
	<head>	
	</head>

	<body>

		<div class="main-panel">
			<header><p>Tu equipo: <span id="dinero-disponible">${ session.equipoSM.dineroDisponible }€</span></p></header>
			<div class="grupo-tabla">

				<div id="tabla-equipo">

					<g:render template="tablaJugadores" model="[jugadores : session.equipoSM.jugadores, equipo: true]" />
				</div>

				<div class="player-data-container" id="datos-jugador-1">
					 
				</div>

			</div>
		</div>
		<div id="panel-mercado">
			<header><p>Mercado: 
				<select id="posicion-select" style="display:block">
					<option value="BASE">Base</option>
					<option value="ALERO">Alero</option>
					<option value="PIVOT">Pivot</option>
				</select>
				</p>
			</header>
		</div>
		<div class="main-panel">
			<div class="grupo-tabla">
		
				<div id="tabla-mercado">
					
				</div>
			
				<div class="player-data-container" id="datos-jugador-2">
					
				</div>

			
			</div>
		</div>

	</body>

</html>
