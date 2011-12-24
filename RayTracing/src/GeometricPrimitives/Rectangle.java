package GeometricPrimitives;

import raytracer.Auxiliary;
import raytracer.IntersectioPoint;
import raytracer.Ray;
import raytracer.Vector;

public class Rectangle extends GeometricPrimitive{
	
	private Vector p0, p1, p2, p3, normal;
	
	/**
	 * Constructs a parallelogram determined by p1-p0, p2-p0
	 * @param p0
	 * @param p1
	 * @param p2
	 */
	public Rectangle(Vector p0, Vector p1, Vector p2){
		this.setP0(p0);
		this.setP1(p1);
		this.setP2(p2);
		p3 = p1.add(p2.substract(p0));
		setNormal((p0.substract(p1)).crossProduct(p0.substract(p2))); // (p0-p1)X(p0-p2) normalized
	}
	
	public Vector getP0() {
		return p0;
	}

	public void setP0(Vector p0) {
		this.p0 = p0;
	}

	public Vector getP1() {
		return p1;
	}

	public void setP1(Vector p1) {
		this.p1 = p1;
	}

	public Vector getP2() {
		return p2;
	}

	public void setP2(Vector p2) {
		this.p2 = p2;
	}
	
	
	
	@Override
	public IntersectioPoint getIntersection(Ray r) {
		
		Vector pointOnHalfPlane = Auxiliary.findIntersectionOfVectorAndHalfPlane(r, getNormal(), p1);
		
		// TODO: DEAL WITH ALL THE EDGE CASES INFINITY ETC
		
		if(pointOnHalfPlane == null){
			return null;
		}
		
		if(pointOnRect(pointOnHalfPlane)){
			return new IntersectioPoint(this, pointOnHalfPlane, getNormal());
		}
		
		else{
			return null;
		}
	}

	private boolean pointOnRect(Vector p) {
		
		Vector v0 = p2.substract(p0);
		Vector v1 = p1.substract(p0);
		Vector v2 = p.substract(p0);
		double v1v1 = v1.dotProduct(v1);
		double v2v0 = v2.dotProduct(v0);
		double v1v0 = v1.dotProduct(v0);
		double v2v1 = v2.dotProduct(v1);
		double v0v0 = v0.dotProduct(v0);
		double v0v1 = v0.dotProduct(v1);
		
		//u = ((v1.v1)(v2.v0)-(v1.v0)(v2.v1)) / ((v0.v0)(v1.v1) - (v0.v1)(v1.v0))
		//v = ((v0.v0)(v2.v1)-(v0.v1)(v2.v0)) / ((v0.v0)(v1.v1) - (v0.v1)(v1.v0))
		double u = (v1v1*v2v0 - v1v0*v2v1) / (v0v0*v1v1 - v0v1*v1v0);
		double v = (v0v0*v2v1 - v0v1*v2v0) / (v0v0*v1v1 - v0v1*v1v0);

		return (u >= 0) && (v >= 0) && (u <= 1) && (v <= 1); 
	}

	public Vector getNormal() {
		return normal;
	}

	public void setNormal(Vector normal) {
		this.normal = normal.normalize();
	}
	
}
