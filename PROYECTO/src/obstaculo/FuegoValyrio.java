package obstaculo;


public class FuegoValyrio extends Obstaculo{
	
	public FuegoValyrio(){
		
		velocidadAtaque = 0;
		proximoAtaque = 0;
		puntosVida = 120;
		fuerzaImpacto = 0;
		grafico = fabricaGrafica.construirGraficoFuegoValyrio();
	}

}
