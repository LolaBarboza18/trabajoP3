package interfaz;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

public class TestJFreeChart {
	    public static void main(String[] args) {
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        dataset.addValue(1.0, "Row 1", "Column 1");
	        dataset.addValue(2.0, "Row 1", "Column 2");

	        JFreeChart chart = ChartFactory.createLineChart(
	            "Test Chart", // título
	            "Categoría",  // etiqueta eje x
	            "Valor",      // etiqueta eje y
	            dataset      // datos
	        );

	        JFrame frame = new JFrame("Test Chart");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(new ChartPanel(chart));
	        frame.pack();
	        frame.setVisible(true);
	    }
}
