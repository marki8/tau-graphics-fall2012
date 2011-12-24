package GeometricPrimitives;

import raytracer.Auxiliary;
import raytracer.IntersectioPoint;
import raytracer.Ray;
import raytracer.Vector;

public class Cylinder extends GeometricPrimitive {

	Vector start = new Vector(0,0,0);
	Vector direction = new Vector(1,0,0);
	double length = 1;
	double radius = 1;
	
	
	
	public Cylinder(Vector start, Vector direction, double length, double radius) {
		super();
		this.start = start;
		this.direction = direction.normalize();
		this.length = length;
		this.radius = radius;
	}



	public Cylinder() {
	}



	@Override
	public IntersectioPoint getIntersection(Ray r) {
		//Vector AB = direction.scalarMult(length);
		Vector AO = r.getOrigin().substract(start);
		Vector AOxAB = AO.crossProduct(direction);
		Vector VxAB  = r.getDirection().crossProduct(direction);
		double a      = VxAB.dotProduct(VxAB);
		double b      = VxAB.dotProduct(AOxAB) * 2.0;
		double c      = AOxAB.dotProduct(AOxAB) - (radius*radius);
		
		double[] t = Auxiliary.quadricRoots(a,b,c);
		if ( t == null ) return null;
		
		Vector int1 = r.retrievePoint(t[0]);
		if ( t.length > 1 ) {
			Vector int2 = r.retrievePoint(t[1]);
			if ( int1.distance(r.getOrigin()) > int2.distance(r.getOrigin())) {
				if ( pointOnCylinder(int2) ) {
					Vector onAxis = Auxiliary.findNearestPointOnLine(int2, start, direction);
					Vector normal = int2.substract(onAxis);
					return new IntersectioPoint(this, int2, normal);
				}
			}
		}
		if ( pointOnCylinder(int1) ) {
			Vector onAxis = Auxiliary.findNearestPointOnLine(int1, start, direction);
			Vector normal = int1.substract(onAxis);
			return new IntersectioPoint(this, int1, normal);
		}
		return null;
	}



	private boolean pointOnCylinder(Vector point) {
		Vector diff = point.substract(start);
		double projection = direction.dotProduct(diff);
		return ((projection >= 0) && (projection <= length));
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
