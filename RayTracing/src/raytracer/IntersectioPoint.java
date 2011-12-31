package raytracer;

import GeometricPrimitives.GeometricPrimitive;

public class IntersectioPoint {
	GeometricPrimitive geom;
	Vector location;
	Vector normal;
	private GeometricPrimitive innerPrimitive = null;
	
	// color? material? ...
	
	public IntersectioPoint(GeometricPrimitive geom, Vector location,
			Vector normal) {
		super();
		this.geom = geom;
		this.location = location;
		this.normal = normal.normalize();
	}
	
	public IntersectioPoint(GeometricPrimitive geom, Vector location,
			Vector normal, GeometricPrimitive innerPrimitive) {
		super();
		this.geom = geom;
		this.location = location;
		this.normal = normal.normalize();
		this.setInnerPrimitive(innerPrimitive);
	}

	public GeometricPrimitive getGeom() {
		return geom;
	}

	public Vector getLocation() {
		return location;
	}
	
	public Vector getNormal() {
		return normal;
	}

	public GeometricPrimitive getInnerPrimitive() {
		return innerPrimitive;
	}

	public void setInnerPrimitive(GeometricPrimitive innerPrimitive) {
		this.innerPrimitive = innerPrimitive;
	}
	
}
