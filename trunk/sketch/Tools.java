//Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
//CSCE 436
//Tools.java

package sketch;

import java.util.ArrayList;
import java.awt.Point;

public class Tools
{
	//gets the distance between two points
	//Mike Chenault
	public static double getLength(Point p1, Point p2)
	{
		return Math.sqrt(Math.pow((double)Math.abs(p2.getX()-p1.getX()), 2.0) +
				Math.pow((double)Math.abs(p2.getY()-p1.getY()), 2.0));
	}

	//sqrt((x2-x1)^2 + (y2-y1)^2)
	//Mike Chenault
	public static double singleStrokeLength(ArrayList<Point> points)
	{
		int i;
		double totalLength = 0;
		for(i = 0; i < points.size()-1; i++)
		{
			totalLength += getLength(points.get(i), points.get(i+1));
		}
		return totalLength;
	}

	//sums up the length of all strokes
	//Mike Chenault
	public static double allStrokesLength(ArrayList<Stroke> myStrokes)
	{
		int i;
		double totalLength = 0;
		for(i = 0; i < myStrokes.size(); i++)
		{
			totalLength += singleStrokeLength(myStrokes.get(i).getPoints());
		}
		return totalLength;
	}

	//Finds the length of the shortest stroke
	//Mike Chenault
	public static double minStrokeLength(ArrayList<Stroke> myStrokes)
	{
		int i;
		double minLength = singleStrokeLength(myStrokes.get(0).getPoints());
		for(i = 1; i < myStrokes.size(); i++)
		{
			double currStrokeLength = singleStrokeLength(myStrokes.get(i).getPoints());
			if(currStrokeLength < minLength)
			{
				minLength = currStrokeLength;
			}
		}
		return minLength;
	}

	//Finds the length of the longest stroke
	//Mike Chenault
	public static double maxStrokeLength(ArrayList<Stroke> myStrokes)
	{
		int i;
		double maxLength = singleStrokeLength(myStrokes.get(0).getPoints());
		for(i = 1; i < myStrokes.size(); i++)
		{
			double currStrokeLength = singleStrokeLength(myStrokes.get(i).getPoints());
			if(currStrokeLength > maxLength)
			{
				maxLength = currStrokeLength;
			}
		}
		return maxLength;
	}

	//Mike Chenuault
	public static double meanStrokeLength(ArrayList<Stroke> myStrokes)
	{
		return allStrokesLength(myStrokes) / (double)myStrokes.size();
	}

	//returns length from start point of stroke to end point of stroke
	//Mike Chenault
	public static double endToEndLength(ArrayList<Point> points)
	{
		return getLength(points.get(points.size()-1), points.get(0));
	}

	//Mike Chenault
	public static int numStrokes(ArrayList<Stroke> myStrokes)
	{
		return myStrokes.size();
	}

	//returns the average straight line distance between the strokes
	//Mike Chenault
	public static double avgDistBetweenStrokes(ArrayList<Stroke> myStrokes)
	{
		int i;
		double totalLength = 0;
		for(i=0; i<myStrokes.size()-1; i++)
		{
			if(i!=myStrokes.size()-1  && (myStrokes.get(i+1).getPoints().size()>1))
				totalLength += getLength(myStrokes.get(i).getPoints().get(myStrokes.get(i).getPoints().size()-1), myStrokes.get(i+1).getPoints().get(0));
		}
		return totalLength / (double)myStrokes.size();
	}

	//connects all the strokes to make one continious stroke
	//Mike Chenault
	public static ArrayList<Stroke> connectAllStrokes(ArrayList<Stroke> myStrokes)
	{
		int i;
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();

		for(Stroke myStroke : myStrokes)
		{
			newStrokes.add(myStroke);
		}

		for(i = 0; i < myStrokes.size() - 1; i++)
		{
			if(i!=myStrokes.size()-1  && (myStrokes.get(i+1).getPoints().size()>1))
			{
				Stroke newStroke = new Stroke(myStrokes.get(i).getColor());
				newStroke.addPoint(myStrokes.get(i).getPoints().get(myStrokes.get(i).getPoints().size()-1));
				newStroke.addPoint(myStrokes.get(i+1).getPoints().get(0));
				newStrokes.add(newStroke);
			}
		}
		return newStrokes;
	}

