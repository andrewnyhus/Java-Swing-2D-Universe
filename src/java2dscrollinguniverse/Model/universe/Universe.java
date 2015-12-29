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
package java2dscrollinguniverse.Model.universe;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java2dscrollinguniverse.Model.PerimeterSide;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.actors.Player;
import java2dscrollinguniverse.Model.actors.Wall;

/**
 *
 * @author andrewnyhus
 */
public class Universe {
    
    private Actor[] membersOfUniverse;
    private Dimension boundsDimension;
    private MemberFactory factory;
    
    private Wall[] perimeterWalls;//wall: (0)left (1)top (2)right (3)bottom.
    private Actor bgRect;
    private Player player;
    
    public Universe(Dimension boundsDimension){
        this.boundsDimension = boundsDimension;
        this.factory = new MemberFactory(this.boundsDimension);
        
        this.perimeterWalls = this.factory.generateWalls();
        this.bgRect = this.factory.getBackgroundRect();
        this.player = new Player(15, 15);//create player with width and height
        
        this.membersOfUniverse = this.factory.generateActors();
    }
    
    public Actor getBackgroundRect(){
        return this.bgRect;
    }
    public Wall[] getPerimeterWalls(){
        return this.perimeterWalls;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    
    
    public void attemptToMovePlayer(TwoDimensionalMovement movement){
        Point origPlayerLoc = this.player.getTopLeftLocation();
        
        Point playerLocIfMoveSuccessful = new Point(origPlayerLoc.x + movement.getXMovement(),
                origPlayerLoc.y + movement.getYMovement());
        
        //init test player
        Player testMoveOnPlayer = Player.copyInstanceOfPlayer(this.player);
        
        //complete move on test player
        testMoveOnPlayer.setTopLeftLocation(playerLocIfMoveSuccessful);
        
        //TODO: later implement some checking of newLoc
            //to make sure that newLoc is on the universe still, and if it
            //is no longer on the universe, then instead of using newLoc
            //as the new location, the player's new location should give the appearance
            //that the players sprite was moving in the proper direction when it collided 
            //with the perimeter boundary.
        int newX, newY;
        
        //if player too far left
        if(testMoveOnPlayer.getLeftMostValue() < this.getXMin()){
            newX = this.getXMin();
            
        //if player too far right    
        }else if(testMoveOnPlayer.getRightMostValue() > this.getXMax()){
            int playerWidth = testMoveOnPlayer.getWidth();
            newX = this.getXMax() - playerWidth;
            
        //if player okay     
        }else{
            newX = playerLocIfMoveSuccessful.x;
        }
        
        //if player too far up
        if(testMoveOnPlayer.getTopMostValue() < this.getYMin()){
            newY = this.getYMin();
            
        //if player too far down
        }else if(testMoveOnPlayer.getBottomMostValue() > this.getYMax()){
            int playerHeight = testMoveOnPlayer.getHeight();
            newY = this.getYMax() - playerHeight;
            
        //if player is okay    
        }else{
            newY = playerLocIfMoveSuccessful.y;
        }
        
        Point newLocForPlayer = new Point(newX, newY);
        
        //for now blindly add movement to player's location, even if it sends player off of universe:
        this.player.setTopLeftLocation(newLocForPlayer);
            //this.player.setTopLeftLocation(playerLocIfMoveSuccessful);
                
    }
    
    /**
     * Returns the minimum x (leftmost) value that a bounded actor should be able to have in the 
     * universe.
     * @return xMin
     */
    public int getXMin(){
        PerimeterSide leftSide = PerimeterSide.LEFT;
        
        int xMin = this.perimeterWalls[leftSide.getValue()].getRightMostValue();
        
        return xMin;
    }
    
    /**
     * Returns maximum x (rightmost) value a bounded actor can have in the universe.
     * @return xMax
     */
    public int getXMax(){
        PerimeterSide rightSide = PerimeterSide.RIGHT;        
        
        int xMax = this.perimeterWalls[rightSide.getValue()].getLeftMostValue();
        
        return xMax;
    }
    
    
    /**
     * Returns minimum y (topmost) value a bounded actor can have in the universe.
     * @return yMin
     */
    public int getYMin(){
        PerimeterSide topSide = PerimeterSide.TOP;
        
        int yMin = this.perimeterWalls[topSide.getValue()].getBottomMostValue();

        return yMin;
    }
    
    /**
     * Returns maximum y (bottommost) value a bounded actor can have in the universe
     * @return yMax
     */
    public int getYMax(){
        PerimeterSide bottomSide = PerimeterSide.BOTTOM;
        
        int yMax = this.perimeterWalls[bottomSide.getValue()].getTopMostValue();
        
        return yMax;
    }   

    /**
     * @return the membersOfUniverse
     */
    public Actor[] getMembersOfUniverse() {
        return membersOfUniverse;
    }
    
    
}
