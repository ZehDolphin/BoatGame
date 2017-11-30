package com.wirsching.entities.turrets;

public class CouldNotFindProjectileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouldNotFindProjectileException() {
	}

	public CouldNotFindProjectileException(String message) {
		super("Could not find the projectile '" + message + "'.");
	}


}
