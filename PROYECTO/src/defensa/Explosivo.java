package defensa;

import java.util.ArrayList;
import java.util.List;

import colisiones.*;
import objetos.ObjetoMovil;

public abstract class Explosivo extends Defensa{

	
	public void recibirAtaque(int i) {
		
		destruir();
	
	}
	
	public void destruir() {
		
		List<ObjetoMovil> l=new ArrayList<ObjetoMovil>();
		l=celda.adyacentes();
		VisitorExplosivo v=new VisitorExplosivo(this);
		for (ObjetoMovil o:l)
			o.aceptar(v);	
		celda.destruirObjetoMovil();
	}

	public void aceptar(Visitor v){
		
		v.visitarExplosivo(this);
    }
}
