package rendering;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static java.lang.Math.*;

public class Camera {

    private Vector3f position;
    private float speed = 420f * 0.001f, sensitivity = 1f, yaw = 0f, pitch = 0f;

    public Camera() {
        this(new Vector3f(0f, 0f, 0f));
    }

    public Camera(Vector3f position) {
        this.position = position;
    }

    public void Update(float delta) {
        this.Yaw(Mouse.getDX() * this.sensitivity);
        this.Pitch(Mouse.getDY() * this.sensitivity);
        if(Keyboard.isKeyDown(Keyboard.KEY_E))
            this.position.y += speed;
        if(Keyboard.isKeyDown(Keyboard.KEY_A))
            this.position.y -= speed;
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            this.position.x -= speed * (float)Math.sin(Math.toRadians(yaw));
            this.position.z += speed * (float)Math.cos(Math.toRadians(yaw));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.position.x -= speed * (float)Math.sin(Math.toRadians(yaw + 90));
            this.position.z += speed * (float)Math.cos(Math.toRadians(yaw + 90));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            this.position.x -= speed * (float)Math.sin(Math.toRadians(yaw - 90));
            this.position.z += speed * (float)Math.cos(Math.toRadians(yaw - 90));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.position.x += speed * (float)Math.sin(Math.toRadians(yaw));
            this.position.z -= speed * (float)Math.cos(Math.toRadians(yaw));
        }
    }

    private void Yaw(float amount) {
        this.yaw += amount;
        if(this.yaw > 360)
            yaw = yaw - 360;
        if(this.yaw < 0)
            yaw = yaw + 360;
    }

    private void Pitch(float amount) {
        amount = -amount;//Invert Y-Axis
        if(pitch + amount > 90 || pitch + amount < -90)
            return;
        this.pitch += amount;
    }

    public Vector3f GetPosition() {
        return new Vector3f(-this.position.x, this.position.y, -this.position.z);
    }

    public Matrix4f GetViewMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix.rotate((float) toRadians(this.pitch), new Vector3f(1, 0, 0))
                .rotate((float) toRadians(this.yaw), new Vector3f(0, 1, 0))
                .rotate((float) toRadians(0), new Vector3f(0, 0, 1));
        //matrix.translate(new Vector3f(-position.x, -position.y, -position.z));
        matrix.translate(position);
        return matrix;
    }
}