/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples.PingPong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import neuLayout.Controller.UniverseController;
import neuLayout.Model.TwoDimensionalMovement;
import neuLayout.Model.actors.Actor;
import neuLayout.Model.actors.ActorType;
import neuLayout.Model.actors.CenterOfViewActor;
import neuLayout.SettingsSingleton;

/**
 *
 * @author andrewnyhus
 */
public class PingPongController extends UniverseController{

    private Dimension table_size;
    private Actor leftPaddle, rightPaddle, leftScoreBox, rightScoreBox;
    

    public PingPongController(Dimension size){
        super("Ping Pong", size, null);
        
        //the only reason that I did not use the table_size variable
        //in the call to the super constructor was because it would not
        //allow me to since the supercall needs to be the first line
        this.table_size = size;
        
        //set proper settings
        this.applyInitialSettings();
        
        //set actors
        this.setupActors();
        
        
    }

    public void setupActors(){
        Actor middleLine, leftEdge, rightEdge;
        
        this.leftScoreBox = new Actor(ActorType.miscObject, 
                        new Point(this.table_size.width/2 - 75, 50), Color.white,
                        new Rectangle(0, 0, 0, 0));

        this.rightScoreBox = new Actor(ActorType.miscObject, 
                        new Point(this.table_size.width/2 + 75, 50), Color.white,
                        new Rectangle(0, 0, 0, 0));
        
        
        
        Rectangle paddleShape = new Rectangle(0, 0, 15, 80);
        int xPaddingForPaddles = 30;
        int yPaddlesInitLoc = (this.table_size.height/2) - (paddleShape.height/2);
        
        this.leftPaddle = new Actor(ActorType.miscObject, new Point(xPaddingForPaddles, yPaddlesInitLoc), 
                                                    Color.white, paddleShape);
        this.leftPaddle.setIdString("PADDLE_LEFT");
        
        this.rightPaddle = new Actor(ActorType.miscObject, 
                new Point(this.table_size.width - xPaddingForPaddles - paddleShape.width, yPaddlesInitLoc), 
                                                    Color.white, paddleShape);
        this.rightPaddle.setIdString("PADDLE_RIGHT");

                
        Point ballPoint = new Point(this.table_size.width/2 - 10, this.table_size.height/2 - 10);
        Ball ball = new Ball(ballPoint, this.leftScoreBox, this.rightScoreBox);
        
        int leftX = SettingsSingleton.getInstance().getBorderThickness();
        int leftY = SettingsSingleton.getInstance().getBorderThickness();
        
        int edgesWidth = xPaddingForPaddles - leftX;
        int edgesHeight = this.table_size.height - (2*leftY);
        
        int rightX = this.table_size.width - edgesWidth - leftX;
        int rightY = leftY;
        
        leftEdge = new Actor(ActorType.miscObject, new Point(leftX, leftY), Color.black, 
                    new Rectangle(0, 0, edgesWidth, edgesHeight));
        leftEdge.setIdString("LEFT_EDGE");
        rightEdge = new Actor(ActorType.miscObject, new Point(rightX, rightY), Color.black, 
                    new Rectangle(0, 0, edgesWidth, edgesHeight));
        rightEdge.setIdString("RIGHT_EDGE");
        
        int middleLineThickness = 15;
        
        middleLine = new Actor(ActorType.miscObject,
                new Point(this.table_size.width/2 - middleLineThickness/2, leftY), Color.white, 
                    new Rectangle(0, 0, middleLineThickness, edgesHeight));
        
        
        
        this.addActor(this.leftPaddle);
        this.addActor(this.rightPaddle);
        this.addActor(leftEdge);
        this.addActor(rightEdge);
        this.addActor(middleLine);
        this.addActor(ball);
        this.addActor(this.leftScoreBox);
        this.addActor(this.rightScoreBox);
                        
    }
    
    
    
    private void applyInitialSettings() {
        SettingsSingleton.getInstance().setPerimeterColor(Color.white);
        SettingsSingleton.getInstance().setContainerBackgroundColor(Color.black);
        SettingsSingleton.getInstance().setLabelColor(Color.white);
        SettingsSingleton.getInstance().setScrollMode(SettingsSingleton.ViewScrollMode.DONT_SCROLL);        
    }
    
    
    
    /**
     * Adds actor a to container if it would be valid there.
     * @param a 
     */
    public void addActor(Actor a){
        //if a is valid in container
        if(this.getContainer().actorIsValidInContainerUniverse(a)){
            //add a to container
            this.getContainer().addActorToContainer(a);
        }    
    }
    
    @Override
    protected void addMenuBar(){
        //we overrode this method here to remove the default menu bar
        
    }
    
    
    //Below is the implementation for the KeyListener methods.
    //These handle detection of various keys being pressed on the keyboard.
    
 //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    @Override
    public void keyTyped(KeyEvent e) {
        //ignore
    }

    /**
     * We will only pay attention to this KeyListener method, 
     * this method fires when the user presses a key on their 
     * keyboard, and the reason we must handle this event 
     * is to respond to the keyboard either in attempts to pause, or 
     * move their paddle.
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        //store the int that corresponds to the key that was pressed
        int keycode = e.getKeyCode();

        switch(keycode){
        
            //if 's' key is pressed
            case KeyEvent.VK_S:
                
                if(this.leftPaddle.getVelocity().getYMovement() == 0){                
                    this.leftPaddle.setVelocity(new TwoDimensionalMovement(0, 3));
                }else if(this.leftPaddle.getVelocity().getYMovement() == -3){
                    this.leftPaddle.setVelocity(new TwoDimensionalMovement(0, 0));
                }            
                break;                
                
            //if 'w' key is pressed
            case KeyEvent.VK_W:
                
                if(this.leftPaddle.getVelocity().getYMovement() == 3){
                    this.leftPaddle.setVelocity(new TwoDimensionalMovement(0, 0));
                }else if(this.leftPaddle.getVelocity().getYMovement() == 0){
                    this.leftPaddle.setVelocity(new TwoDimensionalMovement(0, -3));
                }
                                
                break;
                
            //if down arrow is pressed
            case KeyEvent.VK_DOWN:
                
                if(this.rightPaddle.getVelocity().getYMovement() == 0){                
                    this.rightPaddle.setVelocity(new TwoDimensionalMovement(0, 3));
                }else if(this.rightPaddle.getVelocity().getYMovement() == -3){
                    this.rightPaddle.setVelocity(new TwoDimensionalMovement(0, 0));
                }            
                
                break;
                
            //if up arrow is pressed
            case KeyEvent.VK_UP:

                if(this.rightPaddle.getVelocity().getYMovement() == 3){
                    this.rightPaddle.setVelocity(new TwoDimensionalMovement(0, 0));
                }else if(this.rightPaddle.getVelocity().getYMovement() == 0){
                    this.rightPaddle.setVelocity(new TwoDimensionalMovement(0, -3));
                }
                
                break;
        
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }
 //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    
}
