package rendering;

import world.Chunk;

import static game.Constants.*;

import java.util.Arrays;

import org.lwjgl.util.vector.Vector3f;

import rendering.shaders.Shaders;
import utils.Hitbox;
import utils.Maths;

public class ChunkMesh extends Mesh {
	
	private Chunk chunk;

	public ChunkMesh(Chunk chunk) {
		super(new Hitbox(chunk.GetWorldLocation(), new Vector3f(CHUNK_SIZE, CHUNK_HEIGHT, CHUNK_SIZE)));
		this.chunk = chunk;
	}
	
	public void UpdateData() {
		float[] vertices = new float[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 4 * 3 * 6];
		float[] normals = new float[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 4 * 3 * 6];
		int[] indices = new int[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 6 * 6];
		int verticePointer = 0, normalPointer = 0, indicePointer = 0, faceCount = 0;
		for(int x = 0; x < CHUNK_SIZE; x++) {
			for(int y = 0; y < CHUNK_HEIGHT; y++) {
				for(int z = 0; z < CHUNK_SIZE; z++) {
					if(chunk.GetBlockId(x, y, z) == 0)
						continue;
					if(!chunk.IsOpaque(x, y + 1, z)) {
						this.CubeTop(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						this.AddIndices(indices, indicePointer, faceCount);
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}

					if(!chunk.IsOpaque(x - 1, y, z)) {
						this.CubeLeft(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						this.AddIndices(indices, indicePointer, faceCount);
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					
					if(!chunk.IsOpaque(x + 1, y, z)) {
						this.CubeRight(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						this.AddIndices(indices, indicePointer, faceCount);
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					
					if(!chunk.IsOpaque(x, y, z + 1)) {
						this.CubeFront(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						this.AddIndices(indices, indicePointer, faceCount);
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					if(!chunk.IsOpaque(x, y, z - 1)) {
						this.CubeBack(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						this.AddIndices(indices, indicePointer, faceCount);
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
				}
			}
		}
		

		vertices = Arrays.copyOf(vertices, verticePointer);
		normals = Arrays.copyOf(normals, normalPointer);
		indices = Arrays.copyOf(indices, indicePointer);
		
		this.SetData(vertices, normals, indices);
	}
	
	public void Render(Camera camera) {
		Shaders.STANDARD.Bind();
		Shaders.STANDARD.LoadViewMatrix(camera.GetViewMatrix());
		Shaders.STANDARD.LoadTransformationMatrix(Maths.CreaterTransformationMatrix(this.chunk.GetWorldLocation(), new Vector3f(0f, 0f, 0f), 1f));
		super.Render(camera);
		Shaders.STANDARD.Unbind();
	}
	
	private void CubeTop(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
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
	
	private void CubeLeft(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y - 1, position.z));
		
		
		Vector3f normal = new Vector3f(-1, 0, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	private void CubeRight(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		/*AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y - 1, position.z));*/

		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y - 1, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y - 1, position.z + 1));
		
		Vector3f normal = new Vector3f(1, 0, 0);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	private void CubeFront(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y - 1, position.z + 1));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z + 1));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y, position.z + 1));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y - 1, position.z + 1));
		
		Vector3f normal = new Vector3f(0, 0, -1);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	
	private void CubeBack(float[] vertices, float[] normals, int pointer, int normalPointer, Vector3f position) {
		/*AddPoint(vertices, pointer, new Vector3f(position.x + 1, position.y - 1, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x, position.y - 1, position.z));*/

		AddPoint(vertices, pointer, new Vector3f(position.x, position.y - 1, position.z));
		AddPoint(vertices, pointer+3, new Vector3f(position.x, position.y, position.z));
		AddPoint(vertices, pointer+6, new Vector3f(position.x + 1, position.y, position.z));
		AddPoint(vertices, pointer+9, new Vector3f(position.x + 1, position.y - 1, position.z));
		
		Vector3f normal = new Vector3f(0, 0, 1);
		AddPoint(normals, normalPointer, normal);
		AddPoint(normals, normalPointer+3, normal);
		AddPoint(normals, normalPointer+6, normal);
		AddPoint(normals, normalPointer+9, normal);
	}
	 
	private void AddPoint(float[] vertices, int pointer, Vector3f point) {
		vertices[pointer] = point.x;
		vertices[pointer+1] = point.y;
		vertices[pointer+2] = point.z;
	}
	
	private void AddIndices(int[] indices, int indicePointer, int faceCount) {
		indices[indicePointer] = faceCount * 4;
		indices[indicePointer+1] = faceCount * 4 + 1;
		indices[indicePointer+2] = faceCount * 4 + 2;
		indices[indicePointer+3] = faceCount * 4;
		indices[indicePointer+4] = faceCount * 4 + 2;
		indices[indicePointer+5] = faceCount * 4 + 3;
	}
	
	/*private Vector3f GetSurfaceNormal(Vector3f v1, Vector3f v2, Vector3f v3) {
		Vector3f polyVector1 = new Vector3f(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
		Vector3f polyVector2 = new Vector3f(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);
		Vector3f cross = new Vector3f(polyVector1.y * polyVector2.z - polyVector1.z * polyVector2.y, 
				polyVector1.z * polyVector2.x - polyVector1.x * polyVector2.z,
				polyVector1.x * polyVector2.y - polyVector1.y * polyVector2.x);
		cross.normalise();
		return cross;
	}*/
}
