package com.wirsching.math;

public class Math {

	public static float getDistance(Point2f p1, Point2f p2) {
		return Math.sqrt(
				(p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
	}

	public static float sqrt(float f) {
		return (float) java.lang.Math.sqrt((double) f);
	}

	public static float sin(float f) {
		return (float) java.lang.Math.sin((double) toRadians(f));
	}

	public static float cos(float f) {
		return (float) java.lang.Math.cos((double) toRadians(f));
	}

	public static float getAngle(Point2f p1, Point2f p2) {
		float angle = (float) Math.toDegrees((Float) Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()));
	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}
	
	public static float abs(float f) {
		return java.lang.Math.abs(f);
	}

	public static float toDegrees(float rad) {
		return (float) java.lang.Math.toDegrees((double) rad);
	}
	
	public static float toRadians(float deg) {
		return (float) java.lang.Math.toRadians((double) deg);
	}
	
	public static Object atan2(float f, float g) {
		return java.lang.Math.atan2(f, g);
	}

}
