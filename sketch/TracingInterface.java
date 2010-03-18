// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// TracingInterface.java

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
import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
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
	private JFrame window;
	private JFrame analyzeWindow;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem clear, quit;
	private JMenu color;
	private JMenuItem blue, black, red, green, yellow, orange, magenta;
	private JMenu analyze;
	private JMenuItem analyzeStroke;
	private JButton analyzeButton;
	private PaintWindow panel;
	private int curr_x;
	private int curr_y;
	private Color curr_color;
	private ArrayList<Stroke> strokes;
	private ArrayList<Stroke> modifiedStrokes;
	private int current_stroke;

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
				printAnalysis();
			else if (e.getSource() == analyzeButton)
			{
				analyzeWindow.setVisible(false);
				// Print out the analysis in a new window possibly
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
			for(Stroke stroke : strokes)
			{
				g.setColor(stroke.getColor());
				for(int i = 0; i < stroke.getSize()-1; i++)
				{
					g.drawLine((int)stroke.getPoint(i).getX(), (int)stroke.getPoint(i).getY(),
							   (int)stroke.getPoint(i+1).getX(), (int)stroke.getPoint(i+1).getY());
				}
			}
		}

		// Draws all the strokes in "modifiedStrokes". Used for changing personas.
		public void drawModifiedStrokes(Graphics g)
		{
			for(Stroke modifiedStroke : modifiedStrokes)
			{
				g.setColor(modifiedStroke.getColor());
				for(int i = 0; i < modifiedStroke.getSize()-1; i++)
				{
					g.drawLine((int)modifiedStroke.getPoint(i).getX(), (int)modifiedStroke.getPoint(i).getY(),
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
	
	// Prints an analysis window with all of the features
	public void printAnalysis()
	{
		// Set up window
		analyzeWindow = new JFrame("Stroke Analysis");
		analyzeWindow.setSize(500, 500);
		analyzeWindow.setLocation(100, 100);
		analyzeWindow.setResizable(false);
		analyzeWindow.setVisible(true);
		JPanel analyzePanel = new JPanel();
		analyzeWindow.add(analyzePanel);
		
		// Ask what features the user wants to have analyzed
		
		// Mike's features
		JCheckBox singleStrokeLengths = new JCheckBox("Single Stroke Lengths");
		analyzePanel.add(singleStrokeLengths);
		JCheckBox allStrokeLength = new JCheckBox("Total Stroke Length");
		analyzePanel.add(allStrokeLength);
		JCheckBox minStrokeLength = new JCheckBox("Minimum Stroke Length");
		analyzePanel.add(minStrokeLength);
		JCheckBox maxStrokeLength = new JCheckBox("Maximum Stroke Length");
		analyzePanel.add(maxStrokeLength);
		JCheckBox meanStrokeLength = new JCheckBox("Mean Stroke Length");
		analyzePanel.add(meanStrokeLength);
		JCheckBox endToEndLengths = new JCheckBox("End to End Lengths");
		analyzePanel.add(endToEndLengths);
		JCheckBox averageDistanceBetween = new JCheckBox("Average Distance Between Strokes");
		analyzePanel.add(averageDistanceBetween);
		
		
		// Analyze button at the bottom
		analyzeButton = new JButton("Analyze");
		analyzeButton.setBounds(210, 425, 100, 30);
		JLayeredPane pane = analyzeWindow.getLayeredPane();
		pane.add(analyzeButton, new Integer(1));
		analyzeButton.addActionListener(new MenuListener());
		
		// Print out the analysis based on the user choices
		
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