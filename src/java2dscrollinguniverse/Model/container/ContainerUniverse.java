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
import java2dscrollinguniverse.SettingsSingleton;

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
    
    private boolean actorsWerePreloaded;
    
    public ContainerUniverse(Dimension boundsDimension, Actor[] preloadedMembers){
        this.boundsDimension = boundsDimension;
        
        this.factory = new MemberFactory(this.getBoundsDimension());
        
        this.perimeterWalls = this.factory.generateWalls();
        this.bgRect = this.factory.getBackgroundRect();
        this.centerOfViewActor = new CenterOfViewActor(15, 15);//create centerOfViewActor with width and height
    
        if(preloadedMembers == null){
            this.membersOfContainer = this.factory.generateMiscellaneousActorsRandomly();
            this.actorsWerePreloaded = false;
        }else{
            this.membersOfContainer = new ArrayList();
            for(Actor a: preloadedMembers){
                boolean add = this.membersOfContainer.add(a);
            }
            this.actorsWerePreloaded = true;
        }
    }

    public boolean actorIsValidInContainerUniverse(Actor a){
        if(a.getLeftMostValue() < this.getXMin() ||
                a.getTopMostValue() < this.getYMin()||
                a.getBottomMostValue() > this.getYMax()||
                a.getRightMostValue() > this.getXMax())
            return false;
        return true;
    }
    
    /**
     * This method makes all actors, including the camera/CenterOfView actor
     * take a step forward with their current velocity; All actors except for 
     * the camera/CenterOfView actor should "bounce" off of walls upon colliding.
     */
    public void stepAllActors(){
    
        //first handling CenterOfView Actor since it should not "bounce"
        //off of the walls upon collision, but instead it should take on 
        //velocity of zero for that component.  Otherwise, this follows the same
        //logic as stepActor, which has more comments than this segment does.
        //--------------------------------------------------
        //create test clone actor
        Actor testCenterActor = Actor.copyInstanceOfActor(this.getCenterOfViewActor());
        Point currentLoc = testCenterActor.getTopLeftLocation();
        TwoDimensionalMovement currentVel = testCenterActor.getVelocity();
        
        testCenterActor.setTopLeftLocation(new Point(currentLoc.x + currentVel.getXMovement(),
                                            currentLoc.y + currentVel.getYMovement()));

        int newX, newY;
        
        if(testCenterActor.getRightMostValue() > this.getXMax()){
            newX = this.getXMax() - testCenterActor.getWidth();
            //collided with right, so zero out x velocity.
            this.getCenterOfViewActor().getVelocity().setXMovement(0);           
            
        }else if(testCenterActor.getLeftMostValue() < this.getXMin()){
            newX = this.getXMin();
            //collided with left, so zero out x velocity.
            this.getCenterOfViewActor().getVelocity().setXMovement(0);
            
        }else{
            newX = testCenterActor.getTopLeftLocation().x ;
        }
        
        if(testCenterActor.getBottomMostValue() > this.getYMax()){
            newY = this.getYMax() - testCenterActor.getHeight();
            //collided with bottom, so zero out y velocity.
            this.getCenterOfViewActor().getVelocity().setYMovement(0);
            
        }else if(testCenterActor.getTopMostValue() < this.getYMin()){
            newY = this.getYMin();
            //collided with top, so zero out y velocity.
            this.getCenterOfViewActor().getVelocity().setYMovement(0);
            
        }else{
            newY = testCenterActor.getTopLeftLocation().y;
        }

        this.getCenterOfViewActor().setTopLeftLocation(new Point(newX, newY));
        
        //--------------------------------------------------
        
        //now calling stepActor() on all other members (not including background or walls
        for(Actor a: this.getMembersOfContainer()){
            this.stepActor(a);
        }
        
    }
    
    /**
     * This is a helper function for stepAllActors, which calls this method
     * on every Actor in the ContainerUniverse with the exception of the Background,
     * Wall Actors, HUD elements and the CenterOfView Actor.
     * @param a - actor to step
     */
    public void stepActor(Actor a){
        
        Point origPoint = a.getTopLeftLocation();
        
        //create clone of a to test before "stepping"
        Actor testActor = Actor.copyInstanceOfActor(a);
        
        //current location of Actor testActor and a 
        Point currentLoc = testActor.getTopLeftLocation();
        
        //current velocity of both actors
        TwoDimensionalMovement currentVel = testActor.getVelocity();
        
        //move the testActor based solely on its velocity
        testActor.setTopLeftLocation(new Point(currentLoc.x + currentVel.getXMovement(),
                                                currentLoc.y + currentVel.getYMovement()));
        
        int newX, newY;
        
        //if testActor collides with the right wall, flip x axis velocity
        //and move x value of current actor to be the highest it can be "hug the wall"
        if(testActor.getRightMostValue() > this.getXMax()){
            a.getVelocity().setXMovement(-currentVel.getXMovement());            
            newX = this.getXMax() - a.getWidth();
            
        //else if the testActor collides with the left wall, flip x vel
        //and "hug wall"
        }else if(testActor.getLeftMostValue() < this.getXMin()){
            a.getVelocity().setXMovement(-currentVel.getXMovement());
            newX = this.getXMin();
            
        //else if the testActor collides with no wall on x axis, set test.x as a.x
        //and leave velocity alone (unless applying friction or other forces).
        }else{
            newX = testActor.getTopLeftLocation().x;            
        }
        
        //same logic for x axis as seen just above
        if(testActor.getBottomMostValue() > this.getYMax()){
            a.getVelocity().setYMovement(-currentVel.getYMovement());
            newY = this.getYMax() - a.getHeight();
        }else if(testActor.getTopMostValue() < this.getYMin()){
            a.getVelocity().setYMovement(-currentVel.getYMovement());
            newY = this.getYMin();
        }else{
            newY = testActor.getTopLeftLocation().y;
        }        
        
        a.setTopLeftLocation(new Point(newX, newY));

        //int changeX = a.getTopLeftLocation().x - origPoint.x;
        //int changeY = a.getTopLeftLocation().y - origPoint.y;
        
        
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

    /**
     * @return the actorsWerePreloaded
     */
    public boolean actorsWerePreloaded() {
        return actorsWerePreloaded;
    }

    /**
     * @param centerOfViewActor the centerOfViewActor to set
     */
    public void setCenterOfViewActor(CenterOfViewActor centerOfViewActor) {
        this.centerOfViewActor = centerOfViewActor;
    }
    

    

    
}
