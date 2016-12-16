package com.lazyrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Español:
 * Pantalla de menú. Se puede iniciar a jugar o ver los créditos. Se crea una stage, una skin,
 * la imagen y un boton de texto. Estás clases pertenecen a la UI de Scene2D.
 * English:
 * Menu screen. You could choose between the Play Screen or the Credits Screen. We create a stage,
 * a skin, an image and a text button. This classes belongs to the UI of Scene2D.
 */

public class MenuPantalla extends PantallaBase {

    private Stage stage;//El stage de la pantalla.
    private Skin skin; //Para representar el estilo.
    private Image logo;
    private TextButton play,creditos;//El text button que nos enviará a Retry

    public MenuPantalla(final LazyRunner game) {
        super(game);

        stage = new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        logo = new Image(game.getManager().get("logo.png",Texture.class));
        play = new TextButton("Play!",skin);
        creditos = new TextButton("Credits",skin);

        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.pantallaJuego);//Si tocamos al botón 'Retry!', cambiar de pantalla.
            }
        });

        creditos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.creditos);
            }
        });

        logo.setPosition(310 - logo.getWidth() / 2, 320 - logo.getHeight());
        play.setSize(200, 40);
        creditos.setSize(200, 40);
        play.setPosition(220,50);
        creditos.setPosition(220, 5);
        stage.addActor(play);
        stage.addActor(creditos);
        stage.addActor(logo);
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
        stage.act();
        stage.draw();
    }
}
