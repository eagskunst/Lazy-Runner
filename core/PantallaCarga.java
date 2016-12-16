package com.lazyrunner;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Español:
 * Pantalla de carga. Igual que las demás, tiene un stage, una skin, etc.
 * Muy simple.
 * English:
 * Loading screen. As the other screen, has a stage, a skin, etc.
 * Very simple.
 */

public class PantallaCarga extends PantallaBase {

    private Stage stage;
    private Skin skin;
    private Label loading;//El label pertenece a Scene2DUI y nos permite mostrar texto

    public PantallaCarga(LazyRunner game) {
        super(game);
        stage = new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        loading = new Label("Loading...",skin);

        loading.setPosition(320 - loading.getWidth() / 2, loading.getHeight() / 2);
        stage.addActor(loading);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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

        //Update se ocupa de cargar un recurso y devuelve true si ha cargado todos los recursos
        if(game.getManager().update()){
            game.despuesDeCargar();
        }
        //getProgress devuelve un flotante con el progreso de carga de assets.
        else{
            int progress = (int) (game.getManager().getProgress() * 100);
            loading.setText("Loading ("+progress+"%)");
        }

        stage.act();
        stage.draw();
    }
}
