package com.example.birdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class BirdGame extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    Circle birdCircle;
    Circle[] enemyCircle;
    Circle[] enemyCircle2;
    Circle[] enemyCircle3;
    BitmapFont font;
    BitmapFont font2;
    BitmapFont font12;
    BitmapFont font13;


    SpriteBatch batch;
    Texture background,background2,background3,background4;
    Texture bee1, bee2, bee3;
    Texture bird;
    Texture cactus;
    Texture bee;   //TARGET & ENEMY
    float birdX;

    float birdY;
    int score =0;
    int scoredEnemy=0;
    int gameState = 0;

    float velocity = 0;
    float gravity = 0.3f;

    int numberOfEnemies = 4;
    float[] enemyX = new float[numberOfEnemies];
    float distance = 0;
    float enemyVelocity = 5;
    float[] enemyOffSet = new float[numberOfEnemies];
    float[] enemyOffSet2 = new float[numberOfEnemies];
    float[] enemyOffSet3 = new float[numberOfEnemies];
    Random random;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        background2 = new Texture("background2.png");
        background3 = new Texture("background3.png");
        background4 = new Texture("background4.png");


        cactus = new Texture("cactus.png");
        bird = new Texture("bird.png");
        birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
        birdY = Gdx.graphics.getHeight() / 3;
        bee = new Texture("bee.png");
        bee1 = new Texture("bee.png");
        bee2 = new Texture("bee.png");
        bee3 = new Texture("bee.png");
        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();

        /*font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4);*/

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontnew.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter2.size = 80;
        parameter2.color=Color.WHITE;

        font13 = new BitmapFont();
        font13 = generator.generateFont(parameter2);


        parameter1.size = 100;
        parameter1.color=Color.RED;
        font12 = new BitmapFont();
        font12 = generator.generateFont(parameter1); // font size 12 pixels
        generator.dispose();


        /*font2 = new BitmapFont();
        font2.setColor(Color.RED);
        font2.getData().setScale(8);*/

        ////TEXT COLOR
        birdCircle = new Circle();
        shapeRenderer = new ShapeRenderer();

        enemyCircle = new Circle[numberOfEnemies];
        enemyCircle2 = new Circle[numberOfEnemies];
        enemyCircle3 = new Circle[numberOfEnemies];
        for (int i = 0; i < numberOfEnemies; i++) {

            enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

            enemyCircle[i] = new Circle();
            enemyCircle2[i] = new Circle();
            enemyCircle3[i] = new Circle();
        }
    }

    @Override
    public void render() {

        batch.begin();

        if(score>=0 || score>= 25){
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }
        if(score>=5 || score>= 30) {
            batch.draw(background2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        if(score>=10 || score>= 35 ){
            batch.draw(background3, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }
        if(score>=15 || score>= 45){
            batch.draw(background4, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        if(score>=20 || score>= 60){
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }


        //batch.draw(cactus,0,Gdx.graphics.getHeight()/8,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        if (gameState == 1) {
            if(enemyX[scoredEnemy]< Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2){
                score++;
                if(scoredEnemy<numberOfEnemies-1){
                    scoredEnemy++;
                }else{
                    scoredEnemy=0;
                }
            }
            if (Gdx.input.justTouched()) {
                velocity = -10;
            }
            for (int i = 0; i < numberOfEnemies; i++) {
                if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;
                    enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                enemyX[i] = enemyX[i] - enemyVelocity;
                batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

                enemyCircle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
                enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
                enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
            }
            if (birdY > 0 || velocity < 0) {
                velocity = velocity + gravity;
                birdY = birdY - velocity;
            } else {
                gameState = 2;
            }
        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            font12.draw(batch,"Game Over!",Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                birdY = Gdx.graphics.getHeight() / 3;

                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

                    enemyCircle[i] = new Circle();
                    enemyCircle2[i] = new Circle();
                    enemyCircle3[i] = new Circle();
                }
                velocity = 0;
                scoredEnemy=0;
                score=0;
            }
        }

        if (birdY >=Gdx.graphics.getHeight() ){

            gameState = 2;
        }
        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
        font13.draw(batch,String.valueOf(score),100, 200);
        batch.end();

        birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

        for (int i = 0; i < numberOfEnemies; i++) {
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight()/2+enemyOffSet[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth()/30);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight()/2+enemyOffSet2[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth()/30);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight()/2+enemyOffSet3[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth()/30);
            if (Intersector.overlaps(birdCircle, enemyCircle[i]) || Intersector.overlaps(birdCircle, enemyCircle2[i]) || Intersector.overlaps(birdCircle, enemyCircle3[i])) {
                gameState = 2;
            }
        }
        //shapeRenderer.end();
    }
    @Override
    public void dispose() {
    }
}