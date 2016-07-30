package utils;

import org.lwjgl.util.vector.Vector3f;

public class Hitbox {

	private float x1, x2, y1, y2, z1, z2;
	
	public Hitbox(float x, float y, float z, float width, float height, float depth) {
		this.x1 = x;
		this.x2 = x + width;
		this.y1 = y;
		this.y2 = y + height;
		this.z1 = z;
		this.z2 = z + depth;
	}
	
	public Hitbox(Vector3f location, Vector3f size) {
		this.x1 = location.x;
		this.x2 = location.x + size.x;
		this.y1 = location.y;
		this.y2 = location.y + size.y;
		this.z1 = location.z;
		this.z2 = location.z + size.z;
	}
	
	public boolean IsInFrustum(Frustum frustum) {
		return frustum.cubeInFrustum(x1, y1, z1, x2, y2, z2);
	}
}
