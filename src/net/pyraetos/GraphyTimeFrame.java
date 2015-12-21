package net.pyraetos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.pyraetos.util.Images;
import net.pyraetos.util.Sys;

public class GraphyTimeFrame extends JFrame{

	private JPanel inputPanel;
	private JPanel graphPanel;
	private JLabel xAxisLabel;
	private JLabel yAxisLabel;
	private JTextField functionField;
	private JLabel functionLabel;
	private JTextField stepField;
	private JLabel stepLabel;
	private JTextField scaleField;
	private JLabel scaleLabel;
	private JButton graphButton;
	private JLabel statusLabel;
	
	private Point[] function;
	private double scale;
	private double step;
	
	public GraphyTimeFrame(){
		initWindow();
		initIcon();
		initGraphPanel();
		initAxesLabels();
		initInputPanel();
		initFunctionLabel();
		initFunctionField();
		initStepLabel();
		initStepField();
		initScaleLabel();
		initScaleField();
		initGraphButton();
		initStatusLabel();
		setVisible(true);
	}
	
	private void initWindow(){
		function = null;
		scale = 0;
		step = 0.0;
		this.setLayout(null);
		this.setBounds(200, 100, 530, 640);
		this.setResizable(false);
		this.setTitle("GraphyTime");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void initIcon(){
		Images.fromResource(this.getClass());
		this.setIconImage(Images.retrieve("graphytime.png"));
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
	}
	
	public void graph(Graphics2D g){
		statusLabel.setForeground(Color.black);
		statusLabel.setText("Ready to graph!");
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(3));
		for(int i = 0; i < function.length - 1; i++){
			if(!Double.isFinite(function[i].getY()))
				continue;
			if(!Double.isFinite(function[i+1].getY()))
				continue;
			Point first = planeToFrame(function[i]);
			Point second = planeToFrame(function[i + 1]);
			g.drawLine((int)first.getX(), (int)first.getY(), (int)second.getX(), (int)second.getY());
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
				if(function != null)
					graph((Graphics2D)g);
			}
		};
		graphPanel.setBounds(65, 25, 400, 400);
		this.add(graphPanel);
	}
	
	private void initAxesLabels(){
		xAxisLabel = new JLabel("x = 10.0");
		xAxisLabel.setBounds(470, 214, 55, 20);
		this.add(xAxisLabel);
		yAxisLabel = new JLabel("y = 10.0");
		yAxisLabel.setBounds(250, 3, 100, 20);
		this.add(yAxisLabel);
	}
	
	private void initInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(0,2,0,10));
		inputPanel.setBounds(135,450,200,150);
		inputPanel.setOpaque(false);
		this.add(inputPanel);
	}
	
	private void initStatusLabel(){
		statusLabel = new JLabel("Ready to graph!");
		statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 16));
		statusLabel.setBounds(200, 580, 500, 20);
		this.add(statusLabel);
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
		stepField = new JTextField("0.001");
		stepField.setEditable(true);
		stepField.setBackground(Color.WHITE);
		inputPanel.add(stepField);
	}
	
	private void initScaleLabel(){
		scaleLabel = new JLabel("                   Scale: ");
		inputPanel.add(scaleLabel);
	}
	
	private void initScaleField(){
		scaleField = new JTextField("10.0");
		scaleField.setEditable(true);
		scaleField.setBackground(Color.WHITE);
		inputPanel.add(scaleField);
	}
	
	private void initGraphButton(){
		inputPanel.add(Sys.space());
		graphButton = new JButton("Graph");
		graphButton.addActionListener((e) -> {
			String inputFunction = functionField.getText();
			try{
				Function function = FunctionParser.parse(inputFunction);
				loadStep();
				loadScale();
				this.function = function.evaluate(-scale, scale, step);
				repaint();
			}catch(ParseException pe){
				statusLabel.setForeground(Color.red);
				statusLabel.setText("Function syntax error!");
			}
		});
		inputPanel.add(graphButton);
		inputPanel.add(Sys.space());
	}
	
	public Point planeToFrame(Point planePoint){
		return new Point(planePoint.getX() * (200 / scale) + 200, 200 - planePoint.getY() * (200 / scale));
	}
	
	public void loadStep(){
		this.step = Double.parseDouble(stepField.getText());
	}
	
	public void loadScale(){
		this.scale = Double.parseDouble(scaleField.getText());
		this.xAxisLabel.setText("x = " + this.scale);
		this.yAxisLabel.setText("y = " + this.scale);
	}
	
	public static void main(String[] args){
		new GraphyTimeFrame();
	}

	
}
