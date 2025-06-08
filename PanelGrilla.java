package tp3_p3;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.util.List;

public class PanelGrilla extends JPanel {

	private Grilla grilla;
	private Integer[][] matrizGrilla;
	private int filas;
	private int columnas;
	private List<Point> caminoActual;

	private int tamanoCelda = 40;
	private boolean mostrarCamino = false;

	/**
	 * Create the application.
	 */
	
	public PanelGrilla() {
		initialize();
//      dibujarGrillaDefault();
//		ya no se inicializa la grilla acá, es null hasta que se cargue
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
	
//	 private void dibujarGrillaDefault() {
//	        // grilla de ejemplo, asumo q esto después se puede hacer en logica 
//	        filas = 3;
//	        columnas = 4;
//	        matrizGrilla = new Integer[][] {
//	        	 {1, -1, 1, 1},
//	             {-1, 1, -1, 1},
//	             {1, -1, -1, -1}
//	        };
//		 
////		 	matrizGrilla = g.getMatriz(); ?
//	        calculoTamanoCelda();
//	    }


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
	        
	        Graphics2D g2d = (Graphics2D) g.create();
	        // Graphics2D: clase de Java que extiende de Graphics
	        // para dibujar figuras en pantalla con más precisión geometrica. Dibuja sobre un objeto Component.
	        // Es de la libreria de java.awt. Info de Oracle. :9
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        int comienzoX = (getWidth() - columnas * tamanoCelda) / 2;
	        int comienzoY = (getHeight() - filas * tamanoCelda) / 2 + 10;
	        
	        if (mostrarCamino && caminoActual != null) {
	            dibujarCamino(g2d, comienzoX, comienzoY);
	        }
	        
	        dibujarGrilla(g2d, comienzoX, comienzoY);
	        
	        dibujarPosicionesComienzoFin(g2d, comienzoX, comienzoY);
	        
	        g2d.dispose();
	    }
	    
	    private void dibujarGrilla(Graphics2D g2d, int stX, int stY) {
	        for (int i = 0; i < filas; i++) {
	            for (int j = 0; j < columnas; j++) {
	                int x = stX + j * tamanoCelda;
	                int y = stY + i * tamanoCelda;
	                
	                g2d.setColor(Color.WHITE);
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
	            
	             //   String valor = (grilla.darElemento(i, j) == 1) ? "+1" : "-1"
	                
	                // FontMetrics: de la libreria de java.awt también, encapsula info 
	                // de una font particular de la pantalla para usarla después.
	                // como está dentro de un loop, se usan los métodos .getAscent por ejemplo
	                // para que no entre en un ciclo recursivo infinito. Info de Oracle x2
	                
	                // aaaaunque, se dice que es ineficiente, así que quizá lo cambie
	               
	            }
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
	    
	    private void dibujarCamino(Graphics2D g2d, int stX, int stY) {
	        if (caminoActual.size() < 2) {
	        	return;
	        }
	        
	        g2d.setColor(new Color(255, 165, 0, 128));
	        g2d.setStroke(new BasicStroke(tamanoCelda / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	        
	        for (int i = 0; i < caminoActual.size() - 1; i++) {
	            Point p1 = caminoActual.get(i);
	            Point p2 = caminoActual.get(i + 1);
	            
	            int x1 = stX + p1.y * tamanoCelda + tamanoCelda / 2;
	            int y1 = stY + p1.x * tamanoCelda + tamanoCelda / 2;
	            int x2 = stX + p2.y * tamanoCelda + tamanoCelda / 2;
	            int y2 = stY + p2.x * tamanoCelda + tamanoCelda / 2;
	            
	            g2d.drawLine(x1, y1, x2, y2);
	        }
	        
	        g2d.setColor(Color.ORANGE);
	        for (Point p : caminoActual) {
	            int x = stX + p.y * tamanoCelda + tamanoCelda / 2 - 3;
	            int y = stY + p.x * tamanoCelda + tamanoCelda / 2 - 3;
	            g2d.fillOval(x, y, 6, 6);
	        }
	    }

//	    public void setGrilla(Integer[][] grilla) {
//	    	this.matrizGrilla = grilla;
//	    	this.filas = grilla.length;
//	    	this.columnas = grilla[0].length;
//	        calculoTamanoCelda();
//	        repaint();
//	    }
	    
	    public void actualizarGrilla(Grilla grilla) {
	    	if (grilla != null) {
	            this.grilla = grilla;
	            this.matrizGrilla = grilla.getMatriz();
	            if (this.matrizGrilla != null) {
	                this.filas = matrizGrilla.length;
	                this.columnas = matrizGrilla[0].length;
	                calculoTamanoCelda();
	                
	                // para ver namas
	                System.out.println("Actualizando grilla:");
	                System.out.println("Filas: " + filas);
	                System.out.println("Columnas: " + columnas);
	                System.out.println("Tamaño celda: " + tamanoCelda);
	                
	                revalidate(); 
	                calculoTamanoCelda();
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
	    
	    public void setCamino(List<Point> camino) {
	        this.caminoActual = camino;
	        // (camino != null && !camino.isEmpty());
	        repaint();
	    }
	    
	    public void limpiarCamino() {
	        this.caminoActual = null;
	        this.mostrarCamino = false;
	        repaint();
	    }
	    
	    public void colorearCamino(List<Point> camino) {
	        setCamino(camino);
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
	    
	    public String getInfoCelda(int f, int c) {
	        if (f >= 0 && f < filas && c >= 0 && c < columnas) {
	            return String.format("Posición (%d,%d): Carga %s", 
//	                f, c, (grilla.darElemento(f, c) == 1) ? "+1" : "-1");
	            		f, c, (matrizGrilla[f][c] == 1) ? "+1" : "-1");

	        }
	        return "Posición inválida";
	    }
}
