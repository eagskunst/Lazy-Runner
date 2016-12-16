package com.lazyrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Español: Al cesar lo que es del cesar
 * Inglés: Who make it, Who deserves the congrats!
 */
public class CreditosScreen extends PantallaBase{

    private Stage stage;//El stage de la pantalla.
    private Skin skin; //Para representar el estilo.
    private Label creditos;
    private TextButton back;//El text button que nos enviará a Retry

    public CreditosScreen(final LazyRunner game) {
        super(game);
        stage = new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        back = new TextButton("Back",skin);

        creditos = new Label(
                "Lazy Runner v1.0.0\n" +
                "Copyright(C) 2016-2017 Emmanuel Guerra.\n"+
                "CC BY-NC-SA 4.0.\n"+
                "Art:\n"+
                        "Floor: White Stone Tile Floor by Tiziana.\n"+
                            "License: LGPL 2.1, LGPL 3.0, CC-BY 3.0\n"+
                        "Background: Background-Canterlot High Hallway by knightwolf09.\n"+
                "Music:\n"+
                        "Background music: Jump and Run(8-Bit) by bart.\n"+
                            "(https://goo.gl/1M6gHj)\n"+
                            "License: CC-BY 3.0.\n"+
                        "Jump sound: Platformer Jumping Sounds by dklon.\n"+
                                "License: CC-BY 3.0.\n",skin
        );

        back.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // De vuelta al menú.
                game.setScreen(game.menu);
            }
        });

        creditos.setPosition(20, 340 - creditos.getHeight());

        back.setSize(200, 60);
        back.setPosition(40, 10);

        stage.addActor(back);
        stage.addActor(creditos);
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
