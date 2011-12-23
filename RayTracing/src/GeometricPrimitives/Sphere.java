package GeometricPrimitives;

import raytracer.IntersectioPoint;
import raytracer.Ray;
import raytracer.Vector;

public class Sphere extends GeometricPrimitive {

	Vector center;
	double radius;
	
	
	public Sphere(Vector center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}


	@Override
	public IntersectioPoint getIntersection(Ray r) {
		
		// is there an intersection?
		// find the distance between the ray and the center 
		double d = r.getDirection().dotProduct(r.getOrigin());
		double distance = (d + Math.abs( r.getDirection().dotProduct(center) )) / r.getDirection().length();
		if ( distance > radius )
			return null;
		
		
		
		return null;
	}


	public Vector getCenter() {
		return center;
	}


	public double getRadius() {
		return radius;
	}

}
