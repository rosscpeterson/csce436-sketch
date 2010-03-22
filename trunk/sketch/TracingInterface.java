// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// TracingInterface.java

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TracingInterface
{
	// Constants
	public final static int WINDOW_LOCATION_X = 25;
	public final static int WINDOW_LOCATION_Y = 25;
	public final static int WINDOW_SIZE_X = 700;
	public final static int WINDOW_SIZE_Y = 700;

	// Member variables
	private JFrame window, analyzeWindow, printWindow, modifyWindow;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem clear, quit;
	private JMenu color;
	private JMenuItem blue, black, red, green, yellow, orange, magenta;
	private JMenu analyze;
	private JMenuItem analyzeStroke;
	private JMenu modify;
	private JMenuItem modifyStroke;
	private JButton analyzeButton, singleButton, modifyButton;
	private JCheckBox selectAll;
	private JLabel titleLabel;
	private JTextArea leftText, rightText;
	private PaintWindow panel;
	private int curr_x;
	private int curr_y;
	private Color curr_color;
	private ArrayList<Stroke> strokes;
	private ArrayList<Stroke> modifiedStrokes;
	private int current_stroke, analysis_index;
	
	// Checkboxes for analysis
	JCheckBox singleStrokeLengths, allStrokeLength, minStrokeLength, maxStrokeLength,
	          meanStrokeLength, numStrokes, endToEndLengths, averageDistanceBetween, cosStartingAngle,
	          sineStartingAngle, lengthOfDiagonal, angleOfDiagonal, strokeDistance,
	          cosEndingAngle, sinEndingAngle, smoothness, maximumSpeedSquared, longestPause,
	          minimumSpeedSquared, averageSpeedSquared, strokeTime, aspect, curvature,
	          density1, density2, openness, areaOfBox, logAreaOfBox, logTotalLength, logOfCurviness;
	
	// Checkboxes for modify
	JCheckBox randomMessy, accelerationMessy, boundingBoxSubStrokes, lowerBoundShaded, upperBoundShaded,
	          makeRigid, ribbonEffect, diamondBasedStroke, subStrokesWithMinSpeed, subStrokesWithMaxSpeed,
	          connectBeginToEndOfAllStrokes, connectBeginToEndOfAllStrokesOnly, fastStrokesOnly,
	          slowStrokesOnly, elongateStroke, makeDashed, mirrorVerticalStroke, mirrorHorizontalStroke,
	          lsdEffect, connectAllStrokes;
			
	// Constructor
	public TracingInterface()
	{
		// Initializing values for the first stroke
		curr_x = 0;
		curr_y = 0;
		curr_color = Color.black;
		strokes = new ArrayList<Stroke>();
		strokes.add(new Stroke(curr_color));
		modifiedStrokes = new ArrayList<Stroke>();
		current_stroke = analysis_index = 0;

		// Set up the window
		window = new JFrame("Tracing Interface");
		window.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		file = new JMenu("File");
		color = new JMenu("Color");
		analyze = new JMenu("Analyze");
		modify = new JMenu("Modify");
		menuBar.add(file);
		menuBar.add(color);
		menuBar.add(analyze);
		menuBar.add(modify);

		// Set up the file menu
		clear = new JMenuItem("Clear");
		quit = new JMenuItem("Quit");
		file.add(clear);
		file.add(quit);

		// Set up the color menu
		blue = new JMenuItem("Blue");
		black = new JMenuItem("Black");
		red = new JMenuItem("Red");
		green = new JMenuItem("Green");
		yellow = new JMenuItem("Yellow");
		orange = new JMenuItem("Orange");
		magenta = new JMenuItem("Magenta");
		color.add(blue);
		color.add(black);
		color.add(red);
		color.add(green);
		color.add(yellow);
		color.add(orange);
		color.add(magenta);
		
		// Set up the analyze and modify menu
		analyzeStroke = new JMenuItem("Analyze Stroke");
		analyze.add(analyzeStroke);
		modifyStroke = new JMenuItem("Modify Stroke");
		modify.add(modifyStroke);

		// Add the actionListeners
		MenuListener menulistener = new MenuListener();
		quit.addActionListener(menulistener);
		clear.addActionListener(menulistener);
		analyzeStroke.addActionListener(menulistener);
		modifyStroke.addActionListener(menulistener);

		ColorListener colorlistener = new ColorListener();
		blue.addActionListener(colorlistener);
		black.addActionListener(colorlistener);
		red.addActionListener(colorlistener);
		green.addActionListener(colorlistener);
		yellow.addActionListener(colorlistener);
		orange.addActionListener(colorlistener);
		magenta.addActionListener(colorlistener);

		// Create the panel with its own listeners
		panel = new PaintWindow();
		panel.setBackground(Color.white);
		panel.setOpaque(true);
		panel.setDoubleBuffered(true);
		panel.addMouseMotionListener(new PaintListener());
		panel.addMouseListener(new MouseUpListener());

		// Add the menu bar and the panel to the window
		window.setJMenuBar(menuBar);
		window.add(panel);

		// Display the window
		window.setVisible(true);
		window.setResizable(false);
	}

	// Listener for various menu options
	private class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == quit)
				System.exit(0);
			else if (e.getSource() == clear)
				panel.clearPaintWindow();
			else if (e.getSource() == analyzeStroke)
				chooseAnalysis();
			else if (e.getSource() == analyzeButton)
			{
				analyzeWindow.setVisible(false);
				analysis_index = 0;
				printAnalysis();
				
			} else if (e.getSource() == modifyStroke)
			{
				chooseModify();
			} else if (e.getSource() == singleButton)
			{
				if (analysis_index < strokes.size() - 1)
				{
					printSingleAnalysis(analysis_index);
					analysis_index++;
				} else
				{
					printWindow.setVisible(false);
				}
			} else if (e.getSource() == modifyButton)
			{
				modifyWindow.setVisible(false);
				modifyStrokes();
		    }else if (e.getSource() == selectAll)
			{
				// Check All the boxes
				if (selectAll.isSelected())
				{
					longestPause.setSelected(true);
					singleStrokeLengths.setSelected(true);
					allStrokeLength.setSelected(true);
					minStrokeLength.setSelected(true);
					maxStrokeLength.setSelected(true);
			        meanStrokeLength.setSelected(true);
			        numStrokes.setSelected(true);
			        endToEndLengths.setSelected(true);
			        averageDistanceBetween.setSelected(true);
			        cosStartingAngle.setSelected(true);
			        sineStartingAngle.setSelected(true);
			        lengthOfDiagonal.setSelected(true);
			        angleOfDiagonal.setSelected(true);
			        strokeDistance.setSelected(true);
			        cosEndingAngle.setSelected(true);
			        sinEndingAngle.setSelected(true);
			        smoothness.setSelected(true);
			        maximumSpeedSquared.setSelected(true);
			        minimumSpeedSquared.setSelected(true);
			        averageSpeedSquared.setSelected(true);
			        strokeTime.setSelected(true);
			        aspect.setSelected(true);
			        curvature.setSelected(true);
			        density1.setSelected(true);
			        density2.setSelected(true);
			        openness.setSelected(true);
			        areaOfBox.setSelected(true);
			        logAreaOfBox.setSelected(true);
			        logTotalLength.setSelected(true);
			        logOfCurviness.setSelected(true);
				} 
				
				// Uncheck All the boxes
				else
				{
					longestPause.setSelected(false);
					singleStrokeLengths.setSelected(false);
					allStrokeLength.setSelected(false);
					minStrokeLength.setSelected(false);
					maxStrokeLength.setSelected(false);
			        meanStrokeLength.setSelected(false);
			        numStrokes.setSelected(false);
			        endToEndLengths.setSelected(false);
			        averageDistanceBetween.setSelected(false);
			        cosStartingAngle.setSelected(false);
			        sineStartingAngle.setSelected(false);
			        lengthOfDiagonal.setSelected(false);
			        angleOfDiagonal.setSelected(false);
			        strokeDistance.setSelected(false);
			        cosEndingAngle.setSelected(false);
			        sinEndingAngle.setSelected(false);
			        smoothness.setSelected(false);
			        maximumSpeedSquared.setSelected(false);
			        minimumSpeedSquared.setSelected(false);
			        averageSpeedSquared.setSelected(false);
			        strokeTime.setSelected(false);
			        aspect.setSelected(false);
			        curvature.setSelected(false);
			        density1.setSelected(false);
			        density2.setSelected(false);
			        openness.setSelected(false);
			        areaOfBox.setSelected(false);
			        logAreaOfBox.setSelected(false);
			        logTotalLength.setSelected(false);
			        logOfCurviness.setSelected(false);	
				}
			}
		}
	}

	// Listener for the color options
	private class ColorListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == blue)
				curr_color = Color.blue;
			else if (e.getSource() == black)
				curr_color = Color.black;
			else if (e.getSource() == red)
				curr_color = Color.red;
			else if (e.getSource() == green)
				curr_color = Color.green;
			else if (e.getSource() == yellow)
				curr_color = Color.yellow;
			else if (e.getSource() == orange)
				curr_color = Color.orange;
			else if (e.getSource() == magenta)
				curr_color = Color.magenta;
			strokes.get(current_stroke).setColor(curr_color);
		}
	}

	// PaintWindow class
	private class PaintWindow extends JPanel
	{
		public PaintWindow()
		{
			super();
		}

		// Paints the components in the window
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if (modifiedStrokes.size() == 0 && strokes.size() == 0);
			else if (modifiedStrokes.size() == 0)
				drawInitialStrokes(g);
			else
				drawModifiedStrokes(g);
		}
		
		// Draws all the initial strokes
		public void drawInitialStrokes(Graphics g)
		{
			Graphics2D g2d = (Graphics2D)g;
			for(Stroke stroke : strokes)
			{
				g2d.setColor(stroke.getColor());
				for(int i = 0; i < stroke.getSize()-1; i++)
				{
				    g2d.setStroke(new BasicStroke(3));
					g2d.drawLine((int)stroke.getPoint(i).getX(), (int)stroke.getPoint(i).getY(),
							   (int)stroke.getPoint(i+1).getX(), (int)stroke.getPoint(i+1).getY());
				}
			}
		}

		// Draws all the strokes in "modifiedStrokes". Used for changing personas.
		public void drawModifiedStrokes(Graphics g)
		{
			Graphics2D g2d = (Graphics2D)g;
			for(Stroke modifiedStroke : modifiedStrokes)
			{
				g2d.setColor(modifiedStroke.getColor());
				for(int i = 0; i < modifiedStroke.getSize()-1; i++)
				{
					g2d.setStroke(new BasicStroke(3));
					g2d.drawLine((int)modifiedStroke.getPoint(i).getX(), (int)modifiedStroke.getPoint(i).getY(),
							   (int)modifiedStroke.getPoint(i+1).getX(), (int)modifiedStroke.getPoint(i+1).getY());
				}
			}
			modifiedStrokes = new ArrayList<Stroke>();
		}

		// Clears all strokes in the window
		public void clearPaintWindow()
		{
			strokes = new ArrayList<Stroke>();
			strokes.add(new Stroke(curr_color));
			current_stroke = 0;
			modifiedStrokes = new ArrayList<Stroke>();
			this.repaint();
		}
	}
	
	// Modifies the strokes and repaints them
	public void modifyStrokes()
	{
		// Populate modified strokes with the initial strokes
		modifiedStrokes = new ArrayList<Stroke>();
		
		// Apply modifications
		for (int i = 0; i < strokes.size(); i++)
		{
			Stroke tempStroke = strokes.get(i);
			if (randomMessy.isSelected())
				tempStroke = Tools.randomMessy(tempStroke);
			if (accelerationMessy.isSelected())
				tempStroke = Tools.accelerationMessy(tempStroke);
			if (makeRigid.isSelected())
				tempStroke = Tools.makeRigid(tempStroke);
			if (subStrokesWithMinSpeed.isSelected())
				tempStroke = Tools.subStrokesWithMinSpeed(tempStroke);
			if (subStrokesWithMaxSpeed.isSelected())
				tempStroke = Tools.subStrokesWithMaxSpeed(tempStroke);
			if (elongateStroke.isSelected())
				tempStroke = Tools.elongateStroke(tempStroke);
			if (boundingBoxSubStrokes.isSelected())
				tempStroke = Tools.boundingBoxSubStrokes(tempStroke);
			if (lowerBoundShaded.isSelected())
				tempStroke = Tools.lowerBoundShaded(tempStroke);
			if (upperBoundShaded.isSelected())
				tempStroke = Tools.upperBoundShaded(tempStroke);
			if (ribbonEffect.isSelected())
				tempStroke = Tools.ribbonEffect(tempStroke);
			if (diamondBasedStroke.isSelected())
				tempStroke = Tools.diamondBasedStroke(tempStroke);
			if (mirrorVerticalStroke.isSelected())
				tempStroke = Tools.mirrorVerticalStroke(tempStroke, WINDOW_SIZE_Y);
			if (mirrorHorizontalStroke.isSelected())
				tempStroke = Tools.mirrorHorizontalStroke(tempStroke, WINDOW_SIZE_X);
			modifiedStrokes.add(tempStroke);
		}
		
		// Multiple stroke modifications
		if (fastStrokesOnly.isSelected())
			modifiedStrokes = Tools.fastStrokesOnly(modifiedStrokes);
		if (slowStrokesOnly.isSelected())
			modifiedStrokes = Tools.slowStrokesOnly(modifiedStrokes);
		if (connectBeginToEndOfAllStrokes.isSelected())
			modifiedStrokes = Tools.connectBeginToEndOfAllStrokes(modifiedStrokes);
		if (connectBeginToEndOfAllStrokesOnly.isSelected())
			modifiedStrokes = Tools.connectBeginToEndOfAllStrokesOnly(modifiedStrokes);
		if (makeDashed.isSelected())
			modifiedStrokes = Tools.makeAllDashed(modifiedStrokes);
		if (lsdEffect.isSelected())
			modifiedStrokes = Tools.lsdEverything(modifiedStrokes);
		
		// Repaint the window
		panel.repaint();
	}
	
	// Lets the user choose their modification options
	public void chooseModify()
	{
		// Set up window
		modifyWindow = new JFrame("Modify Stroke");
		modifyWindow.setSize(450, 350);
		modifyWindow.setLocation(100, 50);
		modifyWindow.setResizable(false);
		modifyWindow.setVisible(true);
		
		modifyWindow.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		modifyWindow.add(leftPanel);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		modifyWindow.add(rightPanel);
		
		// Choices
		randomMessy = new JCheckBox("Random Messiness");
		leftPanel.add(randomMessy);
		accelerationMessy = new JCheckBox("Acceleration Messiness");
		leftPanel.add(accelerationMessy);
		boundingBoxSubStrokes = new JCheckBox("Alter Bounding Box");
		leftPanel.add(boundingBoxSubStrokes);
		lowerBoundShaded = new JCheckBox("Shade the Lower Bound");
		leftPanel.add(lowerBoundShaded);
		upperBoundShaded = new JCheckBox("Shade the Upper Bound");
		leftPanel.add(upperBoundShaded);
		makeRigid = new JCheckBox("Make Rigid");
		leftPanel.add(makeRigid);
		ribbonEffect = new JCheckBox("Ribbon Effect");
		leftPanel.add(ribbonEffect);
		diamondBasedStroke = new JCheckBox("Diamond-Based Stroke");
		leftPanel.add(diamondBasedStroke);
		subStrokesWithMinSpeed = new JCheckBox("Sub-Strokes, Min Speed");
		leftPanel.add(subStrokesWithMinSpeed);
		subStrokesWithMaxSpeed = new JCheckBox("Sub-Strokes, Max Speed");
		leftPanel.add(subStrokesWithMaxSpeed);
		connectBeginToEndOfAllStrokes = new JCheckBox("Connect Begin-to-End");
		rightPanel.add(connectBeginToEndOfAllStrokes);
		connectBeginToEndOfAllStrokesOnly = new JCheckBox("Connect Begin-to-End, Alternate");
		rightPanel.add(connectBeginToEndOfAllStrokesOnly);
		fastStrokesOnly = new JCheckBox("Fast Strokes Only");
		rightPanel.add(fastStrokesOnly);
		slowStrokesOnly = new JCheckBox("Slow Strokes Only");
		rightPanel.add(slowStrokesOnly);
		elongateStroke = new JCheckBox("Elongate Stroke");
		rightPanel.add(elongateStroke);
		makeDashed = new JCheckBox("Make Dashed");
		rightPanel.add(makeDashed);
		mirrorVerticalStroke = new JCheckBox("Mirror Vertical Stroke");
		rightPanel.add(mirrorVerticalStroke);
		mirrorHorizontalStroke = new JCheckBox("Mirror Horizontal Stroke");
		rightPanel.add(mirrorHorizontalStroke);
		lsdEffect = new JCheckBox("LSD Effect");
		rightPanel.add(lsdEffect);
		connectAllStrokes = new JCheckBox("Connect All Strokes");
		rightPanel.add(connectAllStrokes);
		
		// Analyze button at the bottom
		modifyButton = new JButton("Modify");
		modifyButton.setBounds(150, 275, 100, 30);
		JLayeredPane pane = modifyWindow.getLayeredPane();
		pane.add(modifyButton, new Integer(1));
		modifyButton.addActionListener(new MenuListener());
	}
	
	// Lets the user choose their analysis options
	public void chooseAnalysis()
	{
		// Set up window
		analyzeWindow = new JFrame("Stroke Analysis");
		analyzeWindow.setSize(500, 450);
		analyzeWindow.setLocation(100, 50);
		analyzeWindow.setResizable(false);
		analyzeWindow.setVisible(true);
		
		analyzeWindow.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		analyzeWindow.add(leftPanel);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		analyzeWindow.add(rightPanel);
		
		// Ask what features the user wants to have analyzed
		
		// Mike's features
		singleStrokeLengths = new JCheckBox("Single Stroke Lengths");
		leftPanel.add(singleStrokeLengths);
		allStrokeLength = new JCheckBox("Total Stroke Length");
		leftPanel.add(allStrokeLength);
		minStrokeLength = new JCheckBox("Minimum Stroke Length");
		leftPanel.add(minStrokeLength);
		maxStrokeLength = new JCheckBox("Maximum Stroke Length");
		leftPanel.add(maxStrokeLength);
	    meanStrokeLength = new JCheckBox("Mean Stroke Length");
		leftPanel.add(meanStrokeLength);
		numStrokes = new JCheckBox("Number of Strokes");
		leftPanel.add(numStrokes);
		endToEndLengths = new JCheckBox("End to End Lengths");
		leftPanel.add(endToEndLengths);
		averageDistanceBetween = new JCheckBox("Average Distance Between Strokes");
		leftPanel.add(averageDistanceBetween);
		
		// Ross's Features
		cosStartingAngle = new JCheckBox("Cosine of Starting Angle");
		leftPanel.add(cosStartingAngle);
		sineStartingAngle = new JCheckBox("Sine of Starting Angle");
		leftPanel.add(sineStartingAngle);
		lengthOfDiagonal = new JCheckBox("Length of Diagonal");
		leftPanel.add(lengthOfDiagonal);
		angleOfDiagonal = new JCheckBox("Angle of Diagonal");
		leftPanel.add(angleOfDiagonal);
		
		// This one may be a duplicate of Mike's feature
		strokeDistance = new JCheckBox("Stroke Distance");
		leftPanel.add(strokeDistance);
		
		cosEndingAngle = new JCheckBox("Cosine of Ending Angle");
		leftPanel.add(cosEndingAngle);
		sinEndingAngle = new JCheckBox("Sine of Ending Angle");
		leftPanel.add(sinEndingAngle);
		smoothness = new JCheckBox("Smoothness");
		rightPanel.add(smoothness);
		maximumSpeedSquared = new JCheckBox("Maximum Speed Squared");
		rightPanel.add(maximumSpeedSquared);
		minimumSpeedSquared = new JCheckBox("Minimum Speed Squared");
		rightPanel.add(minimumSpeedSquared);
		averageSpeedSquared = new JCheckBox("Average Speed Squared");
		rightPanel.add(averageSpeedSquared);
		strokeTime = new JCheckBox("Stroke Time");
		rightPanel.add(strokeTime);
		aspect = new JCheckBox("Aspect");
		rightPanel.add(aspect);
		curvature = new JCheckBox("Curvature");
		rightPanel.add(curvature);
		density1 = new JCheckBox("Density Metric 1");
		rightPanel.add(density1);
		density2 = new JCheckBox("Density Metric 2");
		rightPanel.add(density2);
		openness = new JCheckBox("Openness");
		rightPanel.add(openness);
		areaOfBox = new JCheckBox("Area of Bounding Box");
		rightPanel.add(areaOfBox);
		logAreaOfBox = new JCheckBox("Log of Area of Bounding Box");
		rightPanel.add(logAreaOfBox);
		logTotalLength = new JCheckBox("Log of Total Length");
		rightPanel.add(logTotalLength);
		logOfCurviness = new JCheckBox("Log of Curviness");
		rightPanel.add(logOfCurviness);
		
		// Travis's feature
		longestPause = new JCheckBox("Longest Pause");
		rightPanel.add(longestPause);
		
		// Analyze button at the bottom
		analyzeButton = new JButton("Analyze");
		analyzeButton.setBounds(100, 375, 100, 30);
		JLayeredPane pane = analyzeWindow.getLayeredPane();
		pane.add(analyzeButton, new Integer(1));
		analyzeButton.addActionListener(new MenuListener());
		
		// Check All JCheckBox
		selectAll = new JCheckBox("Check/Uncheck All");
		selectAll.setBounds(275, 360, 200, 50);
		pane.add(selectAll, new Integer(1));
		selectAll.addActionListener(new MenuListener());
	}
	
	// Prints the chosen analysis
	public void printAnalysis()
	{
		printWindow = new JFrame("Stroke Analysis");
		printWindow.setSize(600, 500);
		printWindow.setLocation(100, 50);
		printWindow.setResizable(false);
		printWindow.setVisible(true);
		
		printWindow.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel();
		leftText = new JTextArea();
		leftText.setEditable(false);
		leftPanel.add(leftText);
		printWindow.add(leftPanel);
		JPanel rightPanel = new JPanel();
		rightText = new JTextArea();
		rightText.setEditable(false);
		rightPanel.add(rightText);
		printWindow.add(rightPanel);
		
		// Large Font
		Font largeFont = new Font("Verdana", Font.PLAIN, 20);
		
		// Show multiple stroke Analysis first
		titleLabel = new JLabel("Multi-Stroke Analysis");
		titleLabel.setFont(largeFont);
		JLayeredPane lp = printWindow.getLayeredPane();
		titleLabel.setBounds(175, -20, 350, 100);
		lp.add(titleLabel, new Integer(1));
		
		// Formatting
		DecimalFormat myFormat = new DecimalFormat("###.##");
		leftText.append("\n\n\n");
		
		// Total Stroke Length
		if (allStrokeLength.isSelected())
			leftText.append("Total Stroke Length: " + myFormat.format(Tools.allStrokesLength(strokes)) + "\n");
		
		// Minimum Stroke Length
		if (minStrokeLength.isSelected())
			leftText.append("Minimum Stroke Length: " + myFormat.format(Tools.minStrokeLength(strokes)) + "\n");

		// Maximum Stroke Length
		if (maxStrokeLength.isSelected())
			leftText.append("Maximum Stroke Length: " + myFormat.format(Tools.maxStrokeLength(strokes)) + "\n");

		// Average Stroke Length
		if (meanStrokeLength.isSelected())
			leftText.append("Mean Stroke Length: " + myFormat.format(Tools.meanStrokeLength(strokes)) + "\n");

		// Number of Strokes
		if (numStrokes.isSelected())
			leftText.append("Number of Strokes: " + myFormat.format(Tools.numStrokes(strokes)) + "\n");

		// Average Distance Between Strokes
		if (averageDistanceBetween.isSelected())
			leftText.append("Average Distance Between Strokes: " + myFormat.format(Tools.avgDistBetweenStrokes(strokes)) + "\n");
		
		// Longest Pause
		if (longestPause.isSelected())
			leftText.append("Longest Pause: " + myFormat.format(Tools.longestPause(strokes)) + "\n");
		
		// Move to single analysis
		ImageIcon icon = new ImageIcon("arrow.gif");
		singleButton = new JButton(icon);
		singleButton.setBounds(500, 365, 61, 48);
		JLayeredPane pane = printWindow.getLayeredPane();
		pane.add(singleButton, new Integer(1));
		singleButton.addActionListener(new MenuListener());
	}
	
	// Print single analysis for each stroke
	public void printSingleAnalysis(int index)
	{
		titleLabel.setBounds(125, -20, 350, 100);
		titleLabel.setText("Single Stroke Analysis: Stroke " + (index+1));
		leftText.setText("\n\n\n");
		rightText.setText("\n\n\n");
		DecimalFormat myFormat = new DecimalFormat("###.##");
		
		// Single analysis
		if(singleStrokeLengths.isSelected())
			leftText.append("Single Stroke Length: " + myFormat.format(Tools.singleStrokeLength(strokes.get(index))) + "\n");
		if (cosStartingAngle.isSelected())
			leftText.append("Cosine of Starting Angle: " + myFormat.format(Tools.cosStartingAngle(strokes.get(index))) + "\n");
		if (sineStartingAngle.isSelected())
			leftText.append("Sine of Starting Angle: " + myFormat.format(Tools.sinStartingAngle(strokes.get(index))) + "\n");
		if (lengthOfDiagonal.isSelected())
			leftText.append("Length of Diagonal: " + myFormat.format(Tools.lengthOfDiagonal(strokes.get(index))) + "\n");
		if (angleOfDiagonal.isSelected())
			leftText.append("Angle of Diagonal: " + myFormat.format(Tools.angleOfDiagonal(strokes.get(index))) + "\n");
		
		// Possibly duplicated
		if (strokeDistance.isSelected())
			leftText.append("Stroke Distance: " + myFormat.format(Tools.strokeDistance(strokes.get(index))) + "\n");
		
		if (cosEndingAngle.isSelected())
			leftText.append("Cosine of Ending Angle: " + myFormat.format(Tools.cosEndingAngle(strokes.get(index))) + "\n");
		if (sinEndingAngle.isSelected())
			leftText.append("Sine of Ending Angle: " + myFormat.format(Tools.sinEndingAngle(strokes.get(index))) + "\n");
		if (smoothness.isSelected())
			leftText.append("Smoothness: " + myFormat.format(Tools.smoothness(strokes.get(index))) + "\n");
		if (maximumSpeedSquared.isSelected())
			leftText.append("Maximum Speed Squared: " + myFormat.format(Tools.maximumSpeedSquared(strokes.get(index))) + "\n");
		if (minimumSpeedSquared.isSelected())
			leftText.append("Minimum Speed Squared: " + myFormat.format(Tools.minimumSpeedSquared(strokes.get(index))) + "\n");
		if (averageSpeedSquared.isSelected())
			leftText.append("Average Speed Squared: " + myFormat.format(Tools.averageSpeedSquared(strokes.get(index))) + "\n");
		if (strokeTime.isSelected())
			leftText.append("Stroke Time: " + myFormat.format(Tools.strokeTime(strokes.get(index))) + "\n");
		if (aspect.isSelected())
			leftText.append("Aspect: " + myFormat.format(Tools.aspect(strokes.get(index))) + "\n");
		if (curvature.isSelected())
			leftText.append("Curvature: " + myFormat.format(Tools.curvature(strokes.get(index))) + "\n");
		if (density1.isSelected())
			leftText.append("Density Metric 1: " + myFormat.format(Tools.density1(strokes.get(index))) + "\n");
		if (density2.isSelected())
			leftText.append("Density Metric 2: " + myFormat.format(Tools.density2(strokes.get(index))) + "\n");
		if (openness.isSelected())
			leftText.append("Openness: " + myFormat.format(Tools.openness(strokes.get(index))) + "\n");
		if (areaOfBox.isSelected())
			leftText.append("Area of Bounding Box: " + myFormat.format(Tools.areaOfBoundingBox(strokes.get(index))) + "\n");
		if (logAreaOfBox.isSelected())
			leftText.append("Log of Area of Bounding Box: " + myFormat.format(Tools.logAreaOfBoundingbox(strokes.get(index))) + "\n");
		if (logTotalLength.isSelected())
			leftText.append("Log of Total Length: " + myFormat.format(Tools.logTotalLength(strokes.get(index))) + "\n");
		if (logOfCurviness.isSelected())
			leftText.append("Log of Curviness: " + myFormat.format(Tools.logCurviness(strokes.get(index))) + "\n");
	}

	// Add a mouse listener that checks for mouse up, and if there is a mouse up, then clear past and current x and y
	private class MouseUpListener implements MouseListener
	{
		// Unused
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){}

		public void mouseReleased(MouseEvent e)
		{
			// When mouse is released, clear curr_x, curr_y
			curr_x = 0;
			curr_y = 0;

			strokes.add(new Stroke(curr_color));
			current_stroke++;
		}
	}

	// MouseMotionListener for the painting operations
	private class PaintListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			// X and Y values
			curr_x = e.getX();
			curr_y = e.getY();
			strokes.get(current_stroke).addPoint(new Point(curr_x, curr_y));
			strokes.get(current_stroke).addTimestamp(new Long(System.currentTimeMillis()));
			panel.repaint();
		}

		public void mouseMoved(MouseEvent e){}
	}

	public static void main(String[]args)
	{
		TracingInterface trace = new TracingInterface();
	}
}
