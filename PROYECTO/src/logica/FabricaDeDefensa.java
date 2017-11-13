package logica;

import defensa.*;

public class FabricaDeDefensa 
{
	//Instancia de la fabrica para el singleton
	private static FabricaDeDefensa instancia = null;
	
	//Ultimo producto creado
	private Defensa defensaCreada = null;
	
	private FabricaDeDefensa() 
	{
		
	}
	
	public static FabricaDeDefensa getInstancia() {
		if(instancia == null) 
			instancia = new FabricaDeDefensa();
		return instancia;
	}
	
	//PERSONAJES
	public void construirJorgito() {
		
		defensaCreada = new Jorgito();
	}
	
	
	public void construirYgritte()
	{
		defensaCreada = new Ygritte();
	}
	
	public void construirDothraki()
	{
		defensaCreada = new Dothraki();
	}
	
	public void construirDragon()
	{
		defensaCreada = new Dragon();
	}
	
	public void construirInmaculado()
	{
		defensaCreada = new Inmaculado();
	}
	
	public void construirJonSnow()
	{
		defensaCreada = new JonSnow();
	}
	
	public Defensa getDefensa() 
	{
		return defensaCreada;
	}
	
	public void reset() {
		defensaCreada = null;
	}
	
	// OBSTACULOS
	public void construirBomba() {

		defensaCreada=new Bomba();	
	}
	
	public void construirMina() {

		defensaCreada=new Mina();
	}

}
