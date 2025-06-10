package interfaz;

import javax.swing.*;

//import logica.CaminoValido;
//import logica.Grilla;

import java.awt.*;
import java.net.URL;

public class Vista extends JFrame {
	
    private JMenuBar menu;
    private PanelGrilla grilla;
    private PanelControl panelDeControl;
    private PanelResultados panelResultados;
    private PanelEstado panelEstado;
    
    private JPanel principal;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista window = new Vista();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Vista() {
		initialize();
        pantallaLayout();
        barraMenuSetup();
        conectarPaneles();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Optimización de Ruta de Robot");
        setSize(1200, 800);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		grilla = new PanelGrilla();
      panelResultados = new PanelResultados();
        panelDeControl = new PanelControl();
        panelEstado = new PanelEstado();
        principal = new JPanel(new BorderLayout());
        panelIzquierdo = new JPanel(new BorderLayout());
        panelDerecho = new JPanel(new BorderLayout());
        try {
            URL iconURL = getClass().getResource("/robotSimpatico.png");
            if (iconURL != null) {
                ImageIcon img = new ImageIcon(iconURL);
                setIconImage(img.getImage());
            }
        } catch (Exception e) {
            System.err.println("Error cargando el ícono: " + e.getMessage());
        }
    
	}
	
	 private void conectarPaneles() {
	        panelDeControl.setPanelEstado(panelEstado);
	        panelDeControl.setPanelGrilla(grilla);
	        panelDeControl.setPanelResultados(panelResultados);
	        

	        
	    }

	 private void pantallaLayout() {
		 panelIzquierdo.add(grilla, BorderLayout.CENTER);

		 panelDerecho.add(panelResultados, BorderLayout.CENTER);
		 panelDerecho.add(panelEstado, BorderLayout.SOUTH);
		 panelDerecho.add(panelDeControl, BorderLayout.NORTH);
		 JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
		 splitPanel.setDividerLocation(600);
		 splitPanel.setResizeWeight(0.6);

		 principal.add(splitPanel, BorderLayout.CENTER);

		 add(principal);
	 }

	    private void barraMenuSetup() {
	        menu = new JMenuBar();
	        
	        JMenu menuArchivo = new JMenu("Archivo");
	        menuArchivo.setMnemonic('A');
	        
	        JMenuItem cargarGrilla = new JMenuItem("Cargar Grilla...");
	        cargarGrilla.setMnemonic('C');
	        cargarGrilla.addActionListener(e -> panelDeControl.cargarGrillaDeArchivo());
	        
	        JMenuItem guardarResultado = new JMenuItem("Guardar Resultados...");
	        guardarResultado.setMnemonic('R');
	        guardarResultado.addActionListener(e -> guardarResultados());
	        
	        menuArchivo.add(cargarGrilla);
	        menuArchivo.addSeparator();
	        menuArchivo.add(guardarResultado);
	        menuArchivo.addSeparator();
	        
	        
	        JMenu menuOtro= new JMenu("Otro");
	        menuOtro.setMnemonic('Y');
	        
	        JMenuItem botonAcerca = new JMenuItem("Como funciona");
	        botonAcerca.addActionListener(e -> mostrarFuncionamiento());
	        
	        JMenuItem botonDevs = new JMenuItem("Devs");
	        botonDevs.addActionListener(e -> mostrarDevs());
	        
	        menuOtro.add(botonAcerca);
	        menuOtro.add(botonDevs);
	        
	        menu.add(menuArchivo);
	        menu.add(menuOtro);
	        
	        setJMenuBar(menu);
	    }
	    
	    private void guardarResultados() {
	        JFileChooser archivoEscoge = new JFileChooser();
	        archivoEscoge .setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
	            "Archivos CSV (*.csv)", "csv"));
	        
	        if (archivoEscoge .showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	            String archivoElegido = archivoEscoge.getSelectedFile().getAbsolutePath();
	            panelEstado.setEstatus("Guardando resultados en: " + archivoElegido);
	            // pendiente...
	        }
	    }
	    
	    private void mostrarFuncionamiento() {
	        JOptionPane.showMessageDialog(this,
	            "Querido usuario: \n Si quieres crear una grilla aleatoria, haz click en 'Nueva grilla aleatoria "
	            + "y completa los datos.\n Si quieres cargar una grilla, puedes encontrar una de ejemlo en la carpeta de este proyecto. \n"
	            + "Si quieres volver a correr el programa solo selecciona como quieres generar la grilla nuevamente.",
	            "Como funciona",
	            JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    private void mostrarDevs() {
	        JOptionPane.showMessageDialog(this,
	            "Hecho por: Nicole, Lola, Lucia y Fede. :)", "Devs",
	            JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    // getters
	    public PanelGrilla getPanelGrilla() {
	        return grilla;
	    }
	    
	    public PanelControl getPanelDeControl() {
	        return panelDeControl;
	    }
	    
	    public PanelResultados getPanelResultados() {
	        return panelResultados;
	    }
	    
	    public PanelEstado getPanelEstado() {
	        return panelEstado;
	    }
	    

}
