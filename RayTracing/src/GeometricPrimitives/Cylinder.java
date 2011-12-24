package GeometricPrimitives;

import raytracer.IntersectioPoint;
import raytracer.Ray;
import raytracer.Vector;

public class Cylinder extends GeometricPrimitive {

	Vector start;
	Vector direction;
	double length;
	double radius;
	
	
	
	public Cylinder(Vector start, Vector direction, double length, double radius) {
		super();
		this.start = start;
		this.direction = direction;
		this.length = length;
		this.radius = radius;
	}



	public Cylinder() {
		// TODO Auto-generated constructor stub
	}



	@Override
	public IntersectioPoint getIntersection(Ray r) {
//		if ( Math.abs( r.getDirection().dotProduct(direction) ) == 1.0 ) { // parallel
//			return null; // in this case, no intersection
//			
//		}
		return null;
	}



	public Vector getStart() {
		return start;
	}



	public void setStart(Vector start) {
		this.start = start;
	}



	public Vector getDirection() {
		return direction;
	}



	public void setDirection(Vector direction) {
		this.direction = direction;
	}



	public double getLength() {
		return length;
	}



	public void setLength(double length) {
		this.length = length;
	}



	public double getRadius() {
		return radius;
	}



	public void setRadius(double radius) {
		this.radius = radius;
	}

}
