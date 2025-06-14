package interfaz;


import javax.swing.*;
import java.awt.*;

public class PanelEstado extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel estatus;

	/**
	 * Create the application.
	 */
	public PanelEstado() {
		initialize();
		panelLayout();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		estatus = new JLabel("Listo");
		estatus.setFont(new Font("Arial", Font.PLAIN, 12));

	}

	private void panelLayout() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		setPreferredSize(new Dimension(0, 30));

		JPanel estadoProgreso = new JPanel(new BorderLayout());
		estadoProgreso.add(estatus, BorderLayout.WEST);

		add(estadoProgreso, BorderLayout.CENTER);
	}
	public void setEstatus(String s) {
		estatus.setText(s);
		estatus.repaint();
	}

	public void mostrarProgreso(String mensaje) {
		setEstatus(mensaje);
		repaint();
	}

}

