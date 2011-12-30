package GeometricPrimitives;

import raytracer.IntersectioPoint;
import raytracer.Ray;
import raytracer.Vector;

public class Sphere extends GeometricPrimitive {

	private Vector center;
	private double radius;
	
	
	public Sphere(Vector center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}


	public Sphere() {
		super();
	}


	@Override
	public IntersectioPoint getIntersection(Ray r) {

		// Algorithm from lecture slides 4a
		Vector L = center.substract(r.getOrigin());
		double t_CA = L.dotProduct(r.getDirection());
		if ( t_CA < 0 ) return null;
		double d2 = L.dotProduct(L) - t_CA*t_CA;
		double r2 = radius*radius;
		if ( d2 > r2) return null;
		double t_hc = Math.sqrt(r2 - d2);
		
		Vector int1 = r.getOrigin().add(r.getDirection().scalarMult(t_CA-t_hc));
		Vector int2 = r.getOrigin().add(r.getDirection().scalarMult(t_CA+t_hc));
		
		if ( int1.distance(r.getOrigin()) < int2.distance(r.getOrigin()))
			return new IntersectioPoint(this, int1, int1.substract(center));
		else
			return new IntersectioPoint(this, int2, int2.substract(center));
//		// is there an intersection?
//		// find the distance between the ray and the center 
//		double d = r.getDirection().dotProduct(r.getOrigin());
//		double distance = (d + Math.abs( r.getDirection().dotProduct(center) )) / r.getDirection().length();
//		if ( distance > radius )
//			return null;
//		
//		
//		
//		return null;
	}


	public Vector getCenter() {
		return center;
	}


	public double getRadius() {
		return radius;
	}


	public void setCenter(Vector center) {
		this.center = center;
	}


	public void setRadius(double radius) {
		this.radius = radius;
	}


	@Override
	public Vector getParam(Vector interPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}
