// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// Stroke.java

import java.util.*;
import java.awt.*;

public class Stroke
{
	private ArrayList<Point> points = new ArrayList<Point>();
	
	// Change to milliseconds
	private ArrayList<Date> timestamps = new ArrayList<Date>();
	private Color color = Color.black;
	private Date begin_date, end_date;
	
	// Constructor
	Stroke(){}
	Stroke(Color c)
	{
		color = c;
	}
	
	// Adds a point
	public void addPoint(Point pt)
	{
		points.add(pt);
	}
	
	// Adds a timestamp
	public void addTimestamp(Date dt)
	{
		timestamps.add(dt);
	}
	
	// Gets a point at the index
	public Point getPoint(int index)
	{
		return points.get(index);
	}
	
	// Gets a timestamp at the index
	public Date getTimestamp(int index)
	{
		return timestamps.get(index);
	}
	
	// Sets the color of the stroke
	public void setColor(Color c)
	{
		color = c;
	}
	
	// Returns the color of the stroke
	public Color getColor()
	{
		return color;
	}
	
	// Returns the number of points in the stroke
	public int getSize()
	{
		return points.size();
	}
}
