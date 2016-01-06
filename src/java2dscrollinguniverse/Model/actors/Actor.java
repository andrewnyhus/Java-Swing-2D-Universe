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

/**
 *
 * @author andrewnyhus
 */
public class Actor {
    
    private ActorType type;
    private Point topLeftLocation;
    Color color;
    private Shape shape;
    private Actor[] childActors;
    
    public Actor(ActorType type, Point loc, Color color){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.childActors = null;
    }
    
    public Actor(ActorType type, Point loc, Color color, Shape s){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.shape = s;        
        this.childActors = null;
    }
    
    public Actor(ActorType type, Point loc, Color color, Shape s, Actor[] childActors){
        this.type = type;
        this.topLeftLocation = loc;
        this.color = color;
        this.shape = s;        
        this.childActors = childActors;
    }

    

    
 
    
    public int getWidth(){
        return this.getShape().getBounds().width;
    }
    
    public int getHeight(){
        return this.getShape().getBounds().height;
    }
    
    public Color getColor(){
        return this.color;
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
    
    
    public static Actor copyInstanceOfActor(Actor a){
        return new Actor(a.getType(), a.getTopLeftLocation(),
                a.getColor(), a.getShape(), a.getChildActors());
    }
    
    
}
