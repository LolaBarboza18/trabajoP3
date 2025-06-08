package tp3_p3;

import java.util.ArrayList;
import java.util.List;

public class CaminoValido {
	
	private Grilla grilla;
	private int caminosEncontrados;
	private int llamadasRecursivas;
	private int llamadasRecursivasPoda;
	private List<List<int[]>> caminosValidos;
	private int n;
	private int m;
	private long tiempoTotal;
	private long tiempoTotalPoda;
	private ArrayList <Observer> observers;

	
	public  CaminoValido (Grilla g){
		this.caminosEncontrados = 0;
		this.llamadasRecursivas = 0;
		this.llamadasRecursivasPoda = 0;
		this.caminosValidos = new ArrayList<>();
		this.grilla= g; 
		this.n= g.getMatriz().length;
		this.m= g.getMatriz()[0].length;
		observers = new ArrayList <Observer>();
		
		
	}
		
	public void agregarObserver(Observer obs)
	{
		this.observers.add(obs);
	}
	public void encontrarCaminosSinPoda() {
		long inicio = System.nanoTime();
		llamadasRecursivas = 0;
		caminosEncontrados = 0;
		caminosValidos.clear();
		
		buscarSinPoda(0, 0, 0, new ArrayList<>());
		tiempoTotal= calculoTiempo(inicio);
		System.out.println("Encontrar caminos sin poda: Resultados");
		verificacionDeBusqueda();	
		System.out.println("El tiempo  total fue de " + tiempoTotal + " nanosegundos");
		avisarObserver();
	} 
	
	private void buscarSinPoda(int i, int j, int suma, List<int[]> camino) {
	    llamadasRecursivas++;
	    suma += grilla.darElemento(i,j);
	    camino.add(new int[] {i, j});

	    if (i == n - 1 && j == m - 1) {
	        if (suma == 0) {
	            caminosEncontrados++;
	            caminosValidos.add(new ArrayList<>(camino));
	        }
	        return;
	    }

	    if (i + 1 < n) {
	        buscarSinPoda(i + 1, j, suma, new ArrayList<>(camino));
	    }
	    if (j + 1 < m) {
	        buscarSinPoda(i, j + 1, suma, new ArrayList<>(camino));
	    }

	}
	
	public void encontrarCaminosConPoda() {
		long inicio = System.nanoTime();
		llamadasRecursivas = 0;
		caminosEncontrados = 0;
		caminosValidos.clear();
		
		buscarConPoda(0, 0, 0, 0, new ArrayList<>());
		tiempoTotalPoda= calculoTiempo(inicio);
		System.out.println("Encontrar caminos con poda: Resultados");
		
		verificacionDeBusqueda();	
		System.out.println("El tiempo  total fue de " + tiempoTotalPoda + " nanosegundos");
		avisarObserver();
	}

	private void verificacionDeBusqueda() {
		if (caminosEncontrados == 0) {
			throw new IllegalArgumentException("No se ha podido encontrar un camino.");
        }

		System.out.println("Llamadas recursivas: " + llamadasRecursivas);
		System.out.println("Llamadas recursivas con poda: " + llamadasRecursivasPoda);
		System.out.println("Caminos válidos encontrados: " + caminosEncontrados);
		
	}

	private long calculoTiempo(long inicio) {
		long fin = System.nanoTime();
		long Total = (fin - inicio);
		return Total;
	}
	
	private void buscarConPoda(int i, int j, Integer suma, int pasos, List<int[]> camino) {
		llamadasRecursivasPoda++;
		suma+=grilla.darElemento(i,j);
		camino.add(new int[] {i, j});
		pasos++;
		int pasosRestantes = (n + m - 1) - pasos;

		// Poda 1: la suma se fue de rango
		if (Math.abs(suma) > pasosRestantes) return;

		// Poda 2: paridad incompatible
		if ((pasosRestantes + suma) % 2 != 0) return;

		if (i == n - 1 && j == m - 1) {

			if (suma == 0) {
				caminosEncontrados++;
				caminosValidos.add(new ArrayList<>(camino));
			}
			return;
		}
		if (i + 1 < n) {
			buscarConPoda(i + 1, j, suma, pasos, new ArrayList<>(camino));
		}
		if(j + 1 < m) {
			buscarConPoda(i, j + 1, suma, pasos, new ArrayList<>(camino));
		}
	}
	
	public void mostrarCaminosGuardados() {
	    int numeroCamino = 1;
	    for (List<int[]> camino : caminosValidos) {
	        System.out.print("Camino " + numeroCamino + ": ");
	        for (int[] paso : camino) {
	            System.out.print("(" + paso[0] + "," + paso[1] + ") -> ");
	        }
	        System.out.println("FIN");
	        numeroCamino++;
	    }
	}

	public ArrayList<int[]> getSolucion (int i){
		ArrayList<int[]> soluciones = new ArrayList<>(caminosValidos.get(i));

		return soluciones;
	} 
	
	

	public void avisarObserver() {
		for (Observer obs : observers) {
			obs.actualizar(this);
		}
	}
	
	public int getCaminosEncontrados() {
		return this.caminosEncontrados;
	}
	
	public int getLlamadasRecursivas() {
		return this.llamadasRecursivas;
	}
	
	public long getTiempoTotal() {
		return this.tiempoTotal;
	}
	public long getTiempoTotalPoda() {
		return this.tiempoTotalPoda;
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {	
//	Integer[][] grilla = {
//            {1, -1, 1, 1},
//            {-1, 1, -1, 1},
//            {1, -1, -1, -1}
//        };
//	
//	Grilla g = new Grilla (grilla);
	
		Grilla g = new Grilla (3,4);
	CaminoValido c = new CaminoValido(g);
	c.encontrarCaminosSinPoda();
	c.mostrarCaminosGuardados();
	}
}
