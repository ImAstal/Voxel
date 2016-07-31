package rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import utils.Hitbox;

public abstract class Mesh {
	
	private Hitbox hitbox;
	private int vao, verticesVbo, normalsVbo, texturesVbo, indicesVbo, indicesCount;
	private boolean readyToRender = false, dataUpdated = false;
	private float[] vertices, normals, textures;
	private int[] indices;
	
	public Mesh(Hitbox hitbox) {
		this.hitbox = hitbox;
		this.vao = GL30.glGenVertexArrays();
		this.verticesVbo = GL15.glGenBuffers();
		this.normalsVbo = GL15.glGenBuffers();
		this.texturesVbo = GL15.glGenBuffers();
		this.indicesVbo = GL15.glGenBuffers();
		
		this.indicesCount = 0;
	}
	
	private void Bind() {
		GL30.glBindVertexArray(this.vao);
	}
	
	private void Unbind() {
		GL30.glBindVertexArray(0);
	}
	
	public void SetData(float[] vertices, float[] normals, float[] textures, int[] indices) {
		this.vertices = vertices;
		this.normals = normals;
		this.textures = textures;
		this.indices = indices;
		this.dataUpdated = true;
	}
	
	public void UpdateData() {
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(this.vertices.length);
		verticesBuffer = BufferUtils.createFloatBuffer(this.vertices.length);
		verticesBuffer.put(this.vertices);
		verticesBuffer.flip();
		this.Bind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.verticesVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        verticesBuffer.clear();
        this.vertices = null;
		
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(this.normals.length);
		normalsBuffer = BufferUtils.createFloatBuffer(this.normals.length);
		normalsBuffer.put(this.normals);
		normalsBuffer.flip();
		this.Bind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.normalsVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        normalsBuffer.clear();
        this.normals = null;
        
        FloatBuffer texturesBuffer = BufferUtils.createFloatBuffer(this.textures.length);
        texturesBuffer.put(this.textures);
        texturesBuffer.flip();
        this.Bind();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.texturesVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texturesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);
        this.Unbind();
        this.textures = null;
        
		
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(this.indices.length);
		indicesBuffer = BufferUtils.createIntBuffer(this.indices.length);
		indicesBuffer.put(this.indices);
		indicesBuffer.flip();
		this.Bind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesVbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);		
		this.Unbind();
		this.indicesCount = this.indices.length;
		this.indices = null;
		this.Unbind();
		this.readyToRender = true;
	}
	
	public void Render(Camera camera) {
		this.Bind();
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL11.glDrawElements(GL11.GL_TRIANGLES, this.indicesCount, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		this.Unbind();
	}
		
	
	public void Cleanup() {
		GL15.glDeleteBuffers(this.verticesVbo);
		GL15.glDeleteBuffers(this.normalsVbo);
		GL15.glDeleteBuffers(this.texturesVbo);
		GL15.glDeleteBuffers(this.indicesVbo);
		GL30.glDeleteVertexArrays(this.vao);
	}
	
	public Hitbox GetHitbox() {
		return this.hitbox;
	}
	
	public boolean IsReadyToRender() {
		return this.readyToRender;
	}
	
	public boolean DataUpdated() {
		return this.dataUpdated;
	}

}
