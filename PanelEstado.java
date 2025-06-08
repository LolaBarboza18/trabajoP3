package tp3_p3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelEstado extends JPanel {

	private JLabel estatus;
	private JLabel tiempo;
	private JProgressBar progreso;
	private JButton cancelar;
//	private Timer timer;
//	private SimpleDateFormat formatoTiempo;

	/**
	 * Create the application.
	 */
	public PanelEstado() {
		initialize();
		panelLayout();
//		empezarTimer();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		formatoTiempo = new SimpleDateFormat("HH:mm:ss");
		estatus = new JLabel("Listo");
		estatus.setFont(new Font("Arial", Font.PLAIN, 12));

		tiempo = new JLabel();
		tiempo.setFont(new Font("Arial", Font.PLAIN, 11));
		tiempo.setHorizontalAlignment(SwingConstants.RIGHT);

		progreso = new JProgressBar(0, 100);
		progreso.setStringPainted(true);
		progreso.setString("");
		progreso.setVisible(false);

		cancelar = new JButton("Cancelar");
		cancelar.setPreferredSize(new Dimension(80, 25));
		cancelar.setVisible(false);
	}

	private void panelLayout() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		setPreferredSize(new Dimension(0, 30));

		JPanel estadoProgreso = new JPanel(new BorderLayout());
		estadoProgreso.add(estatus, BorderLayout.WEST);
		estadoProgreso.add(progreso, BorderLayout.CENTER);

		JPanel tiempoYCancelar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 2));
		tiempoYCancelar.add(cancelar);
		tiempoYCancelar.add(tiempo);

		add(estadoProgreso, BorderLayout.CENTER);
		add(tiempoYCancelar, BorderLayout.EAST);
	}
//
//	private void empezarTimer() {
//		timer = new Timer(1000, e -> actualizarTiempo());
//		timer.start();
//	}
//
//	private void actualizarTiempo() {
//		tiempo.setText(formatoTiempo.format(new Date()));
//	}

	public void setEstatus(String s) {
		estatus.setText(s);
		estatus.repaint();
	}

	public void mostrarProgreso(String mensaje) {
		setEstatus(mensaje);
		progreso.setString(mensaje);
		progreso.setIndeterminate(true);
		progreso.setVisible(true);
		cancelar.setVisible(true);
		repaint();
	}

	public void mostrarProgreso(String mensaje, int actual, int total) {
		setEstatus(mensaje);
		progreso.setIndeterminate(false);
		progreso.setMaximum(total);
		progreso.setValue(actual);
		progreso.setString(String.format("%s (%d/%d)", mensaje, actual, total));
		progreso.setVisible(true);
		cancelar.setVisible(true);
		repaint();
	}

	public void esconderProgreso() {
		progreso.setVisible(false);
		cancelar.setVisible(false);
		setEstatus("Listo");
		repaint();
	}
	
//	public void cancelarEjecucion() {
//		cancelar.addActionListener(e -> {
//            setEstatus("Ejecución cancelada.");
//            
//        });
//	}
	
// me falta hacer que cancele bien el algoritmo y vuelva a enablear los botones

	public void setValorProgreso(int valor) {
		progreso.setValue(valor);
	}

	public void setMaximoProgreso(int maximo) {
		progreso.setMaximum(maximo);
	}

//	public void agregarListenerCancelar(ActionListener l) {
//		cancelar.addActionListener(l);
//	}

	public void setCancelarActivo(boolean activo) {
		cancelar.setEnabled(activo);
	}

//	@Override
//	public void removeNotify() {
//		if (timer != null) {
//			timer.stop();
//		}
//		super.removeNotify();
//	}
}

