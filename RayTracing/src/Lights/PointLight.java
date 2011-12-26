package Lights;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;
import raytracer.Vector;

public class PointLight extends Light {
	
	private Vector position = new Vector(0,0,0);
	private Vector attenuation = new Vector(1,0,0);
	
	public PointLight(Vector position){
		this.position = position;
	}
	
	public PointLight() {
		
	}

	public Vector getPosition() {
		return position;
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public Vector getAttenuation() {
		return attenuation;
	}
	
	public void setAttenuation(Vector attenuation) {
		this.attenuation = attenuation;
	}

	@Override
	public Vector findLightImpact(Scene scene, Ray ray, Intersection hit) { // nothing intersects with the ray from the object to the light
		Vector hitToLight = position.substract(hit.getMinIntPoint().getLocation());
		Vector towardsLight = hitToLight.normalize();
		Ray rayFromObjToLight = new Ray(hit.getMinIntPoint().getLocation(), towardsLight);
		Intersection objToLightInt = Scene.findInteresction(scene, rayFromObjToLight, hit.getMinIntPoint().getGeom());
		
		if (objToLightInt.getMinIntPoint() == null) {
			Vector col = calcColor(scene, ray, hit, towardsLight);
			double dist = hitToLight.length();
			double factor = attenuation.getDoubleX() 
					+ attenuation.getDoubleY() * dist
					+ attenuation.getDoubleZ() * dist * dist;
			if ( factor == 0 ) return new Vector(0,0,0);
			return col.scalarMult(1.0 / factor);
		}
		else{
			double distToInt = rayFromObjToLight.getOrigin().distance(objToLightInt.getMinIntPoint().getLocation());
			double dist = rayFromObjToLight.getOrigin().distance(this.position);
			if ( distToInt < dist )
				return new Vector (0,0,0);
		 	Vector col = calcColor(scene, ray, hit, towardsLight);
		 	double factor = attenuation.getDoubleX() 
		 			+ attenuation.getDoubleY() * dist
		 			+ attenuation.getDoubleZ() * dist * dist;
			if ( factor == 0 ) return new Vector(0,0,0);
			return col.scalarMult(1.0 / factor);
			 
		}
	}
	
}
