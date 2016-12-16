package com.lazyrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Español:
 * Pantalla en caso de perder. Se crea una stage, una skin, la imagen y un boton de texto.
 * Estás clases pertenecen a la UI de Scene2D.
 * English:
 * If we lose, this screen will show up. We create a stage, a skin, an image and a text button.
 * This classes belongs to the UI of Scene2D.
 */

public class GameOverScreen extends PantallaBase{

    private Stage stage;//El stage de la pantalla.
    private Skin skin; //Para representar el estilo.
    private Image oh_no;
    private TextButton retry;//El text button que nos enviará a Retry
    private TextField score;
    public GameOverScreen(final LazyRunner game) {
        super(game);

        stage = new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        oh_no = new Image(game.getManager().get("game over.png",Texture.class));
        retry = new TextButton("Retry!",skin);

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.pantallaJuego.setScore(0);
                game.setScreen(game.pantallaJuego);//Si tocamos al botón 'Retry!', cambiar de pantalla.
            }
        });

        oh_no.setPosition(320 - oh_no.getWidth() / 2, 320 - oh_no.getHeight());
        retry.setSize(200,100);
        retry.setPosition(220,50);
        stage.addActor(retry);
        stage.addActor(oh_no);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        score = new TextField("Score: "+game.pantallaJuego.getScore(),skin);
        score.setPosition(250,180);
        stage.addActor(score);
        /*Stage hereda de inputProcessor y para poder tener interacción
        con el botón, le pasamos esta stage.
        */
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);//Eliminamos el inputProcessor para evitar problemas a la hora de jugar.
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }


    //Renderizamos para poder interactuar.
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
