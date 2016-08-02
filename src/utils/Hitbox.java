package utils;

import org.lwjgl.util.vector.Vector3f;

public class Hitbox {

	public float x0, y0, z0, x1, y1, z1;
	
	public Hitbox(float x0, float y0, float z0, float x1, float y1, float z1) {
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
	}

	public Hitbox(Vector3f position, Vector3f size) {
		this(position.x, position.y, position.z, position.x + size.x, position.y + size.y, position.z + size.z);
	}
  
 
	public boolean Collision(Hitbox box) {
		if ((box.x1 <= this.x0) || (box.x0 >= this.x1))
			return false;
		if ((box.y1 <= this.y0) || (box.y0 >= this.y1))
			return false;
		if ((box.z1 <= this.z0) || (box.z0 >= this.z1))
			return false;
		return true;
	}
  
	public boolean IsInFrustum(Frustum frustum) {
		return frustum.cubeInFrustum(this.x0, this.y0, this.z0, this.x1, this.y1, this.z1);
	}
}