// Class notes:
// mutable and immutable data types:
//   mutable data type => contents can change after it is created, mainly because having mutators inside
//   immutable data type => contents can't change after it is created. Can be acheived by having deep copy of input data

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
//import java.util.TreeSet;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	
   private SET<Point2D> Point_Array;
   
   public PointSET() { // construct an empty set of points 
	 Point_Array = new SET<Point2D>();   
   }
   public boolean isEmpty() { // is the set empty? 
	   return Point_Array.isEmpty();
   }
   public int size() { // number of points in the set 
	   return Point_Array.size();
   }
   public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
	   if (!Point_Array.contains(p)) {
		   Point_Array.add(p);
	   }
   }
   public boolean contains(Point2D p) { // does the set contain point p? 
	   return Point_Array.contains(p);
   }
   public void draw() { // draw all points to standard draw 
	   for (Point2D points : Point_Array) {
		   points.draw();
	   }
   }
   public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle
	   SET<Point2D> points_in_rec = new SET<Point2D>();
	   for (Point2D points : Point_Array) {
		   if (rect.contains(points)) {
			   points_in_rec.add(points);
		   }
	   }
	   return points_in_rec;
   }
   public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
	   Point2D nearest_point = null;
	   double min_distance = Double.POSITIVE_INFINITY;
	   for (Point2D points : Point_Array) {
		   if (points.distanceSquaredTo(p) < min_distance) {
			   nearest_point = points;
			   min_distance = points.distanceSquaredTo(p);
		   }
	   }
	   return nearest_point;
   }
   public static void main(String[] args) { // unit testing of the methods (optional) 
   }
}