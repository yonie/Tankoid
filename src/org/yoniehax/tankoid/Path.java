package org.yoniehax.tankoid;

import java.text.DecimalFormat;

import javax.swing.text.NumberFormatter;

public class Path {

	private Place startingPlace;
	private Place destinationPlace;
	private double startingAngle;
	private double movementSpeed;
	private double rotationSpeed;

	/**
	 * A <b>Path</b> is used to store a route a Tank takes from a starting place
	 * to a destination place. The route has a starting angle, a movement and
	 * Tank rotation speed.
	 * 
	 * @param startingPlace
	 *            the starting Place of the Path.
	 * @param destinationPlace
	 *            the destination Place of the Path.
	 * @param startingAngle
	 *            the starting angle of the Tank.
	 * @param movementSpeed
	 *            the movement speed used to traverse this path.
	 * @param rotationSpeed
	 *            the rotation speed of the Tank.
	 */
	public Path(Place startingPlace, Place destinationPlace, double startingAngle, double movementSpeed,
			double rotationSpeed) {
		this.startingPlace = startingPlace;
		this.destinationPlace = destinationPlace;
		this.startingAngle = startingAngle;
		this.movementSpeed = movementSpeed;
		this.rotationSpeed = rotationSpeed;
	}

	/**
	 * Gets the movement duration based on the distance to travel and movement
	 * speed.
	 * 
	 * @return movement duration for this Path in milliseconds.
	 */
	public double getMovementDuration() {
		return (getDistance() / movementSpeed) * 1000;
	}

	/**
	 * Gets the movement duration based on the distance to travel and movement
	 * speed, incorporating a movement speed correction percentage.
	 * 
	 * @param speedCorrection
	 *            percentual correction, 100 is no correction
	 * @return movement duration for this Path in milliseconds.
	 */
	public double getMovementDuration(double speedCorrection) {
		return (getDistance() / ((movementSpeed * speedCorrection) / 100)) * 1000;
	}

	/**
	 * Gets the rotation duration based on the current and required angle.
	 * 
	 * @return the rotation duration for this Path in milliseconds.
	 */
	public double getRotationDuration() {
		return Math.abs(getRotationAngle() / rotationSpeed) * 1000;
	}

	/**
	 * Gets the rotation duration based on the current and required angle,
	 * incorporating a rotation speed correction percentage.
	 * 
	 * @param speedCorrection
	 *            percentual correction, 100 is no correction
	 * @return the rotation duration for this Path in milliseconds.
	 */
	public double getRotationDuration(double speedCorrection) {
		return Math.abs(getRotationAngle() / ((rotationSpeed * 100) / speedCorrection)) * 1000;
	}

	/**
	 * Gets the rotation angle to use for this Path. This angle is relative to
	 * the given starting angle.
	 * 
	 * @return the rotation angle.
	 */
	public double getRotationAngle() {

		double distanceX = destinationPlace.getX() - startingPlace.getX();
		double distanceY = -(destinationPlace.getY() - startingPlace.getY());

		// do math magic to get new angle
		double newAngle = Math.toDegrees(Math.atan2(distanceY, distanceX));

		// incorporate the starting angle
		double rotationAngle = (newAngle - startingAngle);

		// fix angles that end up lower than -180 degrees
		if (rotationAngle < -180)
			rotationAngle += 360;
		if (rotationAngle > 180)
			rotationAngle -= 360;

		assert (rotationAngle <= 180 && rotationAngle >= -180);

		return rotationAngle;
	}

	/**
	 * Gets the movement speed for this Path.
	 * 
	 * @return the movement speed.
	 */
	public double getMovementSpeed() {
		return movementSpeed;
	}

	/**
	 * Gets the starting Place for this Path.
	 * 
	 * @return the starting Place.
	 */
	public Place getStartingPlace() {
		return startingPlace;
	}

	/**
	 * Gets the destination Place for this Path.
	 * 
	 * @return the destination Place.
	 */
	public Place getDestinationPlace() {
		return destinationPlace;
	}

	/**
	 * Gets the distance between the starting Place and destination Place of
	 * this Path. Note that the actual distance might vary based on the route
	 * the Tank will choose to take.
	 * 
	 * @return the distance between the starting Place and destination Place of
	 *         this Path.
	 */
	public double getDistance() {
		double xDistance = Math.abs(destinationPlace.getX() - startingPlace.getX());
		double yDistance = Math.abs(destinationPlace.getY() - startingPlace.getY());
		return (Math.sqrt((xDistance * xDistance) + (yDistance * yDistance)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "From: (" + startingPlace + "), to: (" + destinationPlace + "), distance: " + new DecimalFormat("#.##").format(getDistance())
				+ ", rotation angle: " + new DecimalFormat("#.##").format(getRotationAngle());
	}

	/**
	 * Gets the Tank rotation speed.
	 * 
	 * @return the rotation speed.
	 */
	public double getRotationSpeed() {
		return rotationSpeed;
	}

	/**
	 * Gets the given starting angle.
	 * 
	 * @return the starting angle.
	 */
	public double getStartingAngle() {
		return startingAngle;
	}
}
