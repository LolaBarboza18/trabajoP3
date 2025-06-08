package tp3_p3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grilla {
	
	private Integer[][] matriz;
	private int n;
	private int m;
	
	public Grilla (Integer [][] grilla) {
		this.n= grilla.length;
		this.m= grilla[0].length;
		this.matriz = grilla ;
	}
	
	public Grilla (int fila, int col) {
		this.matriz = new Integer [fila][col];
		Random rand = new Random();
		for (int f = 0; f < matriz.length; f++) {
			for (int c = 0; c < matriz[0].length; c++) {
				this.matriz[f][c]= rand.nextBoolean() ? 1 : -1 ;
			}
		}
	}
	
	public Integer[][] getMatriz(){
		return matriz.clone();
	}
	
	public int darElemento (int fila, int col) {
		return matriz[fila][col];
	}
	
	
	public void mostrarMatriz() {
		if (matriz == null){
			return;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(matriz[i][j] + " ");
	        }
	        System.out.println();
		}
	}
	
	
	
	public Grilla(String nombreDeArchivo) {
	    try (BufferedReader reader = new BufferedReader(
	            new InputStreamReader(new FileInputStream(nombreDeArchivo), StandardCharsets.UTF_8))) {
	        
	        List<Integer[]> filas = new ArrayList<>();
	        String linea;

	        while ((linea = reader.readLine()) != null) {
	            if (linea.trim().isEmpty()) continue;

	            linea = linea.trim()
                        .replace("{", "")
                        .replace("}", "")
                        .replace(" ", "");

	            if (linea.endsWith(",")) {
	            	linea = linea.substring(0, linea.length() - 1);
	            }
	            
	            String[] valores = linea.split(",");
	            Integer[] fila = new Integer[valores.length];

	            for (int i = 0; i < valores.length; i++) {
	                fila[i] = Integer.parseInt(valores[i].trim());
	            }
	            filas.add(fila);
	        }
	        
	        if (filas.isEmpty()) {
	            throw new IllegalArgumentException("El archivo no contiene datos válidos");
	        }

	        int numeroColumnas = filas.get(0).length;
	        for (int i = 1; i < filas.size(); i++) {
	            if (filas.get(i).length != numeroColumnas) {
	                throw new IllegalArgumentException("Todas las filas deben tener el mismo número de columnas");
	            }
	        }

	        // Cargar dimensiones
	        this.n = filas.size();
	        this.m = filas.get(0).length;

	        // Pasar a matriz
	        this.matriz = new Integer[n][m];
	        for (int i = 0; i < n; i++) {
	            this.matriz[i] = filas.get(i);
	        }
	        
	        


	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    } catch (NumberFormatException e) {
	        System.err.println("Formato incorrecto en el archivo: " + e.getMessage());
	    } 
	}

}
