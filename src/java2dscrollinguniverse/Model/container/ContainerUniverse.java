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
package java2dscrollinguniverse.Model.container;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java2dscrollinguniverse.Model.PerimeterSide;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.actors.CenterOfViewActor;
import java2dscrollinguniverse.Model.actors.Wall;

/**
 *
 * @author andrewnyhus
 */
public class ContainerUniverse {
    
    private ArrayList<Actor> membersOfContainer;
    private Dimension boundsDimension;
    private MemberFactory factory;
    
    private Wall[] perimeterWalls;//wall: (0)left (1)top (2)right (3)bottom.
    private Actor bgRect;
    private CenterOfViewActor centerOfViewActor;
    
    public ContainerUniverse(Dimension boundsDimension){
        this.boundsDimension = boundsDimension;
        this.factory = new MemberFactory(this.getBoundsDimension());
        
        this.perimeterWalls = this.factory.generateWalls();
        this.bgRect = this.factory.getBackgroundRect();
        this.centerOfViewActor = new CenterOfViewActor(15, 15);//create centerOfViewActor with width and height
        
        this.membersOfContainer = this.factory.generateMiscellaneousActorsRandomly();
    }

    public boolean actorIsValidInContainerUniverse(Actor a){
        if(a.getLeftMostValue() < this.getXMin() ||
                a.getTopMostValue() < this.getYMin()||
                a.getBottomMostValue() > this.getYMax()||
                a.getRightMostValue() > this.getXMax())
            return false;
        return true;
    }
    
    public void attemptToMoveActor(Actor a, TwoDimensionalMovement movement){
        
        //get actor's current location
        Point origActorLoc = a.getTopLeftLocation();
        //store actor's location if the move is completed successfully
        Point actorLocIfMoveCompleted = new Point(origActorLoc.x + movement.getXMovement(),
                                                  origActorLoc.y + movement.getYMovement());

        //clone Actor 'a' for assessing move before completing it on 'a'
        Actor cloneActorToTest = Actor.copyInstanceOfActor(a);
        
        //completing move on the clone actor
        cloneActorToTest.setTopLeftLocation(actorLocIfMoveCompleted);
        
        //
        int newX, newY;
        
        //if clone actor too far left
        if(cloneActorToTest.getLeftMostValue() < this.getXMin()){
            newX = this.getXMin();
            
        //if clone actor too far right    
        }else if(cloneActorToTest.getRightMostValue() > this.getXMax()){
            int cloneActorWidth = cloneActorToTest.getWidth();
            newX = this.getXMax() - cloneActorWidth;
            
        //if clone actor location is okay     
        }else{
            newX = actorLocIfMoveCompleted.x;
        }
        
        //if clone actor is too far up
        if(cloneActorToTest.getTopMostValue() < this.getYMin()){
            newY = this.getYMin();
            
        //if clone actor is too far down
        }else if(cloneActorToTest.getBottomMostValue() > this.getYMax()){
            int cloneActorHeight = cloneActorToTest.getHeight();
            newY = this.getYMax() - cloneActorHeight;
            
        //if clone actor location is okay     
        }else{
            newY = actorLocIfMoveCompleted.y;
        }
        
        //store new location for actor 'a'
        Point newLocForActor = new Point(newX, newY);
        
        //set new location for actor 'a'
        a.setTopLeftLocation(newLocForActor);
        
    }
    
    
    public boolean foundDuplicateID(){
        ArrayList<Integer> idsFound = new ArrayList();
        
        for(Actor a: this.getMembersOfContainer()){
            int currentId = a.getIdNumber();
            
            if(idsFound.indexOf(currentId) == -1){
                idsFound.add(currentId);
            }else{
                return true;
            }
            
        }
        
        return false;
    }
        
    public Actor getBackgroundRect(){
        return this.bgRect;
    }

    
    /**
     * @return the boundsDimension
     */
    public Dimension getBoundsDimension() {
        return boundsDimension;
    }
   
