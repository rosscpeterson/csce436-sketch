// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// Stroke.java

import java.util.ArrayList;
import java.util.Date;
import java.awt.Point;
import java.awt.Color;

public class Stroke
{
	private ArrayList<Point> points;

	// Change to milliseconds
	private ArrayList<Date> timestamps;
	private Color color;
	private Date begin_date, end_date;

	// Constructor
	Stroke()
	{
		points = new ArrayList<Point>();
		timestamps = new ArrayList<Date>();
		color = Color.black;
	}

	Stroke(Color c)
	{
		points = new ArrayList<Point>();
		timestamps = new ArrayList<Date>();
		color = c;
	}

	// Adds a point
	public void addPoint(Point pt)
	{
		points.add(pt);
		addTimestamp(new Date());
	}

	// Adds a timestamp
	public void addTimestamp(Date dt)
	{
		if(timestamps.size()==0)
			begin_date = dt;
		end_date = dt;
		timestamps.add(dt);
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
	public Date getTimestamp(int index)
	{
		return timestamps.get(index);
	}

	// Gets a timestamp at the index
	public Date getStrokeTimestamp(int index)
	{
		return new Date(timestamps.get(index).getTime() - begin_date.getTime());
	}

	// Gets the beginning date timestamp
	public Date getBeginTimestamp()
	{
		return begin_date;
	}

	// Gets the end date timestamp
	public Date getEndTimestamp()
	{
		return end_date;
	}

	// Gets the stroke duration in a Date format
	public Date getStrokeDuration()
	{
		return new Date(end_date.getTime() - begin_date.getTime());
	}

	// Gets the stroke duration in long format (milliseconds)
	public long getStrokeDurationInMilliseconds()
	{
		return end_date.getTime() - begin_date.getTime();
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
			output += getStrokeTimestamp(i).getTime();
			output += "\n";
		}

		return output;
	}
}
