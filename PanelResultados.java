package interfaz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logica.CaminoValido;
import logica.Grilla;
import logica.Observer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelResultados extends JPanel implements Observer {

	private JTable resultados;
    private DefaultTableModel tablaModelo;
    private JScrollPane tablaScroll;
    
    private JTextArea detalles;
    private JScrollPane detallesScroll;
    
    private JPanel estadisticas;
    private JLabel testTotales;
    private JLabel mejorTiempo;
    private JLabel peorTiempo;
    private JLabel tiempoPromedio;
    
    private JButton exportaResultados;
    private JButton mostrarChart;
    private JButton limpiarTabla;
    
    private JTabbedPane pestañas;
    
    private Grilla grilla;
    private CaminoValido caminoOptimo;
    private boolean mostrarDetalles = false;
    


	/**
	 * Create the application.
	 * @param camino 
	 * @param g 
	 */
    public void setGrilla (Grilla g) {
    	this.grilla = g;
    }
    public void setCaminoOptimo(CaminoValido cv) {
    	this.caminoOptimo= cv;
    }
    
	public PanelResultados() {
		initialize();
        panelLayout();
        setupEventHandlers();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBorder(BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(), 
	            "Resultados", 
	            TitledBorder.CENTER, 
	            TitledBorder.TOP));
	        
	        String[] nombresCols = {
		            "Grilla", 
		            "Sin Podas (ms)", 
		            "Con Podas (ms)", 
		            "Caminos encontrados Con y Sin Podas", 
		            "Llamados recursivos",
		            "Llamados recursivos con poda",
		            "Mejora (%)"
	        };
	        
	        tablaModelo = new DefaultTableModel(nombresCols, 0) {
	            @Override
	            public boolean isCellEditable(int f, int c) {
	                return false; 
	            }
	            
	            @Override
	            public Class<?> getColumnClass(int columnIndex) {
	                switch (columnIndex) {
                    case 0: return String.class;  // grilla
                    case 1: case 2: return Long.class;    // tiempos
                    case 3: return Integer.class; // caminos
                    case 4: return Double.class;  // mejora
                    default: return String.class;
	                   
	                }
	            }
	        };
	        
	        resultados = new JTable(tablaModelo);
	        resultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        resultados.setRowHeight(25);
	        resultados.getTableHeader().setReorderingAllowed(false);
	        
	        TableRowSorter<DefaultTableModel> orden = new TableRowSorter<>(tablaModelo);
	        resultados.setRowSorter(orden);
	        
	        resultados.getColumn("Grilla").setPreferredWidth(80);
	        resultados.getColumn("Sin Podas (ms)").setPreferredWidth(100);
	        resultados.getColumn("Con Podas (ms)").setPreferredWidth(100);

	        resultados.getColumn("Caminos encontrados Con y Sin Podas").setPreferredWidth(120);
	        resultados.getColumn("Llamados recursivos").setPreferredWidth(120);
	        resultados.getColumn("Llamados recursivos con poda").setPreferredWidth(120);

	        resultados.getColumn("Mejora (%)").setPreferredWidth(80);
	        
	        tablaScroll = new JScrollPane(resultados);
	        tablaScroll.setPreferredSize(new Dimension(600, 200));
	        
	        detalles = new JTextArea();
	        detalles.setEditable(false);
	        detalles.setFont(new Font("Courier New", Font.PLAIN, 12));
	        detalles.setBackground(new Color(248, 248, 248));
	        detallesScroll = new JScrollPane(detalles);
	        detallesScroll.setPreferredSize(new Dimension(600, 150));
	        
	        crearPanelEstadisticas();
	        
	        exportaResultados = new JButton("Exportar CSV");
	        exportaResultados.setPreferredSize(new Dimension(120, 30));
	        
	        mostrarChart = new JButton("Mostrar Gráfico");
	        mostrarChart.setPreferredSize(new Dimension(120, 30));
	        // para después hacer JFreeChart
	        
	        limpiarTabla = new JButton("Limpiar Tabla");
	        limpiarTabla.setPreferredSize(new Dimension(120, 30));
	        
	        pestañas = new JTabbedPane();
	}
	
	public void setMostrarDetalles(boolean mostrar) {
        this.mostrarDetalles = mostrar;
    }
	
	public void mostrarUltimaEjecucion() {
        if (tablaModelo.getRowCount() > 0) {
            int ultimaFila = tablaModelo.getRowCount() - 1;
            mostrarDetallesDeFila(ultimaFila);
            if (mostrarDetalles) {
                pestañas.setSelectedIndex(1); 
            }
        }
    }

	 private void crearPanelEstadisticas() {
	        estadisticas = new JPanel(new GridBagLayout());
	        estadisticas.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
	        
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 10, 5, 10);
	        gbc.anchor = GridBagConstraints.WEST;
	        
	        testTotales = new JLabel("Pruebas ejecutadas: 0");
	        mejorTiempo = new JLabel("Mejor tiempo: N/A");
	        peorTiempo = new JLabel("Peor tiempo: N/A");
	        tiempoPromedio = new JLabel("Tiempo promedio: N/A");
	        
	        gbc.gridx = 0; gbc.gridy = 0;
	        estadisticas.add(testTotales, gbc);
	        gbc.gridx = 1;
	        estadisticas.add(mejorTiempo, gbc);
	        
	        gbc.gridx = 0; gbc.gridy = 1;
	        estadisticas.add(peorTiempo, gbc);
	        gbc.gridx = 1;
	        estadisticas.add(tiempoPromedio, gbc);
	    }
	    
	    private void panelLayout() {
	        setLayout(new BorderLayout());
	        
	        JPanel panelTabla = new JPanel(new BorderLayout());
	        panelTabla.add(tablaScroll, BorderLayout.CENTER);
	        
	        JPanel panelBotones = new JPanel(new FlowLayout());
	        panelBotones.add(exportaResultados);
	        panelBotones.add(mostrarChart);
	        panelBotones.add(limpiarTabla);
	        
	        panelTabla.add(panelBotones, BorderLayout.SOUTH);
	        
	        pestañas.addTab("Tabla de Resultados", panelTabla);
	        pestañas.addTab("Detalles", detallesScroll);
	        pestañas.addTab("Estadísticas", estadisticas);
	        
	        add(pestañas, BorderLayout.CENTER);
	    }
	    
	    private void setupEventHandlers() {
	        resultados.getSelectionModel().addListSelectionListener(e -> {
	            if (!e.getValueIsAdjusting()) {
	            	mostrarDetallesDeFila();
	            }
	        });
	        
	        resultados.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 2) {
	                    pestañas.setSelectedIndex(1); 
	                }
	            }
	        });
	        
	        limpiarTabla.addActionListener(e -> limpiarResultados());
	        exportaResultados.addActionListener(e -> exportarResultados());
	        mostrarChart.addActionListener(e -> mostrarChart());
	    }
	    
	    private void mostrarDetallesDeFila(int fila) {
	        if (fila >= 0 && fila < tablaModelo.getRowCount()) {
	            int modelRow = resultados.convertRowIndexToModel(fila);
	            
	            StringBuilder detallesTexto = new StringBuilder();
	            detallesTexto.append("DETALLES DE LA EJECUCIÓN\n");
	            detallesTexto.append("========================\n\n");
	            	            
	            String tamanoGrilla = (String) tablaModelo.getValueAt(modelRow, 0);
	            Long tiempoSinPoda = (Long) tablaModelo.getValueAt(modelRow, 1);
	            Long tiempoConPoda = (Long) tablaModelo.getValueAt(modelRow, 2);

	            Integer LlamadasRecursivasPoda = (Integer) tablaModelo.getValueAt(modelRow, 3);
	            Integer LlamadasRecursivas = (Integer) tablaModelo.getValueAt(modelRow, 4);
	            Integer caminosEncontrados = (Integer) tablaModelo.getValueAt(modelRow, 5);

	            Integer caminosRecursivos = (Integer) tablaModelo.getValueAt(modelRow, 3);


	            Double mejora  = (Double) tablaModelo.getValueAt(modelRow, 6);
	            
	            detallesTexto.append(String.format("Tamaño de grilla: %s\n", tamanoGrilla));
	            detallesTexto.append(String.format("Tiempo sin poda: %d ms\n", tiempoSinPoda));
	            detallesTexto.append(String.format("Tiempo con poda: %d ms\n", tiempoConPoda));
	            detallesTexto.append(String.format("LLamadas recursivas sin poda %d\n", LlamadasRecursivas));
	            detallesTexto.append(String.format("LLamadas recursivas con poda %d\n", LlamadasRecursivasPoda));
	            detallesTexto.append(String.format("Caminos encontrados: %d\n", caminosEncontrados));
	            detallesTexto.append(String.format("Mejora: %.2f%%\n\n", mejora));
	            
	            if (tiempoSinPoda > 0 && tiempoConPoda > 0) {
	                double aceleracion = (double) tiempoSinPoda / tiempoConPoda;
	                detallesTexto.append(String.format("Aceleración: %.2fx\n", aceleracion));
	                
	                int LlamadasReducidas = LlamadasRecursivasPoda - LlamadasRecursivas;
	                double reduccion = ((double) LlamadasReducidas / LlamadasRecursivas) * 100;
	                detallesTexto.append(String.format("Reducción de llamadas recursivas: %.2f%%\n", reduccion));
	            }
	            
	            detalles.setText(detallesTexto.toString());
	        }
	    }
	    
	    private void mostrarDetallesDeFila() {
	        int filaSeleccionada = resultados.getSelectedRow();
	        if (filaSeleccionada >= 0) {
	            mostrarDetallesDeFila(filaSeleccionada);
	        }
	    }
	    

	    public void agregarResultado(String tamanoGrilla, long tiempoSinPoda, long tiempoConPoda, int caminosEncontrados , int LlamadasRecursivas, int LlamadasRecursivasConPoda) {

	        double mejora = 0;
	        if (LlamadasRecursivas > 0) {
	        	mejora = ((double)(LlamadasRecursivas - LlamadasRecursivasConPoda) / LlamadasRecursivas) * 100;
	        }
	        
	        Object[] datosFila = {
	            tamanoGrilla,
	            tiempoSinPoda,
	            tiempoConPoda,
	            caminosEncontrados,
	            LlamadasRecursivas,
	            LlamadasRecursivasConPoda,
	            mejora
	        };
	        
	        tablaModelo.addRow(datosFila);

	    }

	    
	    public void limpiarResultados() {
	        tablaModelo.setRowCount(0);
	        detalles.setText("");
	        reiniciarEstadisticas();
	    }
	    
	    private void reiniciarEstadisticas() {
	        int contFila = tablaModelo.getRowCount();
	        testTotales.setText("Pruebas ejecutadas: " + contFila);
	        
	        if (contFila == 0) {
	            mejorTiempo.setText("Mejor tiempo: N/A");
	            peorTiempo.setText("Peor tiempo: N/A");
	            tiempoPromedio.setText("N/A");
	            return;
	        }
	       
	    }
	    
	    private void exportarResultados() {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setSelectedFile(new java.io.File("resultados_robot.csv"));
	        
	        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	            // exportación a CSV
	            JOptionPane.showMessageDialog(this, 
	                "funcionalidad de exportación pendiente jeje",
	                "Información", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	    
	    private void mostrarChart() {
	        if (tablaModelo.getRowCount() == 0) {
	            JOptionPane.showMessageDialog(this,
	                "No hay datos para mostrar en el gráfico",
	                "Sin datos", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        
	        //gráfico con JFreeChart, pendiente
	        JOptionPane.showMessageDialog(this,
	            "hay que usar jfreechart para mostrar tiempos de ejecucion :]",
	            "Información", JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    //getters 
	    public DefaultTableModel getTablaModelo() {
	        return tablaModelo;
	    }
	    
	    public boolean hayResultados() {
	        return tablaModelo.getRowCount() > 0;
	    }
	    
	    public void setTextoDetalles(String texto) {
	        detalles.setText(texto);
	    }
	    
	    public void appendTextoDetalles(String texto) {
	        detalles.append(texto);
	    }

	    
	    
	    private void actualizarPanelEstadisticas( long tPoda, long tSpoda) {

	    	int contFila = tablaModelo.getRowCount();
	        
	        
	        Long pTiempo= Math.max(tSpoda,tPoda );
	        Long mTiempo= Math.min(tSpoda,tPoda );

	        
	        testTotales.setText("Pruebas ejecutadas: " + contFila);
            mejorTiempo.setText("Mejor tiempo: " + mTiempo + " ms");
            peorTiempo.setText("Peor tiempo: " + pTiempo + " ms");
            tiempoPromedio.setText("Tiempo promedio: " + (pTiempo + mTiempo)/2 + " ms");
	     
	    }
	    
	    
		@Override
		public void actualizar() {
		    int filas = grilla.getMatriz().length;
		    int cols = grilla.getMatriz()[0].length;
		    String tamanioGrilla = filas + "x" + cols;

		    long tiempoSinPoda = caminoOptimo.getTiempoTotal();
		    long tiempoConPoda = caminoOptimo.getTiempoTotalPoda();

		    int caminosEntontados = caminoOptimo.getCaminosEncontradosPoda();
		    int llamadasRecursivas = caminoOptimo.getLlamadasRecursivas();
		    int llamadasRecursivasPoda = caminoOptimo.getLlamadasRecursivasPoda();
		    
		    agregarResultado(tamanioGrilla, tiempoSinPoda, tiempoConPoda, caminosEntontados, llamadasRecursivas, llamadasRecursivasPoda);
		    actualizarPanelEstadisticas(tiempoConPoda, tiempoSinPoda);

	        mostrarUltimaEjecucion();

		    
}
}
