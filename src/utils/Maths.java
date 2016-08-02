package utils;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static game.Constants.*;
import static java.lang.Math.toRadians;

public class Maths {
	
	private static Matrix4f projectionMatrix;

    public static Matrix4f CreateTransformationMatrix(Vector3f position, Vector3f rotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix.translate(new Vector3f(position.x, position.y, position.z));
        matrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }
    
    public static Matrix4f CreateProjectionMatrix() {
    	if(projectionMatrix == null) {
	        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
	        float x_scale = y_scale / aspectRatio;
	        float frustum_length = FAR_PLANES - NEAR_PLANES;
	
	        projectionMatrix = new Matrix4f();
	        projectionMatrix.m00 = x_scale;
	        projectionMatrix.m11 = y_scale;
	        projectionMatrix.m22 = -((FAR_PLANES + NEAR_PLANES) / frustum_length);
	        projectionMatrix.m23 = -1;
	        projectionMatrix.m32 = -((2 * NEAR_PLANES * FAR_PLANES) / frustum_length);
	        projectionMatrix.m33 = 0;
	
	        return projectionMatrix;
    	}
    	return projectionMatrix;
    }
    
    public static Matrix4f CreateOrhtographicMatrix( float left, float right, float bottom, float top,
			float near, float far) {
		Matrix4f m = new Matrix4f();
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (far - near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		m.m00 = x_orth;
		m.m10 = 0;
		m.m20 = 0;
		m.m30 = 0;
		m.m01 = 0;
		m.m11 = y_orth;
		m.m21 = 0;
		m.m31 = 0;
		m.m02 = 0;
		m.m12 = 0;
		m.m22 = z_orth;
		m.m32 = 0;
		m.m03 = tx;
		m.m13 = ty;
		m.m23 = tz;
		m.m33 = 1;
		return m;
	}
    
    public static Matrix4f CreateViewMatrix(Vector3f position, Vector3f rotation) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix.rotate(rotation.x, new Vector3f(1, 0, 0))
                .rotate(rotation.y, new Vector3f(0, 1, 0))
                .rotate((float) toRadians(0), new Vector3f(0, 0, 1));
        //matrix.translate(new Vector3f(-position.x, -position.y, -position.z));
        matrix.translate(position);
        return matrix;
    }
    
    public static Matrix4f LookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f forward = new Vector3f(0, 0, 0);
        Vector3f.sub(target, eye, forward);
        forward.normalise();
        Vector3f side = new Vector3f(0, 0, 0);
        Vector3f.cross(forward, up, side);
        side.normalise();
        Vector3f.cross(side, forward, up);

        Matrix4f mat = new Matrix4f();
        mat.setIdentity();
        
        mat.m00 = side.x;
        mat.m01 = side.y;
        mat.m02 = side.z;
        mat.m10 = up.x;
        mat.m11 = up.y;
        mat.m12 = up.z;
        mat.m20 = -forward.x;
        mat.m21 = -forward.y;
        mat.m22 = -forward.z;

        return mat;
         
      } 
}
