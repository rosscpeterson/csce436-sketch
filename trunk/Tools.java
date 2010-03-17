// Aaron Loveall, Mike Chenault, Travis Kosarek, and Ross Peterson
// CSCE 436
// Tools.java

import java.util.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;

public class Tools
{
	Tools(){}
	
	public double getLength(Point p1, Point p2) {
		//Mike Chenault
		//gets the distance between two points
		return Math.sqrt(Math.pow((double)Math.abs(p2.getX()-p1.getX()), 2.0) + 
				Math.pow((double)Math.abs(p2.getY()-p1.getY()), 2.0));
	}
	
	public double singleStrokeLength(ArrayList<Point> points) {
		//Mike Chenault
		//sqrt((x2-x1)^2 + (y2-y1)^2)
		int i;
		double totalLength = 0;
		for(i = 0; i < points.size()-1; i++) {
			totalLength += getLength(points.get(i), points.get(i+1)); 
		}
		return totalLength;
	}
	
	public double allStrokesLength(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		//sums up the length of all strokes
		int i;
		double totalLength = 0;
		for(i = 0; i < myStrokes.size(); i++) {
			totalLength += singleStrokeLength(myStrokes.get(i));
		}
		return totalLength;
	}
	
	public double minStrokeLength(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		//Finds the length of the shortest stroke
		int i;
		double minLength = singleStrokeLength(myStrokes.get(0));
		for(i = 1; i < myStrokes.size(); i++) {
			double currStrokeLength = singleStrokeLength(myStrokes.get(i));
			if(currStrokeLength < minLength) {
				minLength = currStrokeLength;
			}
		}
		return minLength;
	}
	
	public double maxStrokeLength(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		//Finds the length of the longest stroke
		int i;
		double maxLength = singleStrokeLength(myStrokes.get(0));
		for(i = 1; i < myStrokes.size(); i++) {
			double currStrokeLength = singleStrokeLength(myStrokes.get(i));
			if(currStrokeLength > maxLength) {
				maxLength = currStrokeLength;
			}
		}
		return maxLength;
	}
	
	public double meanStrokeLength(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenuault
		return allStrokesLength(myStrokes) / (double)myStrokes.size();
	}
	
	public double endToEndLength(ArrayList<Point> points) {
		//Mike Chenault
		//returns lenght from start point of stroke to end point of stroke
		return getLength(points.get(points.size()-1), points.get(0)); 
	}
	
	public int numStrokes(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		return myStrokes.size();
	}
	
	public double avgDistBetweenStrokes(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		//returns the average straight line distance between the strokes
		int i;
		double totalLength = 0;
		for(i=0; i<myStrokes.size()-1; i++) {
			totalLength += getLength(myStrokes.get(i).get(myStrokes.get(i).size()-1), myStrokes.get(i+1).get(0));
		}
		return totalLength / (double)myStrokes.size();
	}
	
	public ArrayList<ArrayList<Point>> lsdEffect(ArrayList<Point> points) {
		//Mike Chenault
		//draws a line from the start point to every point on the line
		//returns an array list of strokes
		int i;
		Point startPoint = points.get(0);
		ArrayList<ArrayList<Point>> newStrokes = new ArrayList<ArrayList<Point>>();
		newStrokes.add(points);  //make sure the origional stroke is added
		for(i = 1; i < points.size(); i++) {
			ArrayList<Point> newStroke = new ArrayList<Point>();
			newStroke.add(startPoint);
			newStroke.add(points.get(i));
			newStrokes.add(newStroke);
		}
		return newStrokes;
	}
	
	public ArrayList<ArrayList<Point>> lsdEverything(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		//Connects begining of each stroke to every point of every other stroke
		int i, j, k;
		ArrayList<ArrayList<Point>> newStrokes = new ArrayList<ArrayList<Point>>();
		newStrokes.equals(myStrokes);
		for(k=0; k < myStrokes.size(); k++) {
			Point startPoint = myStrokes.get(k).get(0);
			for(j = 0; j < myStrokes.size(); j++) {
				//newStrokes.add(points);  //make sure the origional stroke is added
				for(i = 1; i < myStrokes.get(j).size(); i++) {
					ArrayList<Point> newStroke = new ArrayList<Point>();
					newStroke.add(startPoint);
					newStroke.add(myStrokes.get(j).get(i));
					newStrokes.add(newStroke);
				}
			}
		}
		return newStrokes;
	}
	