    public CenterOfViewActor getCenterOfViewActor(){
        return this.centerOfViewActor;
    }

    public ArrayList<Actor> getMembersIntersectingWithPoint(Point p){
        ArrayList<Actor> returnActors = new ArrayList();
        
        for(Actor aInContainer: this.getMembersOfContainer()){
        
            Shape shapeWhenDrawn = this.getShapeWithOffsetFromOrigin(aInContainer.getShape(),
                    aInContainer.getTopLeftLocation());
            
            if(shapeWhenDrawn.contains(p))
                returnActors.add(aInContainer);
        
        }
        
        return returnActors;
    }    
    
    
    /**
     * @return the membersOfContainer
     */
    public ArrayList<Actor> getMembersOfContainer() {
        return membersOfContainer;
    }

    
    public Wall[] getPerimeterWalls(){
        return this.perimeterWalls;
    }
    
    
    public Shape getShapeWithOffsetFromOrigin(Shape s, Point p){
                
        if(s instanceof Rectangle){
            Rectangle returnShape = new Rectangle(p.x, p.y, s.getBounds().width, s.getBounds().height);
            return returnShape;
        }else if(s instanceof Ellipse2D.Double){
            Ellipse2D.Double returnShape = new Ellipse2D.Double(p.getX(), p.getY(), s.getBounds().getWidth(), s.getBounds().getHeight());
            return returnShape;
        }
        
        //TODO: include more shapes than just rectangle
        
        return s;
    }    
    
    
    /**
     * Returns maximum x (rightmost) value a bounded actor can have in the container.
     * @return xMax
     */
    public int getXMax(){
        PerimeterSide rightSide = PerimeterSide.RIGHT;        
        
        int xMax = this.perimeterWalls[rightSide.getValue()].getLeftMostValue();
        
        return xMax;
    }
    
    
    /**
     * Returns the minimum x (leftmost) value that a bounded actor should be able to have in the 
     * container.
     * @return xMin
     */
    public int getXMin(){
        PerimeterSide leftSide = PerimeterSide.LEFT;
        
        int xMin = this.perimeterWalls[leftSide.getValue()].getRightMostValue();
        
        return xMin;
    }
    
    
    
    /**
     * Returns maximum y (bottommost) value a bounded actor can have in the container
     * @return yMax
     */
    public int getYMax(){
        PerimeterSide bottomSide = PerimeterSide.BOTTOM;
        
        int yMax = this.perimeterWalls[bottomSide.getValue()].getTopMostValue();
        
        return yMax;
    }   

    
    
    /**
     * Returns minimum y (topmost) value a bounded actor can have in the container.
     * @return yMin
     */
    public int getYMin(){
        PerimeterSide topSide = PerimeterSide.TOP;
        
        int yMin = this.perimeterWalls[topSide.getValue()].getBottomMostValue();

        return yMin;
    }
    
    public void handleDuplicates(){

        ArrayList<Integer> arrayListOfIndexesToDelete = new ArrayList();
        
        for(int i = 0; i < this.membersOfContainer.size(); i++){
            
            Actor currentMember = this.membersOfContainer.get(i);
            
            int j = i + 1;
            
            while(j < this.membersOfContainer.size()){

                Actor currentMemberToCompare = this.membersOfContainer.get(j);
                if(currentMember.equals(currentMemberToCompare))
                    arrayListOfIndexesToDelete.add(i);                
                j++;
            }
                        
        }
        
        for(int index: arrayListOfIndexesToDelete){
            this.membersOfContainer.remove(index);
        }
        
        this.resetIDsOfMembers();
    
    }

    
    public void resetIDsOfMembers(){
    
        while(this.foundDuplicateID()){
            int i = 0;
            for(Actor a: this.getMembersOfContainer()){
                a.setIdNumber(i);
                i++;
            }
        }
    
    }
    


    /**
     * @param boundsDimension the boundsDimension to set
     */
    public void setBoundsDimension(Dimension boundsDimension) {
        this.boundsDimension = boundsDimension;
    }
    

    

    
}
