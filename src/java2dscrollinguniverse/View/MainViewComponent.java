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
import java.awt.RenderingHints;
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
            
            g2d.setColor(this.updatedUniverse.getBackgroundRect().getColor());

            g2d.fill(this.updatedUniverse.getBackgroundRect().getShape());
            
            for(Wall w: this.updatedUniverse.getPerimeterWalls()){
                g2d.setColor(w.getColor());
                
                g2d.fill(w.getShape());
            }
            
            g2d.setColor(this.updatedUniverse.getPlayer().getColor());
            g2d.fill(this.updatedUniverse.getPlayer().getShape());
            
    }
        
}
