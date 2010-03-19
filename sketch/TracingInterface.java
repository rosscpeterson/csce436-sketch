// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// TracingInterface.java

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.Date;

public class TracingInterface
{
	// Constants
	public final static int WINDOW_LOCATION_X = 25;
	public final static int WINDOW_LOCATION_Y = 25;
	public final static int WINDOW_SIZE_X = 700;
	public final static int WINDOW_SIZE_Y = 700;
	private static Date initialApplicationTimeStamp;

	// Member variables
	private JFrame window, analyzeWindow, printWindow;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem clear, quit;
	private JMenu color;
	private JMenuItem blue, black, red, green, yellow, orange, magenta;
	private JMenu analyze;
	private JMenuItem analyzeStroke;
	private JButton analyzeButton;
	private JCheckBox selectAll;
	private PaintWindow panel;
	private int curr_x;
	private int curr_y;
	private Color curr_color;
	private ArrayList<Stroke> strokes;
	private ArrayList<Stroke> modifiedStrokes;
	private int current_stroke;
	
	// Checkboxes
	JCheckBox singleStrokeLengths, allStrokeLength, minStrokeLength, maxStrokeLength,
	          meanStrokeLength, endToEndLengths, averageDistanceBetween, cosStartingAngle,
	          sineStartingAngle, lengthOfDiagonal, angleOfDiagonal, strokeDistance,
	          cosEndingAngle, sinEndingAngle, totalRotationalChange, totalAbsoluteRotation,
	          smoothness, maximumSpeedSquared, minimumSpeedSquared, averageSpeedSquared,
	          strokeTime, aspect, curvature, density1, density2, openness, areaOfBox,
	          logAreaOfBox, totalAngleOverAbsAngle, logTotalLength, logOfCurviness;
			
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
		current_stroke = 0;

		// Set up initial date for the application
		initialApplicationTimeStamp = new Date();

		// Set up the window
		window = new JFrame("Tracing Interface");
		window.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		file = new JMenu("File");
		color = new JMenu("Color");
		analyze = new JMenu("Analyze");
		menuBar.add(file);
		menuBar.add(color);
		menuBar.add(analyze);

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
		
		// Set up the analyze menu
		analyzeStroke = new JMenuItem("Analyze Stroke");
		analyze.add(analyzeStroke);

