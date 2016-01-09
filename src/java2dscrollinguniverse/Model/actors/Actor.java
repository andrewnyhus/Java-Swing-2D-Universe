/* 
 * The MIT License
 *
 * Copyright 2015 andrewnyhus.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package java2dscrollinguniverse.Model.actors;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java2dscrollinguniverse.Model.actors.ActorLabel.PositionOfLabel;

/**
 *
 * @author andrewnyhus
 */
public class Actor {
    
    private ActorType type;
    private Point topLeftLocation;
    private Color color;
    private Shape shape;
    private Actor[] childActors;
    private ActorLabel actorLabel;
    private int idNumber;
    
    public Actor(ActorType type, Point loc, Color color){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.childActors = null;
        this.actorLabel = new ActorLabel("", PositionOfLabel.LEFT_OF_BOTTOM);
    }
    
    public Actor(ActorType type, Point loc, Color color, Shape s){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.shape = s;        
        this.childActors = null;
        this.actorLabel = new ActorLabel("", PositionOfLabel.LEFT_OF_BOTTOM);
    }
    
    public Actor(ActorType type, Point loc, Color color, Shape s, Actor[] childActors){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.shape = s;        
        this.childActors = childActors;
        this.actorLabel = new ActorLabel("", PositionOfLabel.LEFT_OF_BOTTOM);
    }

    public Actor(ActorType type, Point loc, Color color, Shape s, Actor[] childActors, ActorLabel actorLabel){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.shape = s;        
        this.childActors = childActors;
        this.actorLabel = actorLabel;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Actor){
            Actor a = (Actor)obj;
            return (a.actorLabel.getLabelText().equals(this.actorLabel.getLabelText()))&&
            (a.actorLabel.getPosition().getValue()==this.actorLabel.getPosition().getValue())&&
            (a.shape.equals(this.shape))&&
            (a.color.equals(this.color))&&
            (a.topLeftLocation.equals(this.topLeftLocation));
            
        }else{
            return false;
        }
    }
    
    public int getWidth(){
        return this.getShape().getBounds().width;
    }
    
    public int getHeight(){
        return this.getShape().getBounds().height;
    }
    
    
    /**
     * Returns the left most (minimum x) value within the actors bounds.
     * @return xMin
     */
    public int getLeftMostValue(){
        int xMin = this.topLeftLocation.x;
        return xMin;
    }
    
    
    /**
     * Returns the right most (maximum x) value within the actors bounds.
     * @return xMax
     */
    public int getRightMostValue(){
        int xMax = this.topLeftLocation.x +
                this.getWidth();

        return xMax;
    }
    
    
    /**
     * Returns the top most (minimum y) value within the actors bounds.
     * @return yTop
     */
    public int getTopMostValue(){
        int yTop = this.topLeftLocation.y;
        
        return yTop;
    }
    
    /**
     * Returns the bottom most (maximum y) value within the actors 
     * @return 
     */
    public int getBottomMostValue(){
        int yBottom = this.topLeftLocation.y +
                this.getHeight();
        return yBottom;
    }
    
    /**
     * Gives access to the topLeftLocation object of this actor.
     * @return Point this.topLeftLocation
     */
    public Point getTopLeftLocation(){
        return this.topLeftLocation;
    }
    
    /**
     * Takes in the newLoc topLeftLocation object and updates the topLeftLocation 
 of this Actor, with respect to the world (The World object), not with respect to 
 the currently drawn portion of the world (Java Swing stuff).
     * @param newLoc 
     */
    public void setTopLeftLocation(Point newLoc){
        this.topLeftLocation = newLoc;

        
    }

    /**
     * @return the type
     */
    public ActorType getType() {
        return type;
    }

    /**
     * @return the childActors
     */
    public Actor[] getChildActors() {
        return childActors;
    }

    /**
     * @param childActors the childActors to set
     */
    public void setChildActors(Actor[] childActors) {
        this.childActors = childActors;
    }

    /**
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @param shape the shape to set
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }
    
    
    public Point getLocationOfLabelPosition(){
        Point currentActorLoc = this.getTopLeftLocation();
        int labelPosX = currentActorLoc.x, labelPosY = currentActorLoc.y;
        int offsetFromActor = 12;
        switch(this.getActorLabel().getPosition()){
        
            case LEFT_OF_TOP:
                //leave labelPosX alone, should have same x value as actor
                labelPosY -= offsetFromActor; //just above actor
                break;
                
            case MIDDLE_OF_TOP:
                labelPosX += this.getWidth()/2; // actor.x + 1/2 of width
                labelPosY -= offsetFromActor;//just above actor
                break;
            
            case RIGHT_OF_TOP:
                labelPosX += this.getWidth()*(4/5); //label.x should be mostly at right corner 
                labelPosY -= offsetFromActor;//just above actor
                break;
                
            case TOP_OF_RIGHT:
                labelPosX += offsetFromActor + this.getWidth();//label.x is actor.x + width + offsetFromActor
                //labelPosY is left alone, since it's at the top
                break;
                
            case MIDDLE_OF_RIGHT:
                labelPosX += offsetFromActor + this.getWidth();//label.x is actor.x + width + offsetFromActor
                labelPosY += this.getHeight()/2; //label.y aligned with actor along y axis
                break;
                
            case BOTTOM_OF_RIGHT:
                labelPosX += offsetFromActor + this.getWidth();//label.x is actor.x + width + offsetFromActor
                labelPosY += this.getHeight()*(4/5);//mostly at bottom of actor
                break;
                
            case RIGHT_OF_BOTTOM:
                labelPosX += this.getWidth()*(4/5); //label.x should be mostly at right corner 
                labelPosY += this.getHeight() + offsetFromActor;//label.y is below actor
                break;
                
            case MIDDLE_OF_BOTTOM:
                labelPosX += this.getWidth()/2; // actor.x + 1/2 of width
                labelPosY += this.getHeight() + offsetFromActor;//label.y is below actor
                break;
                
            case LEFT_OF_BOTTOM:
                //leave labelPosX alone, should have same x value as actor
                labelPosY += this.getHeight() + offsetFromActor;//label.y is below actor
                break;
                
        }
        
        return new Point(labelPosX, labelPosY);
        
    }
    
    public static Actor copyInstanceOfActor(Actor a){
        return new Actor(a.getType(), a.getTopLeftLocation(),
                a.getColor(), a.getShape(), a.getChildActors(), a.getActorLabel());
    }

    /**
     * @return the actorLabel
     */
    public ActorLabel getActorLabel() {
        return actorLabel;
    }

    /**
     * @param actorLabel the actorLabel to set
     */
    public void setActorLabel(ActorLabel actorLabel) {
        this.actorLabel = actorLabel;
    }
    
    @Override
    public String toString(){
        return "Shape:" + this.getShape().getClass().getName() +
                "\nw:" + this.shape.getBounds().width + " h:" + this.shape.getBounds().height +
                "\nx:" + this.getTopLeftLocation().x + " y:" + this.getTopLeftLocation().y;
    }

    /**
     * @return the idNumber
     */
    public int getIdNumber() {
        return idNumber;
    }

    /**
     * @param idNumber the idNumber to set
     */
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
}
