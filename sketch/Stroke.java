//Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
//CSCE 436
//Stroke.java

import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;

public class Stroke
{
	private ArrayList<Point> points;
	private ArrayList<Long> timestamps;
	private Color color;

	// Constructor
	Stroke()
	{
		points = new ArrayList<Point>();
		timestamps = new ArrayList<Long>();
		color = Color.black;
	}

	Stroke(Color c)
	{
		points = new ArrayList<Point>();
		timestamps = new ArrayList<Long>();
		color = c;
	}

	// Adds a point
	public void addPoint(Point pt)
	{
		points.add(pt);
	}

	// Adds a timestamp
	public void addTimestamp(long time)
	{
		timestamps.add(new Long(time));
	}

	// Gets a point at the index
	public Point getPoint(int index)
	{
		return points.get(index);
	}

	// Returns an ArrayList of Points defining the Stroke
	public ArrayList<Point> getPoints()
	{
		return points;
	}

	// Gets a timestamp at the index
	public long getTimestamp(int index)
	{
		return timestamps.get(index);
	}

	// Gets the stroke duration in milliseconds
	public long getStrokeDuration()
	{
		return (long)(timestamps.get(timestamps.size() - 1) - timestamps.get(0));
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

	// toString method
	public String toString()
	{
		String output = new String();
		for(int i = 0; i < this.getSize(); i++)
		{
			output += "[x = ";
			output += points.get(i).getX();
			output += ", y = ";
			output += points.get(i).getY();
			output += "]\t";
			output += getTimestamp(i);
			output += "\n";
			
			//Ross testing area
			//output += " cosStartingAngle = " + Tools.cosStartingAngle(points);
			//output += ",";
			//output += " sinStartingAngle = " + Tools.sinStartingAngle(points);
		}
		return output;
	}
}
