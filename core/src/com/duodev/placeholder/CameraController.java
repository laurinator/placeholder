package com.duodev.placeholder;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraController {

    private final OrthographicCamera camera;
    private final Player player;
    public float cameraSpeed = 2f;
    public float cameraDistance = 60f;
    public float cameraAcceleration = 0.25f;

    private float oldCameraVelocityX = 0f;
    private float oldCameraVelocityY = 0f;

    public CameraController(OrthographicCamera camera, Player player) {
        this.camera = camera;
        this.camera.position.set(player.getMidpoint().x, player.getMidpoint().y, 0);
        this.player = player;
    }

    public void update(float delta, boolean mode) {

        float diffX;
        float diffY;
        float currentCameraVelocityX = 0f;
        float currentCameraVelocityY = 0f;

        if (mode) {

            diffX = player.getMidpoint().x - camera.position.x;
            diffY = player.getMidpoint().y - camera.position.y;

        } else {

            diffX = player.getMidpoint().x + player.lookingDirectionVector.x * cameraDistance - camera.position.x;
            diffY = player.getMidpoint().y + player.lookingDirectionVector.y * cameraDistance - camera.position.y;

            float wantedCameraVelocityX = (delta * player.speed * player.direction.x) + (delta * diffX * cameraSpeed);
            float wantedCameraVelocityY = (delta * player.speed * player.direction.y) + (delta * diffY * cameraSpeed);

            float velocityDiffX = wantedCameraVelocityX - oldCameraVelocityX;
            float velocityDiffY = wantedCameraVelocityY - oldCameraVelocityY;

            currentCameraVelocityX = oldCameraVelocityX + (velocityDiffX * cameraAcceleration);
            currentCameraVelocityY = oldCameraVelocityY + (velocityDiffY * cameraAcceleration);

            float newX = camera.position.x + currentCameraVelocityX;
            float newY = camera.position.y + currentCameraVelocityY;
            oldCameraVelocityX = currentCameraVelocityX;
            oldCameraVelocityY = currentCameraVelocityY;

            camera.position.set(newX, newY, 0);

        }
    }
}
