package com.lazyrunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Español:
 * Puesto que crear la imagen del fondo en 'PantallaJuego' llevaría mucho código, decidí
 * crearlo como una clase diferente. No es una entity pues no interactúa con nada, sin embargo si es
 * un actor puesto que hay que dibujarlo y moverlo junto con la camara.
 * English:
 * As creating the background image(Fondo means background in spanish) in 'PantallaJuego' will
 * take a lot of code, I decided to create another class. Besides is an actor, this is not an
 * entity because it doesn't interact. However, as is an actor, we have have to draw it and move
 * the texture with the camera.
 */

public class Fondo extends Actor{

    private Texture fondo;
    private Sprite fondoBack1,fondoBack2;
    /*Creamos dos sprites de fondo para ir cargando uno cuando ya se vaya a terminar
     el otro */
    private Stage stage;
    private int width,height;

    public Fondo(Texture fondo,Stage stage){//Pasamos la texture y el stage de 'PantallaJuego'.
        this.fondo = fondo;
        width = fondo.getWidth();
        height = fondo.getHeight() - 30;
        this.stage = stage;
        fondoBack1 = new Sprite(fondo,0,0,width,height);
        fondoBack2 = new Sprite(fondoBack1);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*
        Como explique arriba, este algoritmo sirve para determinar la posicion de
         la imagen2 cuando haya terminado la 1 y viceversa.
         */
        if(stage.getCamera().position.x - width/2> fondoBack2.getX()){
            fondoBack1.setPosition(fondoBack2.getX(),0);
            fondoBack2.setPosition(fondoBack1.getX()+width, 0);
        }

        fondoBack1.draw(batch);

        fondoBack2.draw(batch);
    }


    public void detach(){
        fondo.dispose();
    }

}
