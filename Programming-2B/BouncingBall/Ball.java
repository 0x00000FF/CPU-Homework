package me.patche.bouncingball;

public class Ball {
	private double velocity;
	private double pos;
	private boolean drop;
	
	public Ball() {
		pos = 0;
		velocity = 0;
		drop = true;
	}
	
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public double getPos() {
		return pos;
	}
	public void setPos(double pos) {
		this.pos = pos;
	}

	public boolean isDrop() {
		return drop;
	}

	public void setDrop(boolean drop) {
		this.drop = drop;
	}
}
