package Lights;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;
import raytracer.Surface;
import raytracer.Vector;

public abstract class Light {
	
	private Vector intensity = new Vector(255,255,255);

	public Vector getIntensity() {
		return intensity;
	}

	public void setIntensity(Vector intensity) {
		this.intensity = intensity.scalarMult(255.0);
	}

	
	public abstract Vector findLightImpact(Scene scene, Ray ray, Intersection hit);
	
	public Vector calcColor(Scene scene, Ray ray, Intersection hit, Vector L) {
		
		Surface surface = hit.getMinIntPoint().getGeom().getSurface();
		Vector Kd = surface.getMtlDiffuse(); // ONLY TRUE IF FLAT!!!
		Vector Ks = surface.getMtlSpecular();
		double n = surface.getMtlShininess();
		Vector N = hit.getMinIntPoint().getNormal().normalize();
		Vector R = Vector.vectorReflection(L,N);
		Vector V = ray.getDirection().scalarMult(-1).normalize();
		double VR = V.dotProduct(R);
		double NL = N.dotProduct(L.normalize());
		
		if(NL<0) NL*=-1;
		if(VR<0) VR=0;
		
		// TODO: take in consideration the distance of the lighting 
		double colorX = intensity.getX()*((Kd.getDoubleX()*NL) + (Ks.getDoubleX()*Math.pow(VR, n)));
		double colorY = intensity.getY()*((Kd.getDoubleY()*NL) + (Ks.getDoubleY()*Math.pow(VR, n)));
		double colorZ = intensity.getZ()*((Kd.getDoubleZ()*NL) + (Ks.getDoubleZ()*Math.pow(VR, n)));
		return new Vector(colorX,colorY,colorZ);
	}
	
	
}
