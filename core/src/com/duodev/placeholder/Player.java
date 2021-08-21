package com.duodev.placeholder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.EnumMap;

public class Player extends Rectangle {

    private final Placeholder game;

    private final CharacterAnimationRenderer playerRenderer;
    private PlayerAnimationType currentAnimationType = PlayerAnimationType.IDLE_DOWN;
    private MainDirection lookingDirection = MainDirection.DOWN;
    private final EnumMap<MainDirection, PlayerAnimationType> idleAnimations = new EnumMap<>(MainDirection.class);
    private final EnumMap<MainDirection, PlayerAnimationType> runningAnimations = new EnumMap<>(MainDirection.class);
    private final EnumMap<MainDirection, PlayerAnimationType> idleInitAnimations = new EnumMap<>(MainDirection.class);

    private float timeSinceIdle = 0f;
    private boolean isIdle = false;

    private float xInternal;
    private float yInternal;
    public Vector2 direction = new Vector2(0,0);
    public Vector2 lookingDirectionVector = new Vector2(1,0);
    public float speed = 100;
    private long lastSpeedTime;

    private long lastHopTime;

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
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_DOWN, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleDown.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_STARTUP_DOWN, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleInitDown.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.RUNNING_DOWN, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("runningDown.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_UP, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleUp.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_STARTUP_UP, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleInitUp.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.RUNNING_UP, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("runningUp.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_RIGHT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleRight.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_STARTUP_RIGHT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleInitRight.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.RUNNING_RIGHT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("runningRight.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_LEFT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleLeft.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.IDLE_STARTUP_LEFT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("idleInitLeft.png")), 32, 32, 0.125f));
        playerRenderer.addAnimation(PlayerAnimationType.RUNNING_LEFT, AnimationFactory.createAnimation(new Texture(Gdx.files.internal("runningLeft.png")), 32, 32, 0.125f));

        idleAnimations.put(MainDirection.UP, PlayerAnimationType.IDLE_UP);
        idleAnimations.put(MainDirection.RIGHT, PlayerAnimationType.IDLE_RIGHT);
        idleAnimations.put(MainDirection.DOWN, PlayerAnimationType.IDLE_DOWN);
        idleAnimations.put(MainDirection.LEFT, PlayerAnimationType.IDLE_LEFT);

        idleInitAnimations.put(MainDirection.UP, PlayerAnimationType.IDLE_STARTUP_UP);
        idleInitAnimations.put(MainDirection.RIGHT, PlayerAnimationType.IDLE_STARTUP_RIGHT);
        idleInitAnimations.put(MainDirection.DOWN, PlayerAnimationType.IDLE_STARTUP_DOWN);
        idleInitAnimations.put(MainDirection.LEFT, PlayerAnimationType.IDLE_STARTUP_LEFT);

        runningAnimations.put(MainDirection.UP, PlayerAnimationType.RUNNING_UP);
        runningAnimations.put(MainDirection.RIGHT, PlayerAnimationType.RUNNING_RIGHT);
        runningAnimations.put(MainDirection.DOWN, PlayerAnimationType.RUNNING_DOWN);
        runningAnimations.put(MainDirection.LEFT, PlayerAnimationType.RUNNING_LEFT);

        lastSpeedTime = TimeUtils.nanoTime();
        lastHopTime = TimeUtils.nanoTime();

    }

    public Vector2 getMidpoint() {

        return new Vector2((float) (this.x + 0.5 * width), (float) (this.y + 0.5 * height));

    }

    public void draw(float delta) {

        if(isIdle){
            timeSinceIdle += delta;
        }

        x = (int) xInternal;
        y = (int) yInternal;

        playerRenderer.render(currentAnimationType, (int) xInternal, (int) yInternal);

        game.font.draw(game.batch, speed + "", 10, 480);

    }

    public boolean oneDirectionalKeyPressed(int[] keys) {
        int sum = 0;
        for (int key : keys) {
            sum += key;
        }
        return sum == 1;
    }

    public MainDirection getMainDirection(int[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == 1) {
                return MainDirection.values()[i];
            }
        }
        return null;
    }

    public boolean isMoving(){
        return !(this.direction.x == 0 && this.direction.y == 0);
    }

    public void updateAnimation(){
        if(isMoving()){
            setAnimation(runningAnimations.get(lookingDirection));
            timeSinceIdle = 0f;
        } else if (timeSinceIdle <= 1.5f) { //1.5s per idle startup animation
            isIdle = true;
            setAnimation(idleInitAnimations.get(lookingDirection));
        } else {
            setAnimation(idleAnimations.get(lookingDirection));
        }
    }

    public void setAnimation(PlayerAnimationType animationType){

        if(!animationType.equals(currentAnimationType)){
            playerRenderer.resetTimer();
        }
        currentAnimationType = animationType;

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

        int[] keys = new int[4];
        keys[MainDirection.UP.ordinal()] = (Gdx.input.isKeyPressed(Input.Keys.W)) ? 1 : 0;
        keys[MainDirection.RIGHT.ordinal()] = (Gdx.input.isKeyPressed(Input.Keys.D)) ? 1 : 0;
        keys[MainDirection.DOWN.ordinal()] = (Gdx.input.isKeyPressed(Input.Keys.S)) ? 1 : 0;
        keys[MainDirection.LEFT.ordinal()] = (Gdx.input.isKeyPressed(Input.Keys.A)) ? 1 : 0;

        direction = new Vector2((float) (keys[MainDirection.RIGHT.ordinal()] - keys[MainDirection.LEFT.ordinal()]), (float) (keys[MainDirection.UP.ordinal()] - keys[MainDirection.DOWN.ordinal()]));
        direction.nor();


        //for camera controller, only zoom in one direction if the player is moving
        if(isMoving()){
            lookingDirectionVector = direction;
        }


        if (oneDirectionalKeyPressed(keys)) {
            lookingDirection = getMainDirection(keys);
        }

        xInternal += direction.x * delta * speed;
        yInternal += direction.y * delta * speed;

        updateAnimation();

    }
}