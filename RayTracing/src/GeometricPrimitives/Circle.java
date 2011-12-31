package GeometricPrimitives;

import raytracer.Auxiliary;
import raytracer.IntersectioPoint;
import raytracer.Intersection;
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
		if ( intPoint.distance(center) <= getRadius() ){
			Vector normal = getNormal();
			if ( normal.dotProduct(r.getDirection()) > 0.0 )
				normal = normal.scalarMult(-1.0);
			return new IntersectioPoint(this, intPoint, normal);
		}
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

	@Override
	public Vector getTextureParam(Intersection hit) {
		Vector intPt = hit.getMinIntPoint().getLocation();
		double u = intPt.distance(center) / radius;
		Vector D1;
		D1 = getNormal().crossProduct(new Vector(0,1,0)).normalize();
		if ( D1.equals(new Vector(0,0,0)) ) // normal is parallel to X axis
			D1 = getNormal().crossProduct(new Vector(1,0,0)).normalize();
		Vector D2 = D1.crossProduct(getNormal()).normalize();
		double xCoord = intPt.dotProduct(D1);
		double yCoord = intPt.dotProduct(D2);
		double theta = Math.atan2(yCoord,xCoord);
		double v;
		if ( theta >= 0 )
			v = theta / (2.0*Math.PI);
		else
			v = ((2.0*Math.PI) + theta) / (2.0*Math.PI);
		
		return new Vector(v,u,0);
	}
}
