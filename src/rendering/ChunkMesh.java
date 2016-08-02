package rendering;

import world.Chunk;

import static game.Constants.*;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;

import rendering.shaders.Shaders;
import resources.ResourcesManager;
import utils.Hitbox;
import utils.Maths;

public class ChunkMesh extends Mesh {
	
	private Chunk chunk;

	public ChunkMesh(Chunk chunk) {
		super(new Hitbox(chunk.GetWorldLocation(), new Vector3f(CHUNK_SIZE, CHUNK_HEIGHT, CHUNK_SIZE)));
		this.chunk = chunk;
	}
	
	public void GenerateMesh() {
		float[] vertices = new float[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 4 * 3 * 6];
		float[] normals = new float[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 4 * 3 * 6];
		float[] textures = new float[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 6 * 8];
		float[] colors = new float[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 6 * 4 * 3];
		int[] indices = new int[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE * 6 * 6];
		int verticePointer = 0, normalPointer = 0, texturePointer = 0, colorPointer = 0, indicePointer = 0, faceCount = 0;
		for(int x = 0; x < CHUNK_SIZE; x++) {
			for(int y = 0; y < CHUNK_HEIGHT; y++) {
				for(int z = 0; z < CHUNK_SIZE; z++) {
					if(chunk.GetBlockId(x, y, z) == 0)
						continue;
					int textureId = 0;
					if(!chunk.IsOpaque(x, y + 1, z)) {
						CubeRenderer.CubeTop(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						CubeRenderer.AddIndices(indices, indicePointer, faceCount);
						CubeRenderer.AddTexture(textureId, textures, texturePointer);
						this.ProcessAO(new Vector3f(x, y, z), BlockFace.TOP, colors, colorPointer);
						colorPointer += 4*3;
						texturePointer += 8;
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					
					if(!chunk.IsOpaque(x, y - 1, z)) {
						CubeRenderer.CubeBottom(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						CubeRenderer.AddIndices(indices, indicePointer, faceCount);
						CubeRenderer.AddTexture(textureId, textures, texturePointer);
						this.ProcessAO(new Vector3f(x, y, z), BlockFace.TOP, colors, colorPointer);
						colorPointer += 4*3;
						texturePointer += 8;
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}

					if(!chunk.IsOpaque(x - 1, y, z)) {
						CubeRenderer.CubeLeft(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						CubeRenderer.AddIndices(indices, indicePointer, faceCount);
						CubeRenderer.AddTexture(textureId, textures, texturePointer);
						this.ProcessAO(new Vector3f(x, y, z), BlockFace.LEFT, colors, colorPointer);
						colorPointer += 4*3;
						texturePointer += 8;
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					
					if(!chunk.IsOpaque(x + 1, y, z)) {
						CubeRenderer.CubeRight(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						CubeRenderer.AddIndices(indices, indicePointer, faceCount);
						CubeRenderer.AddTexture(textureId, textures, texturePointer);
						this.ProcessAO(new Vector3f(x, y, z), BlockFace.RIGHT, colors, colorPointer);
						colorPointer += 4*3;
						texturePointer += 8;
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					
					if(!chunk.IsOpaque(x, y, z + 1)) {
						CubeRenderer.CubeFront(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						CubeRenderer.AddIndices(indices, indicePointer, faceCount);
						CubeRenderer.AddTexture(textureId, textures, texturePointer);
						ProcessAO(new Vector3f(x, y, z), BlockFace.FRONT, colors, colorPointer);
						colorPointer += 4*3;
						texturePointer += 8;
						verticePointer += 3*4;
						normalPointer += 4*3;
						indicePointer += 6;
						faceCount++;
					}
					if(!chunk.IsOpaque(x, y, z - 1)) {
						CubeRenderer.CubeBack(vertices, normals, verticePointer, normalPointer, new Vector3f(x, y, z));
						CubeRenderer.AddIndices(indices, indicePointer, faceCount);
						CubeRenderer.AddTexture(textureId, textures, texturePointer);
						this.ProcessAO(new Vector3f(x, y, z), BlockFace.BACK, colors, colorPointer);
						colorPointer += 4*3;
						texturePointer += 8;
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
		textures = Arrays.copyOf(textures, texturePointer);
		colors = Arrays.copyOf(colors, colorPointer);
		indices = Arrays.copyOf(indices, indicePointer);
		this.SetData(vertices, normals, textures, colors, indices);
	}
	
	public void Render(Camera camera) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ResourcesManager.TERRAIN.GetId());
		Shaders.STANDARD.Bind();
		Shaders.STANDARD.LoadViewMatrix(camera.GetViewMatrix());
		Shaders.STANDARD.LoadTransformationMatrix(Maths.CreateTransformationMatrix(this.chunk.GetWorldLocation(), new Vector3f(0f, 0f, 0f), 1f));
		super.Render(camera);
		Shaders.STANDARD.Unbind();
	}
	
	
	
	private void ProcessAO(Vector3f blockPosition, BlockFace face, float[] colors, int pointer) {
		Vector3f defaultColor = new Vector3f(1.0f, 1.0f, 1.0f);
		int x = (int)blockPosition.x;
		int y = (int)blockPosition.y;
		int z = (int)blockPosition.z;
		float[] ao = new float[4];
		switch(face) {
			/*case TOP:
				if(chunk.IsOpaque(x - 1, y + 1, z, false)) {
					ao[0] = AO_COLOR;
					ao[3] = AO_COLOR;
				}
				if(chunk.IsOpaque(x + 1, y + 1, z, false)) {
					ao[1] = AO_COLOR;
					ao[2] = AO_COLOR;
				}
				if(chunk.IsOpaque(x, y + 1, z - 1, false)) {
					ao[2] = AO_COLOR;
					ao[3] = AO_COLOR;
				}
				if(chunk.IsOpaque(x, y + 1, z + 1, false)) {
					ao[0] = AO_COLOR;
					ao[1] = AO_COLOR;
				}
				
				if(chunk.IsOpaque(x - 1, y + 1, z - 1, false))
					ao[3] = AO_COLOR;
				if(chunk.IsOpaque(x + 1, y + 1, z - 1, false))
					ao[2] = AO_COLOR;
				
				if(chunk.IsOpaque(x - 1, y + 1, z + 1, false))
					ao[0] = AO_COLOR;
				if(chunk.IsOpaque(x + 1, y + 1, z + 1, false))
					ao[1] = AO_COLOR;
			break;*/
			
			case LEFT:
				if(chunk.IsOpaque(x - 1, y - 1, z, false)) {
					ao[2] = AO_COLOR;
					ao[3] = AO_COLOR;
				}
				if(chunk.IsOpaque(x - 1, y + 1, z, false)) {
					ao[0] = AO_COLOR;
					ao[1] = AO_COLOR;
				}
				if(chunk.IsOpaque(x - 1, y, z - 1, false)) {
					ao[1] = AO_COLOR;
					ao[3] = AO_COLOR;
				}
				break;
			
			default:
				break;
		}
		this.AddColor(defaultColor, ao[0], colors, pointer);
		this.AddColor(defaultColor, ao[1], colors, pointer+3);
		this.AddColor(defaultColor, ao[2], colors, pointer+6);
		this.AddColor(defaultColor, ao[3], colors, pointer+9);
	}
	
	private void AddColor(Vector3f color, float ao, float[] colors, int pointer) {
		colors[pointer] = color.x - ao;
		colors[pointer+1] = color.y - ao;
		colors[pointer+2] = color.z - ao;
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