	//draws a line from the start point to every point on the line
	//returns an array list of strokes
	//Mike Chenault
	public static ArrayList<Stroke> lsdEffect(Stroke points)
	{
		Point startPoint = points.getPoints().get(0);
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();
		newStrokes.add(points);  //make sure the original stroke is added
		for(int i = 1; i < points.getPoints().size(); i++)
		{
			Stroke newStroke = new Stroke(points.getColor());
			newStroke.addPoint(startPoint);
			newStroke.addPoint(points.getPoints().get(i));
			newStrokes.add(newStroke);
		}
		return newStrokes;
	}

	//Connects begining of each stroke to every point of every other stroke
	//Mike Chenault
	public static ArrayList<Stroke> lsdEverything(ArrayList<Stroke> myStrokes)
	{
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();
		for(int k=0; k < myStrokes.size(); k++)
		{
			if(myStrokes.get(k).getPoints().size()>0)
			{
				Point startPoint = myStrokes.get(k).getPoints().get(0);
				for(int j = 0; j < myStrokes.size(); j++)
				{
					for(int i = 1; i < myStrokes.get(j).getPoints().size(); i++)
					{
						Stroke newStroke = new Stroke();
						newStroke.addPoint(startPoint);
						newStroke.addPoint(myStrokes.get(j).getPoints().get(i));
						newStrokes.add(newStroke);
					}
				}
			}
		}
		return newStrokes;
	}

	//Mirrors a stroke along a vertical line
	//Mike Chenault
	public static Stroke mirrorHorizontalStroke(Stroke stroke, int windowSize_X)
	{
		Stroke newStroke = new Stroke();
		for(int i = 0; i < stroke.getPoints().size(); i++)
		{
			int diff = windowSize_X - (int)stroke.getPoints().get(i).getX();
			Point newPoint = new Point(diff, (int)stroke.getPoints().get(i).getY());
			newStroke.addPoint(newPoint);
		}
		return newStroke;
	}

	/***	This code incorrectly mirrors vertically.  Why? I don't know!  ***/
	//Mirrors a stroke accross a horizontal line
	//Mike Chenault
	public static Stroke mirrorVerticalStroke(Stroke stroke, int windowSize_Y)
	{
		Stroke newStroke = new Stroke();
		for(int i = 0; i < stroke.getPoints().size(); i++)
		{
			int diff = windowSize_Y - (int)stroke.getPoints().get(i).getY();
			Point newPoint = new Point((int)stroke.getPoints().get(i).getY(), diff);
			newStroke.addPoint(newPoint);
		}
		return newStroke;
	}

	//Mirrors all strokes accross a verical line
	//Mike Chenault
	public static ArrayList<Stroke> mirrorAllHorizontal(ArrayList<Stroke> strokes, int windowSize_X)
	{
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();
		for(int i = 0; i < strokes.size(); i++)
		{
			newStrokes.add(mirrorHorizontalStroke(strokes.get(i), windowSize_X));
		}
		return newStrokes;
	}

	/***	This code incorrectly mirrors vertically.  Why? I don't know!  ***/
	//Mirrors all strokes accross a horizontal line
	//Mike Chenault
	public static ArrayList<Stroke> mirrorAllVertical(ArrayList<Stroke> strokes, int windowSize_Y)
	{
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();
		for(int i = 0; i < strokes.size(); i++)
		{
			newStrokes.add(mirrorVerticalStroke(strokes.get(i), windowSize_Y));
		}
		return newStrokes;
	}

	/***	This code does not create dashed lines.  Why? I don't know!  ***/
	//Removes every other segment of a stroke to make all the stroke appear dashed
	//Mike Chenault
	public static ArrayList<Stroke> makeDashed(Stroke stroke)
	{
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();
		for(int i = 0; i < stroke.getPoints().size(); i+=2)
		{
			if(i+1 < stroke.getPoints().size())
			{
				Stroke newStroke = new Stroke();
				newStroke.addPoint(stroke.getPoints().get(i));
				newStroke.addPoint(stroke.getPoints().get(i+1));
				newStrokes.add(newStroke);
			}
		}
		return newStrokes;
	}

	/***	This code does not create dashed lines.  Why? I don't know!  ***/
	//Removes every other segment of a stroke to make all the stroke appear dashed
	//Mike Chenault
	public static ArrayList<Stroke> makeAllDashed(ArrayList<Stroke> strokes)
	{
		ArrayList<Stroke> newStrokes = new ArrayList<Stroke>();
		for(Stroke stroke : strokes)
		{
			ArrayList<Stroke> myStrokes = new ArrayList<Stroke>();
			myStrokes = makeDashed(stroke);
			for(Stroke myStroke : myStrokes)
			{
				newStrokes.add(myStroke);
			}
		}
		return newStrokes;
	}
}

