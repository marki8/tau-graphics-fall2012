package GeometricPrimitives;

import raytracer.Ray;
import raytracer.Surface;
import raytracer.Vector;

public abstract class GeometricPrimitive {
	
	private Surface surface;

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}
	
	public abstract Vector getIntersection(Ray r);
	
	public abstract Vector findNormal();

	
}
