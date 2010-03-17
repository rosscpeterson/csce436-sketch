//Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
//CSCE 436
//Tools.java

//package sketch;

import java.sql.Date;
import java.util.ArrayList;
import java.awt.Point;

public class Tools
{
	
	//Rubines Features:
	
	//Rubine feature 1
	//cos(alpha)
	//cosine of the starting angle of the stroke
	//Ross Peterson
	//fist attempt, I think it's wrong, but somebody smarter might want to look at it.
	public static double cosStartingAngle(Point p1, Point p2)
	{
		return ( (p2.getX() - p1.getX())
				/
				Math.sqrt(  Math.pow( (p2.getY()- p1.getY()), 2) + Math.pow((p2.getX()- p1.getX()), 2) ) );
	}
	
	//Rubine feature 1
	//cos(alpha)
	//cosine of the starting angle of the stroke
	//Ross Peterson
	public static double cosStartingAngle(ArrayList<Point> points)
	{
		int secondPointIndex = 2;
		int firstPointIndex = 0;
		
		Point p2 = points.get(secondPointIndex);
		Point p1 = points.get(firstPointIndex);
		
		return ( (p2.getX() - p1.getX())
				/
				Math.sqrt(  Math.pow( (p2.getY()- p1.getY()), 2) + Math.pow((p2.getX()- p1.getX()), 2) ) );
	}
	
	//Rubine feature 2
	//sin(alpha)
	//sine of the starting angle of the stroke
	//Ross Peterson
	//fist attempt, I think it's wrong, but somebody smarter might want to look at it.
	public static double sinStartingAngle(Point p1, Point p2)
	{	
		return ( (p2.getY()*p1.getY())
				/
				Math.sqrt(  Math.pow( (p2.getY()- p1.getY()), 2) + Math.pow((p2.getX()- p1.getX()), 2) ) );
	}
	
	//Rubine feature 2
	//sin(alpha)
	//sine of the starting angle of the stroke
	//Ross Peterson
	public static double sinStartingAngle(ArrayList<Point> points)
	{	
		int secondPointIndex = 2;
		int firstPointIndex = 0;
		
		Point p2 = points.get(secondPointIndex);
		Point p1 = points.get(firstPointIndex);
		
		return ( (p2.getY()*p1.getY())
				/
				Math.sqrt(  Math.pow( (p2.getY() - p1.getY()), 2) + 
							Math.pow( (p2.getX() - p1.getX()), 2) ) );
	}
	
