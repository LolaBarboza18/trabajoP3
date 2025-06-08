package tp3_p3;

import java.util.ArrayList;


public class GrillaSolucion {
	
	private CaminoValido camino;
	private boolean [][] solucion;
	private int nroSolucion = 1;
	
	
	public GrillaSolucion (CaminoValido c, Grilla g) {
		this.camino = c;
		this.solucion= new boolean [g.getMatriz().length][g.getMatriz()[0].length];
	}
	
	public void mostrarSolucion() {
		 ArrayList<int[]> estaciones = camino.getSolucion(nroSolucion);		 
		 for (int[] est : estaciones) {
			 solucion[est[0]][est[1]]= true;
			
		}
	}
	


}
