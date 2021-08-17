package com.duodev.placeholder;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraController {

    private final OrthographicCamera camera;
    private final Player player;
    private final float cameraSpeed = 1f;
    private final float speedAdaptability = 50f;

    private float realCameraX;
    private float realCameraY;

    public CameraController(OrthographicCamera camera, Player player){
        this.camera = camera;
        this.camera.position.set(player.getMidpoint().x, player.getMidpoint().y, 0);
        this.player = player;
        realCameraX = camera.position.x;
        realCameraY = camera.position.y;
    }

    public void update(float delta){

        float diffX = player.getMidpoint().x - realCameraX;
        float diffY = player.getMidpoint().y - realCameraY;

        realCameraX = realCameraX + (diffX * delta * cameraSpeed * (player.speed / speedAdaptability));
        realCameraY = realCameraY + (diffY * delta * cameraSpeed * (player.speed / speedAdaptability));
        camera.position.set(MathUtils.floor(realCameraX), MathUtils.floor(realCameraY), 0);

    }

}
