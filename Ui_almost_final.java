package Projekt;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Ui class represents the graphical user interface for the power simulation application.
 * It allows users to input parameters, generate graphs, and save graphs and datasets.
 */
public class Ui extends JFrame implements ActionListener {
    public static JTextField textField1;
    public static JTextField textField2;
    public static JTextField textField3;
    public static JTextField textField4;
    public static JTextField textField5;
    public static JTextField textField6;
    public JCheckBox useInitialValues;
    public JLabel equationLabel1, equationLabel2, equationLabel3;
    private Graph graph;
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    /**
     * Handles action events triggered by user interactions with the UI components.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events
    }

    /**
     * Saves the graph as a JPEG file.
     */
    private void saveGraph() {
        BufferedImage image = new BufferedImage(chartPanel.getWidth(), chartPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        chartPanel.paint(graphics2D);
        graphics2D.dispose();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Graph");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG files", "jpg"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".jpg")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".jpg");
                }
                javax.imageio.ImageIO.write(image, "jpg", fileToSave);
                JOptionPane.showMessageDialog(this, "Graph saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving graph: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Saves the dataset as a text file.
     */
    private void saveDataset() {
        // Check if the dataset is empty
        if (graph.time == null || graph.time.length == 0) {
            JOptionPane.showMessageDialog(this, "Dataset is empty. Please generate the data first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Dataset");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                String[] headers = {"Time", "Moc Fizjologiczna", "Moc Jałowa", "Moc Użytkowa"};
                
                // Calculate the maximum width for each column
                int maxTimeWidth = headers[0].length();
                int maxPowerWidth = headers[1].length();
                int maxPowerJWidth = headers[2].length();
                int maxPowerUWidth = headers[3].length();

                for (int i = 0; i < graph.time.length; i++) {
                    maxTimeWidth = Math.max(maxTimeWidth, String.valueOf(graph.time[i]).length());
                    maxPowerWidth = Math.max(maxPowerWidth, String.valueOf(graph.power[i]).length());
                    maxPowerJWidth = Math.max(maxPowerJWidth, String.valueOf(graph.powerJ[i]).length());
                    maxPowerUWidth = Math.max(maxPowerUWidth, String.valueOf(graph.powerU[i]).length());
                }

                // Adjust widths for spacing and delimiter
                maxTimeWidth += 4;  // 2 spaces before and 2 spaces after
                maxPowerWidth += 4;
                maxPowerJWidth += 4;
                maxPowerUWidth += 4;

                // Write headers
                writer.write(String.format("%-" + maxTimeWidth + "s| %-" + maxPowerWidth + "s| %-" + maxPowerJWidth + "s| %-" + maxPowerUWidth + "s\n",
                        headers[0], headers[1], headers[2], headers[3]));

                // Write data rows
                for (int i = 0; i < graph.time.length; i++) {
                    writer.write(String.format("%-" + maxTimeWidth + "s| %-" + maxPowerWidth + "s| %-" + maxPowerJWidth + "s| %-" + maxPowerUWidth + "s\n",
                            String.valueOf(graph.time[i]),
                            String.valueOf(graph.power[i]),
                            String.valueOf(graph.powerJ[i]),
                            String.valueOf(graph.powerU[i])));
                }
                JOptionPane.showMessageDialog(this, "Dataset saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving dataset: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Checks if a string is a valid double.
     *
     * @param str the string to check
     * @return true if the string is a valid double, false otherwise
     */
    private boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Listener for the "Use Initial Values" checkbox.
     */
    ActionListener checkBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (useInitialValues.isSelected()) {
                textField1.setText("2024.0");
                textField2.setText("10.0");
                textField3.setText("10.0");
                textField4.setText("0.01");
                textField5.setText("2000.0");
                textField6.setText("1.0");

                textField1.setEditable(false);
                textField2.setEditable(false);
                textField3.setEditable(false);
                textField4.setEditable(false);
                textField5.setEditable(false);
                textField6.setEditable(false);
            } else {
                textField1.setEditable(true);
                textField2.setEditable(true);
                textField3.setEditable(true);
                textField4.setEditable(true);
                textField5.setEditable(true);
                textField6.setEditable(true);
            }
        }
    };

    /**
     * Listener for the "Start" button.
     */
    ActionListener startButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean valid = true;
            StringBuilder warningMessage = new StringBuilder("Te pola mają zły format danych:\n");

            if (!isValidDouble(textField1.getText())) {
                valid = false;
                warningMessage.append("- V\n");
            }
            if (!isValidDouble(textField2.getText())) {
                valid = false;
                warningMessage.append("- a_0\n");
            }
            if (!isValidDouble(textField3.getText())) {
                valid = false;
                warningMessage.append("- c_g\n");
            }
            if (!isValidDouble(textField4.getText())) {
                valid = false;
                warningMessage.append("- A\n");
            }
            if (!isValidDouble(textField5.getText())) {
                valid = false;
                warningMessage.append("- w\n");
            }
            if (!isValidDouble(textField6.getText())) {
                valid = false;
                warningMessage.append("- n\n");
            }

            if (valid) {
                double V = Double.parseDouble(textField1.getText());
                double w = Double.parseDouble(textField5.getText());
                if (w >= V) {
                    valid = false;
                    warningMessage.append("Wartość w nie może być równa lub większa od V\n");
                }
            }

            if (!valid) {
                JOptionPane.showMessageDialog(Ui.this, warningMessage.toString(), "Niepoprawny input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            graph.createGraph();
            double[] power = graph.power;
            double[] powerJ = graph.powerJ;
            double[] powerU = graph.powerU;

            dataset.removeAllSeries();
            XYSeries totalPowerSeries = new XYSeries("Moc Fizjologiczna");
            XYSeries joulePowerSeries = new XYSeries("Moc Jałowa");
            XYSeries usefulPowerSeries = new XYSeries("Moc Użytkowa");

            Color blue = Color.BLUE;
            Color red = Color.RED;
            Color green = Color.GREEN;

            for (int i = 0; i < Graph.L; i++) {
                totalPowerSeries.add(graph.time[i], power[i]);
                joulePowerSeries.add(graph.time[i], powerJ[i]);
                usefulPowerSeries.add(graph.time[i], powerU[i]);
            }

            dataset.addSeries(totalPowerSeries);
            chart.getXYPlot().getRenderer().setSeriesPaint(dataset.getSeriesCount() - 1, blue);
            dataset.addSeries(joulePowerSeries);
            chart.getXYPlot().getRenderer().setSeriesPaint(dataset.getSeriesCount() - 1, red);
            dataset.addSeries(usefulPowerSeries);
            chart.getXYPlot().getRenderer().setSeriesPaint(dataset.getSeriesCount() - 1, green);
            repaint();

            equationLabel1.setText("Praca Fizjologiczna = " + Graph.integral);
            equationLabel1.setForeground(blue);
            equationLabel1.setOpaque(true);
            equationLabel1.setBackground(Color.GRAY);
            equationLabel2.setText("Praca Jałowa = " + Graph.integralJ);
            equationLabel2.setForeground(red);
            equationLabel2.setOpaque(true);
            equationLabel2.setBackground(Color.GRAY);
            equationLabel3.setText("Praca Użytkowa= " + Graph.integralU);
            equationLabel3.setForeground(green);
            equationLabel3.setOpaque(true);
            equationLabel3.setBackground(Color.GRAY);
        }
    };

    /**
     * Sets up the chart for displaying the power data.
     */
    private void setupChart() {
        dataset = new XYSeriesCollection();

        chart = ChartFactory.createXYLineChart(
                "Power Graph",
                "Time",
                "Power",
                dataset
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
    }

    /**
     * Constructs the user interface for the application.
     */
    public Ui() {
        setTitle("Graph UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem startMenuItem = new JMenuItem("Start");
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        fileMenu.add(startMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save Graph");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGraph();
            }
        });
        fileMenu.add(saveMenuItem);

        JMenuItem saveDatasetMenuItem = new JMenuItem("Save Dataset");
        saveDatasetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataset();
            }
        });
        fileMenu.add(saveDatasetMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        setupChart();

        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();

        int textFieldWidth = 20;
        textField1.setColumns(textFieldWidth);
        textField2.setColumns(textFieldWidth);
        textField3.setColumns(textFieldWidth);
        textField4.setColumns(textFieldWidth);
        textField5.setColumns(textFieldWidth);
        textField6.setColumns(textFieldWidth);

        JPanel textBoxPanel = new JPanel(new GridLayout(6, 3, 5, 5));
        textBoxPanel.setPreferredSize(new Dimension(400, 100));

        JLabel label1 = new JLabel("V:", SwingConstants.RIGHT);
        textBoxPanel.add(label1);
        textBoxPanel.add(textField1);
        textBoxPanel.add(new JLabel());

        JLabel label2 = new JLabel("a_0:", SwingConstants.RIGHT);
        textBoxPanel.add(label2);
        textBoxPanel.add(textField2);
        textBoxPanel.add(new JLabel());

        JLabel label3 = new JLabel("c_g:", SwingConstants.RIGHT);
        textBoxPanel.add(label3);
        textBoxPanel.add(textField3);
        textBoxPanel.add(new JLabel());

        JLabel label4 = new JLabel("A:", SwingConstants.RIGHT);
        textBoxPanel.add(label4);
        textBoxPanel.add(textField4);
        textBoxPanel.add(new JLabel());

        JLabel label5 = new JLabel("w:", SwingConstants.RIGHT);
        textBoxPanel.add(label5);
        textBoxPanel.add(textField5);
        textBoxPanel.add(new JLabel());

        JLabel label6 = new JLabel("n:", SwingConstants.RIGHT);
        textBoxPanel.add(label6);
        textBoxPanel.add(textField6);
        useInitialValues = new JCheckBox("Use Initial Values", true);
        textBoxPanel.add(useInitialValues);

        useInitialValues.addActionListener(checkBoxListener);

        checkBoxListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        JPanel equationPanel = new JPanel();
        equationPanel.setLayout(new GridLayout(3, 2, 5, 5));
        equationPanel.setBorder(BorderFactory.createTitledBorder("Obliczone Prace"));

        equationLabel1 = new JLabel("Praca Fizjologiczna=", SwingConstants.CENTER);
        equationLabel2 = new JLabel("Praca Jałowa =", SwingConstants.CENTER);
        equationLabel3 = new JLabel("Praca Użytkowa =", SwingConstants.CENTER);

        equationPanel.add(equationLabel1);
        equationPanel.add(equationLabel2);
        equationPanel.add(equationLabel3);

        JButton startButton = new JButton("Start");

        startButton.addActionListener(startButtonListener);
        JButton saveButton = new JButton("Save Graph");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGraph();
            }
        });

        JButton saveButton2 = new JButton("Save Data");
        saveButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataset();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(startButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveButton2);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(equationPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(chartPanel, BorderLayout.CENTER);
        add(textBoxPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.PAGE_END);

        setLocationRelativeTo(null);
        setVisible(true);

        graph = new Graph();
    }
    
    /**
     * Main method to launch the UI.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ui());
    }
}
