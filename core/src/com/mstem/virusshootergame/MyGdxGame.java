package com.mstem.virusshootergame;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MainClass, creates, render
 */
public class MyGdxGame extends Game {
    //utilities
    public static final int VIEWPORT_WIDTH = 1000;
    public static final int VIEWPORT_HEIGHT = 558;
    SpriteBatch batch;
    private OrthographicCamera camera;

    //variables for checking game conditions
    private float timeRemain = 120f;
    public int winScore = 200;
    public int playerScore = 0;

    //general
    Texture background, gunTexture;
    private Sprite gunSprite;
    private AnimatedSprite gunAnimated;
    private ShotManager shotManager;
    //collision detection for each type of target
    private CollisionDetect collisionDetectGood;
    private CollisionDetect collisionDetectBad;
    private CollisionDetect collisionDetectVirus;
    //list of targets
    private ArrayList<Target> goodTargets = new ArrayList<Target>();
    private ArrayList<Target> badTargets = new ArrayList<Target>();
    private ArrayList<Target> virusTargets = new ArrayList<Target>();
    private Random random = new Random();

    public MyGdxGame() {
        this.create();
    }

    /**
     * create
     */
	@Override
	public void create () {

        //Set up the camera with a dimension;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        //for drawing texture and sprites on the screen
		batch = new SpriteBatch();

        //background texture
		background = new Texture(Gdx.files.internal("android/assets/background_mosaic.jpg"));

        Random random = new Random();

        //gun sprite and initial position
        gunTexture = new Texture(Gdx.files.internal("android/assets/data/gun1.png"));
        gunSprite = new Sprite(gunTexture);
        gunAnimated = new AnimatedSprite(gunSprite);
        gunAnimated.setPosition(background.getWidth()/2-(gunSprite.getWidth()/2),30);

        //bullet texture and initialization for action
        Texture shotTexture = new Texture(Gdx.files.internal("android/assets/data/bullet.png"));
        shotManager = new ShotManager(shotTexture);

        //Target setup
        setupTargets();

        //Collision Detection for all targets
        collisionDetectGood = new CollisionDetect(gunAnimated,goodTargets,shotManager);
        collisionDetectBad = new CollisionDetect(gunAnimated, badTargets, shotManager);
        collisionDetectVirus = new CollisionDetect(gunAnimated, virusTargets, shotManager);

	}

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * render the screen
     */
	@Override
	public void render () {
//        //initialize window
        //clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //drawing objects to the screen
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(background, 0, 0);

        //check if game is over
        if(!isGameOver()) {
            //can't seem to move to a method
            gunAnimated.draw(batch);
            if(timeRemain >40f){
            for (Target good : goodTargets) {
                good.draw(batch);
            }}
            for (Target bad : badTargets) {
                bad.draw(batch);
            }
            if (timeRemain <= 60f) {
                for (Target virus : virusTargets) {
                    virus.draw(batch);
                }
            }
            shotManager.draw(batch);

            //check user input
            handleInput();
            //actions in response to inputs
            update();

            //check if the game is finished
            isGameOver();
        }

        batch.end();
	}

    /**
     * update
     */
    public void update() {
        gunAnimated.move();
        for(Target good: goodTargets) {
            good.update();
        }
        for(Target bad: badTargets) {
            bad.update();
        }
        for(Target virus: virusTargets) {
            virus.update();
        }
        shotManager.update();

        //check collision
        collisionDetectGood.hasCollide();
        collisionDetectBad.hasCollide();
        collisionDetectVirus.hasCollide();

        addPlayerScore();

        //count down timer
        countDown();
    }

    /**
     * check for user input and reacts
     */
    private void handleInput() {
        //if left or a is pressed
        if (gunSprite.getX() > 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.A)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveLeft(500);
                }
                else {
                    gunAnimated.moveLeft(200);
                }
            }
        }
        //if right or d is pressed
        if(gunSprite.getX() < background.getWidth() - gunSprite.getWidth()){
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveRight(500);
                }
                else {
                    gunAnimated.moveRight(200);
                }
            }
        }
        //if up or space or w is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
                Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
                Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            shotManager.fire(gunAnimated.getX());
        }
    }

    /**
     * Check if the any of the game over condition are meet
     * @return
     */
    private boolean isGameOver() {
        if(shotManager.shotRemain == 0)
            return true;
        else if(timeRemain == 0)
            return true;
        else if(playerScore == winScore)
            return true;
        return false;
    }

    /**
     * count down timer
     * @return
     */
    private float countDown() {
        return timeRemain -= Gdx.graphics.getDeltaTime();
    }

    /**
     * sets up the targets
     */
    public void setupTargets() {
        Target target;
        //for reading files for each target
        String[] targetGoodNames = {"update.png", "changepassword.png", "intallanti.png","runscan.png"} ;
        String[] targetBadNames = {"attachment.png", "ad.png", "unknownsender.png", "freeitem.png"
                ,"facebookapp.png"};
        String[] targetVirus = {"adware.png", "spyware.png" };
        for(int i = 0; i < targetGoodNames.length; i++) {
            Texture targetTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetGoodNames[i]));
            target = new Target(targetTexture, random.nextInt(80)+50, 25);
            goodTargets.add(target);
        }
        for(int a = 0; a < targetBadNames.length; a++) {
            Texture adwareTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetBadNames[a]));
            target = new Target(adwareTexture, random.nextInt(120)+80, -10);
            badTargets.add(target);
        }
        for(int i = 0; i < targetVirus.length; i++) {
            Texture targetTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetVirus[i]));
            target = new Target(targetTexture, random.nextInt(250)+150, 50);
            virusTargets.add(target);
        }
    }

    public void addPlayerScore() {
        playerScore = collisionDetectBad.getNumberOfHits() * -10 +
                collisionDetectGood.getNumberOfHits()*25 +
                collisionDetectVirus.getNumberOfHits()*50;
    }

    @Override
    public void resize(int width, int height) {

    }

}//End of MyGdxGame.java
