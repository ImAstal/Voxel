package rendering;

import org.lwjgl.util.vector.Vector3f;

import resources.ResourcesManager;

public class CubeRenderer {
	
	public static void CubeTop(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y + 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y + 1, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y + 1, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y + 1, position.z));
		
		Vector3f normal = new Vector3f(0, 1, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeBottom(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {

		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y, position.z));
		
		Vector3f normal = new Vector3f(0, -1, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeLeft(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y + 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y + 1, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y, position.z + 1));
		
		
		Vector3f normal = new Vector3f(-1, 0, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeRight(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y + 1, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y + 1, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y, position.z));
		
		Vector3f normal = new Vector3f(1, 0, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeFront(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y + 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y + 1, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y, position.z + 1));
		
		Vector3f normal = new Vector3f(0, 0, -1);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeBack(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y + 1, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y + 1, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y, position.z));
		
		Vector3f normal = new Vector3f(0, 0, 1);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}

	/*public static void CubeTop(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y, position.z));
		
		Vector3f normal = new Vector3f(0, 1, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeBottom(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {

		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y - 1, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y - 1, position.z));
		
		Vector3f normal = new Vector3f(0, -1, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeLeft(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y - 1, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y - 1, position.z + 1));
		
		
		Vector3f normal = new Vector3f(-1, 0, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeRight(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y - 1, position.z));
		
		Vector3f normal = new Vector3f(1, 0, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeFront(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y - 1, position.z + 1));
		
		Vector3f normal = new Vector3f(0, 0, -1);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	public static void CubeBack(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y - 1, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y - 1, position.z));
		
		Vector3f normal = new Vector3f(0, 0, 1);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}*/
	 
	public static void AddPoint(float[] vertices, int pointer, Vector3f point) {
		vertices[pointer] = point.x;
		vertices[pointer+1] = point.y;
		vertices[pointer+2] = point.z;
	}
	
	public static void AddIndices(int[] indices, int indicePointer, int faceCount) {
		indices[indicePointer] = faceCount * 4;
		indices[indicePointer+1] = faceCount * 4 + 1;
		indices[indicePointer+2] = faceCount * 4 + 2;
		indices[indicePointer+3] = faceCount * 4;
		indices[indicePointer+4] = faceCount * 4 + 2;
		indices[indicePointer+5] = faceCount * 4 + 3;
	}
	
	public static void AddTexture(int textureId, float[] textures, int texturePointer) {
		float x = 1 / (float)ResourcesManager.TERRAIN.GetSubWidth() ;
		float y = 0 / (float)ResourcesManager.TERRAIN.GetSubHeight();
		float width = (float)ResourcesManager.TERRAIN.GetSubWidth() / (float)ResourcesManager.TERRAIN.GetWidth();
		float height = (float)ResourcesManager.TERRAIN.GetSubHeight() / (float)ResourcesManager.TERRAIN.GetHeight();
		textures[texturePointer] = x;
		textures[texturePointer+1] = y;
		textures[texturePointer+2] = x + width;
		textures[texturePointer+3] = y;
		textures[texturePointer+4] = x + width;
		textures[texturePointer+5] = y + height;
		textures[texturePointer+6] = x;
		textures[texturePointer+7] = y + height;
	}
	
	public static void AddFaceColor(Vector3f color, float[] colors, int pointer) {
		AddColor(color, colors, pointer);
		AddColor(color, colors, pointer+3);
		AddColor(color, colors, pointer+6);
		AddColor(color, colors, pointer+9);
	}
	
	public static void AddColor(Vector3f color, float[] colors, int pointer) {
		colors[pointer] = color.x;
		colors[pointer+1] = color.y;
		colors[pointer+2] = color.z;
	}
}