	public ArrayList<ArrayList<Point>> connectAllStrokes(ArrayList<ArrayList<Point>> myStrokes) {
		//Mike Chenault
		//connects all the strokes to make one continious stroke
		int i;
		ArrayList<ArrayList<Point>> newStrokes = new ArrayList<ArrayList<Point>>();
		newStrokes.equals(myStrokes);
		
		for(i = 0; i < myStrokes.size() - 1; i++) {
			ArrayList<Point> newStroke = new ArrayList<Point>();
			newStroke.add(myStrokes.get(i).get(myStrokes.get(i).size()-1));
			newStroke.add(myStrokes.get(i+1).get(0));
			newStrokes.add(newStroke);
		}
		return newStrokes;
	}
	
	public ArrayList<Point> mirrorHorizontalStroke(ArrayList<Point> stroke, int windowSize_X) {
		//Mike Chenault
		//Mirrors a stroke along a vertical line
		ArrayList<Point> newStroke = new ArrayList<Point>();
		for(int i = 0; i < stroke.size(); i++) {
			int diff = windowSize_X - (int)stroke.get(i).getX();
			Point newPoint = new Point(diff, (int)stroke.get(i).getY());
			newStroke.add(newPoint);
		}
		return newStroke;
	}
	
	public ArrayList<Point> mirrorVerticalStroke(ArrayList<Point> stroke, int windowSize_Y) {
		//Mike Chenault
		//Mirrors a stroke accross a horizontal line
		ArrayList<Point> newStroke = new ArrayList<Point>();
		for(int i = 0; i < stroke.size(); i++) {
			int diff = windowSize_Y - (int)stroke.get(i).getY();
			Point newPoint = new Point((int)stroke.get(i).getY(), diff);
			newStroke.add(newPoint);
		}
		return newStroke;
	}
	
	public ArrayList<ArrayList<Point>> mirrorAllHorizontal(ArrayList<ArrayList<Point>> strokes, int windowSize_X) {
		//Mike Chenault
		//Mirrors all strokes accross a verical line
		ArrayList<ArrayList<Point>> newStrokes = new ArrayList<ArrayList<Point>>();
		for(int i = 0; i < strokes.size(); i++) {
			newStrokes.add(mirrorHorizontalStroke(strokes.get(i), windowSize_X));
		}		
		return newStrokes;
	}
	
	public ArrayList<ArrayList<Point>> mirrorAllVertical(ArrayList<ArrayList<Point>> strokes, int windowSize_Y) {
		//Mike Chenault
		//Mirrors all strokes accross a horizontal line
		ArrayList<ArrayList<Point>> newStrokes = new ArrayList<ArrayList<Point>>();
		for(int i = 0; i < strokes.size(); i++) {
			newStrokes.add(mirrorVerticalStroke(strokes.get(i), windowSize_Y));
		}		
		return newStrokes;
	}
	
	public ArrayList<ArrayList<Point>> makeAllDashed(ArrayList<ArrayList<Point>> strokes) {
		//Mike Chenault
		//Removes every other segment of a stroke to make all the stroke appear dashed
		ArrayList<ArrayList<Point>> newStrokes = new ArrayList<ArrayList<Point>>();
		for(int i = 0; i < strokes.size(); i++) {
			for(int j = 2; j < strokes.get(i).size(); j+=2) {
				ArrayList<Point> newStroke = new ArrayList<Point>();
				newStroke.add(strokes.get(i).get(j-2));
				newStroke.add(strokes.get(i).get(j));
				newStrokes.add(newStroke);
			}
		}
		return newStrokes;
	}
	
}
