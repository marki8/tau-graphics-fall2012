package raytracer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.eclipse.swt.graphics.ImageData;

public class Surface {
	
	public enum mtlType {
		FLAT,
		CHECKERS,
		TEXTURE
	}
	private mtlType surfaceType = mtlType.FLAT;
	
	private Vector mtlDiffuse  = new Vector(0.8,0.8,0.8); //Kd
	private Vector mtlSpecular = new Vector(1,1,1); //Ks
	private Vector mtlAmbient = new Vector(0.1,0.1,0.1); //Ka 
	private Vector mtlEmission = new Vector(0,0,0);
	private double mtlShininess = 100;
	private double checkersSize = 0.1;
	private Vector checkersDiffuse1 = new Vector(1,1,1); 
	private Vector checkersDiffuse2 = new Vector(0.1,0.1,0.1);
	private String texture = null;
	private double reflectance = 0;
	
	private BufferedImage textureImg;
	public Surface() {
		
	}
	
	public Vector getMtlDiffuse() {
		return mtlDiffuse;
	}
	
	public void setMtlDiffuse(Vector mtlDiffuse) {
		this.mtlDiffuse = mtlDiffuse;
	}
	
	public Vector getMtlSpecular() {
		return mtlSpecular;
	}
	
	public void setMtlSpecular(Vector mtlSpecular) {
		this.mtlSpecular = mtlSpecular;
	}
	
	public Vector getMtlAmbient() {
		return mtlAmbient;
	}
	
	public void setMtlAmbient(Vector mtlAmbient) {
		this.mtlAmbient = mtlAmbient;
	}
	
	public Vector getMtlEmission() {
		return mtlEmission;
	}
	
	public void setMtlEmission(Vector mtlEmission) {
		this.mtlEmission = mtlEmission;
	}
	
	public double getMtlShininess() {
		return mtlShininess;
	}
	
	public void setMtlShininess(double mtlShininess) {
		this.mtlShininess = mtlShininess;
	}
	
	public double getCheckersSize() {
		return checkersSize;
	}
	
	public void setCheckersSize(double checkersSize) {
		this.checkersSize = checkersSize;
	}
	
	public Vector getCheckersDiffuse1() {
		return checkersDiffuse1;
	}
	
	public void setCheckersDiffuse1(Vector checkersDiffuse1) {
		this.checkersDiffuse1 = checkersDiffuse1;
	}
	
	public Vector getCheckersDiffuse2() {
		return checkersDiffuse2;
	}
	
	public void setCheckersDiffuse2(Vector checkersDiffuse2) {
		this.checkersDiffuse2 = checkersDiffuse2;
	}
	
	public String getTexture() {
		return texture;
	}
	
	public void setTexture(String texture) {
		this.texture = texture;
		//textureImg = ImageIO.read(new File(texture));
		try {
			setTextureImg(ImageIO.read(new File(texture)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public double getReflectance() {
		return reflectance;
	}
	
	public void setReflectance(double reflectance) {
		this.reflectance = reflectance;
	}

	public mtlType getSurfaceType() {
		return surfaceType;
	}

	public void setSurfaceType(String type) {
		if(type.equals("flat")) surfaceType = mtlType.FLAT;
		if(type.equals("checkers")) surfaceType = mtlType.CHECKERS;
		if(type.equals("texture")) surfaceType = mtlType.TEXTURE;
	}

	public BufferedImage getTextureImg() {
		return textureImg;
	}

	public void setTextureImg(BufferedImage textureImg) {
		this.textureImg = textureImg;
	}
}
