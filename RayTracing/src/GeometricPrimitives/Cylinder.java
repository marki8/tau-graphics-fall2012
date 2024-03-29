package GeometricPrimitives;

import raytracer.Auxiliary;
import raytracer.IntersectioPoint;
import raytracer.Intersection;
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
		
		if ( (t[0] < 0.0) && (t.length > 1) && (t[1] < 0.0) )
			return null;
		else if ( t[0] < 0.0 ){
			if ( t.length < 2 ) return null;
			t = new double[]{t[1]};
		}
		Vector int1 = r.retrievePoint(t[0]);
		if ( t.length > 1 ) {
			if ( t[1] > 0.0 ){
				Vector int2 = r.retrievePoint(t[1]);
				if ( int1.distance(r.getOrigin()) > int2.distance(r.getOrigin())) {
					if ( pointOnCylinder(int2) ) {
						Vector onAxis = Auxiliary.findNearestPointOnLine(int2, start, direction);
						Vector normal = int2.substract(onAxis);
						if ( normal.dotProduct(r.getDirection()) > 0.0 )
							normal = normal.scalarMult(-1.0);
						return new IntersectioPoint(this, int2, normal);//getNormal(int2));
					}
				}
			}
		}
		if ( pointOnCylinder(int1) ) {
			Vector onAxis = Auxiliary.findNearestPointOnLine(int1, start, direction);
			Vector normal = int1.substract(onAxis);
			if ( normal.dotProduct(r.getDirection()) > 0.0 )
				normal = normal.scalarMult(-1.0);
			return new IntersectioPoint(this, int1, normal);//getNormal(int1));
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
		this.direction = direction.normalize();
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



	@Override
	public Vector getTextureParam(Intersection hit) {
		Vector intPt = hit.getMinIntPoint().getLocation().substract(getStart());
		double u = intPt.dotProduct(getDirection()) / getLength();
		Vector D1;
		D1 = getDirection().crossProduct(new Vector(0,1,0)).normalize();
		if ( D1.equals(new Vector(0,0,0)) ) // normal is parallel to X axis
			D1 = getDirection().crossProduct(new Vector(1,0,0)).normalize();
		Vector D2 = D1.crossProduct(getDirection()).normalize();
		double xCoord = intPt.dotProduct(D1);
		double yCoord = intPt.dotProduct(D2);
		double theta = Math.atan2(yCoord,xCoord);
		double v;
		if ( theta >= 0.0 )
			v = theta / (2.0*Math.PI);
		else
			v = ((2.0*Math.PI) + theta) / (2.0*Math.PI);
		if ( u < 0.0 )
			u = 0;
		return new Vector(v,u,0);
	}

}
