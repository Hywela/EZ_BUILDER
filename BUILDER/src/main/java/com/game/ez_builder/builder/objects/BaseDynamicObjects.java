package com.game.ez_builder.builder.objects;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.game.ez_builder.builder.div.Color3;
import com.game.ez_builder.builder.div.Constants;
import com.game.ez_builder.builder.main.WorldHandler;
import com.game.ez_builder.builder.physics.BodyQueueDef;
import com.game.ez_builder.builder.physics.PhysicWorld;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import org.jbox2d.dynamics.Body;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class BaseDynamicObjects {

    public Color3 color = new Color3(255, 255, 255);
    public boolean visible = true;

    private int id;
    private Body body = null;

    private FloatBuffer textBuffer;
    private float radius = -1;
    protected Vec2 position = new Vec2(0.0f, 0.0f);
    protected float rotation = 0.0f;
    protected FloatBuffer vertBuffer;
    protected float[] vertices;

    final float[] textureCoordinates =
            {
                    // Front face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,
            };
    // Saved for when body is recreated on a vert refresh
    private float friction;
    private float density;
    private float restitution;

    private int textureData;
    private int positionHandle;
    private int textureHandle;
    private int textureCord;
    private int colorHandle;
    private int modelHandle;

    public BaseDynamicObjects() {

        this.id = WorldHandler.getNextParticleId();
        WorldHandler.playerActors.add(this);
        setUniforms(Constants.getColorShader());
    }
    public void setUniforms(int shader){
        positionHandle = GLES20.glGetAttribLocation(shader, "Position");
        textureHandle = GLES20.glGetUniformLocation(shader, "texture");
        textureCord = GLES20.glGetAttribLocation(shader, "TexCoordinate");
        colorHandle = GLES20.glGetAttribLocation(shader, "color");
        modelHandle = GLES20.glGetUniformLocation(shader, "ModelView");
    }
    public void setRadius(float radius){
        this.radius = radius;
    }
    public void setVertices(float[] _vertices) {

        this.vertices = _vertices;

        // Allocate a new byte buffer to move the vertices into a FloatBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertBuffer = byteBuffer.asFloatBuffer();
        vertBuffer.put(vertices);
        vertBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        textBuffer = byteBuffer.asFloatBuffer();
        textBuffer.put(textureCoordinates);
        textBuffer.position(0);

        if(body != null) {
            destroyPhysicsBody();
            createPhysicsBody(density, friction, restitution);
        }
    }

    public void draw() {

        if(!visible) { return; }

        // Update local data from physics engine, if applicable
        if(body != null) {
            position = WorldHandler.worldToScreen(body.getPosition());
            rotation = body.getAngle() * 57.2957795786f;
        }

        // Construct mvp to be applied to every vertex
        float[] modelView = new float[16];

        // Equivalent of gl.glLoadIdentity()
        Matrix.setIdentityM(modelView, 0);

        // gl.glTranslatef()
        Matrix.translateM(modelView, 0, position.x, position.y, 1.0f);

        // gl.glRotatef()
        Matrix.rotateM(modelView, 0, rotation, 0, 0, 1.0f);

        // Load our matrix and color into our shader
        GLES20.glUniformMatrix4fv(modelHandle, 1, false, modelView, 0);

        GLES20.glVertexAttrib3f(colorHandle, (float)color.r/ 255.0f,(float)color.g/ 255.0f, (float)color.b/ 255.0f);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureData);
        GLES20.glUniform1i(textureHandle, 0);

        GLES20.glVertexAttribPointer(textureCord, 2 , GLES20.GL_FLOAT, false,
                0, textBuffer);
        GLES20.glEnableVertexAttribArray(textureCord);
        // Set up pointers, and draw using our vertBuffer as before
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, vertBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertices.length /2);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(textureCord);
    }

    public void createPhysicsBody(float _density, float _friction, float _restitution) {

        boolean isItACircle=false;
        if(radius >= 0){
            isItACircle = true;}

        if(body != null) { return; }
        // Save values
        friction = _friction;
        density = _density;
        restitution = _restitution;

        // Create the body
        BodyDef bd = new BodyDef();
        if(density > 0) {
            bd.type = BodyType.DYNAMIC;
        } else {
            bd.type = BodyType.STATIC;
        }
        bd.position = WorldHandler.screenToWorld(position);
        // Add to physics world body creation queue, will be finalized when possible
        PhysicWorld.requestBodyCreation(new BodyQueueDef(id, bd,isItACircle),1);
    }

    public void destroyPhysicsBody() {

        if(body == null) { return; }

        PhysicWorld.destroyBody(body,1);
        body = null;
    }

    public void onBodyCreationCircle(Body _body) {
        // Threads ftw
        body = _body;
        // Body has been created, make fixture and finalize it
        // Physics world waits for completion before continuing
        // Create fixture from vertices

        CircleShape shape = new CircleShape();
        shape.setRadius(radius/ WorldHandler.getPPM());
        // shape.set(verts, verts.length);
        // Attach fixture
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = density;
        fd.friction = friction;
        fd.restitution = restitution;
        body.createFixture(fd);
    }
    public void onBodyCreationPolygon(Body _body) {
        // Threads ftw
        body = _body;

        // Body has been created, make fixture and finalize it
        // Physics world waits for completion before continuing

        // Create fixture from vertices
        PolygonShape shape = new PolygonShape();
        Vec2[] verts = new Vec2[vertices.length / 2];

        int vertIndex = 0;
        for(int i = 0; i < vertices.length; i += 2) {
            verts[vertIndex] = new Vec2(vertices[i] / WorldHandler.getPPM(), vertices[i + 1] / WorldHandler.getPPM());
            vertIndex++;
        }

        shape.set(verts, verts.length);

        // Attach fixture
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = density;
        fd.friction = friction;
        fd.restitution = restitution;

        body.createFixture(fd);
        body.createFixture(fd);
    }
    public void setTexture(int texture) {
        this.textureData = texture;
    }
    // Modify the actor or the body
    public void setPosition(Vec2 position) {
        if(body == null) {
            this.position = position;
        } else {
            body.setTransform(WorldHandler.screenToWorld(position), body.getAngle());
        }
    }

    // Modify the actor or the body
    public void setRotation(float rotation) {
        if(body == null) {
            this.rotation = rotation;
        } else {
            body.setTransform(body.getPosition(), rotation * 0.0174532925f); // Convert to radians
        }
    }

    // Get from the physics body if avaliable
    public Vec2 getPosition() {
        if(body == null) {
            return position;
        } else {
            return WorldHandler.worldToScreen(body.getPosition());
        }
    }
    public float getRotation() {
        if(body == null) {
            return rotation;
        } else {
            return body.getAngle() * 57.2957795786f;
        }
    }

    public int getId() { return id; }
}