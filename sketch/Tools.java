//Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
//CSCE 436
//Tools.java

//package sketch;

import java.util.ArrayList;
import java.awt.Point;

public class Tools
{
	
	//Rubines Features:
	
	//Rubine feature 1
	//cos(alpha)
	//cosine of the starting angle of the stroke
	//Ross Peterson
	public static double cosStartingAngle(Stroke stroke)
	{
		int secondPointIndex = 2;
		int firstPointIndex = 0;
		
		Point p2 = stroke.getPoint(secondPointIndex);
		Point p1 = stroke.getPoint(firstPointIndex);
		
		return ( (p2.getX() - p1.getX())
				/
				Math.sqrt(  Math.pow( (p2.getY()- p1.getY()), 2) + Math.pow((p2.getX()- p1.getX()), 2) ) );
	}
	
	//Rubine feature 2
	//sin(alpha)
	//sine of the starting angle of the stroke
	//Ross Peterson
	public static double sinStartingAngle(Stroke stroke)
	{	
		int secondPointIndex = 2;
		int firstPointIndex = 0;
		
		Point p2 = stroke.getPoint(secondPointIndex);
		Point p1 = stroke.getPoint(firstPointIndex);
		
		return ( (p2.getY() - p1.getY()) //FLAG used to be a '*' instead of '-'
				/
				Math.sqrt(  Math.pow( (p2.getY() - p1.getY()), 2) + 
							Math.pow( (p2.getX() - p1.getX()), 2) ) );
	}
	
