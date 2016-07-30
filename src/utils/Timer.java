package utils;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import rendering.MainRenderer;

import static game.Constants.*;

public class Timer {

    private long lastFrame, lastFps;
    private int fps;

    public Timer() {
        this.GetDeltaTime();
        this.lastFps = GetTime();
    }

    public void Update() {
        if (GetTime() - this.lastFps > 1000) {
            Display.setTitle(GAME_TITLE + "  " + this.fps + "fps " + MainRenderer.CLIPPED + " clipped " + MainRenderer.MODEL_RENDERED + " rendered");
            this.fps = 0;
            this.lastFps += 1000;
        }
        this.fps++;
    }

    private long GetTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public int GetDeltaTime() {
        long time = GetTime();
        int delta = (int) (time - this.lastFrame);
        this.lastFrame = time;

        return delta;
    }
}
