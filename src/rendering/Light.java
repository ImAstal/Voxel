package rendering;

import org.lwjgl.util.vector.Vector3f;

public class Light {

	private Vector3f direction;
	private Vector3f color;
	
	public Light(Vector3f position, Vector3f color) {
		this.direction = position;
		this.color = color;
	}
	
	public Vector3f GetDirection() {
		return this.direction;
	}
	
	public Vector3f GetColor() {
		return this.color;
	}
}
