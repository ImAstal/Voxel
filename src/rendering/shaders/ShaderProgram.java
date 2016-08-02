package rendering.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import game.Constants;

public abstract class ShaderProgram {
	
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private int program, fragment, vertex;

	public ShaderProgram(String vertexShader, String fragmentShader) {
		this.fragment = this.LoadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		this.vertex = this.LoadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		this.program = GL20.glCreateProgram();
		GL20.glAttachShader(this.program, this.fragment);
		GL20.glAttachShader(this.program, this.vertex);
		GL20.glLinkProgram(this.program);
		if (GL20.glGetProgrami(this.program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
		    System.err.println(GL20.glGetShaderInfoLog(this.program, GL20.glGetShaderi(this.program, GL20.GL_INFO_LOG_LENGTH)));
		GL20.glValidateProgram(this.program);
		this.BindAllAttributes();
		this.GetAllUniformLocations();
	}
	
	public void Bind() {
		GL20.glUseProgram(this.program);
	}
	
	public void Unbind() {
		GL20.glUseProgram(0);
	}
	
	public void Cleanup() {
		this.Unbind();
		GL20.glDetachShader(this.program, this.fragment);
		GL20.glDetachShader(this.program, this.vertex);
		GL20.glDeleteShader(this.fragment);
		GL20.glDeleteShader(this.vertex);
		GL20.glDeleteProgram(this.program);
	}
	
	protected abstract void BindAllAttributes();	
	protected void BindAttribute(int attribute, String variable) {
		GL20.glBindAttribLocation(this.program, attribute, variable);
	}
	
	protected abstract void GetAllUniformLocations();
	
	protected int GetUniformLocation(String uniform) {
		return GL20.glGetUniformLocation(this.program, uniform);
	}
	
	protected void LoadInt(int value, int location) {
		GL20.glUniform1i(location, value);
	}
	
	protected void LoadFloat(float value, int location) {
		GL20.glUniform1f(location, value);
	}
	
	protected void LoadVector(Vector3f value, int location) {
		GL20.glUniform3f(location, value.x, value.y, value.z);
	}
	
	protected void LoadBoolean(boolean value, int location) {
		GL20.glUniform1i(location, (value == true)?1:0);
	}
	
	protected void LoadMatrix(Matrix4f matrix, int location) {
		matrix.store(this.matrixBuffer);
		this.matrixBuffer.flip();
		Util.checkGLError();
		GL20.glUniformMatrix4(location, false, this.matrixBuffer);
		Util.checkGLError();
	}
	
	private int LoadShader(String file, int type) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Constants.SHADER_FOLDER_PATH + file));
			String line;
			while((line = reader.readLine()) != null)
				builder.append(line).append("\n");
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		int shaderId = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderId, builder);
		GL20.glCompileShader(shaderId);
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println("Could not compile shader: " + file);
            System.out.println(GL20.glGetShaderInfoLog(shaderId,500));
            System.exit(-1);
        }
		return shaderId;
	}
}
