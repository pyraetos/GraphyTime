package net.pyraetos;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.pyraetos.util.Sys;

public class GraphyTimeFrame extends JFrame{

	private static JPanel panel;
	private static JPanel inputPanel;
	private static JPanel graphPanel;
	private static JTextField functionField;
	private static JLabel functionLabel;
	private static JButton graphButton;
	
	private Point[] function;
	private Point min;
	private int length;
	
	public GraphyTimeFrame(){
		initWindow();
		initGraphPanel();
		initInputPanel();
		initFunctionLabel();
		initFunctionField();
		initGraphButton();
		setVisible(true);
	}
	
	private void initWindow(){
		function = null;
		min = new Point(-10, -10);
		length = 20;
		this.setLayout(null);
		this.setBounds(200, 100, 500, 525);
		this.setResizable(false);
		this.setTitle("GraphyTime");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void drawAxes(Graphics g){
		g.drawLine(200, 0, 200, 400);
		g.drawLine(0, 200, 400, 200);
		for(int x = 20; x < 400; x += 20){
			g.drawLine(x, 195, x, 205);
		}
		for(int y = 20; y < 400; y += 20){
			g.drawLine(195, y, 205, y);
		}
		if(function != null){
			
			function = null;
		}
	}
	
	private void initGraphPanel(){
		graphPanel = new JPanel(){
			@Override
			public void paint(Graphics g){
				g.setColor(Color.white);
				g.fillRect(0, 0, 400, 400);
				g.setColor(Color.black);
				drawAxes(g);
			}
		};
		graphPanel.setBounds(50, 25, 400, 400);
		this.add(graphPanel);
	}
	
	private void initInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		inputPanel.setBounds(100,450,300,25);
		this.add(inputPanel);
	}
	
	private void initFunctionLabel(){
		functionLabel = new JLabel("f(x) = ");
		inputPanel.add(functionLabel);
	}
	
	private void initFunctionField(){
		functionField = new JTextField("...");
		functionField.setEditable(false);
		functionField.setBackground(Color.WHITE);
		inputPanel.add(functionField);
		inputPanel.add(Sys.space());
	}
	
	private void initGraphButton(){
		graphButton = new JButton("Graph");
		graphButton.addActionListener((e) -> {
			String inputFunction = functionField.getText();
			Point[] function = FunctionParser.parse(inputFunction, min, length);
			if(function != null)
				this.function = function;
		});
		inputPanel.add(graphButton);
		inputPanel.add(Sys.space());
	}
	public static void main(String[] args){
		new GraphyTimeFrame();
	}

	
}
