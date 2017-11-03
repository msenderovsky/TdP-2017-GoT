package celdas;

import defensa.Defensa;
import logica.FabricaObjetoGrafico;
import mapa.Mapa;
import objetos.GameObject;
import objetos.GameObjectGrafico;
import objetos.ObjetoMovil;

/**
 * Clase que modela las celdas logicas de un mapa para ser usada por los personajes y elementos.
 * @author Comision 15
 *
 */
public abstract class Celda 
{
	//atributos
    protected Mapa mapa;
    //Posicion de la celda dentro del mapa.
    protected int x;
    protected int y;
    
    //Objeto grafico que se muestra en pantalla
    protected GameObjectGrafico grafica;
    
    //Multiplicador de velocidad para los que se muevan sobre esta celda.
    protected double multiVelocidad;
    
    //Instancia de la fabrica de objetos graficos
    protected FabricaObjetoGrafico fabricaGrafica = FabricaObjetoGrafico.getInstancia();
  
    
    //constructor 
    public Celda(Mapa m, int x, int y)
    {
    	mapa=m;
    	this.x=x;
    	this.y=y;
    }
    
   
    
    //metodos
    public int getX()
    {return x; }
    
    public int getY()
    {return y; }
    
    public double getMultiVelocidad() {
    	return multiVelocidad;
    }
    
    public GameObjectGrafico getGrafico() {
    	return grafica;
    }
    
    public Celda celdaIzquierda() 
    {
    	return mapa.celdaIzquierda(this);
    }
    
    public Celda celdaDerecha()
    {
    	return mapa.celdaDerecha(this);
    }
    
    //Devuelve el objeto movil que está sobre esta celda
    public ObjetoMovil objetoMovil() 
    {
    	return mapa.getObjeto(this);
    }
    
<<<<<<< HEAD
    
    public void moverEnemigo(ObjetoMovil o,int xAnterior, int yAnterior) {
    	mapa.moverEnemigo(o,x, y, xAnterior, yAnterior);
=======
    //Mueve un enemigo de la posicion (xAnterior, yAnterior) a su nueva posicion (x,y).
    public void moverEnemigo(int xAnterior, int yAnterior) {
    	mapa.moverEnemigo(x, y, xAnterior, yAnterior);
>>>>>>> b463d95563a49a116de57a0d094830dd644f7b52
    }
    
    //Elemina un objeto movil del juego
    public void destruirObjetoMovil() 
    {
    	mapa.eliminarObjetoMovil(x, y);
    }
    
    //Genera un nuevo disparo en esta celda
    public void generarDisparo() {
    	mapa.generarDisparo(x, y);
    }

    //Devuelve el objeto estatico sobre esta celda.
	public GameObject getEstatico() {
		return mapa.getEstatico(this);
	}


}
