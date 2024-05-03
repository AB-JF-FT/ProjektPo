package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Graphical User Interface for displaying a graph and allowing user input.
 * <p>
 * Authors:
 * Main author: Andrzej Brzostowicz
 * Sub author: Jakub Fabiński
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
    public JPanel graphPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events
    }

    /**
     * ActionListener for the checkbox.
     */
    ActionListener checkBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (useInitialValues.isSelected()) {
                textField1.setText("1.0");
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


    private void saveGraph() {
        // Create a buffered image to hold the graph panel content
        BufferedImage image = new BufferedImage(graphPanel.getWidth(), graphPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        graphPanel.paint(graphics2D);
        graphics2D.dispose();

        // Prompt the user to choose a file location to save the image
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Graph");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                // Save the buffered image as a PNG file
                ImageIO.write(image, "png", fileToSave);
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
                // Add action for the Start menu item
                // For example: startProcess();
            }
        });
        fileMenu.add(startMenuItem);

        // Create "Save" menu item and add it to the "File" menu
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGraph(); // Call saveGraph() method when the Save menu item is clicked
            }
        });
        fileMenu.add(saveMenuItem);
        
        // Add the "File" menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar to this frame
        setJMenuBar(menuBar);
        
        // Create a panel for the graph
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                double[] data = new double[10];
                Random random = new Random();

                for (int i = 0; i < data.length; i++) {
                    data[i] = random.nextDouble() * 100; // Generates random doubles between 0 and 100
                }

                drawGraph(g, data);
            }
        };
        graphPanel.setBackground(Color.LIGHT_GRAY);
        graphPanel.setPreferredSize(new Dimension(500, 400));

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
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGraph(); // Call saveGraph() method when the Save button is clicked
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
        add(graphPanel, BorderLayout.CENTER);
        add(textBoxPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.PAGE_END);

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Draws the graph using the provided data set.
     *
     * @param g the Graphics object to draw on
     */
    private void drawGraph(Graphics g, double[] data) {
        Graphics2D g2d = (Graphics2D) g;

        int width = this.graphPanel.getWidth();
        int height = this.graphPanel.getHeight();

        // Drawing x-axis
        g2d.drawLine(50, height - 50, width - 50, height - 50);

        // Drawing y-axis
        g2d.drawLine(50, height - 50, 50, 50);

        // Find the maximum and minimum values in the data set
        double maxValue = Double.MIN_VALUE;
        double minValue = Double.MAX_VALUE;
        for (double value : data) {
            if (value > maxValue) {
                maxValue = value;
            }
            if (value < minValue) {
                minValue = value;
            }
        }

        // Calculate scaling factors
        double xScale = (width - 100) / (data.length - 1);
        double yScale = (height - 100) / (maxValue - minValue);

        g2d.setColor(Color.RED);
        for (int i = 0; i < data.length - 1; i++) {
            double x1 = 50 + i * xScale;
            double y1 = height - 50 - (data[i] - minValue) * yScale;
            double x2 = 50 + (i + 1) * xScale;
            double y2 = height - 50 - (data[i + 1] - minValue) * yScale;
            g2d.draw(new Line2D.Double(x1, y1, x2, y2)); // Draw line using Line2D.Double
            g2d.fillOval((int) (x1 - 2), (int) (y1 - 2), 4, 4); // Draw data points as small circles
            g2d.fillOval((int) (x2 - 2), (int) (y2 - 2), 4, 4);
        }
    }

    /**
     * Launches the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ui());
    }
}
