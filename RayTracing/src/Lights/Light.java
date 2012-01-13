package Lights;

import java.awt.image.BufferedImage;

import org.eclipse.swt.graphics.ImageData;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.RayTracer;
import raytracer.Scene;
import raytracer.Surface;
import raytracer.Vector;
import raytracer.Surface.mtlType;

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
		
		Vector N = hit.getMinIntPoint().getNormal();
		double NL = N.dotProduct(L.normalize());
		
		// if the ray comes from the back of the surface,
		// or the light is behind it, return BLACK
		if ( (NL < 0.0) || (ray.getDirection().dotProduct(N) > 0.0) ){
			// Hit point is on a different side of the surface than the eye
			return new Vector(0,0,0);
		}
		
		Surface surface = hit.getMinIntPoint().getGeom().getSurface();
		Vector Kd = new Vector(0,0,0);
		if(surface.getSurfaceType() ==  mtlType.FLAT){
			Kd = surface.getMtlDiffuse();
		}
		else if (surface.getSurfaceType() ==  mtlType.TEXTURE){
			
			BufferedImage textureImg = surface.getTextureImg();
			Vector paramVec = hit.getMinIntPoint().getGeom().getTextureParam(hit);
			int x = (int) (paramVec.getDoubleX() * (textureImg.getWidth()-1));
			int y = (int) (paramVec.getDoubleY() * (textureImg.getHeight()-1));
			//System.out.println("getting pixel at (" + x + ", " + y +") (paramVec:" + paramVec + ")");
			int rgb = textureImg.getRGB(x, y);
			double r = ((rgb & 0xFF0000)>>16);
			double g = ((rgb & 0x00FF00)>>8 );
			double b = (rgb & 0x0000FF);

			Kd = new Vector(r,g,b).scalarMult(1.0/255.0);
		}
		else if (surface.getSurfaceType() ==  mtlType.CHECKERS){
			Vector paramVec = hit.getMinIntPoint().getGeom().getTextureParam(hit);
			int u = (int)(paramVec.getDoubleX() / surface.getCheckersSize());
			int v = (int)(paramVec.getDoubleY() / surface.getCheckersSize());
			if ( (u+v) % 2 == 0) 
				Kd = surface.getCheckersDiffuse2();
			else
				Kd = surface.getCheckersDiffuse1();
		}
		
		Vector Ks = surface.getMtlSpecular();
		double n = surface.getMtlShininess();
		Vector R = Vector.vectorReflection(L,N);
		Vector V = ray.getDirection().scalarMult(-1).normalize();
		double VR = V.dotProduct(R);
		
//		if(NL<0) NL*=-1;
		if(VR<0) VR=0;
		
		// TODO: take in consideration the distance of the lighting 
		//Kd= Kd.scalarMult(1.0-RayTracer.GL_FACTOR);
		double colorX = intensity.getDoubleX()*((Kd.getDoubleX()*NL) + (Ks.getDoubleX()*Math.pow(VR, n)));
		double colorY = intensity.getDoubleY()*((Kd.getDoubleY()*NL) + (Ks.getDoubleY()*Math.pow(VR, n)));
		double colorZ = intensity.getDoubleZ()*((Kd.getDoubleZ()*NL) + (Ks.getDoubleZ()*Math.pow(VR, n)));
		return new Vector(colorX,colorY,colorZ);
	}
	
	
}
