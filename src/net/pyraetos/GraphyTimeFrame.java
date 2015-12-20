package net.pyraetos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.pyraetos.util.Sys;

public class GraphyTimeFrame extends JFrame{

	private static JPanel inputPanel;
	private static JPanel graphPanel;
	private static JTextField functionField;
	private static JLabel functionLabel;
	private static JTextField stepField;
	private static JLabel stepLabel;
	private static JButton graphButton;
	
	private Point[] function;
	private Point center;
	private double step;
	
	public GraphyTimeFrame(){
		initWindow();
		initGraphPanel();
		initInputPanel();
		initFunctionLabel();
		initFunctionField();
		initStepLabel();
		initStepField();
		initGraphButton();
		setVisible(true);
	}
	
	private void initWindow(){
		function = null;
		center = new Point(0,0);
		step = 0.25;
		this.setLayout(null);
		this.setBounds(200, 100, 500, 600);
		this.setResizable(false);
		this.setTitle("GraphyTime");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void drawAxes(Graphics graphics){
		Graphics2D g = (Graphics2D)graphics;
		g.drawLine(200, 0, 200, 400);
		g.drawLine(0, 200, 400, 200);
		for(int x = 20; x < 400; x += 20){
			g.drawLine(x, 195, x, 205);
		}
		for(int y = 20; y < 400; y += 20){
			g.drawLine(195, y, 205, y);
		}
		if(function != null){
			g.setColor(Color.blue);
			g.setStroke(new BasicStroke(3));
			for(int i = 0; i < function.length - 1; i++){
				Point first = planeToFrame(function[i]);
				Point second = planeToFrame(function[i + 1]);
				g.drawLine((int)first.getX(), (int)first.getY(), (int)second.getX(), (int)second.getY());
			}
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
		inputPanel.setLayout(new GridLayout(0,2,0,10));
		inputPanel.setBounds(120,450,200,120);
		this.add(inputPanel);
	}
	
	private void initFunctionLabel(){
		functionLabel = new JLabel("                   f(x) = ");
		inputPanel.add(functionLabel);
	}
	
	private void initFunctionField(){
		functionField = new JTextField();
		functionField.setEditable(true);
		functionField.setBackground(Color.WHITE);
		inputPanel.add(functionField);
	}
	
	private void initStepLabel(){
		stepLabel = new JLabel("                   Step: ");
		inputPanel.add(stepLabel);
	}
	
	private void initStepField(){
		stepField = new JTextField("0.25");
		stepField.setEditable(true);
		stepField.setBackground(Color.WHITE);
		inputPanel.add(stepField);
	}
	
	private void initGraphButton(){
		inputPanel.add(Sys.space());
		graphButton = new JButton("Graph");
		graphButton.addActionListener((e) -> {
			String inputFunction = functionField.getText();
			Function function = FunctionParser.parse(inputFunction);
			loadStep();
			this.function = function.evaluate(-10, 10, step);
			repaint();
		});
		inputPanel.add(graphButton);
		inputPanel.add(Sys.space());
	}
	
	public Point planeToFrame(Point planePoint){
		return new Point(planePoint.getX() * 20 + 200, 200 - planePoint.getY() * 20);
	}
	
	public void loadStep(){
		this.step = Double.parseDouble(stepField.getText());
	}
	
	public static void main(String[] args){
		new GraphyTimeFrame();
	}

	
}
