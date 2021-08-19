package com.duodev.placeholder;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraController {

    private final OrthographicCamera camera;
    private final Player player;
    public float cameraSpeed = 1f;
    public float speedAdaptability = 50f;
    public float cameraDistance = 100f;
    public float walkingCameraSpeedMultiplier = 3f;

    public CameraController(OrthographicCamera camera, Player player){
        this.camera = camera;
        this.camera.position.set(player.getMidpoint().x, player.getMidpoint().y, 0);
        this.player = player;
    }

    public void update(float delta, boolean mode){

        float diffX;
        float diffY;
        float speedMultiplier = 1f;

        if(mode) {

            diffX = player.getMidpoint().x - camera.position.x;
            diffY = player.getMidpoint().y - camera.position.y;

        } else {

            if(!(player.direction.x == 0 && player.direction.y == 0)){
                speedMultiplier = walkingCameraSpeedMultiplier;
            }

            diffX = player.getMidpoint().x + player.lookingDirection.x * cameraDistance - camera.position.x;
            diffY = player.getMidpoint().y + player.lookingDirection.y * cameraDistance - camera.position.y;

        }

        float newX = camera.position.x + (diffX * delta * cameraSpeed * (player.speed / speedAdaptability) * speedMultiplier);
        float newY = camera.position.y + (diffY * delta * cameraSpeed * (player.speed / speedAdaptability) * speedMultiplier);
        camera.position.set(newX, newY, 0);

    }

}