package com.duodev.placeholder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Rectangle {

    private final Placeholder game;

    private final Texture[] textures; //UP, RIGHT, DOWN, LEFT
    int currentTextureIndex = 0;

    private final CharacterAnimationRenderer playerRenderer;
    private PlayerAnimationType currentType = PlayerAnimationType.IDLE_FRONT;

    private float xInternal;
    private float yInternal;
    private Vector2 direction;
    public float speed = 100;
    private long lastSpeedTime;

    private long lastHopTime;

    private int[] keys;

    public Player(final Placeholder game) {

        this(game, 16, 16, 270, 420);

    }

    public Player(final Placeholder game, int width, int height, int x, int y) {

        this.game = game;

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.xInternal = (float) x;
        this.yInternal = (float) y;

        playerRenderer = new CharacterAnimationRenderer(this.game, this.game.batch);
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_FRONT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleFront.png")), 32, 32, 0.1f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_STARTUP_FRONT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleInitFront.png")), 32, 32, 0.1f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_BACK, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleBack.png")), 32, 32, 0.1f));


        textures = new Texture[4];
        textures[0] = new Texture(Gdx.files.internal("vonHinten.png"));
        textures[1] = new Texture(Gdx.files.internal("vonLinks.png"));
        textures[2] = new Texture(Gdx.files.internal("vonVorne.png"));
        textures[3] = new Texture(Gdx.files.internal("vonRechts.png"));

        lastSpeedTime = TimeUtils.nanoTime();
        lastHopTime = TimeUtils.nanoTime();

    }

    public Vector2 getMidpoint() {

        return new Vector2((float) (this.x + 0.5 * width), (float) (this.y + 0.5 * height));

    }

    public void draw(float delta) {

        x = (int) xInternal;
        y = (int) yInternal;

        //game.batch.draw(textures[currentTextureIndex], x, y, width, height);

        playerRenderer.render(currentType, xInternal, yInternal);

        game.font.draw(game.batch, speed + "", 10, 480);

    }

    public boolean oneDirectionalKeyPressed(int[] keys) {
        int sum = 0;
        for (int key : keys) {
            sum += key;
        }
        return sum == 1;
    }

    public int getMainDirection(int[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    public void updateMovement(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && TimeUtils.nanoTime() - lastSpeedTime > 1000000000) {
            speed *= 2;
            lastSpeedTime = TimeUtils.nanoTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && TimeUtils.nanoTime() - lastSpeedTime > 1000000000) {
            speed /= 2;
            lastSpeedTime = TimeUtils.nanoTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H) && TimeUtils.nanoTime() - lastHopTime > 1000000000) {
            xInternal += 50;
            lastHopTime = TimeUtils.nanoTime();
        }

        keys = new int[4]; //UP, RIGHT, DOWN, LEFT
        keys[0] = (Gdx.input.isKeyPressed(Input.Keys.W)) ? 1 : 0;
        keys[1] = (Gdx.input.isKeyPressed(Input.Keys.D)) ? 1 : 0;
        keys[2] = (Gdx.input.isKeyPressed(Input.Keys.S)) ? 1 : 0;
        keys[3] = (Gdx.input.isKeyPressed(Input.Keys.A)) ? 1 : 0;

        direction = new Vector2((float) (keys[1] - keys[3]), (float) (keys[0] - keys[2]));
        direction.nor();

        if (oneDirectionalKeyPressed(keys)) {
            currentTextureIndex = getMainDirection(keys);
        }

        xInternal += direction.x * delta * speed;
        yInternal += direction.y * delta * speed;

    }
}