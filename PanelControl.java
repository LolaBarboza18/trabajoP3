package interfaz;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import logica.CaminoValido;
import logica.Grilla;
import logica.Observer;

import java.awt.*;

public class PanelControl extends JPanel implements Observer{

	private JButton ejecutarAlgoritmos;
    private JButton limpiarResultados;
    private JButton cargarGrilla;
    private JButton generarGrillaAleatoria;
    
    private JCheckBox mostrarCamino;
    private JCheckBox resultadosDetalles;
    
    private JLabel infoGrilla;
    private JLabel caminoValido;
    
    private PanelEstado panelEstado;
    private PanelGrilla panelGrilla;
    private PanelResultados panelResultados;
    
    private Grilla grilla;
    private CaminoValido caminoOptimo;


	public PanelControl() {
		initialize();
		panelLayout();
        setupEventHandlers();
	}

	 public void setPanelGrilla(PanelGrilla panel) {
	        this.panelGrilla = panel;
	    }
	 
	 public void setPanelResultados(PanelResultados panel) {
	        this.panelResultados = panel;
	    }

	private void initialize() {
		setBorder(BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(), 
	            "Panel de Control", 
	            TitledBorder.CENTER, 
	            TitledBorder.TOP));


		ejecutarAlgoritmos = new JButton("Ejecutar Algoritmos");
		ejecutarAlgoritmos.setPreferredSize(new Dimension(300, 30));
		ejecutarAlgoritmos.setToolTipText("Ejecuta el algoritmo de fuerza bruta con y sin poda.");

		limpiarResultados = new JButton("Limpiar Resultados");
		limpiarResultados.setPreferredSize(new Dimension(180, 30));
		limpiarResultados.setToolTipText("Limpia el camino encontrado.");

		cargarGrilla = new JButton("Cargar Archivo");
		cargarGrilla.setPreferredSize(new Dimension(120, 25));
		cargarGrilla.setToolTipText("Cargar archivo .txt con grilla existente.");
		generarGrillaAleatoria = new JButton("Nueva Grilla Aleatoria");
		generarGrillaAleatoria.setPreferredSize(new Dimension(120, 25));
		generarGrillaAleatoria.setToolTipText("Generar una nueva grilla con dimensiones especificadas.");
	        
		mostrarCamino = new JCheckBox("Mostrar camino encontrado", true);
		resultadosDetalles = new JCheckBox("Resultados detallados", false);

		infoGrilla = new JLabel();
		actualizarInfoGrilla();
		infoGrilla.setFont(new Font("Arial", Font.BOLD, 12));
	        
		caminoValido = new JLabel("Estado: Sin ejecutar");
		caminoValido.setFont(new Font("Arial", Font.ITALIC, 11));

