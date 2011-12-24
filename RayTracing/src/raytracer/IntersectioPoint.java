package raytracer;

import GeometricPrimitives.GeometricPrimitive;

public class IntersectioPoint {
	GeometricPrimitive geom;
	Vector location;
	Vector normal;
	// color? material? ...
	
	public IntersectioPoint(GeometricPrimitive geom, Vector location,
			Vector normal) {
		super();
		this.geom = geom;
		this.location = location;
		this.normal = normal.normalize();
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
	
}
