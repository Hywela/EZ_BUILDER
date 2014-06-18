package com.game.ez_builder.builder.physics;

/**
 * Created by Kristoffer on 07.06.2014.
 */
public class BodyQueueDef {
    private int actorID;
    private org.jbox2d.dynamics.BodyDef bd;
    private boolean isACircle;
    public BodyQueueDef(int _actorID, org.jbox2d.dynamics.BodyDef _bd, boolean isACircle) {
        bd = _bd;
        actorID = _actorID;
        this.isACircle = isACircle;
    }

    public int getActorID() { return actorID; }
    public org.jbox2d.dynamics.BodyDef getBd() { return bd; }
    public boolean isItACircle(){return isACircle;}
}
