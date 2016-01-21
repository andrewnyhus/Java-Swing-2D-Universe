/*
 * The MIT License
 *
 * Copyright 2016 andrewnyhus.
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
package Examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java2dscrollinguniverse.Controller.UniverseController;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.actors.ActorType;
import java2dscrollinguniverse.SettingsSingleton;
import javax.swing.SwingUtilities;

/**
 *
 * @author andrewnyhus
 */
public class PingPong {
    private static Dimension game_size = new Dimension(600, 400);
    private static Actor[] actors;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        SwingUtilities.invokeLater(() -> {
            setupActors();
            applyInitialSettings();
            UniverseController mainController = new UniverseController("PingPong", game_size, actors);

        });
        
    }

    private static void applyInitialSettings() {
        SettingsSingleton.getInstance().setWindowDimension(game_size);
        SettingsSingleton.getInstance().setPerimeterColor(Color.white);
        SettingsSingleton.getInstance().setContainerBackgroundColor(Color.black);
        SettingsSingleton.getInstance().setLabelColor(Color.white);
        SettingsSingleton.getInstance().setScrollMode(SettingsSingleton.ViewScrollMode.DONT_SCROLL);
    }
    
    public static void setupActors(){
        Actor paddleLeft, paddleRight, ball, middleLine, pLeftScoreBox, pRightScoreBox;

        Rectangle paddleShape = new Rectangle(0, 0, 15, 50);
        int xPaddingForPaddles = 30;
        int yPaddlesInitLoc = (game_size.height/2) - (paddleShape.height/2);
        
        paddleLeft = new Actor(ActorType.miscObject, new Point(xPaddingForPaddles, yPaddlesInitLoc), 
                                                    Color.white, paddleShape);
        
        paddleRight = new Actor(ActorType.miscObject, 
                new Point(game_size.width - xPaddingForPaddles - paddleShape.width, yPaddlesInitLoc), 
                                                    Color.white, paddleShape);

        actors = new Actor[2]; 
        actors[0] = paddleLeft;
        actors[1] = paddleRight;
    }
    
    
}
