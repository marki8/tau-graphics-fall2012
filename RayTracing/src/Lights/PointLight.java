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
	public Vector findLightImpact(Scene scene, Ray ray, Intersection hit) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
