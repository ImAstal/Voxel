package utils;

import org.lwjgl.util.vector.Vector3f;

public class Ray {

	private Vector3f position, direction;
	
	public Ray(Vector3f position, Vector3f direction) {
		this.position = position;
		this.position.y = - this.position.y;
		this.direction = direction;
	}
	
	public Vector3f GetPosition() {
		return position;
	}
	
	public Vector3f GetDirection() {
		return direction;
	}
	
	public Vector3f GetNormal() {
		Vector3f out = new Vector3f();
		direction.normalise(out);
		return out;
	}
	
	public Vector3f Move(float accuracy) {
		this.position.x += this.direction.x * accuracy;
		this.position.y += this.direction.y * accuracy;
		this.position.z += this.direction.z * accuracy;
		return this.position;
	}
	
	public Vector3f Move(Vector3f vector) {
		this.position.x += vector.x;
		this.position.y += vector.y;
		this.position.z += vector.z;
		return this.position;
	}
}
