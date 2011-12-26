package GeometricPrimitives;

import java.util.ArrayList;
import java.util.List;

import raytracer.IntersectioPoint;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Vector;

public class Box extends GeometricPrimitive {

	private List<Rectangle> rectList = new ArrayList<>(6);
	private Vector p0 = new Vector(0,0,0);
	private Vector p1 = new Vector(0,0,0); 
	private Vector p2 = new Vector(0,0,0);
	private Vector p3 = new Vector(0,0,0);
	
	/**
	 * Construct the hexahedron defined by (p1-p0)x(p2-p0)x(p3-p0)
	 * @param p0
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Box(Vector p0, Vector p1, Vector p2, Vector p3){
		this.setP0(p0);
		this.setP1(p1);
		this.setP2(p2);
		this.setP3(p3);
		updateSides();
	}

	public Box() {
		// TODO Auto-generated constructor stub
	}

	private void updateSides(){
		// Immediate sides
		rectList.add( new Rectangle(p0, p2, p1) );
		rectList.add( new Rectangle(p0, p1, p3) );
		rectList.add( new Rectangle(p0, p3, p2) );
		
		Vector p2p0 = p2.substract(p0);
		Vector p3p0 = p3.substract(p0);
		
		// calculated sides
		rectList.add( new Rectangle(p3, p3.add(p2p0), p1.add(p3p0)) );
		rectList.add( new Rectangle(p1, p1.add(p3p0), p1.add(p2p0)) );
		rectList.add( new Rectangle(p2, p3.add(p2p0), p1.add(p2p0)) );
	}
	
	@Override
	public IntersectioPoint getIntersection(Ray r) {
		Intersection hit = new Intersection(r.getOrigin());
		for ( Rectangle rect : getRectList() ){
			IntersectioPoint intPoint = rect.getIntersection(r);
			if ( intPoint != null ){
				hit.addPoint(intPoint);
			}
		}
		IntersectioPoint rectInt = hit.getMinIntPoint();
		if ( rectInt == null )
			return null;
		return new IntersectioPoint(this, rectInt.getLocation(), rectInt.getNormal());
	}
	
	public List<Rectangle> getRectList() {
		return rectList;
	}

	public Vector getP0() {
		return p0;
	}

	public void setP0(Vector p0) {
		this.p0 = p0;
		updateSides();
	}

	public Vector getP1() {
		return p1;
	}

	public void setP1(Vector p1) {
		this.p1 = p1;
		updateSides();
	}

	public Vector getP2() {
		return p2;
	}

	public void setP2(Vector p2) {
		this.p2 = p2;
		updateSides();
	}

	public Vector getP3() {
		return p3;
	}

	public void setP3(Vector p3) {
		this.p3 = p3;
		updateSides();
	}

}