	//Rubine feature 3
	//d
	//length of the diagonal of the bounding box of the stroke
	//Ross Peterson
	public static double lengthOfDiagonal(Stroke stroke){
		
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<stroke.getSize(); i++){
			
			if( stroke.getPoint(i).getX() >= currXmax )
				pXmax = stroke.getPoint(i);	
			
			if( stroke.getPoint(i).getX() <= currXmin )
				pXmin = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() >= currYmax )
				pYmax = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() <= currYmin )
				pYmin = stroke.getPoint(i);
		}
		
		return Math.sqrt( Math.pow( (pYmax.getY()- pYmin.getY()), 2) + 
						  Math.pow( (pXmax.getX()- pXmin.getX()), 2) );
	}
	
	//Rubine feature 4
	//angle of the diagonal of the bounding box
	//Ross Peterson
	public static double angleOfDiagonal(Stroke stroke){
		
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<stroke.getSize(); i++){
			
			if( stroke.getPoint(i).getX() >= currXmax )
				pXmax = stroke.getPoint(i);	
			
			if( stroke.getPoint(i).getX() <= currXmin )
				pXmin = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() >= currYmax )
				pYmax = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() <= currYmin )
				pYmin = stroke.getPoint(i);
		}
		
		return Math.atan( ( pYmax.getY() - pYmin.getY() ) / 
						  ( pXmax.getX() - pXmin.getX() ) );	
	}
	
	//Rubine feature 5
	//distance between the start and end point of the stroke
	//Ross Peterson
	//note: Mike has done something similar to this...
	public static double strokeDistance(Stroke stroke)
	{
		Point p0 = stroke.getPoint(0);
		Point pNminus1 = stroke.getPoint(stroke.getSize()-1);
		
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
	public static double cosEndingAngle(Stroke stroke)
	{
		Point p0 = stroke.getPoint(0);
		Point pN = stroke.getPoint(stroke.getSize()-1);
		
		return ( (pN.getX() - p0.getX()) / strokeDistance(stroke) ); //FLAG - used to be a '*' instead of a '-'
	}
	
	//Rubine feature 7
	//sin(Beta)
	//Ross Peterson
	public static double sinEndingAngle(Stroke stroke)
	{
		Point p0 = stroke.getPoint(0);
		Point pN = stroke.getPoint(stroke.getSize()-1);	
		
		return ( (pN.getY() - p0.getY()) / strokeDistance(stroke) ); //FLAG - used to be a '*' instead of a '-'
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
	public static double totalRotationalChange(Stroke stroke){
		
		double totalRotationalChange = 0;
		for(int i = stroke.getSize()-2; i > 1; i--){

			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			Point pMinus2 = stroke.getPoint(i-2);
			
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
	public static double absoluteTotalRotationalChange(Stroke stroke){
		
		double totalRotationalChange = 0;
		for(int i = stroke.getSize()-2; i > 1; i--){

			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			Point pMinus2 = stroke.getPoint(i-2);
			
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
	public static double smoothness(Stroke stroke){
		
		double totalRotationalChange = 0;
		for(int i = stroke.getSize()-2; i > 1; i--){

			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			Point pMinus2 = stroke.getPoint(i-2);
			
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
	public static double maximumSpeedSquared(Stroke stroke){
		
		double maximumSpeed = Double.NEGATIVE_INFINITY;
		
		for(int i = 1; i < stroke.getSize()-2; i++){
			
			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			
			Long t       = stroke.getTimestamp(i);
			Long tMinus1 = stroke.getTimestamp(i-1);
			
			double d = t - tMinus1;
			
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
	public static double minimumSpeedSquared(Stroke stroke){
		
		double minimumSpeed = Double.POSITIVE_INFINITY;
		
		for(int i = 1; i < stroke.getSize()-2; i++){
			
			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			
			Long t       = stroke.getTimestamp(i);
			Long tMinus1 = stroke.getTimestamp(i-1);
			
			double d = t - tMinus1;
			
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
	public static double averageSpeedSquared(Stroke stroke){
		
		double averageSpeed = 0;
		double totalSpeed = 0;
		
		for(int i = 1; i < stroke.getSize()-2; i++){
			
			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			
			Long t       = stroke.getTimestamp(i);
			Long tMinus1 = stroke.getTimestamp(i-1);
			
			double d = t - tMinus1;
			
			totalSpeed  += (
					   			 ( Math.pow((p.getX()-pMinus1.getX()),2) + Math.pow((p.getX()-pMinus1.getX()),2) ) /
					   			 Math.pow( d,2 ) 
					  			);
			}
			
		averageSpeed = totalSpeed / stroke.getSize();	
		return averageSpeed;
	}
	
	//Rubine feature 13
	//stroke time
	//Ross Peterson
	public static double strokeTime(Stroke stroke){
	
	Long tNminus1 = stroke.getTimestamp(stroke.getSize()-1);
	Long t0 	  = stroke.getTimestamp(0);
	
	double strokeTime = tNminus1 - t0;
	
	return strokeTime;
	
	}
	
	//Long's Features
	
	//Long's Feature 13 
	//Aspect: [abs(45#4)] //? abs(4) ? // ? abs(4*5/4) ? I dunno
	//note: May not be right... it is currently abs(rubine feature 4)
	//original height / original width x new width = new height
	//Ross Peterson
	public static double aspect(Stroke stroke)
	{
	double currXmax = Double.NEGATIVE_INFINITY;
	double currXmin = Double.POSITIVE_INFINITY;
	Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
	Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

	double currYmax = Double.NEGATIVE_INFINITY;
	double currYmin = Double.POSITIVE_INFINITY;
	Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
	Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
	
	for(int i=0; i<stroke.getSize(); i++){
		
		if( stroke.getPoint(i).getX() >= currXmax )
			pXmax = stroke.getPoint(i);	
		
		if( stroke.getPoint(i).getX() <= currXmin )
			pXmin = stroke.getPoint(i);
		
		if( stroke.getPoint(i).getY() >= currYmax )
			pYmax = stroke.getPoint(i);
		
		if( stroke.getPoint(i).getY() <= currYmin )
			pYmin = stroke.getPoint(i);
	}
	
	return Math.abs( Math.atan( ( pYmax.getY() - pYmin.getY() ) / 
					  			( pXmax.getX() - pXmin.getX() ) ) );
}
	//Long's Feature 14 
	//Curviness (curvature)
	//(compute the change of angle of the line between each point and sum the angles together)
	//could be (probably) wrong
	//Ross Peterson
	public static double curvature(Stroke stroke)
	{
		//Rubine feature 6
		//cos(Beta) 
		//Ross Peterson
		
		//double cosBeta = ( (pN.getX() - p0.getX()) / strokeDistance(points) ); //FLAG - used to be a '*' instead of a '-'
		
		//Rubine feature 7
		//sin(Beta)
		//Ross Peterson
			
		//double sinBeta = ( (pN.getY() - p0.getY()) / strokeDistance(points) ); //FLAG - used to be a '*' instead of a '-'
	
		double totalCosAngle = 0;
		double totalSinAngle = 0;
		
		for(int i=3; i<stroke.getSize(); i = i+4){
			Point p3 = stroke.getPoint(i);
			Point p2 = stroke.getPoint(i-1);
			Point p1 = stroke.getPoint(i-2);
			Point p0 = stroke.getPoint(i-3);
			
			double cosAngle1 = ( (p3.getX() - p2.getX()) / strokeDistance(stroke) );
			double cosAngle0 = ( (p1.getX() - p0.getX()) / strokeDistance(stroke) );
			
			double cosDifference = cosAngle1 - cosAngle0;
			
			double sinAngle1 = ( (p3.getY() - p2.getY()) / strokeDistance(stroke) );
			double sinAngle0 = ( (p1.getY() - p0.getY()) / strokeDistance(stroke) );
			
			double sinDifference = sinAngle1 - sinAngle0;
			
			totalCosAngle += cosDifference;
			totalSinAngle += sinDifference;
		}
			
		return totalCosAngle;
		//return totalSinAngle;
	}
	
	//15 Total angle traversed / total length 
	//?? Not sure on the total angle traversed
	
	//Long's feature 16 
	//Density Metric 1 [8/5]
	//Ross Peterson
	public static double density1(Stroke stroke)
	{
		//rubine feature 8 - Total Stroke Length
		int i;
		double totalLength = 0;
		for(i = 0; i < stroke.getSize()-1; i++)
		{
			totalLength += getLength(stroke.getPoint(i), stroke.getPoint(i+1));
		}
		
		Point p0 = stroke.getPoint(0);
		Point pNminus1 = stroke.getPoint(stroke.getSize()-1);
		
		//rubine feature 5 - Distance between the first and last points
		double distBetweenFirstAndLastPoints = Math.sqrt( Math.pow( (pNminus1.getX()- p0.getX()), 2) + 
				   										  Math.pow( (pNminus1.getY()- p0.getY()), 2) );
		
		double density1 = totalLength / distBetweenFirstAndLastPoints;
		return density1;
		
	}
	
	//Long's feature 17 
	//Density Metric 2 [8/3]
	//Ross Peterson
	public static double density2(Stroke stroke)
	{
		//rubine feature 8 - Total Stroke Length
		double totalLength = 0;
		for(int i = 0; i < stroke.getSize()-1; i++)
		{
			totalLength += getLength(stroke.getPoint(i), stroke.getPoint(i+1));
		}
		
		//rubine feature 3 - Length of the Diagonal (Size of the bounding box)
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<stroke.getSize(); i++){
			
			if( stroke.getPoint(i).getX() >= currXmax )
				pXmax = stroke.getPoint(i);	
			
			if( stroke.getPoint(i).getX() <= currXmin )
				pXmin = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() >= currYmax )
				pYmax = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() <= currYmin )
				pYmin = stroke.getPoint(i);
		}
		
		double lengthOfDiagonal = Math.sqrt( Math.pow( (pYmax.getY()- pYmin.getY()), 2) + 
				   							 Math.pow( (pXmax.getX()- pXmin.getX()), 2) );
		
		double density2 = totalLength / lengthOfDiagonal;
		return density2;
	}
	
	//Long's feature 18
	//Non-subjective openness [5/3]
	//Ross Peterson
	public static double openness(Stroke stroke)
	{
		
		//Rubine feature 5 - Distance between the first and last poitns
		Point p0 = stroke.getPoint(0);
		Point pNminus1 = stroke.getPoint(stroke.getSize()-1);
		
		double distBetweenFirstLastPoints = Math.sqrt( Math.pow( (pNminus1.getX()- p0.getX()), 2) + 
						  					   Math.pow( (pNminus1.getY()- p0.getY()), 2) );
		
		//Rubine feature 3 - Length of the Diagonal (Size of the bounding box)
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<stroke.getSize(); i++){
			
			if( stroke.getPoint(i).getX() >= currXmax )
				pXmax = stroke.getPoint(i);	
			
			if( stroke.getPoint(i).getX() <= currXmin )
				pXmin = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() >= currYmax )
				pYmax = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() <= currYmin )
				pYmin = stroke.getPoint(i);
		}
		
		double lengthOfDiagonal = Math.sqrt( Math.pow( (pYmax.getY()- pYmin.getY()), 2) + 
				   							 Math.pow( (pXmax.getX()- pXmin.getX()), 2) );
		
		double nonSubjectiveOpenness = distBetweenFirstLastPoints / lengthOfDiagonal;
		return nonSubjectiveOpenness;
	}
	
	//Long's feature 19
	//Area of the Bounding Box
	//Ross Peterson
	public static double areaOfBoundingBox(Stroke stroke)
	{
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<stroke.getSize(); i++){
			
			if( stroke.getPoint(i).getX() >= currXmax )
				pXmax = stroke.getPoint(i);	
			
			if( stroke.getPoint(i).getX() <= currXmin )
				pXmin = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() >= currYmax )
				pYmax = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() <= currYmin )
				pYmin = stroke.getPoint(i);
		}
		
		double width  = pXmax.getX() - pXmin.getY();
		double height = pYmax.getY() - pYmin.getY();
		
		double area = width * height;
		return area;	
	}
	
	//Long's feature 20
	//Log(area)
	//Ross Peterson
	public static double logAreaOfBoundingbox(Stroke stroke)
	{
		double currXmax = Double.NEGATIVE_INFINITY;
		double currXmin = Double.POSITIVE_INFINITY;
		Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

		double currYmax = Double.NEGATIVE_INFINITY;
		double currYmin = Double.POSITIVE_INFINITY;
		Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
		Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
		
		for(int i=0; i<stroke.getSize(); i++){
			
			if( stroke.getPoint(i).getX() >= currXmax )
				pXmax = stroke.getPoint(i);	
			
			if( stroke.getPoint(i).getX() <= currXmin )
				pXmin = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() >= currYmax )
				pYmax = stroke.getPoint(i);
			
			if( stroke.getPoint(i).getY() <= currYmin )
				pYmin = stroke.getPoint(i);
		}
		
		double width  = pXmax.getX() - pXmin.getY();
		double height = pYmax.getY() - pYmin.getY();
		
		double area = width * height;
		return Math.log10(area);
	}
	
	//Long's feature 21
	//total angle/totalAbsAngle  [9/10]
	//Ross Peterson
	public static double totalAngleOverAbsAngle(Stroke stroke)
	{
		//Rubine Feature 9
		//total angle
		//could be wrong
		double totalRotationalChange = 0;
		for(int i = stroke.getSize()-2; i > 1; i--){

			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			Point pMinus2 = stroke.getPoint(i-2);
			
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
		
		//Rubine Feature 10
		//total absolute angle
		double absTotalRotationalChange = 0;
		for(int i = stroke.getSize()-2; i > 1; i--){

			Point p 	  = stroke.getPoint(i);
			Point pMinus1 = stroke.getPoint(i-1);
			Point pMinus2 = stroke.getPoint(i-2);
			
			absTotalRotationalChange += Math.abs(Math.atan(
					(
					( (p.getX()       - pMinus1.getX()) * (pMinus1.getY() - pMinus2.getY()) ) *
					( (pMinus1.getX() - pMinus2.getX()) * (p.getY()       - pMinus1.getY())   ) 
					)/(
					( (p.getX()-pMinus1.getX()) * (pMinus1.getX()-pMinus2.getX()) ) +
					( (p.getY()-pMinus1.getY()) * (pMinus1.getY()-pMinus2.getY()) ) 
					)
				    ));
		}
		return totalRotationalChange / absTotalRotationalChange;
		
	}
	
	//Long's feature 22
	//log of Total Length
	//Ross Peterson
	public static double logTotalLength(Stroke stroke)
	{
		int i;
		double totalLength = 0;
		for(i = 0; i < stroke.getSize()-1; i++)
		{
			totalLength += getLength(stroke.getPoint(i), stroke.getPoint(i+1));
		}
		return Math.log10(totalLength);
	}
	
	//Long's feature 23
	//log of aspect
	//probably wrong until we know how to measure aspect
	//aspect is currently the abs(angleOfdiagonal) // aba(4)
	//Ross Peterson
	public static double logCurviness(Stroke stroke)
	{
	double currXmax = Double.NEGATIVE_INFINITY;
	double currXmin = Double.POSITIVE_INFINITY;
	Point pXmax = stroke.getPoint(0); //default initialization to make Eclipse happy
	Point pXmin = stroke.getPoint(0); //default initialization to make Eclipse happy

	double currYmax = Double.NEGATIVE_INFINITY;
	double currYmin = Double.POSITIVE_INFINITY;
	Point pYmax = stroke.getPoint(0); //default initialization to make Eclipse happy
	Point pYmin = stroke.getPoint(0); //default initialization to make Eclipse happy
	
	for(int i=0; i<stroke.getSize(); i++){
		
		if( stroke.getPoint(i).getX() >= currXmax )
			pXmax = stroke.getPoint(i);	
		
		if( stroke.getPoint(i).getX() <= currXmin )
			pXmin = stroke.getPoint(i);
		
		if( stroke.getPoint(i).getY() >= currYmax )
			pYmax = stroke.getPoint(i);
		
		if( stroke.getPoint(i).getY() <= currYmin )
			pYmin = stroke.getPoint(i);
	}
	
	return Math.log10( Math.abs( Math.atan( ( pYmax.getY() - pYmin.getY() ) / 
					  		   ( pXmax.getX() - pXmin.getX() ) ) ) );
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
	public static double singleStrokeLength(Stroke stroke)
	{
		int i;
		double totalLength = 0;
		for(i = 0; i < stroke.getSize()-1; i++)
		{
			totalLength += getLength(stroke.getPoint(i), stroke.getPoint(i+1));
		}
		return totalLength;
	}

	//sums up the length of all strokes
	//Mike Chenault
	public static double allStrokesLength(ArrayList<Stroke> myStrokes)
	{
		int i;
		double totalLength = 0;
		for(i = 0; i < myStrokes.size() - 1; i++)
		{
			totalLength += singleStrokeLength(myStrokes.get(i));
		}
		return totalLength;
	}

	//Finds the length of the shortest stroke
	//Mike Chenault
	public static double minStrokeLength(ArrayList<Stroke> myStrokes)
	{
		int i;
		double minLength = singleStrokeLength(myStrokes.get(0));
		for(i = 1; i < myStrokes.size() - 1; i++)
		{
			double currStrokeLength = singleStrokeLength(myStrokes.get(i));
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
		double maxLength = singleStrokeLength(myStrokes.get(0));
		for(i = 1; i < myStrokes.size() - 1; i++)
		{
			double currStrokeLength = singleStrokeLength(myStrokes.get(i));
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
	public static double endToEndLength(Stroke stroke)
	{
		return getLength(stroke.getPoint(stroke.getSize()-1), stroke.getPoint(0));
	}

	//Mike Chenault
	public static int numStrokes(ArrayList<Stroke> myStrokes)
	{
		return myStrokes.size() - 1;
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

