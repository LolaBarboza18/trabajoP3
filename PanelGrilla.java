package interfaz;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import logica.CaminoValido;
import logica.Grilla;
import logica.GrillaSolucion;
import logica.Observer;

public class PanelGrilla extends JPanel implements Observer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CaminoValido camino;
	private Integer[][] matrizGrilla;
	private int filas;
	private int columnas;

	private Graphics2D g2d;

	private int tamanoCelda = 40;
	private GrillaSolucion solucion;
	
	private boolean mostrarCaminoActivo = true;

	/**
	 * Create the application.
	 */
	
	public PanelGrilla() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
				"Grilla de cargas eléctricas", 
				TitledBorder.CENTER, 
				TitledBorder.TOP));

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 400));

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				calculoTamanoCelda();
				repaint();
			}
		});
	}

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        
	        if (matrizGrilla == null) {
	        	Graphics2D g2d = (Graphics2D) g.create();
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2d.setColor(Color.GRAY);
	            g2d.setFont(new Font("Arial", Font.ITALIC, 16));
	            String mensaje = "Cargue una grilla o genere una aleatoria";
	            FontMetrics fm = g2d.getFontMetrics();
	            int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
	            int y = getHeight() / 2;
	            g2d.drawString(mensaje, x, y);
	            g2d.dispose();
	            return;
	        }
	        
	        g2d = (Graphics2D) g.create();
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        int comienzoX = (getWidth() - columnas * tamanoCelda) / 2;
	        int comienzoY = (getHeight() - filas * tamanoCelda) / 2 + 10;
	        

	        dibujarGrilla(g2d, comienzoX, comienzoY);
	        
	        dibujarPosicionesComienzoFin(g2d, comienzoX, comienzoY);
	        dibujarSolucion();
	        
	        g2d.dispose();
	    }
	    
	    private void dibujarGrilla(Graphics2D g2d, int stX, int stY) {
	    	
	    	g2d.setStroke(new BasicStroke(1.0f));
	    	
	        for (int i = 0; i < filas; i++) {
	            for (int j = 0; j < columnas; j++) {
	                int x = stX + j * tamanoCelda;
	                int y = stY + i * tamanoCelda;
	                
	                pintarCelda(g2d, i, j, x, y, Color.WHITE);
	            }
	        }
	    }

		private void pintarCelda(Graphics2D g2d, int i, int j, int x, int y, Color color) {
			g2d.setStroke(new BasicStroke(1.0f));
			
			g2d.setColor(color);
			g2d.fillRect(x, y, tamanoCelda, tamanoCelda);
			
			g2d.setColor(Color.GRAY);
			g2d.drawRect(x, y, tamanoCelda, tamanoCelda);
			
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.BOLD, tamanoCelda / 3));
			if (matrizGrilla[i][j] != null) {
			    g2d.setColor(Color.BLACK);
			    g2d.setFont(new Font("Arial", Font.BOLD, tamanoCelda / 3));
			    String valor = (matrizGrilla[i][j] == 1) ? "+1" : "-1";
			    
			    FontMetrics fm = g2d.getFontMetrics();
			    int textX = x + (tamanoCelda - fm.stringWidth(valor)) / 2;
			    int textY = y + (tamanoCelda + fm.getAscent()) / 2;
			    
			    g2d.drawString(valor, textX, textY);
			}
		}
	    
	    private void dibujarPosicionesComienzoFin(Graphics2D g2d, int stX, int stY) {
	        g2d.setStroke(new BasicStroke(3));
	        
	        int finX = stX + (columnas) * tamanoCelda;
	        int finY = stY + (filas) * tamanoCelda;
	        
	        g2d.setColor(Color.BLACK);
	        g2d.setFont(new Font("Arial", Font.BOLD, 10));
	        g2d.drawString("INICIO", stX + 5, stY - 5);
	        g2d.drawString("FIN", finX + 5, finY - 5);
	    }
	    


	    public void actualizarGrilla(Grilla grilla, CaminoValido cv) {
	    	if (grilla != null) {
	    		this.camino = cv;
	    		this.solucion = null;

	            this.matrizGrilla = grilla.getMatriz();
	            if (this.matrizGrilla != null) {
	                this.filas = matrizGrilla.length;
	                this.columnas = matrizGrilla[0].length;
	               
	                calculoTamanoCelda();
	                invalidate();
	                revalidate();
	                repaint();
	            }
	        }
	    }
	    
	    private void calculoTamanoCelda() {
	        if (filas > 0 && columnas > 0) {
	            int anchura = getWidth() - 40; 
	            int altura = getHeight() - 60; 
	            
	            int celdaAnchuraMax = anchura / columnas;
	            int celdaAlturaMax = altura / filas;
	            
	            tamanoCelda = Math.min(celdaAnchuraMax, celdaAlturaMax);
	            tamanoCelda = Math.max(tamanoCelda, 20); 
	            tamanoCelda = Math.min(tamanoCelda, 80); 
	        }
	    }
	    
	    
	    public void actualizar() {
	    	if(camino != null) {
	    		solucion = camino.darSolucion();
		    	repaint();
	    	}
	    }

		private void dibujarSolucion() {
			
			if (solucion == null || !mostrarCaminoActivo) {
				return;
			}else{
				
					int comienzoX = (getWidth() - columnas * tamanoCelda) / 2;
				
		        int comienzoY = (getHeight() - filas * tamanoCelda) / 2 + 10;
		        for (int i = 0; i < filas; i++) {
		            for (int j = 0; j < columnas; j++) {
		                int x = comienzoX + j * tamanoCelda;
		                int y = comienzoY + i * tamanoCelda;
		                
		                if (solucion.esPaso(i,j)) {
		                
		                pintarCelda(g2d, i, j, x, y, Color.ORANGE);
		            }
		            else{
		            	pintarCelda(g2d, i, j, x, y, Color.WHITE);
		            }
	        }}
	    }
		} 
	    
	    public void setCamino(CaminoValido cv) {
	        this.camino = cv;
	    }
	    
	    public void limpiarCamino() {
	        this.solucion = null;
	        repaint();
	    }

	    // getters
	    public Integer[][] getGrilla() {
	        return matrizGrilla;
	    }
	    
	    public int getFilas() {
	        return filas;
	    }
	    
	    public int getColumnas() {
	        return columnas;
	    }
	    
	    public Dimension getTamanoGrilla() {
	        return new Dimension(columnas, filas);
	    }
	    
	    public void setMostrarCamino(boolean mostrar) {
	    	this.mostrarCaminoActivo = mostrar;
	        repaint();
	    }
	    
	    public String getInfoCelda(int f, int c) {
	        if (f >= 0 && f < filas && c >= 0 && c < columnas) {
	            return String.format("Posición (%d,%d): Carga %s", 
	            		f, c, (matrizGrilla[f][c] == 1) ? "+1" : "-1");

	        }
	        return "Posición inválida";
	    }
}
