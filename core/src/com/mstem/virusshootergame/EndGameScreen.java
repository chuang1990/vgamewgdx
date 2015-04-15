package com.mstem.virusshootergame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mstem.virusshootergame.MyGdxGame;

import java.util.Random;

/**
 * Created by catherinehuang on 4/14/15.
 */
public class EndGameScreen extends Game {
    public static final int VIEWPORT_WIDTH = 1000;
    public static final int VIEWPORT_HEIGHT = 558;
    SpriteBatch batch;
    private OrthographicCamera camera;

    private MyGdxGame newGame;

    //setup font
    private BitmapFont font;

    //things to display
    private int playerScore;
    private int timeUsed;
    private int ramainBullet;

    //buttons
    private Button restart;
    private Button mainReturn;

    //general
    Texture background;
    private Random random = new Random();

    public EndGameScreen(int score, int time, int bullet) {
        this.playerScore = score;
        this.timeUsed = time;
        this.ramainBullet = bullet;
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
        background = new Texture(Gdx.files.internal("android/assets/background/background_mosaic.jpg"));

        //font
        font = new BitmapFont();
        font.setColor(1,1,1,1);

        //buttons
        restart = new Button();
        mainReturn = new Button();


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
        //initialize window
        //clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //drawing objects to the screen
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);

        //check if game is over

        //font

        handleInput();

        batch.end();
    }


    /**
     * check for user input and reacts
     */
    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            newGame = new MyGdxGame();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            return;
        }

    }

    @Override
    public void resize(int width, int height) {

    }

}
