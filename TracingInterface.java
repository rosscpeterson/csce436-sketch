// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// TracingInterface.java

import javax.swing.JFrame;
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

public class TracingInterface implements ActionListener
{
	// Constants
	public final static int WINDOW_LOCATION_X = 100;
	public final static int WINDOW_LOCATION_Y = 100;
	public final static int WINDOW_SIZE_X = 600;
	public final static int WINDOW_SIZE_Y = 600;
	private static Date initialApplicationTimeStamp;

	// Member variables
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem clear, quit;
	private JMenu color;
	private JMenuItem blue, black, red, green, yellow, orange, magenta;
	private PaintWindow panel;
	private int curr_x;
	private int curr_y;
	private int past_x;
	private int past_y;
	private Color curr_color;
	private ArrayList<Stroke> strokes;
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

		// Add the actionListeners
		quit.addActionListener(this);
		clear.addActionListener(this);
		blue.addActionListener(this);
		black.addActionListener(this);
		red.addActionListener(this);
		green.addActionListener(this);
		yellow.addActionListener(this);
		orange.addActionListener(this);
		magenta.addActionListener(this);

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

	// collect all the actions into one method
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == quit)
			System.exit(0);
		else if (e.getSource() == clear)
			panel.clearPaintWindow();
		else if (e.getSource() == blue)
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

	// PaintWindow class
	public class PaintWindow extends JPanel
	{
		public PaintWindow()
		{
			super();
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			drawStrokes(g);
		}

		public void drawStrokes(Graphics g)
		{
			// iterate through all strokes previously drawn and draw them to the screen
			// note: the JPanel is "double buffered" so this operation is actually really fast
			for(Stroke stroke : strokes)
			{
				g.setColor(stroke.getColor());
				for(int i = 0; i < stroke.getSize()-1; i++)
				{
					g.drawLine((int)stroke.getPoint(i).getX(), (int)stroke.getPoint(i).getY(), (int)stroke.getPoint(i+1).getX(), (int)stroke.getPoint(i+1).getY());
				}
			}
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
	public class MouseUpListener implements MouseListener
	{
		// Unused
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){}

		// When mouse is released, clear curr_x, curr_y, past_x, and past_y
		public void mouseReleased(MouseEvent e)
		{
			curr_x = 0;
			curr_y = 0;
			past_x = 0;
			past_y = 0;

			// print out the previous stroke number
			System.out.println();
			System.out.println("Current Stroke was: " + current_stroke);

			// update the stroke number (a stroke as defined by pen down to pen up)
			strokes.add(new Stroke(curr_color));
			current_stroke++;

			// print out the new stroke number
			System.out.println("Current Stroke is now: " + current_stroke);
			System.out.println();
		}
	}

	// MouseMotionListener for the painting operations
	public class PaintListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			// X and Y values
			past_x = curr_x;
			past_y = curr_y;
			curr_x = e.getX();
			curr_y = e.getY();
			strokes.get(current_stroke).addPoint(new Point(curr_x, curr_y));

			// Timestamp
			strokes.get(current_stroke).addTimestamp(new Date());

			// print the current change in stroke to the screen
			Stroke current = strokes.get(current_stroke);
			// the x and y coordinates for the stroke
			System.out.print("[x = " + current.getPoint(current.getSize()-1).getX() + ", y = " + current.getPoint(current.getSize()-1).getY() +  "]\t");
			// the time from the beginning of the stroke to the last stroke made
			System.out.println(current.getStrokeTimestamp(current.getSize()-1).getTime());

			panel.repaint();
		}

		public void mouseMoved(MouseEvent e){}
	}

	public static void main(String[]args)
	{
		TracingInterface trace = new TracingInterface();
	}
}
