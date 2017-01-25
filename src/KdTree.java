import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

   private int size;
   private Node root;
   
   private class Node {
	   private Point2D point;
	   private RectHV  rect;
	   private Node    left;
	   private Node    right;
	   
	   public Node(Point2D point_p, RectHV rect_p) {
		   point = point_p;
		   rect  = rect_p;
	   }
   }
   
   public KdTree() { // construct an empty set of points 
	   root = null;
	   size = 0;
   }
   public boolean isEmpty() { // is the set empty? 
	   return (size == 0);
   }
   public int size() { // number of points in the set 
	   return size;
   }
   public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
	   int pingpong_state = 0; // 0 => x, 1 => y
	   boolean stop_flag = false;
	   Node ptr = root;
	   double xmin = 0.0, xmax = 1.0;
	   double ymin = 0.0, ymax = 1.0;
	   
	   if (ptr == null) {
		   RectHV new_rect = new RectHV(xmin,ymin,xmax,ymax);
		   ptr = new Node(p,new_rect);
		   return;
	   }
	   
	   while (!stop_flag) {
		   // check which subtree the node p belongs to
		   // update rect parameters
		   boolean nxt_left;
		   if (pingpong_state == 0) {
			   if (Point2D.X_ORDER.compare(p, ptr.point) > 0) {
				   nxt_left = false;
				   xmin = ptr.point.x();
			   } else {
				   nxt_left = true;
				   xmax = ptr.point.x();
			   }
		   } else {
			   if (Point2D.Y_ORDER.compare(p, ptr.point) > 0) {
				   nxt_left = false;
				   ymin = ptr.point.y();
			   } else {
				   nxt_left = true;
				   ymax = ptr.point.y();
			   }
		   }
		   // if it's null, new and insert
		   if (nxt_left) {
			   if (ptr.left == null) {
				   RectHV new_rect = new RectHV(xmin,ymin,xmax,ymax);
				   ptr.left = new Node(p,new_rect);
				   stop_flag = true;
			   }
		   } else {
			   if (ptr.right == null) {
				   RectHV new_rect = new RectHV(xmin,ymin,xmax,ymax);
				   ptr.right = new Node(p,new_rect);
				   stop_flag = true;
			   }
		   }
	   }
   }
   public boolean contains(Point2D p) { // does the set contain point p? 
	   int pingpong_state = 0; // 0 => x, 1 => y
	   Node ptr = root;
	   while (ptr != null) {
		  if (p.equals(ptr.point))  {
			  return true;
		  } else {
			if (pingpong_state == 0) {
				if (Point2D.X_ORDER.compare(p, ptr.point) > 0) {
					ptr = ptr.right;
				} else {
					ptr = ptr.left;
				}
				pingpong_state = 1;
			} else {
				if (Point2D.Y_ORDER.compare(p, ptr.point) > 0) {
					ptr = ptr.right;
				} else {
					ptr = ptr.left;
				}
				pingpong_state = 0;
			}
		  }
	   }
	   return false;
   }
   
   public void draw() { // draw all points to standard draw 
	   draw(this.root);
   }
   
   private void draw(Node root) {
	   if (root.left != null) {
		   draw(root.left);
	   }
	   root.point.draw();
	   if (root.right != null) {
		   draw(root.right);
	   }
   }
   
   public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle
	   TreeSet<Point2D> return_set = new TreeSet<Point2D>();
	   range_tranverse(rect, this.root, return_set);
	   return return_set;
   }
   
   private void range_tranverse(RectHV rect, Node root, TreeSet<Point2D> return_set_p) {
	   if (!rect.intersects(root.rect)) {
		   return;
	   }
	   if (root.left != null) {
		   range_tranverse(rect, root.left, return_set_p);
	   }
	   if (root.right != null) {
		   range_tranverse(rect, root.right, return_set_p);
	   }
	   if (rect.contains(root.point)) {
		   return_set_p.add(root.point);
	   }
   }
   
   public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
	   Point2D nearest_point = null;
	   double best_dis = Double.POSITIVE_INFINITY;
	   return nearest_tranverse(this.root,p,nearest_point, best_dis);
   }
   
   private Point2D nearest_tranverse(Node root, Point2D p_p, Point2D nearest_point_p, double best_dis_p){
	    if ( root.rect.distanceSquaredTo(p_p) > best_dis_p) {
	    	return nearest_point_p;
	    }
	    if (root.left != null) {
	    	nearest_point_p = nearest_tranverse(root.left, p_p, nearest_point_p, best_dis_p);
	    	best_dis_p = nearest_point_p.distanceSquaredTo(p_p);
	    }
	    if (root.right != null) {
	    	nearest_point_p = nearest_tranverse(root.right, p_p, nearest_point_p, best_dis_p);
	    	best_dis_p = nearest_point_p.distanceSquaredTo(p_p);
	    }
	    if (root.point.distanceSquaredTo(p_p) < best_dis_p) {
	    	return root.point;
	    } else {
	    	return nearest_point_p;
	    }
   }
   
   public static void main(String[] args) { // unit testing of the methods (optional) 
   }
}