	//Rubine feature 3
	//d
	//length of the diagonal of the bounding box of the stroke
	//Ross Peterson
	public static double lengthOfDiagonal(ArrayList<Point> points){
		
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = points.get(0); //default initialization to make Eclipse happy
		Point pXmin = points.get(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = points.get(0); //default initialization to make Eclipse happy
		Point pYmin = points.get(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<points.size(); i++){
			
			if( points.get(i).getX() >= currXmax )
				pXmax = points.get(i);	
			
			if( points.get(i).getX() <= currXmin )
				pXmin = points.get(i);
			
			if( points.get(i).getY() >= currYmax )
				pYmax = points.get(i);
			
			if( points.get(i).getY() <= currYmin )
				pYmin = points.get(i);
		}
		
		return Math.sqrt( Math.pow( (pYmax.getY()- pYmin.getY()), 2) + 
						  Math.pow( (pXmax.getX()- pXmin.getX()), 2) );
	}
	
	//Rubine feature 4
	//angle of the diagonal of the bounding box
	//Ross Peterson
	public static double angleOfDiagonal(ArrayList<Point> points){
		
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = points.get(0); //default initialization to make Eclipse happy
		Point pXmin = points.get(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = points.get(0); //default initialization to make Eclipse happy
		Point pYmin = points.get(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<points.size(); i++){
			
			if( points.get(i).getX() >= currXmax )
				pXmax = points.get(i);	
			
			if( points.get(i).getX() <= currXmin )
				pXmin = points.get(i);
			
			if( points.get(i).getY() >= currYmax )
				pYmax = points.get(i);
			
			if( points.get(i).getY() <= currYmin )
				pYmin = points.get(i);
		}
		
		return Math.atan( ( pYmax.getY() - pYmin.getY() ) / 
						  ( pXmax.getX() - pXmin.getX() ) );	
	}
	
	//Rubine feature 5
	//distance between the start and end point of the stroke
	//Ross Peterson
	//note: Mike has done something similar to this...
	public static double strokeDistance(ArrayList<Point> points)
	{
		Point p0 = points.get(0);
		Point pNminus1 = points.get(points.size()-1);
		
		return Math.sqrt( Math.pow( (pNminus1.getX()- p0.getX()), 2) + 
						  Math.pow( (pNminus1.getY()- p0.getY()), 2) );
	}
	
	//Rubine features 6 and 7 used to calculate:
	//The ending angle of the stroke
	//i.e. the angle between the horizontal line and the line
	//formed  by the first and last point of the stroke.
	
	//Rubine feature 6
	//cos(Beta) 
	//Ross Peterson
	public static double cosEndingAngle(ArrayList<Point> points)
	{
		Point p0 = points.get(0);
		Point pN = points.get(points.size());
		
		return ( (pN.getX() * p0.getX()) / strokeDistance(points) );
	}
	
	//Rubine feature 7
	//sin(Beta)
	//Ross Peterson
	public static double sinEndingAngle(ArrayList<Point> points)
	{
		Point p0 = points.get(0);
		Point pN = points.get(points.size());	
		
		return ( (pN.getY() * p0.getY()) / strokeDistance(points) );
	}
	
	//Rubine feature 8
	//total length of the path of a stroke
	//It looks like Mike Chenault has covered this through his singleStrokeLength function
	
	//Rubine feature 9
	//total rotational change in a stroke
	//Sum from p=1 to n-2 of theta(p)
	//theta(p) = arctan[(Dxp*Dyp-1Dxp-1*Dyp)=(Dxp*Dxp-1 + Dyp * Dyp-1)]
	//this could be wrong...
	//Ross Peterson
	public static double totalRotationalChange(ArrayList<Point> points){
		
		double totalRotationalChange = 0;
		for(int i = points.size()-2; i > 1; i--){

			Point p 	  = points.get(i);
			Point pMinus1 = points.get(i-1);
			Point pMinus2 = points.get(i-2);
			
			totalRotationalChange += Math.atan(
					(
					( (p.getX()       - pMinus1.getX()) * (pMinus1.getY() - pMinus2.getY()) ) *
					( (pMinus1.getX() - pMinus2.getX()) * (p.getY()       - pMinus1.getY())   ) 
					)/(
					( (p.getX()-pMinus1.getX()) * (pMinus1.getX()-pMinus2.getX()) ) +
					( (p.getY()-pMinus1.getY()) * (pMinus1.getY()-pMinus2.getY()) ) 
					)
				    );
		}
		return totalRotationalChange;
	}
	
	//Rubine feature 10
	//total absolute rotation change
	//Ross Peterson
	public static double absoluteTotalRotationalChange(ArrayList<Point> points){
		
		double totalRotationalChange = 0;
		for(int i = points.size()-2; i > 1; i--){

			Point p 	  = points.get(i);
			Point pMinus1 = points.get(i-1);
			Point pMinus2 = points.get(i-2);
			
			totalRotationalChange += Math.abs(Math.atan(
					(
					( (p.getX()       - pMinus1.getX()) * (pMinus1.getY() - pMinus2.getY()) ) *
					( (pMinus1.getX() - pMinus2.getX()) * (p.getY()       - pMinus1.getY())   ) 
					)/(
					( (p.getX()-pMinus1.getX()) * (pMinus1.getX()-pMinus2.getX()) ) +
					( (p.getY()-pMinus1.getY()) * (pMinus1.getY()-pMinus2.getY()) ) 
					)
				    ));
		}
		return totalRotationalChange;
	}
	
	//Rubine feature 11
	//smoothness of the stroke
	//sum of the squared value of the absolute values of the angles between each stroke
	//Ross Peterson
	public static double smoothness(ArrayList<Point> points){
		
		double totalRotationalChange = 0;
		for(int i = points.size()-2; i > 1; i--){

			Point p 	  = points.get(i);
			Point pMinus1 = points.get(i-1);
			Point pMinus2 = points.get(i-2);
			
			totalRotationalChange += Math.pow(
										Math.abs(Math.atan(
										(
										( (p.getX()       - pMinus1.getX()) * (pMinus1.getY() - pMinus2.getY()) ) *
										( (pMinus1.getX() - pMinus2.getX()) * (p.getY()       - pMinus1.getY())   ) 
										)/(
										( (p.getX()-pMinus1.getX()) * (pMinus1.getX()-pMinus2.getX()) ) +
										( (p.getY()-pMinus1.getY()) * (pMinus1.getY()-pMinus2.getY()) ) 
										)
									    ))
									    , 2);
		}
		return totalRotationalChange;
	}
	
	//Rubine feature 12
	//the squared value of the maximum speed reached
	//essentially finds the maximum speed reached making the stroke
	public static double maximumSpeedSquared(ArrayList<Point> points, ArrayList<Date> timestamps){
		
		double maximumSpeed = Double.NEGATIVE_INFINITY;
		
		for(int i = 1; i < points.size()-2; i++){
			
			Point p 	  = points.get(i);
			Point pMinus1 = points.get(i-1);
			
			Date t       = timestamps.get(i);
			Date tMinus1 = timestamps.get(i-1);
			
			double d = t.getTime() - tMinus1.getTime();
			
			double currSpeed  = (
					   			 ( Math.pow((p.getX()-pMinus1.getX()),2) + Math.pow((p.getX()-pMinus1.getX()),2) ) /
					   			 Math.pow( d,2 ) 
					  			);
			if( currSpeed > maximumSpeed )
			 	{ maximumSpeed = currSpeed; }
			}
		return maximumSpeed;
	}
	
	//minimum speed
	//Ross Peterson
	public static double minimumSpeedSquared(ArrayList<Point> points, ArrayList<Date> timestamps){
		
		double minimumSpeed = Double.POSITIVE_INFINITY;
		
		for(int i = 1; i < points.size()-2; i++){
			
			Point p 	  = points.get(i);
			Point pMinus1 = points.get(i-1);
			
			Date t       = timestamps.get(i);
			Date tMinus1 = timestamps.get(i-1);
			
			double d = t.getTime() - tMinus1.getTime();
			
			double currSpeed  = (
					   			 ( Math.pow((p.getX()-pMinus1.getX()),2) + Math.pow((p.getX()-pMinus1.getX()),2) ) /
					   			 Math.pow( d,2 ) 
					  			);
			if( currSpeed < minimumSpeed )
			 	{ minimumSpeed = currSpeed; }
			}
		return minimumSpeed;
	}

	//average speed
	//Ross Peterson
	public static double averageSpeedSquared(ArrayList<Point> points, ArrayList<Date> timestamps){
		
		double averageSpeed = 0;
		double totalSpeed = 0;
		
		for(int i = 1; i < points.size()-2; i++){
			
			Point p 	  = points.get(i);
			Point pMinus1 = points.get(i-1);
			
			Date t       = timestamps.get(i);
			Date tMinus1 = timestamps.get(i-1);
			
			double d = t.getTime() - tMinus1.getTime();
			
			totalSpeed  += (
					   			 ( Math.pow((p.getX()-pMinus1.getX()),2) + Math.pow((p.getX()-pMinus1.getX()),2) ) /
					   			 Math.pow( d,2 ) 
					  			);
			}
			
		averageSpeed = totalSpeed / points.size();	
		return averageSpeed;
	}
	
	//Rubine feature 13
	//stroke time
	//Ross Peterson
	public static double strokeTime(ArrayList<Point> points, ArrayList<Date> timestamps){
	
	Date tNminus1 = timestamps.get(timestamps.size()-1);
	Date t0 	  = timestamps.get(0);
	
	double strokeTime = tNminus1.getTime() - t0.getTime();
	
	return strokeTime;
	
	}
	
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

	//connects all the strokes to make one continuous stroke
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

	//Connects beginning of each stroke to every point of every other stroke
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
	//Mirrors a stroke across a horizontal line
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

	//Mirrors all strokes across a vertical line
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
	//Mirrors all strokes across a horizontal line
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

