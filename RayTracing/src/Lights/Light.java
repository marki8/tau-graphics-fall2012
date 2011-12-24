package Lights;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;
import raytracer.Surface;
import raytracer.Vector;

public abstract class Light {
	
	private Vector intensity = new Vector(1,1,1);

	public Vector getIntensity() {
		return intensity;
	}

	public void setIntensity(Vector intensity) {
		this.intensity = intensity.scalarMult(255.0);
	}

	public abstract Vector findLightImpact(Scene scene, Ray ray, Intersection hit);
	
	public Vector calcColor(Scene scene, Ray ray, Intersection hit, Vector L) {
		
		Vector color = new Vector(0,0,0);
		Surface surface = hit.getMinIntPoint().getGeom().getSurface();
		Vector Kd = surface.getMtlDiffuse(); // ONLY TRUE IF FLAT!!!
		Vector Ks = surface.getMtlSpecular();
		double n = surface.getMtlShininess();
		Vector N = hit.getMinIntPoint().getNormal().normalize();
		Vector R = Vector.vectorReflection(L,N);
		Vector V = ray.getDirection().scalarMult(-1).normalize();
		double VR = V.dotProduct(R);
		double NL = N.dotProduct(L);
		
		color.setX(intensity.getX()*(Kd.getDoubleX()*NL) + Ks.getX()*Math.pow(VR, n));
		color.setY(intensity.getY()*(Kd.getDoubleY()*NL) + Ks.getY()*Math.pow(VR, n));
		color.setZ(intensity.getZ()*(Kd.getDoubleZ()*NL) + Ks.getZ()*Math.pow(VR, n));
		
		return color;
	}
	
	
}