		resultadosDetalles.addActionListener(e -> {
			if (panelResultados != null) {
				if (resultadosDetalles.isSelected()) {
					panelResultados.setMostrarDetalles(true);
					if (panelResultados.hayResultados()) {
						panelResultados.mostrarUltimaEjecucion();
					}
				} else {
					panelResultados.setMostrarDetalles(false);
				}
			}

		});
	}
	
	public void setPanelEstado(PanelEstado panelEstado) {
	        this.panelEstado = panelEstado;
	    }
	 
	private void panelLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(crearPanelInfo(), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panelPrincipal.add(cargarGrilla, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 1; gbc.gridy = 1;
        panelPrincipal.add(generarGrillaAleatoria, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panelPrincipal.add(new JSeparator(), gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3;
        panelPrincipal.add(ejecutarAlgoritmos, gbc);
       
        gbc.gridwidth = 0;
        gbc.gridy = 5;
        panelPrincipal.add(crearPanelOpciones(), gbc);
        gbc.gridwidth = 0;
        gbc.gridy = 6;
        panelPrincipal.add(limpiarResultados, gbc);
   
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
    
    public void cargarGrillaDeArchivo() {
        JFileChooser seleccionaArchivo = new JFileChooser();
        seleccionaArchivo.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos de texto (*.txt)", "txt"));
       
        if (seleccionaArchivo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String archivo = seleccionaArchivo.getSelectedFile().getAbsolutePath();
            panelEstado.setEstatus("Cargando archivo: " + archivo);
            
            try {
                Grilla nuevaGrilla = new Grilla(archivo);
                this.grilla = nuevaGrilla;
                this.caminoOptimo = new CaminoValido(nuevaGrilla);

                panelResultados.setCaminoOptimo(caminoOptimo);
                caminoOptimo.agregarObserver(panelGrilla);
                caminoOptimo.agregarObserver(this);
                caminoOptimo.agregarObserver(panelResultados);
                
                
                if (panelGrilla != null) {
                    panelGrilla.actualizarGrilla(nuevaGrilla, caminoOptimo);
                }
                
                if (panelResultados != null) {
                    panelResultados.setGrilla(nuevaGrilla);
                }
                actualizarInfoGrilla();
                panelEstado.setEstatus("Archivo cargado exitosamente: " + archivo);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al cargar el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private JPanel crearPanelOpciones() {
        JPanel panelOpciones = new JPanel(new GridLayout(2, 1, 0, 2));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Opciones"));
        panelOpciones.add(mostrarCamino);
        panelOpciones.add(resultadosDetalles);
        return panelOpciones;
    }
    
    private void setupEventHandlers() {
    	ejecutarAlgoritmos.addActionListener(e -> {
    		ejecutarAlgoritmos();
            panelEstado.setEstatus("Ejecutando fuerza bruta...");
            
        });
        
        limpiarResultados.addActionListener(e -> {
            caminoValido.setText("Estado: Sin ejecutar");
            panelGrilla.limpiarCamino();
            if (panelEstado != null) {
                panelEstado.setEstatus("Resultados limpiados");
            }
        });
        
        generarGrillaAleatoria.addActionListener(e -> crearGrillaAleatoria());
    }
    
    private void ejecutarAlgoritmos() {
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	ejecutarAlgoritmos.setEnabled(false);

    	try {
    		caminoOptimo.ejecutarAmbos();
    	}catch (IllegalArgumentException ex) {
    		JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    
    	ejecutarAlgoritmos.setEnabled(true);
    	setCursor(Cursor.getDefaultCursor());
    }

    
	private void crearGrillaAleatoria() {
    	String filasStr = JOptionPane.showInputDialog(this, 
    			"Ingrese número de filas:", "Nueva Grilla", 
    			JOptionPane.QUESTION_MESSAGE);
    	if (filasStr == null) {
    		return;
    	}
    	try {
            int filas = Integer.parseInt(filasStr);
            if (filas <= 0) {
                JOptionPane.showMessageDialog(this,
                    "El número de filas debe ser mayor a 0",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String colsStr = JOptionPane.showInputDialog(this, 
                    "Ingrese número de columnas:", "Nueva Grilla", 
                    JOptionPane.QUESTION_MESSAGE);
            
            if (colsStr == null) {
                return;
            }
            
            int cols = Integer.parseInt(colsStr);
            if (cols <= 0) {
                JOptionPane.showMessageDialog(this,
                    "El número de columnas debe ser mayor a 0",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Grilla nuevaGrilla = new Grilla(filas, cols);
            this.grilla = nuevaGrilla;
            this.caminoOptimo = new CaminoValido(nuevaGrilla);
            
            panelResultados.setCaminoOptimo(caminoOptimo);

            caminoOptimo.agregarObserver(panelGrilla);
            caminoOptimo.agregarObserver(this);
            caminoOptimo.agregarObserver(panelResultados);
            if (panelGrilla != null) {
                panelGrilla.actualizarGrilla(nuevaGrilla, caminoOptimo);
            }
            if (panelResultados != null) {
                panelResultados.setGrilla(nuevaGrilla);
            }
            
            actualizarInfoGrilla();
            if (panelEstado != null) {
                panelEstado.setEstatus("Nueva grilla aleatoria creada");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingrese números válidos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

	public void setBotones(boolean enabled) {
        ejecutarAlgoritmos.setEnabled(enabled);
        cargarGrilla.setEnabled(enabled);
 
    }
    
    public void actualizarInfoGrilla() {
    	if (grilla == null) {
    		infoGrilla.setText(String.format("Aun no se ha cargado una grilla"));
    	} else {
	    	int filas= grilla.getMatriz().length;
			int col= grilla.getMatriz()[0].length;
	        infoGrilla.setText(String.format("Grilla: %dx%d (%d celdas)", filas, col, filas * col));
    	} 
    }
    
    public void setEstadoEjecucion(String estado) {
    	caminoValido.setText("Estado: " + estado);
    	caminoValido.setForeground(Color.BLUE);
    }
    
    
	@Override
	public void actualizar() {
		if (caminoOptimo.getCaminosEncontrados()>0) {
		caminoValido.setText("Estado: Camino válido encontrado");
    	caminoValido.setForeground(new Color(0, 120, 0));
		} else{ 
			caminoValido.setText("Estado: No se encontró camino válido");
			caminoValido.setForeground(Color.RED);
		}
		
	}
	
	public void actualizarGrilla(Grilla nuevaGrilla) {
		 this.grilla = nuevaGrilla;
		 actualizarInfoGrilla();
		
	}

    //getters 
    public boolean estaActivadoMostrarCamino() {
        return mostrarCamino.isSelected();
    }
    
    public boolean estaActivadoResultadosDetallados() {
        return resultadosDetalles.isSelected();
    }
 

    
}
