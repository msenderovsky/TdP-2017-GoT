package premio;

import colisiones.Visitor;
import defensa.Defensa;

public class Curacion extends MagiaTemporal
{

	public Curacion() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void aceptar(Visitor v)
	{
		// NO TIENE VISITOR
		// REESTABLECE LOS PUNTOS DE VIDA DE LOS DEFENSORES
	}

	@Override
	public void accionar(Defensa d) {
		// TODO Auto-generated method stub
		
	}

}
