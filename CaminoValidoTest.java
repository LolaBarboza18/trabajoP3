package logica;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class CaminoValidoTest {

    private Grilla grillaConCamino;
    private Grilla grillaSinCamino;
    private CaminoValido camino;
    private CaminoValido sinCamino;
    
	 @Before 
	    public void inicializarGrilla() {
	        Integer [][] grilla = {
	            {1, -1, -1, -1},
	            {-1, 1, 1, 1},
	            {1, 1, 1, 1}
	        };
	        grillaConCamino = new Grilla(grilla);
	        camino = new CaminoValido(grillaConCamino);
	        
	    	Integer [][] grilla2 = {
		            {1, -1, -1, -1},
		            {-1, 1,- 1, -1},
		            {1, -1,- 1, -1}
		        };
		        grillaSinCamino = new Grilla(grilla2);
		        sinCamino = new CaminoValido(grillaSinCamino);
	    }
	 
	 
		 @Test
		public void ConstructorInicializaCorrectamenteTest() {
	        assertEquals(0, camino.getCaminosEncontrados());
		}

	
	    @Test
	    public void ExisteCaminosSinPodaTest() {
	        camino.encontrarCaminosSinPoda();
	        assertTrue(camino.getCaminosEncontrados() > 0);
	    }
	    
	    @Test
	    public void ExisteCaminosConPodaTest() {
	        camino.encontrarCaminosConPoda();
	        assertTrue(camino.getCaminosEncontrados() > 0);

	    }
	    
	    @Test (expected = IllegalArgumentException.class)
	    
	    public void NoExiteCaminoSinPoda() {	
	        sinCamino.encontrarCaminosSinPoda();
	    }
	    
	    @Test (expected = IllegalArgumentException.class)
	    
	    public void NoExiteCaminoConPoda() {	
	        sinCamino.encontrarCaminosConPoda();
	    }
	    

	    
	}


