package defensa;


public class Mina extends Explosivo{

	public Mina() {	
		
		velocidadAtaque = 0;
		proximoAtaque = 0;
		fuerzaImpacto=90;
		grafico = fabricaGrafica.construirGraficoMina();
		puntosVida=1;
	}
	
}
