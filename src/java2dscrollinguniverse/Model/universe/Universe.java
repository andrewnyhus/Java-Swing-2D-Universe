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

import java.awt.Point;
import java.util.ArrayList;
import java2dscrollinguniverse.Model.TwoDRectangle;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.actors.Player;
import java2dscrollinguniverse.Model.actors.Wall;

/**
 *
 * @author andrewnyhus
 */
public class Universe {
    
    private ArrayList<Actor> membersOfUniverse;
    private TwoDRectangle universeRect;
    private MemberFactory factory;
    
    private Wall[] perimeterWalls;
    private Actor bgRect;
    private Player player;
    
    public Universe(TwoDRectangle rect){
        this.universeRect = rect;
        this.factory = new MemberFactory(this.universeRect);
        
        this.perimeterWalls = this.factory.generateWalls();
        this.bgRect = this.factory.getBackgroundRect();
        this.player = new Player(40, 40);//create player with width 40 and height 40
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
        Point newLoc = new Point(origPlayerLoc.x + movement.getXMovement(),
                origPlayerLoc.y + movement.getYMovement());
        
        //TODO: later implement some checking of newLoc
            //to make sure that newLoc is on the universe still, and if it
            //is no longer on the universe, then instead of using newLoc
            //as the new location, the player's new location should give the appearance
            //that the players sprite was moving in the proper direction when it collided 
            //with the perimeter boundary.
        
        //for now blindly add movement to player's location, even if it sends player off of universe:
        this.player.setTopLeftLocation(newLoc);
                
    }
    
}
