package ui;

//Main author: Andrzej Brzostowicz
//Sub author: Jakub Fabiński

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ui extends JFrame implements ActionListener{
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6;
    private JCheckBox useInitialValue5, useInitialValue6;
    private JLabel equationLabel1, equationLabel2, equationLabel3;
    private JLabel equationValue1, equationValue2, equationValue3;
	private JPanel graphPanel;//this one is initiated earlier because we need it as a field in Ui.java and not in it's constructor


    public Ui() {
        // Set up the main frame
        setTitle("Graph UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());




        // Create a panel for the graph
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);//draws graph
            }
        };
        graphPanel.setBackground(Color.LIGHT_GRAY);
        graphPanel.setPreferredSize(new Dimension(500, 400));
        

        // Create text boxes for values
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();

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
        textBoxPanel.setPreferredSize(new Dimension(400,100));
        
        //makes the labels and adds them to the textBoxanel
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

        // Add check box for Field 5 and set it to be selected by default
        useInitialValue5 = new JCheckBox("Use Initial Value", true);
     
        textBoxPanel.add(useInitialValue5);//checkboxes are added right beside their respective text fields

        JLabel label6 = new JLabel("n:", SwingConstants.RIGHT);
        textBoxPanel.add(label6);
        textBoxPanel.add(textField6);

        // Add check box for Field 6 and set it to be selected by default
        useInitialValue6 = new JCheckBox("Use Initial Value", true);;        

        textBoxPanel.add(useInitialValue6);

        // Add an ActionListener to handle changes in check box state

        useInitialValue5.addActionListener(checkBoxListener5);
        useInitialValue6.addActionListener(checkBoxListener6);
        
        checkBoxListener5.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)); //these lines make the checkboxes activated at launch  
        checkBoxListener6.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)); //in a way their action listeners notice and activate


        // Create a panel for equations and their values
        JPanel equationPanel = new JPanel();
        equationPanel.setLayout(new GridLayout(3, 2, 5, 5));
        equationPanel.setBorder(BorderFactory.createTitledBorder("Obliczone Prace"));

        //makes the labels for equations and positions them
        equationLabel1 = new JLabel("Praca Fizjologiczna=", SwingConstants.RIGHT);
        equationLabel2 = new JLabel("Praca Jałowa =", SwingConstants.RIGHT);
        equationLabel3 = new JLabel("Praca Dyspozycyjna =", SwingConstants.RIGHT);

        equationValue1 = new JLabel("", SwingConstants.LEFT);
        equationValue2 = new JLabel("", SwingConstants.LEFT);
        equationValue3 = new JLabel("", SwingConstants.LEFT);

        //adds labels to the panel
        equationPanel.add(equationLabel1);
        equationPanel.add(equationValue1);
        equationPanel.add(equationLabel2);
        equationPanel.add(equationValue2);
        equationPanel.add(equationLabel3);
        equationPanel.add(equationValue3);

        // Create a start button
        JButton startButton = new JButton("Start");
        JButton saveButton = new JButton("Save");
        
        // Create a panel for the start button with right alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(startButton);
        buttonPanel.add(saveButton);
        // Create a panel for bottom components using BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(equationPanel, BorderLayout.CENTER);//adds equation panel to the bottom panel for better layout
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);//adds button panel to the bottom panel

        //adds main panels to the frame
        add(graphPanel, BorderLayout.CENTER);
        add(textBoxPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.PAGE_END);


        // Display the frame
        setLocationRelativeTo(null);//locates the frame in the center of the screen  
   

    }
    

    
    public static void main(String[] args) {
        Ui frame = new Ui();
        frame.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	

}
	//action listeners for checkboxes 
    ActionListener checkBoxListener5 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (useInitialValue5.isSelected()) { //checks if checkbox is selected
                textField5.setText("Initial Value w"); //sets default text
            	textField5.setEditable(false); //locks the text field
            } else {
            	textField5.setEditable(true);
                textField5.setText("");            	
            }
        }
    };
    ActionListener checkBoxListener6 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (useInitialValue6.isSelected()) {
                textField6.setText("Initial Value n");
            	textField6.setEditable(false);
            } else {
            	textField6.setEditable(true);
                textField6.setText("");            	
            }
        }
        
    };
    
    //function for drawing the graph, provided data set
    private void drawGraph(Graphics g) {
        int[] data = {50, 100, 200, 150, 300, 250}; // Sample data
        Graphics2D g2d = (Graphics2D) g;

        int width =  this.graphPanel.getWidth();//adjusts itself to the graphPanel, hence we need it as a field in Ui and not the constructor
        int height =  this.graphPanel.getHeight();

        // Drawing x-axis
        g2d.drawLine(50, height - 50, width - 50, height - 50);

        // Drawing y-axis
        g2d.drawLine(50, height - 50, 50, 50);

        // Drawing data points and connecting lines
        int xInterval = (width - 100) / (data.length - 1);
        g2d.setColor(Color.RED);
        for (int i = 0; i < data.length - 1; i++) {
            int x1 = 50 + i * xInterval;
            int y1 = height - 50 - data[i];
            int x2 = 50 + (i + 1) * xInterval;
            int y2 = height - 50 - data[i + 1];
            g2d.drawLine(x1, y1, x2, y2);//draws the line between points
            g2d.fillOval(x1 - 2, y1 - 2, 4, 4); // Draw data points as small circles
            g2d.fillOval(x2 - 2, y2 - 2, 4, 4);
        }
    };
}