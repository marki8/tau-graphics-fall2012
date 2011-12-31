package GeometricPrimitives;

import raytracer.IntersectioPoint;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Surface;
import raytracer.Vector;

public abstract class GeometricPrimitive {
	
	private Surface surface = new Surface();

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}
	
	public abstract IntersectioPoint getIntersection(Ray r);
	
	public abstract Vector getTextureParam(Intersection hit);
}
