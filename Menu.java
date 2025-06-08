package tp3_p3;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class Menu {

	private JFrame frame;
	private Image menuImagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
//	public Menu() {
//		menuImagen = new ImageIcon(getClass().getResource("/Senderos A.gif")).getImage();
//
//		initialize();
//	}
////
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 800, 600);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("Senderos AGM");
//
//		JPanel fondo = agregarFondo(menuImagen);
//
//		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10)); 
//		panel.setOpaque(false);
//
//		JButton iniciar = new JButton("Comenzar");
//		iniciar.addActionListener(e -> iniciarPrograma());
//		panel.add(iniciar);
//
//		JButton reglasButton = new JButton("Reglas");
//		reglasButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, 
//				"¿Cómo funciona?\n"
//						+ "- Debes hacer clic donde quieras ubicar las estaciones.\n"
//						+ "- Una vez que tengas todas las estaciones, agregá sus conexiones.\n"
//						+ "- Es importante que ingreses el peso de cada sendero (entre 1 y 10)."));
//		panel.add(reglasButton);
//
//		JButton devsButton = new JButton("Devs");
//		devsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, 
//				"Este juego fue creado por: \n"
//						+ "Nicole, Lola, Fede y Lucia."));
//		panel.add(devsButton);
//		
//		JButton cargarArchButton = new JButton("Cargar grafo");
//		cargarArchButton.addActionListener(e -> cargarGrafoDesdeArchivo());
//		panel.add(cargarArchButton);
//
//		fondo.add(panel);
//		frame.getContentPane().add(fondo); 
//		frame.setVisible(true); 
//	}

//
//	private void iniciarPrograma() {		
//		Vista vistaMapa = new Vista();
//		Component contenidoVista = vistaMapa.getFrame();
//
//		actualizarPantalla(contenidoVista);
//	}
//
//	private void actualizarPantalla(Component contenidoVista) {
//		frame.getContentPane().removeAll();
//		frame.getContentPane().add(contenidoVista, BorderLayout.CENTER);
//		frame.revalidate();
//		frame.repaint();
//	}
//
//	private JPanel agregarFondo (Image im){
//		JPanel fondo = new JPanel() {
//			private Image imagen = im;
//
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				if (imagen != null) {
//					g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
//				}
//			}
//		};
//		fondo.setLayout(new GridBagLayout());
//		return fondo;
//	}
//
//	private void cargarGrafoDesdeArchivo() {
//		JFileChooser fileChooser = new JFileChooser();
//		int resultado = fileChooser.showOpenDialog(frame);
//
//		if (resultado == JFileChooser.APPROVE_OPTION) {
//			File archivoSeleccionado = fileChooser.getSelectedFile();
//			Grafo grafo = new Grafo(); 
//			grafo.cargarDesdeArchivo(archivoSeleccionado.getAbsolutePath());
//
//			Vista vista = new Vista(grafo); 
//			Component contenidoVista = vista.getFrame();
//
//
//			actualizarPantalla(contenidoVista);
//			vista.refrescarMarcadores();
//		}
//	}
}
