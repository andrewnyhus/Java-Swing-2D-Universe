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
import java.awt.Rectangle;
import java2dscrollinguniverse.Model.TwoDRectangle;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.actors.ActorType;
import java2dscrollinguniverse.Model.actors.Wall;
import java2dscrollinguniverse.SettingsSingleton;

/**
 *
 * @author andrewnyhus
 */
public class MemberFactory {

    private TwoDRectangle universeBounds;
    private final int wallThickness = 10;
    public MemberFactory(TwoDRectangle universeRect) {
        this.universeBounds = universeRect;
    }
    
    public Actor getBackgroundRect(){
        return new Actor(ActorType.miscObject, new Point(0, 0), SettingsSingleton.getInstance().getUniverseBackgroundColor(), new Rectangle(0, 0, this.universeBounds.getWidth(), this.universeBounds.getHeight()));
    }
    
    public Wall[] generateWalls(){
        Wall[] walls = new Wall[4];
        
        Point topLeftLoc = new Point(0, 0);
        Point topRightLoc = new Point(this.universeBounds.getWidth(), 0);
        Point bottomLeftLoc = new Point(0, this.universeBounds.getHeight());
        
        Point locWall0 = new Point(0, 0);
        Point locWall1 = new Point(0, 0);
        Point locWall2 = new Point(this.universeBounds.getWidth() - 10, 0);
        Point locWall3 = new Point(0, this.universeBounds.getHeight() - 10);

                //walls[0] = new Wall(topLeftLoc, new Rectangle(0, 0, wallThickness, this.universeBounds.getHeight()));
                //walls[1] = new Wall(topLeftLoc, new Rectangle(topLeftLoc.getX(), topLeftLoc.getY(), this.universeBounds.getWidth(), wallThickness));
                //walls[2] = new Wall(topRightLoc, new Rectangle(topRightLoc.getX() - 10, topRightLoc.getY(), 10, this.universeBounds.getHeight()));
                //walls[3] = new Wall(bottomLeftLoc, new Rectangle(bottomLeftLoc.getX(), bottomLeftLoc.getY() - 10, this.universeBounds.getWidth(), wallThickness));
        
        /*walls[0] = new Wall(topLeftLoc, new Rectangle(0, 0, wallThickness, this.universeBounds.getHeight()));
        walls[1] = new Wall(topLeftLoc, new Rectangle(0, 0, this.universeBounds.getWidth(), wallThickness));
        walls[2] = new Wall(topRightLoc, new Rectangle(0, 0, 10, this.universeBounds.getHeight()));
        walls[3] = new Wall(bottomLeftLoc, new Rectangle(0, 0, this.universeBounds.getWidth(), wallThickness));*/

        walls[0] = new Wall(locWall0, new Rectangle(0, 0, wallThickness, this.universeBounds.getHeight()));
        walls[1] = new Wall(locWall1, new Rectangle(0, 0, this.universeBounds.getWidth(), wallThickness));
        walls[2] = new Wall(locWall2, new Rectangle(0, 0, 10, this.universeBounds.getHeight()));
        walls[3] = new Wall(locWall3, new Rectangle(0, 0, this.universeBounds.getWidth(), wallThickness));
        
        return walls;
    }
    
    
    
}
