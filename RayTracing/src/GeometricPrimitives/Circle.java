package GeometricPrimitives;

import raytracer.Auxiliary;
import raytracer.IntersectioPoint;
import raytracer.Ray;
import raytracer.Vector;

public class Circle extends GeometricPrimitive {

	Vector center = new Vector(0,0,0);
	Vector normal = new Vector(0,0,1);
	double radius = 1;
	
	
	public Circle(Vector center, Vector normal, double radius) {
		super();
		this.center = center;
		this.normal = normal.normalize();
		this.radius = radius;
	}


	public Circle() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public IntersectioPoint getIntersection(Ray r) {
		Vector intPoint = Auxiliary.findIntersectionOfVectorAndHalfPlane(r, getNormal(), getCenter());
		if ( intPoint == null ) return null;
		if ( intPoint.distance(center) <= getRadius() )
			return new IntersectioPoint(this, intPoint, getNormal());
		return null;
	}


	public Vector getCenter() {
		return center;
	}


	public Vector getNormal() {
		return normal;
	}


	public double getRadius() {
		return radius;
	}
	
	public void setCenter(Vector center) {
		this.center = center;
	}


	public void setNormal(Vector normal) {
		this.normal = normal;
	}


	public void setRadius(double radius) {
		this.radius = radius;
	}

}
