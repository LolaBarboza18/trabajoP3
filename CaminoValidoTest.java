package tp3_p3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class CaminoValidoTest {

    private Grilla grillaConCamino;
    private CaminoValido camino;
    
	 @Before 
	    public void inicializarGrilla() {
	        Integer [][] grilla = {
	            {1, -1, -1, -1},
	            {-1, 1, 1, 1},
	            {1, 1, 1, 1}
	        };
	        grillaConCamino = new Grilla(grilla);
	        camino = new CaminoValido(grillaConCamino);
	    }
	 
	 
	// Verifica que al construir el objeto no haya caminos encontrados (porque no se ejecuta encontrarCaminos())
		 @Test
		public void ConstructorInicializaCorrectamenteTest() {
	        assertEquals(0, camino.getCaminosEncontrados());
		}

		
		 // Al buscar caminos sin poda se encuentren resultados y recursividad y tiempo de ejecucion
	    @Test
	    public void CaminosSinPodaTest() {
	        camino.encontrarCaminosSinPoda();
	        assertTrue(camino.getCaminosEncontrados() > 0);
	        assertTrue(camino.getLlamadasRecursivas() > 0);
	        assertTrue(camino.getTiempoTotal() > 0);
	    }
	    
	    @Test
	    public void CaminosConPodaTest() {
	        camino.encontrarCaminosConPoda();
	        assertTrue(camino.getCaminosEncontrados() > 0);
	        assertTrue(camino.getTiempoTotalPoda() > 0);
	    }
	    
	    
	    // Se pueda obtener una solución válida (no vacía) si hay caminos
	    @Test
	    public void SolucionCaminoTest() {
	        camino.encontrarCaminosSinPoda();
	        ArrayList<int[]> solucion = camino.getSolucion(0);
	        assertNotNull(solucion);
	        assertFalse(solucion.isEmpty());  
	    }
	    
	}


