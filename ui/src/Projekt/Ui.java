package Projekt;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
     * Handles action events.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events
    }

    /**
     * Saves the graph as an image.
     */
    private void saveGraph() {
        // Create a buffered image to hold the graph panel content
        BufferedImage image = new BufferedImage(chartPanel.getWidth(), chartPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        chartPanel.paint(graphics2D);
        graphics2D.dispose();

        // Prompt the user to choose a file location to save the image
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Graph");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG files", "jpg")); // Set filter to JPG files only
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                // Append ".jpg" extension if not provided
                if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".jpg")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".jpg");
                }
                // Save the buffered image as a JPG file
                javax.imageio.ImageIO.write(image, "jpg", fileToSave);
                JOptionPane.showMessageDialog(this, "Graph saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving graph: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Constructs the UI.
     */
    public Ui() {
        // Set up the main frame
        setTitle("Graph UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create "Start" menu item and add it to the "File" menu
        JMenuItem startMenuItem = new JMenuItem("Start");
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        fileMenu.add(startMenuItem);

        // Create "Save" menu item and add it to the "File" menu
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGraph();
            }
        });
        fileMenu.add(saveMenuItem);

        // Add the "File" menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar to this frame
        setJMenuBar(menuBar);

        // Set up the chart
        setupChart();

        // Create text boxes for values
        textField1 = new JTextField("initial value");
        textField2 = new JTextField("initial value");
        textField3 = new JTextField("initial value");
        textField4 = new JTextField("initial value");
        textField5 = new JTextField("initial value");
        textField6 = new JTextField("initial value");

        // Make text boxes wider
        int textFieldWidth = 20;
        textField1.setColumns(textFieldWidth);
        textField2.setColumns(textFieldWidth);
        textField3.setColumns(textFieldWidth);
        textField4.setColumns(textFieldWidth);
        textField5.setColumns(textFieldWidth);
        textField6.setColumns(textFieldWidth);

        // Create a panel for text boxes
        JPanel textBoxPanel = new JPanel(new GridLayout(6, 3, 5, 5));
        textBoxPanel.setPreferredSize(new Dimension(400, 100));

        // Create labels for text boxes and add them to the panel
        JLabel label1 = new JLabel("V:", SwingConstants.RIGHT);
        textBoxPanel.add(label1);
        textBoxPanel.add(textField1);
        textBoxPanel.add(new JLabel()); // Empty label for spacing

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
        textBoxPanel.add(useInitialValues); // Add checkbox for text Fields

        // Add action listeners to checkboxes
        useInitialValues.addActionListener(checkBoxListener);

        // Set default values for checkboxes
        checkBoxListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Create a panel for equations and their values
        JPanel equationPanel = new JPanel();
        equationPanel.setLayout(new GridLayout(3, 2, 5, 5));
        equationPanel.setBorder(BorderFactory.createTitledBorder("Obliczone Prace"));

        // Create labels for equations and add them to the panel
        equationLabel1 = new JLabel("Praca Fizjologiczna=", SwingConstants.CENTER);
        equationLabel2 = new JLabel("Praca Jałowa =", SwingConstants.CENTER);
        equationLabel3 = new JLabel("Praca Dyspozycyjna =", SwingConstants.CENTER);

        equationPanel.add(equationLabel1);
        equationPanel.add(equationLabel2);
        equationPanel.add(equationLabel3);

        // Create start and save buttons
        JButton startButton = new JButton("Start");

        startButton.addActionListener(startButtonListener);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGraph();
            }
        });

        // Create a panel for the buttons with right alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(startButton);
        buttonPanel.add(saveButton);

        // Create a panel for bottom components using BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(equationPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panels to the frame
        add(chartPanel, BorderLayout.CENTER);
        add(textBoxPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.PAGE_END);

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);

        // Create an instance of the Graph class
        graph = new Graph();
    }

    /**
     * ActionListener for the checkbox.
     */
    ActionListener checkBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (useInitialValues.isSelected()) {
                textField1.setText("2.0");
                textField2.setText("1.0");
                textField3.setText("1.0");
                textField4.setText("1.0");
                textField5.setText("1.0");
                textField6.setText("1.0");

                textField1.setEditable(false);
                textField2.setEditable(false);
                textField3.setEditable(false);
                textField4.setEditable(false);
                textField5.setEditable(false);
                textField6.setEditable(false);
            } else {
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
                textField5.setText("");
                textField6.setText("");

                textField1.setEditable(true);
                textField2.setEditable(true);
                textField3.setEditable(true);
                textField4.setEditable(true);
                textField5.setEditable(true);
                textField6.setEditable(true);
            }

        }
    };

    // ActionListener for the start button
    ActionListener startButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Generate graph data from the Graph class
            graph.createGraph();
            // Get the graph data arrays
            double[] power = graph.power;
            double[] powerJ = graph.powerJ;
            double[] powerU = graph.powerU;
            // Clear the dataset
            dataset.removeAllSeries();
            // Add new series to the dataset
            XYSeries totalPowerSeries = new XYSeries("Total Power");
            XYSeries joulePowerSeries = new XYSeries("Joule Power");
            XYSeries usefulPowerSeries = new XYSeries("Useful Power");
            for (int i = 0; i < Graph.L; i++) {
                totalPowerSeries.add(graph.time[i], power[i]);
                joulePowerSeries.add(graph.time[i], powerJ[i]);
                usefulPowerSeries.add(graph.time[i], powerU[i]);
            }
            dataset.addSeries(totalPowerSeries);
            dataset.addSeries(joulePowerSeries);
            dataset.addSeries(usefulPowerSeries);
        }
    };

    /**
     * Creates and sets up the JFreeChart for graph display.
     */
    private void setupChart() {
        // Create a dataset for the graph
        dataset = new XYSeriesCollection();

        // Create the chart
        chart = ChartFactory.createXYLineChart(
                "Power Graph", // Title
                "Time", // X-axis label
                "Power", // Y-axis label
                dataset // Dataset
        );

        // Customize the chart...

        // Create a chart panel to display the chart
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ui());
    }
}
