package defensa;

public class Ygritte extends DefensaRango {
	
	public Ygritte() {
		velocidadAtaque = 5;
		proximoAtaque = 0;
		puntosVida = puntosVidaInicio = 250;
		fuerzaImpacto = fuerzaImpactoInicio = 30;
		grafico = fabricaGrafica.construirGraficoYgritte();
		pathDisparo="flecha.png";
	}
}