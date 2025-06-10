package logica;

import java.util.ArrayList;


public class GrillaSolucion {
	
	private CaminoValido camino;
	private boolean [][] solucion;
	private int nroSolucion = 0;
	
	
	public GrillaSolucion (CaminoValido c, Grilla g) {
		this.camino = c;
		this.solucion= new boolean [g.getMatriz().length][g.getMatriz()[0].length];
		crearSolucion();
	}
	
	public void crearSolucion() {
		
		 ArrayList<int[]> estaciones = camino.getSolucion(nroSolucion);	
		 if (estaciones == null) return;
		 for (int[] est : estaciones) {
			 solucion[est[0]][est[1]]= true;			
		}
	}
	
	public boolean esPaso(int f, int c) {
		return solucion [f][c];
	}


}
