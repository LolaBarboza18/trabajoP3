package tp3_p3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelControl extends JPanel implements Observer{

	private JButton ejecutarAlgoritmos; //ex "ejecutarFuerzaBruta"
//    private JButton ejecutarConPoda;
    private JButton limpiarResultados;
    private JButton cargarGrilla;
    
    private JCheckBox mostrarCamino;
    private JCheckBox resultadosDetalles;
    private JCheckBox opcionesComparacion;
    
    private JLabel infoGrilla;
    private JLabel caminoValido;
    
    private PanelEstado panelEstado;
    
    private Grilla grilla;
    private CaminoValido caminoOptimo;


	public PanelControl(Grilla g, CaminoValido camino) {
		initialize(g, camino);
		panelLayout();
        setupEventHandlers();
	}


	private void initialize(Grilla g, CaminoValido camino) {
		setBorder(BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(), 
	            "Panel de Control", 
	            TitledBorder.CENTER, 
	            TitledBorder.TOP));
		
		this.grilla = g;
		this.caminoOptimo = camino;
	    caminoOptimo.agregarObserver(this);

		ejecutarAlgoritmos = new JButton("Ejecutar Algoritmos");
		ejecutarAlgoritmos.setPreferredSize(new Dimension(180, 30));
		ejecutarAlgoritmos.setToolTipText("Ejecuta el algoritmo de fuerza bruta sin poda");
	        
//		ejecutarConPoda = new JButton("Ejecutar con Poda");
//		ejecutarConPoda.setPreferredSize(new Dimension(180, 30));
//		ejecutarConPoda.setToolTipText("Ejecuta el algoritmo con estrategias de poda");

		limpiarResultados = new JButton("Limpiar Resultados");
		limpiarResultados.setPreferredSize(new Dimension(180, 30));

		cargarGrilla = new JButton("Cargar Archivo");
		cargarGrilla.setPreferredSize(new Dimension(120, 25));
	        
		mostrarCamino = new JCheckBox("Mostrar camino encontrado", true);
		resultadosDetalles = new JCheckBox("Resultados detallados", false);
		opcionesComparacion = new JCheckBox("Comparar métodos", true);

		infoGrilla = new JLabel();
		actualizarInfoGrilla();
		infoGrilla.setFont(new Font("Arial", Font.BOLD, 12));
	        
		caminoValido = new JLabel("Estado: Sin ejecutar");
		caminoValido.setFont(new Font("Arial", Font.ITALIC, 11));
 
	}
	
	 public void setPanelEstado(PanelEstado panelEstado) {
	        this.panelEstado = panelEstado;
	    }
	 
	private void panelLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // GridBagConstraints: sirve para manejar características de los componentes
        // que estén siendo mostrados en pantalla con GridBagLayout, un tipo de Layout
        // como BoxLayout, BorderLayout, etc
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(crearPanelInfo(), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panelPrincipal.add(cargarGrilla, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panelPrincipal.add(new JSeparator(), gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3;
        panelPrincipal.add(ejecutarAlgoritmos, gbc);
        
//        gbc.gridy = 4;
//        panelPrincipal.add(ejecutarConPoda, gbc);
        
        gbc.gridy = 5;
        panelPrincipal.add(crearPanelOpciones(), gbc);
        
        gbc.gridy = 6;
        panelPrincipal.add(limpiarResultados, gbc);
        
        // todos los gbc.metodos son reservados de la clase GridBagContraints
        // esto es de AWT para interfaz grafica java, ahora asumo que también
        // hay algo similar en WB/Swing, después lo combiaré o lo dejo así y ya fue xd
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelInfo() {
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Información"));
        infoPanel.add(infoGrilla);
        infoPanel.add(caminoValido);
        cargarGrilla.addActionListener(e -> cargarGrillaDeArchivo());
        return infoPanel;
    }
    //--
    private void cargarGrillaDeArchivo() {
        JFileChooser seleccionaArchivo = new JFileChooser();
        seleccionaArchivo.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos de texto (*.txt)", "txt"));
        
        if (seleccionaArchivo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String archivo = seleccionaArchivo.getSelectedFile().getAbsolutePath();
            panelEstado.setEstatus("Cargando archivo: " + archivo);
        }
    }
    //--
    private JPanel crearPanelOpciones() {
        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 0, 2));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Opciones"));
        panelOpciones.add(mostrarCamino);
        panelOpciones.add(resultadosDetalles);
        panelOpciones.add(opcionesComparacion);
        return panelOpciones;
    }
    
    private void setupEventHandlers() {
        // los event handlers se conectan con la lógica de negocio
        // por ahora dejo todo vacio :9
        
    	ejecutarAlgoritmos.addActionListener(e -> {
            //setBotones(false); 		//ESTO ES PARA QUE SE HABILITE SIEMPRE
            panelEstado.setEstatus("Ejecutando fuerza bruta...");
            
            caminoOptimo.encontrarCaminosSinPoda();
            caminoOptimo.encontrarCaminosConPoda();
         
         
            
        });
        
//        ejecutarConPoda.addActionListener(e -> {
//            setBotones(false);
//            panelEstado.setEstatus("Ejecutando con poda...");
//          
//                caminoOptimo.encontrarCaminosConPoda();
//                
//        });
        
        limpiarResultados.addActionListener(e -> {
            //limpiar resultados
            caminoValido.setText("Estado: Sin ejecutar");
            if (panelEstado != null) {
                panelEstado.setEstatus("Resultados limpiados");
            }
        });
    }
    
    public void setBotones(boolean enabled) {
        ejecutarAlgoritmos.setEnabled(enabled);
//        ejecutarConPoda.setEnabled(enabled);
        cargarGrilla.setEnabled(enabled);
 
    }
    
    public void actualizarInfoGrilla() {
    	int filas= grilla.getMatriz().length;
		int col= grilla.getMatriz()[0].length;
        infoGrilla.setText(String.format("Grilla: %dx%d (%d celdas)", filas, col, filas * col));
        
    }
    
    public void setEstadoEjecucion(String estado) {
    	caminoValido.setText("Estado: " + estado);
    	caminoValido.setForeground(Color.BLUE);
    }
    
    
	@Override
	public void actualizar(CaminoValido cv) {
		
		if (cv.getCaminosEncontrados()>0) {
		caminoValido.setText("Estado: Camino válido encontrado");
    	caminoValido.setForeground(new Color(0, 120, 0));
		} else{ 
			caminoValido.setText("Estado: No se encontró camino válido");
			caminoValido.setForeground(Color.RED);
		}
		
	}
    
    //getters 
    public boolean estaActivadoMostrarCamino() {
        return mostrarCamino.isSelected();
    }
    
    public boolean estaActivadoResultadosDetallados() {
        return resultadosDetalles.isSelected();
    }
    
    public boolean estaActivadoCompararMetodos() {
    	 return opcionesComparacion.isSelected();
    }


    
}
