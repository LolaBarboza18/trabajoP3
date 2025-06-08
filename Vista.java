package tp3_p3;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {
	
    private JMenuBar menu;
    private PanelGrilla grilla;
    private PanelControl panelDeControl;
    private PanelResultados panelResultados;
    private PanelEstado panelEstado;
    
    private JPanel principal;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    
    private Grilla g;
    private CaminoValido caminoOptimo;
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
		Integer[][] gr = {
	            {1, -1, 1, -1},
	            {-1, 1, -1, -1},
	            {1, -1, -1, 1}
	        };
		g = new Grilla(gr);
		caminoOptimo = new CaminoValido(g);
		
        panelDeControl = new PanelControl(g,caminoOptimo);
        panelResultados = new PanelResultados(g,caminoOptimo);
        panelEstado = new PanelEstado();
        principal = new JPanel(new BorderLayout());
        panelIzquierdo = new JPanel(new BorderLayout());
        panelDerecho = new JPanel(new BorderLayout());
//        quería ver si podiamos cambiarle el icono :]
//        try {
//            setIconImage(Toolkit.getDefaultToolkit().getImage("resources/robot.png"));
//        } catch (Exception e) {
//         
//        }
    
	}
	
	 private void conectarPaneles() {
	        panelDeControl.setPanelEstado(panelEstado);
	    }

	 private void pantallaLayout() {
		 panelIzquierdo.add(grilla, BorderLayout.CENTER);
		 panelDerecho.add(panelDeControl, BorderLayout.NORTH);
		 panelDerecho.add(panelResultados, BorderLayout.CENTER);
		 panelDerecho.add(panelEstado, BorderLayout.SOUTH);

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
	        cargarGrilla.addActionListener(e -> cargarGrillaDeArchivo());
	        
//	        JMenuItem generarGrilla = new JMenuItem("Generar Grilla Aleatoria...");
//	        generarGrilla.setMnemonic('G');
//	        generarGrilla.addActionListener(e -> grillaRandom());
	        
	        JMenuItem guardarResultado = new JMenuItem("Guardar Resultados...");
	        guardarResultado.setMnemonic('R');
	        guardarResultado.addActionListener(e -> guardarResultados());
	        
	        menuArchivo.add(cargarGrilla);
//	        menuArchivo.add(generarGrilla);
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
	    
	    
	    private void cargarGrillaDeArchivo() {
	        JFileChooser seleccionaArchivo = new JFileChooser();
	        seleccionaArchivo.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
	            "Archivos de texto (*.txt)", "txt"));
	        
	        if (seleccionaArchivo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	            String archivo = seleccionaArchivo.getSelectedFile().getAbsolutePath();
	            panelEstado.setEstatus("Cargando archivo: " + archivo);
	        }
	    }
	    
//	    private void mostrarGrillaRandom() {
//	        GrillaRandom grillaAleatoria = new GrillaRandom(this);
//	        grillaAleatoria.setVisible(true);
//	    }
	    
	    // esto si uso una clase para grillaRandom.......
	    
	    
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
	            "explicacion blablabla",
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
