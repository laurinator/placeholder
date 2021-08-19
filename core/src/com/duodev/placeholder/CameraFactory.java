package com.duodev.placeholder;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraFactory {

    public static OrthographicCamera createStandardCamera(){

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(false, 480, 270);
        return cam;

    }

}
