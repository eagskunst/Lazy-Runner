package com.lazyrunner.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.lazyrunner.Constantes.PIXELS_IN_METER;

/**
 * Español:
 * El suelo, no necesita mucha explicación.
 * English:
 * The floor, doesn't need a lot of explanation.
 */

public class SueloEntity extends Actor {

    private Texture sueloTexture, overfloorTexture;
    private Body sueloBody,leftBody;
    private Fixture sueloFixture,leftFixture;
    private World mundo;

    //Pasamos el mundo de PantallaJuego, la texture del suelo y del 'sobresuelo', donde comienza,el ancho y su posicion en el eje Y.
    public SueloEntity(World mundo, Texture sueloTexture, Texture overfloorTexture,float x, float width,float y){
        this.mundo = mundo;
        this.sueloTexture = sueloTexture;
        this.overfloorTexture = overfloorTexture;

        BodyDef def = new BodyDef();
        def.position.set(x + width / 2,y - 0.5f);
        sueloBody = mundo.createBody(def);

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(width / 2,0.5f);
        sueloFixture = sueloBody.createFixture(sueloShape,1);
        sueloFixture.setUserData("suelo");
        sueloShape.dispose();

        BodyDef leftdef = new BodyDef();
        leftdef.position.set(x,y - 0.5f);
        leftBody = mundo.createBody(leftdef);
        PolygonShape leftShape = new PolygonShape();
        leftShape.setAsBox(0.02f,0.45f);
        leftFixture = leftBody.createFixture(leftShape,1);
        leftFixture.setUserData("pinchos");
        leftShape.dispose();

        setSize(width * PIXELS_IN_METER,PIXELS_IN_METER);
        setPosition(x * PIXELS_IN_METER,(y-1)*PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sueloTexture,getX(),getY(),getWidth(),getHeight());
        batch.draw(overfloorTexture, getX(), getY() + 0.9f * getHeight(), getWidth(), 0.1f * getHeight());
    }

    public void detach(){
        sueloBody.destroyFixture(sueloFixture);
        mundo.destroyBody(sueloBody);
    }

}