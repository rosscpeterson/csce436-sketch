// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// TracingInterface.java

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
java.lang.Math

public class TracingInterface
{
	// Constants
	public static int WINDOW_LOCATION_X = 100;
	public static int WINDOW_LOCATION_Y = 100;
	public static int WINDOW_SIZE_X = 600;
	public static int WINDOW_SIZE_Y = 600;
	
	// Member variables
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem clear, quit;
	private JMenu color;
	private JMenuItem blue, black, red, green, yellow, orange, magenta;
	private PaintWindow panel;
	private int curr_x = 0;
	private int curr_y = 0;
	private int past_x = 0;
	private int past_y = 0;
	private Color curr_color = Color.black;
	private ArrayList<ArrayList<Point>> strokes = new ArrayList<ArrayList<Point>>();
	private boolean finished = false;
	private int current_stroke = 0;
	
	
	// PaintWindow class
	public class PaintWindow extends JPanel
	{
		
		public double singleStrokeLength(ArrayList<Point> points) {
			//Mike NOT COMPILED COULD HAVE ERROR WITH CASTING IN LOOP**********
			//sqrt((x2-x1)^2 + (y2-y1)^2)
			int i;
			double totalLength = 0;
			for(i = 0; i < points.size()-1; i++) {
				totalLength += sqrt(pow((double)(points[i+1].getX()-points[i].getX()), 2.0) + 
					pow((double)(points[i+1].getY()-points[i].getY()), 2.0)); 
			}
			return totalLength;
		}
		
		public boolean isOpaque()
		{
			return true;
		}
		
		protected void paintComponent(Graphics g)
		{
			g.setColor(curr_color);
			if (past_x == 0 && past_y == 0 && curr_x == 0 && curr_y == 0);
			else if (past_x == 0 && past_y == 0)
				g.drawLine(curr_x, curr_y, curr_x, curr_y);
			else
				g.drawLine(past_x, past_y, curr_x, curr_y);
		}	
		
		public void clearPaintWindow()
		{
			this.repaint();
		}
	}
	
	// Constructor
	public TracingInterface()
	{
		window = new JFrame("Tracing Interface");
		window.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new PaintWindow();
		window.add(panel);
		panel.setBackground(Color.white);
		
		// Set up the menu and add it to the window
		file = new JMenu("File");
		clear = new JMenuItem("Clear");
		quit = new JMenuItem("Quit");
		file.add(clear);
		file.add(quit);
		
		// Color picker
		color = new JMenu("Color");
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
		
		menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(color);
		window.setJMenuBar(menuBar);
		
		// Add the actionListeners
		MenuListener menuListener = new MenuListener();
		ColorListener colorListener = new ColorListener();
		quit.addActionListener(menuListener);
		clear.addActionListener(menuListener);
		blue.addActionListener(colorListener);
		black.addActionListener(colorListener);
		red.addActionListener(colorListener);
		green.addActionListener(colorListener);
		yellow.addActionListener(colorListener);
		orange.addActionListener(colorListener);
		magenta.addActionListener(colorListener);
		panel.addMouseMotionListener(new PaintListener());
		panel.addMouseListener(new MouseUpListener());
		
		// Display the window
		window.setVisible(true);
		window.setResizable(false);
	}
	
	// ActionListener for Color buttons
	public class ColorListener implements ActionListener
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
		}
	}
	
	// ActionListener for menu buttons
	public class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == quit)
				System.exit(0);
			else if (e.getSource() == clear)
				panel.clearPaintWindow();
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
			curr_x = curr_y = past_x = past_y = 0;
			for(int i = 0; i < strokes.get(current_stroke).size(); i++)
			{
				System.out.println(strokes.get(current_stroke).get(i));
			}
			System.out.println();
		}
	}
	
	// MouseMotionListener for the painting operations
	public class PaintListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			if (curr_x == 0 && curr_y == 0 && past_x == 0 && past_y == 0)
			{
				strokes.add(new ArrayList<Point>());
				if (current_stroke != 0)
					current_stroke++;
			}
			
			past_x = curr_x;
			past_y = curr_y;
			curr_x = e.getX();
			curr_y = e.getY();
			strokes.get(current_stroke).add(new Point(curr_x, curr_y));
			panel.repaint();
		}
		
		public void mouseMoved(MouseEvent e){}
	}
	
	public static void main(String[]args)
	{
		TracingInterface trace = new TracingInterface();
	}
}