		// Add the actionListeners
		MenuListener menulistener = new MenuListener();
		quit.addActionListener(menulistener);
		clear.addActionListener(menulistener);
		analyzeStroke.addActionListener(menulistener);

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
				printAnalysis();
				
			} else if (e.getSource() == selectAll)
			{
				// Check All the boxes
				if (selectAll.isSelected())
				{
					singleStrokeLengths.setSelected(true);
					allStrokeLength.setSelected(true);
					minStrokeLength.setSelected(true);
					maxStrokeLength.setSelected(true);
			        meanStrokeLength.setSelected(true);
			        endToEndLengths.setSelected(true);
			        averageDistanceBetween.setSelected(true);
			        cosStartingAngle.setSelected(true);
			        sineStartingAngle.setSelected(true);
			        lengthOfDiagonal.setSelected(true);
			        angleOfDiagonal.setSelected(true);
			        strokeDistance.setSelected(true);
			        cosEndingAngle.setSelected(true);
			        sinEndingAngle.setSelected(true);
			        totalRotationalChange.setSelected(true);
			        totalAbsoluteRotation.setSelected(true);
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
			        totalAngleOverAbsAngle.setSelected(true);
			        logTotalLength.setSelected(true);
			        logOfCurviness.setSelected(true);
				} 
				
				// Uncheck All the boxes
				else
				{
					singleStrokeLengths.setSelected(false);
					allStrokeLength.setSelected(false);
					minStrokeLength.setSelected(false);
					maxStrokeLength.setSelected(false);
			        meanStrokeLength.setSelected(false);
			        endToEndLengths.setSelected(false);
			        averageDistanceBetween.setSelected(false);
			        cosStartingAngle.setSelected(false);
			        sineStartingAngle.setSelected(false);
			        lengthOfDiagonal.setSelected(false);
			        angleOfDiagonal.setSelected(false);
			        strokeDistance.setSelected(false);
			        cosEndingAngle.setSelected(false);
			        sinEndingAngle.setSelected(false);
			        totalRotationalChange.setSelected(false);
			        totalAbsoluteRotation.setSelected(false);
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
			        totalAngleOverAbsAngle.setSelected(false);
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

		/*private void printSingleStrokeFeatureAnalysis()
		{
			//initial check to see if there are strokes and that those strokes have more than one point in them
			if(strokes.size()>0 && strokes.get(0).getPoints().size()>1)
			{
				if(gross_length_of_stroke.getState() ||
						net_length_of_stroke.getState())
					System.out.println("Single Stroke Feature Analysis");

				//Gross Length of Stroke
				if (gross_length_of_stroke.getState())
				{
					System.out.println("\t- Gross Length for each Single Stroke");
					for(int i = 0; i < strokes.size(); i++)
					{
						if(strokes.get(i).getPoints().size()>0)
							System.out.println("\t\tStroke " + (i+1) + ": " + Tools.singleStrokeLength(strokes.get(i).getPoints()));
					}
				}

				//Net Length of Stroke
				if (net_length_of_stroke.getState())
				{
					System.out.println("\t- Net Length for each Single Stroke");
					for(int i = 0; i < strokes.size(); i++)
					{
						if(strokes.get(i).getPoints().size()>0)
							System.out.println("\t\tStroke " + (i+1) + ": " + Tools.endToEndLength(strokes.get(i).getPoints()));
					}
				}

			}
		}

		private void printMultiStrokeFeatureAnalysis()
		{
			//initial check to see if there is more than one stroke and that at least two of those strokes have more than one point in them
			if(strokes.size()>1 && strokes.get(0).getPoints().size()>1 && strokes.get(1).getPoints().size()>1)
			{
				if(min_length_of_strokes.getState() ||
						max_length_of_strokes.getState() ||
						avg_length_of_strokes.getState() ||
						number_of_strokes.getState() ||
						avg_distance_between_strokes.getState())
					System.out.println("Multi Stroke Feature Analysis");

				//Minimum Stroke Length of all Strokes
				if (min_length_of_strokes.getState())
				{
					System.out.println("\t- Minimum Stroke Length of all Strokes: " + Tools.minStrokeLength(strokes));
				}

				//Maximum Stroke Length of all Strokes
				if (max_length_of_strokes.getState())
				{
					System.out.println("\t- Maximum Stroke Length of all Strokes: " + Tools.maxStrokeLength(strokes));
				}

				//Average Stroke Length of all Strokes
				if (avg_length_of_strokes.getState())
				{
					System.out.println("\t- Average Stroke Length of all Strokes: " + Tools.meanStrokeLength(strokes));
				}

				//Number of Strokes
				if (number_of_strokes.getState())
				{
					System.out.println("\t- Number of Strokes: " + Tools.numStrokes(strokes));
				}

				//Average Distance Between Strokes
				if (avg_distance_between_strokes.getState())
				{
					System.out.println("\t- Average Distance Between Strokes: " + Tools.avgDistBetweenStrokes(strokes));
				}

			}
		}

		private void applySingleStrokeFeatureModifications()
		{
			//initial check to see if there are strokes and that those strokes have more than one point in them
			if(strokes.size()>0 && strokes.get(0).getPoints().size()>1)
			{
				//LSD Effect
				if (lsd_effect.getState())
				{
					for(Stroke stroke : strokes)
					{
						if(stroke.getPoints().size()>1)
						{
							ArrayList<Stroke> newStrokes = Tools.lsdEffect(stroke);
							for(Stroke newStroke : newStrokes)
							{
								drawnStrokes.add(newStroke);
							}
						}
					}
				}

				//Mirror Horizontal
				if (mirror_horizontal.getState())
				{
					for(Stroke stroke : strokes)
					{
						if(stroke.getPoints().size()>1)
						{
							drawnStrokes.add(Tools.mirrorHorizontalStroke(stroke,WINDOW_SIZE_X));
						}
					}
				}

				//Mirror Vertical
				if (mirror_vertical.getState())
				{
					for(Stroke stroke : strokes)
					{
						if(stroke.getPoints().size()>1)
						{
							drawnStrokes.add(Tools.mirrorVerticalStroke(stroke,WINDOW_SIZE_Y));
						}
					}
				}

				//Dashed
				if (dashed.getState())
				{
					for(Stroke stroke : strokes)
					{
						if(stroke.getPoints().size()>1)
						{
							ArrayList<Stroke> newStrokes = Tools.makeDashed(stroke);
							for(Stroke newStroke : newStrokes)
							{
								drawnStrokes.add(newStroke);
							}
						}
					}
				}

			}
		}

		private void applyMultiStrokeFeatureModifications()
		{
			//initial check to see if there is more than one stroke and that at least two of those strokes have more than one point in them
			if(strokes.size()>1 && strokes.get(0).getPoints().size()>1 && strokes.get(1).getPoints().size()>1)
			{
				//Connect All Strokes
				if (connect_strokes.getState())
				{
					ArrayList<Stroke> newStrokes = Tools.connectAllStrokes(strokes);
					for(Stroke newStroke : newStrokes)
					{
						drawnStrokes.add(newStroke);
					}
				}

				//Multiple LSD Effect
				if (multi_lsd_effect.getState())
				{
					ArrayList<Stroke> newStrokes = Tools.lsdEverything(strokes);
					for(Stroke newStroke : newStrokes)
					{
						drawnStrokes.add(newStroke);
					}
				}

				//Mirror All Horizontal
				if (mirror_all_horizontal.getState())
				{
					ArrayList<Stroke> newStrokes = Tools.mirrorAllHorizontal(strokes, WINDOW_SIZE_X);
					for(Stroke newStroke : newStrokes)
					{
						drawnStrokes.add(newStroke);
					}
				}

				//Mirror All Vertical
				if (mirror_all_vertical.getState())
				{
					ArrayList<Stroke> newStrokes = Tools.mirrorAllVertical(strokes, WINDOW_SIZE_Y);
					for(Stroke newStroke : newStrokes)
					{
						drawnStrokes.add(newStroke);
					}
				}

				//Make all Dashed
				if (all_dashed.getState())
				{
					ArrayList<Stroke> newStrokes = Tools.makeAllDashed(strokes);
					for(Stroke newStroke : newStrokes)
					{
						drawnStrokes.add(newStroke);
					}
				}

			}
		}
		}*/

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
	
	// Lets the user choose their analysis options
	public void chooseAnalysis()
	{
		// Set up window
		analyzeWindow = new JFrame("Stroke Analysis");
		analyzeWindow.setSize(500, 700);
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
		totalRotationalChange = new JCheckBox("Total Rotational Change");
		leftPanel.add(totalRotationalChange);
		totalAbsoluteRotation = new JCheckBox("Total Absolute Rotation Change");
		leftPanel.add(totalAbsoluteRotation);
		smoothness = new JCheckBox("Smoothness");
		leftPanel.add(smoothness);
		maximumSpeedSquared = new JCheckBox("Maximum Speed Squared");
		leftPanel.add(maximumSpeedSquared);
		minimumSpeedSquared = new JCheckBox("Minimum Speed Squared");
		leftPanel.add(minimumSpeedSquared);
		averageSpeedSquared = new JCheckBox("Average Speed Squared");
		leftPanel.add(averageSpeedSquared);
		strokeTime = new JCheckBox("Stroke Time");
		leftPanel.add(strokeTime);
		aspect = new JCheckBox("Aspect");
		leftPanel.add(aspect);
		curvature = new JCheckBox("Curvature");
		leftPanel.add(curvature);
		density1 = new JCheckBox("Density Metric 1");
		leftPanel.add(density1);
		density2 = new JCheckBox("Density Metric 2");
		leftPanel.add(density2);
		openness = new JCheckBox("Openness");
		rightPanel.add(openness);
		areaOfBox = new JCheckBox("Area of Bounding Box");
		rightPanel.add(areaOfBox);
		logAreaOfBox = new JCheckBox("Log of Area of Bounding Box");
		rightPanel.add(logAreaOfBox);
		totalAngleOverAbsAngle = new JCheckBox("Total Angle / Absolute Angle");
		rightPanel.add(totalAngleOverAbsAngle);
		logTotalLength = new JCheckBox("Log of Total Length");
		rightPanel.add(logTotalLength);
		logOfCurviness = new JCheckBox("Log of Curviness");
		rightPanel.add(logOfCurviness);
		
		
		
		
		
		
		// Analyze button at the bottom
		analyzeButton = new JButton("Analyze");
		analyzeButton.setBounds(100, 625, 100, 30);
		JLayeredPane pane = analyzeWindow.getLayeredPane();
		pane.add(analyzeButton, new Integer(1));
		analyzeButton.addActionListener(new MenuListener());
		
		// Check All JCheckBox
		selectAll = new JCheckBox("Check/Uncheck All");
		selectAll.setBounds(275, 590, 200, 100);
		pane.add(selectAll, new Integer(1));
		selectAll.addActionListener(new MenuListener());
	}
	
	// Prints the chosen analysis
	public void printAnalysis()
	{
		printWindow = new JFrame("Stroke Analysis");
		printWindow.setSize(500, 700);
		printWindow.setLocation(100, 50);
		printWindow.setResizable(false);
		printWindow.setVisible(true);
		
		analyzeWindow.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		analyzeWindow.add(leftPanel);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		analyzeWindow.add(rightPanel);
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
			panel.repaint();
		}

		public void mouseMoved(MouseEvent e){}
	}

	public static void main(String[]args)
	{
		TracingInterface trace = new TracingInterface();
	}
}
