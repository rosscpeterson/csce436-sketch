// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// TracingInterface.java

import javax.swing.JFrame;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
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
	public final static int WINDOW_LOCATION_X = 100;
	public final static int WINDOW_LOCATION_Y = 100;
	public final static int WINDOW_SIZE_X = 850;
	public final static int WINDOW_SIZE_Y = 800;
	private static Date initialApplicationTimeStamp;

	// Member variables
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem	clear,
						quit;
	private JMenu color;
	private JMenuItem	blue,
						black,
						red,
						green,
						yellow,
						orange,
						magenta;
	private JMenu ssfa;
	private JCheckBoxMenuItem	gross_length_of_stroke,
								net_length_of_stroke;
	private JMenu msfa;
	private JCheckBoxMenuItem 	min_length_of_strokes,
								max_length_of_strokes,
								avg_length_of_strokes,
								number_of_strokes,
								avg_distance_between_strokes;
	private JMenu ssfm;
	private JCheckBoxMenuItem	lsd_effect,
								mirror_horizontal,
								mirror_vertical,
								dashed;
	private JMenu msfm;
	private JCheckBoxMenuItem	connect_strokes,
								multi_lsd_effect,
								mirror_all_horizontal,
								mirror_all_vertical,
								all_dashed;
	private PaintWindow panel;
	private int curr_x;
	private int curr_y;
	private int past_x;
	private int past_y;
	private Color curr_color;
	private ArrayList<Stroke> strokes;
	private ArrayList<Stroke> drawnStrokes;
	private int current_stroke;

	// Constructor
	public TracingInterface()
	{
		// initializing values for the first stroke
		curr_x = 0;
		curr_y = 0;
		past_x = 0;
		past_y = 0;
		curr_color = Color.black;
		strokes = new ArrayList<Stroke>();
		strokes.add(new Stroke(curr_color));
		drawnStrokes = new ArrayList<Stroke>();
		current_stroke = 0;

		// might come in handy for a "feature"
		initialApplicationTimeStamp = new Date();

		// set up the window
		window = new JFrame("Tracing Interface");
		window.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add the file menu and the color menu to the menu bar
		menuBar = new JMenuBar();
		file = new JMenu("File");
		menuBar.add(file);
		color = new JMenu("Color");
		menuBar.add(color);
		ssfa = new JMenu("Single Stroke Feature Analysis");
		menuBar.add(ssfa);
		msfa = new JMenu("Multi Stroke Feature Analysis");
		menuBar.add(msfa);
		ssfm = new JMenu("Single Stroke Feature Modification");
		menuBar.add(ssfm);
		msfm = new JMenu("Multi Stroke Feature Modification");
		menuBar.add(msfm);

		// Set up the file menu
		clear = new JMenuItem("Clear");
		file.add(clear);
		quit = new JMenuItem("Quit");
		file.add(quit);

		// Set up the color menu
		blue = new JMenuItem("Blue");
		color.add(blue);
		black = new JMenuItem("Black");
		color.add(black);
		red = new JMenuItem("Red");
		color.add(red);
		green = new JMenuItem("Green");
		color.add(green);
		yellow = new JMenuItem("Yellow");
		color.add(yellow);
		orange = new JMenuItem("Orange");
		color.add(orange);
		magenta = new JMenuItem("Magenta");
		color.add(magenta);

		// Set up the single stroke feature analysis menu
		gross_length_of_stroke = new JCheckBoxMenuItem("Gross Length of Stroke");
		ssfa.add(gross_length_of_stroke);
		net_length_of_stroke = new JCheckBoxMenuItem("Net Length of Stroke");
		ssfa.add(net_length_of_stroke);

		// Set up the multi stroke feature analysis menu
		min_length_of_strokes = new JCheckBoxMenuItem("Minimum Length of Stroke");
		msfa.add(min_length_of_strokes);
		max_length_of_strokes = new JCheckBoxMenuItem("Max Length of Stroke");
		msfa.add(max_length_of_strokes);
		avg_length_of_strokes = new JCheckBoxMenuItem("Average Length of Stroke");
		msfa.add(avg_length_of_strokes);
		number_of_strokes = new JCheckBoxMenuItem("Number of Stroke");
		msfa.add(number_of_strokes);
		avg_distance_between_strokes = new JCheckBoxMenuItem("Average Distance between Stroke");
		msfa.add(avg_distance_between_strokes);

		// Set up the single stroke feature modification menu
		lsd_effect = new JCheckBoxMenuItem("LSD Effect");
		ssfm.add(lsd_effect);
		mirror_horizontal = new JCheckBoxMenuItem("Mirror Horizontal");
		ssfm.add(mirror_horizontal);
		mirror_vertical = new JCheckBoxMenuItem("Mirror Vertical");
		ssfm.add(mirror_vertical);
		dashed = new JCheckBoxMenuItem("Dashed");
		ssfm.add(dashed);

		// Set up the multi stroke feature modification menu
		connect_strokes = new JCheckBoxMenuItem("Connect All Strokes");
		msfm.add(connect_strokes);
		multi_lsd_effect = new JCheckBoxMenuItem("Multi Stroke LSD Effect");
		msfm.add(multi_lsd_effect);
		mirror_all_horizontal = new JCheckBoxMenuItem("Mirror All Horizontal");
		msfm.add(mirror_all_horizontal);
		mirror_all_vertical = new JCheckBoxMenuItem("Mirror All Vertical");
		msfm.add(mirror_all_vertical);
		all_dashed = new JCheckBoxMenuItem("Make All Dashed");
		msfm.add(all_dashed);


		// Add the actionListeners
		MenuListener menulistener = new MenuListener();
		quit.addActionListener(menulistener);
		clear.addActionListener(menulistener);

		ColorListener colorlistener = new ColorListener();
		blue.addActionListener(colorlistener);
		black.addActionListener(colorlistener);
		red.addActionListener(colorlistener);
		green.addActionListener(colorlistener);
		yellow.addActionListener(colorlistener);
		orange.addActionListener(colorlistener);
		magenta.addActionListener(colorlistener);

		FeatureListener featurelistener = new FeatureListener();
		//Mike's Features
		gross_length_of_stroke.addActionListener(featurelistener);
		net_length_of_stroke.addActionListener(featurelistener);
		min_length_of_strokes.addActionListener(featurelistener);
		max_length_of_strokes.addActionListener(featurelistener);
		avg_length_of_strokes.addActionListener(featurelistener);
		number_of_strokes.addActionListener(featurelistener);
		avg_distance_between_strokes.addActionListener(featurelistener);
		lsd_effect.addActionListener(featurelistener);
		connect_strokes.addActionListener(featurelistener);
		multi_lsd_effect.addActionListener(featurelistener);
		mirror_horizontal.addActionListener(featurelistener);
		mirror_vertical.addActionListener(featurelistener);
		mirror_all_horizontal.addActionListener(featurelistener);
		mirror_all_vertical.addActionListener(featurelistener);
		dashed.addActionListener(featurelistener);
		all_dashed.addActionListener(featurelistener);

		//Aaron's Features


		//Ross' Features


		//Travis' Features


		// create the panel with its own listeners
		panel = new PaintWindow();
		panel.setBackground(Color.white);
		panel.setOpaque(true);
		panel.setDoubleBuffered(true);
		panel.addMouseMotionListener(new PaintListener());
		panel.addMouseListener(new MouseUpListener());

		// add the menu bar and the panel to the window
		window.setJMenuBar(menuBar);
		window.add(panel);

		// Display the window
		window.setVisible(true);
		window.setResizable(false);
	}

	private class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == quit)
				System.exit(0);
			else if (e.getSource() == clear)
				panel.clearPaintWindow();
		}
	}

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
			// set the current color of the current stroke in case it changed
			strokes.get(current_stroke).setColor(curr_color);
		}
	}

	private class FeatureListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			panel.printSingleStrokeFeatureAnalysis();
			panel.printMultiStrokeFeatureAnalysis();
			panel.paintComponent(panel.getGraphics());
		}
	}

	// PaintWindow class
	private class PaintWindow extends JPanel
	{
		public PaintWindow()
		{
			super();
		}

		private void printSingleStrokeFeatureAnalysis()
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

		private void applyNoModifications()
		{
			//if there are no modifications add all the original strokes
			if(	((strokes.size()>2) && (!(connect_strokes.getState())) ||
					multi_lsd_effect.getState() ||
					mirror_all_horizontal.getState() ||
					mirror_all_vertical.getState() ||
					all_dashed.getState()) || ((strokes.size()<=2)))
			{
				for(Stroke stroke : strokes)
				{
					drawnStrokes.add(stroke);
				}
			}
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			applySingleStrokeFeatureModifications();
			applyMultiStrokeFeatureModifications();
			applyNoModifications();
			drawStrokes(g);
		}

		public void drawStrokes(Graphics g)
		{
			// iterate through all strokes previously drawn and draw them to the screen
			// note: the JPanel is "double buffered" so this operation is actually really fast
			for(Stroke drawnStroke : drawnStrokes)
			{
				g.setColor(drawnStroke.getColor());
				for(int i = 0; i < drawnStroke.getSize()-1; i++)
				{
					g.drawLine((int)drawnStroke.getPoint(i).getX(), (int)drawnStroke.getPoint(i).getY(), (int)drawnStroke.getPoint(i+1).getX(), (int)drawnStroke.getPoint(i+1).getY());
				}
			}
			drawnStrokes = new ArrayList<Stroke>();
		}

		public void clearPaintWindow()
		{
			// this clears all strokes but keeps the current color selected
			strokes = new ArrayList<Stroke>();
			strokes.add(new Stroke(curr_color));
			current_stroke = 0;
			this.repaint();
		}
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
			// When mouse is released, clear curr_x, curr_y, past_x, and past_y
			curr_x = 0;
			curr_y = 0;
			past_x = 0;
			past_y = 0;

			panel.printSingleStrokeFeatureAnalysis();
			panel.printMultiStrokeFeatureAnalysis();

			// update the stroke number (a stroke as defined by pen down to pen up)
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
			past_x = curr_x;
			past_y = curr_y;
			curr_x = e.getX();
			curr_y = e.getY();
			strokes.get(current_stroke).addPoint(new Point(curr_x, curr_y));
/*			// print the current change in stroke to the screen
			Stroke current = strokes.get(current_stroke);
			// the x and y coordinates for the stroke
			System.out.print("[x = " + current.getPoint(current.getSize()-1).getX() + ", y = " + current.getPoint(current.getSize()-1).getY() +  "]\t");
			// the time from the beginning of the stroke to the last stroke made
			System.out.println(current.getStrokeTimestamp(current.getSize()-1).getTime());
*/
			panel.repaint();
		}

		public void mouseMoved(MouseEvent e){}
	}

	public static void main(String[]args)
	{
		TracingInterface trace = new TracingInterface();
	}
}
