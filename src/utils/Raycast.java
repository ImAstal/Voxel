package utils;

import static game.Constants.CHUNK_SIZE;
import static game.Constants.PICK_DISTANCE;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import rendering.Camera;
import world.Chunk;
import world.World;

public class Raycast {

	public static Ray GetRay(Camera camera) {
		Matrix4f viewMatrix = camera.GetViewMatrix();
		Matrix4f projectionMatrix = Maths.CreateProjectionMatrix();
		
		float mouseX = Display.getWidth()/2f;
		float mouseY =  Display.getHeight()/2f;
		Vector2f normalizedCoords = GetNormalizedDeviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		Vector4f eyeCoords = ToEyeCoords(clipCoords, projectionMatrix);
		Vector3f worldRay = ToWorldCoords(eyeCoords, viewMatrix);
		return new Ray(camera.GetPosition(), worldRay);
	}
	
	private static Vector3f ToWorldCoords(Vector4f eyeCoords, Matrix4f viewMatrix) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}
	
	private static Vector4f ToEyeCoords(Vector4f clipCoords, Matrix4f projectionMatrix) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private static Vector2f GetNormalizedDeviceCoords(float mouseX, float mouseY) {
		float x = (2f * mouseX) / Display.getWidth() - 1f;
		float y = (2f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}
	
	public static HitResult RaycastWorldBlock(World world, Ray ray, float accuracy) {
    	int size = 1;
		for(float i = 0; i < PICK_DISTANCE; i += accuracy) {
    		ray.Move(accuracy);
    		for(int x = -size; x < size; x++) {
        		for(int y = -size; y < size; y++) {
        			for(int z = -size; z < size; z++) {
        				int rx = x + (int)ray.GetPosition().x;
        				int ry = y + (int)ray.GetPosition().y;
        				int rz = z + (int)ray.GetPosition().z;
        				int cx = rx / CHUNK_SIZE;
		        		int cz = rz / CHUNK_SIZE;
		        		Chunk chunk = world.GetChunk(cx, 0, cz);
		        		if(chunk == null)
		        			continue;
		        		int bx = Math.abs(rx - cx * CHUNK_SIZE);
		        		int by = (int)ray.GetPosition().y;
		        		int bz = Math.abs(rz - cz * CHUNK_SIZE);
		        		byte block = chunk.GetBlockId(bx, by, bz);		        		
		        		if(block == (byte)-1 || block == (byte)0)
		        			continue;
		        		Hitbox blockHitbox = new Hitbox(new Vector3f(bx + cx * CHUNK_SIZE, by, bz + cz * CHUNK_SIZE), new Vector3f(1, 1, 1));
		        		Hitbox rayBox = new Hitbox(new Vector3f(ray.GetPosition().x, ray.GetPosition().y, ray.GetPosition().z), new Vector3f(0.001f, 0.001f, 0.001f));
		        		if(blockHitbox.Collision(rayBox))
			        		return new HitResult(ray, chunk, new Vector3f(rx, ry, rz), new Vector3f(bx, by, bz));
        			}
        		}
        	}
		}
		return null;
	}
	
	public static HitResult RaycastWorldNextBlock(World world, Ray ray, float accuracy) {
    	int size = 1;
		for(float i = 0; i < PICK_DISTANCE; i += accuracy) {
    		ray.Move(accuracy);
    		for(int x = -size; x < size; x++) {
        		for(int y = -size; y < size; y++) {
        			for(int z = -size; z < size; z++) {
        				int rx = x + (int)ray.GetPosition().x;
        				int ry = y + (int)ray.GetPosition().y;
        				int rz = z + (int)ray.GetPosition().z;
        				int cx = rx / CHUNK_SIZE;
		        		int cz = rz / CHUNK_SIZE;
		        		Chunk chunk = world.GetChunk(cx, 0, cz);
		        		if(chunk == null)
		        			continue;
		        		int bx = Math.abs(rx - cx * CHUNK_SIZE);
		        		int by = (int)ray.GetPosition().y;
		        		int bz = Math.abs(rz - cz * CHUNK_SIZE);
		        		byte block = chunk.GetBlockId(bx, by, bz);		        		
		        		if(block == (byte)-1 || block == (byte)0)
		        			continue;
		        		Hitbox blockHitbox = new Hitbox(new Vector3f(bx + cx * CHUNK_SIZE, by, bz + cz * CHUNK_SIZE), new Vector3f(1, 1, 1));
		        		Hitbox rayBox = new Hitbox(new Vector3f(ray.GetPosition().x, ray.GetPosition().y, ray.GetPosition().z), new Vector3f(0.001f, 0.001f, 0.001f));
		        		if(blockHitbox.Collision(rayBox)) {
		        			Vector3f normal = ray.GetNormal();
		        			ray.Move(new Vector3f(-normal.x * 0.5f, -normal.y * 0.5f, -normal.z * 0.5f));
		        			rx = (int)(x + ray.GetPosition().x);
	        				ry = (int)(y + ray.GetPosition().y);
	        				rz = (int)(z + ray.GetPosition().z);
	        				cx = rx / CHUNK_SIZE;
			        		cz = rz / CHUNK_SIZE;
			        		bx = Math.abs(rx - cx * CHUNK_SIZE);
			        		by = (int)ray.GetPosition().y;
			        		bz = Math.abs(rz - cz * CHUNK_SIZE);
			        		chunk = world.GetChunk(cx, 0, cz);
			        		if(chunk == null)
			        			continue;
			        		return new HitResult(ray, chunk, new Vector3f(rx, ry, rz), new Vector3f(bx, by, bz));
		        		}
        			}
        		}
        	}
		}
		return null;
	}
}
