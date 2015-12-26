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
package java2dscrollinguniverse.View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java2dscrollinguniverse.Model.actors.Wall;
import java2dscrollinguniverse.Model.universe.Universe;
import javax.swing.JPanel;

/**
 *
 * @author andrewnyhus
 */
public class MainViewComponent extends JPanel{
    
    //private Universe universe;
    private Universe updatedUniverse;
    public MainViewComponent(Universe universe){
        this.updatedUniverse = universe;
    }
    
    /**
     *This method allows us to pass in a KeyListener object
     * which will detect any time the user presses a key on the keyboard.
     * In our case, the listener that will be passed in will be the 'UniverseController'
     * which implements KeyListener to handle any occurrence of the user pressing
     * a directional arrow key.
     * @param listener
     */
    public void addListener(KeyListener listener){
        addKeyListener(listener);
        setFocusable(true);
    }
    
    public void updateUniverse(Universe uni){
        this.updatedUniverse = uni;
        this.repaint();
    }
    
    
    /**
     * Anytime that the view needs to be repainted
     * for a new frame, this method is called and the 
     * new frame is painted through the logic here.
     * @param g 
     */
    @Override
    public void paint(Graphics g){
            //necessary painting code
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
            //end of necessary painting code
            
            //Begin painting :) ...picasso style
            
            //set "pen" with proper color for background rectangle
            g2d.setColor(this.updatedUniverse.getBackgroundRect().getColor());

            //get shape of background rect
            Shape bgRectShape = this.updatedUniverse.getBackgroundRect().getShape();
            
            //get top left location of backgroundRect
            Point bgRectTopLeftLocationToDraw = this.updatedUniverse.getBackgroundRect().getTopLeftLocation();
            
            //** TODO: update this top left loc to be appropriate with the offset of centering 
            //the player within the view.
            
            //set topLeft location to the bgRectShape variable, and use it to draw
            //bgRectShape.getBounds2D()..setLocation(bgRectTopLeftLocationToDraw.getX(), bgRectTopLeftLocationToDraw.getX());
            
            g2d.fill(this.getShapeWithOffset(bgRectShape, bgRectTopLeftLocationToDraw));
            
            //iterate through all walls in perimeter of universe
            for(Wall w: this.updatedUniverse.getPerimeterWalls()){
                //set "pen" to proper color for walls
                g2d.setColor(w.getColor());
                
                // get shape of current wall in iteration
                Shape currentWallShape = w.getShape();
                
                //get top left location of the current wall
                Point currentWallTopLeftLocationToDraw = w.getTopLeftLocation();
                
                //** TODO: update this top left loc to be appropriate with the offset of centering 
                //the player within the view.
                
                //set topLeft location to the currentWallShape variable, and then fill it
                //currentWallShape.getBounds().translate(currentWallTopLeftLocationToDraw.x, currentWallTopLeftLocationToDraw.y);
                
                
                g2d.fill(this.getShapeWithOffset(currentWallShape, currentWallTopLeftLocationToDraw));
            }
            
            //set "pen" to proper color for drawing the player
            g2d.setColor(this.updatedUniverse.getPlayer().getColor());
            
            // get shape of player 
            Shape playerShape = this.updatedUniverse.getPlayer().getShape();
            
            // get top left location of player 
            Point playerTopLeftLocationToDraw = this.updatedUniverse.getPlayer().getTopLeftLocation();
            
            //** TODO: center player in the view but keep surroundings intact  in relation to player
            
            //set top left location of the playerShape variable, and then fill it
            //playerShape.getBounds().setLocation(playerTopLeftLocationToDraw.x, playerTopLeftLocationToDraw.y);
            
            g2d.fill(this.getShapeWithOffset(playerShape, playerTopLeftLocationToDraw));
            
    }
    
    
    public Shape getShapeWithOffset(Shape s, Point p){
                
        if(s instanceof Rectangle){
            Rectangle returnShape = new Rectangle(p.x, p.y, s.getBounds().width, s.getBounds().height);
            return returnShape;
        }
        
        return s;
    }
        
}
