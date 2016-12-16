package com.lazyrunner.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.lazyrunner.Constantes.IMPULSO_SALTO;
import static com.lazyrunner.Constantes.PIXELS_IN_METER;
import static com.lazyrunner.Constantes.VELOCIDAD_JUGADOR;

/**
 * Español:
 * El jugador o como decidí llamarlo: 'Chad'. Este personaje es un rectángulo, igual que el profesor.
 * Pero el no es estático. Así como se tiene que mover, también debe saltar, por lo que se le deben
 * aplicar distintas fuerzas.
 * English:
 * The player or how I decided to name him: 'Chad'. This entity, as the profesor, is a rectangle.
 * But he is a Dynamic Body. As he has to move, he also has to jump, so we should apply different
 * forces to him.
 */

public class ChadEntity extends Actor {

    private Texture chadTexture;
    private Body chadBody;
    private Fixture chadFixture;
    private World mundo;

    /*
    Estos métodos son públicos para llamarlos desde esta clase
    y desde 'PantallaJuego' en el contact listener para
    poder cambiarlos a placer.
     */

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    private boolean vivo = true;

    public boolean isSaltando() {
        return saltando;
    }

    public void setSaltando(boolean saltando) {
        this.saltando = saltando;
    }

    public boolean isDebeSaltar() {
        return debeSaltar;
    }

    public void setDebeSaltar(boolean debeSaltar) {
        this.debeSaltar = debeSaltar;
    }

    private boolean saltando = false;
    private boolean debeSaltar = false;

    /*
    En el constructor enviamos el mundo que está utilizando 'PantallaJuego',
    la textura y un Vector2 con la posición en la que comenzará el jugador.
     */
    public ChadEntity(World mundo, Texture texture,Vector2 posicion){
        this.mundo = mundo;
        chadTexture = texture;

        BodyDef def = new BodyDef();
        def.position.set(posicion);//Asignamos posicon
        def.type = BodyDef.BodyType.DynamicBody;//Como es un cuerpo dinámico, se especifica.
        chadBody = mundo.createBody(def);//Creamos el body.
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f,0.75f);//Un rectángulo, la mitad del largo y la mitad del ancho.
        chadFixture = chadBody.createFixture(shape,3);
        chadFixture.setUserData("chad");//Asignamos nombre a nuestra fixture
        shape.dispose();

        setSize(PIXELS_IN_METER ,PIXELS_IN_METER);//En el constructor solo asignamos tamaño, no posicion pues es dinámico.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((chadBody.getPosition().x - 0.5f) * PIXELS_IN_METER, (chadBody.getPosition().y - 0.75f) * PIXELS_IN_METER);
        batch.draw(chadTexture,getX(),getY(),getWidth(),getHeight());
    }

    //Metodo para destruir la fixture y el cuerpo cuando ya no se utilicen.
    public void detach(){
        chadBody.destroyFixture(chadFixture);
        mundo.destroyBody(chadBody);
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.justTouched()){//Siacabas de tocar la pantalla, se llama a saltar.
            salto();
        }

        if(debeSaltar){//Debe saltar es true si colisiona con el suelo, esto para el salto continuo.
            debeSaltar = false;
            salto();
        }

        if(vivo){//Aplicando la velocidad lineal.
            float velocidadY = chadBody.getLinearVelocity().y;
            chadBody.setLinearVelocity(VELOCIDAD_JUGADOR,velocidadY);
        }

        if(saltando){//Si esta saltando, lo enviamos de regreso al suelo.
            chadBody.applyForceToCenter(0, -IMPULSO_SALTO, true);
        }
    }

    private void salto(){
        /*
        Si el jugador esta vivo y no esta saltando, saltando es verdadero
        y le damos un impulso linear hacía arriba.
         */
        if(vivo && !saltando){
            saltando = true;
            Vector2 position = chadBody.getPosition();
            chadBody.applyLinearImpulse(0,IMPULSO_SALTO, position.x, position.y, true);
        }
    }
}
