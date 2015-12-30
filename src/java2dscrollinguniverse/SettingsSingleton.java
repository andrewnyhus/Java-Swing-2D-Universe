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
package java2dscrollinguniverse;

import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author andrewnyhus
 */
public class SettingsSingleton {
    
    private static SettingsSingleton settings;
    
    private Color universeBackgroundColor;
    private Color perimeterColor;
    private Color playerColor;
    
    private final Color defaultUniverseBackgroundColorValue = new Color(133, 133, 133);
    private final Color defaultPerimeterColorValue = new Color(255, 127, 0);
    private final Color defaultPlayerColorValue = new Color(255, 255, 255);
    private Dimension windowDimension = new Dimension(500, 500);//default window size
    private int playerSpeed = 20; //to be implemented later in player class
    
    
    private SettingsSingleton(){
        
        //set universe background color to be gray
        this.universeBackgroundColor = defaultUniverseBackgroundColorValue;
        
        //set perimeter yellow
        this.perimeterColor = defaultPerimeterColorValue;
        
        //set player to be white
        this.playerColor = defaultPlayerColorValue;
        
        
    }
    
    public static SettingsSingleton getInstance(){
        if(settings == null){
            settings = new SettingsSingleton();
        }
        
        return settings;
    }
    
    
  
    
    public Dimension getWindowDimension(){
        return this.windowDimension;
    }
    
    public int getWindowWidth(){
        return this.windowDimension.width;
    }
    
    public int getWindowHeight(){
        return this.windowDimension.height;
    }
        
    public void setWindowDimension(Dimension d){
        this.windowDimension = d;
    }
    
    public void setWindowWidth(int newWidth){
        this.windowDimension.width = newWidth;
    }
    
    public void setWindowHeight(int newHeight){
        this.windowDimension.height = newHeight;
    }
    
    public void setUniverseBackgroundColor(Color c){
        this.universeBackgroundColor = c;
    }
    
    
    public void setPerimeterColor(Color c){
        this.perimeterColor = c;
    }
    
    
    public void setPlayerColor(Color c){
        this.playerColor = c;
    }
    
    public void revertToDefaultColors(){
        settings.revertToDefaultUniverseColor();
        settings.revertToDefaultPerimeterColor();
        settings.revertToDefaultPlayerColor();
    }
    
    public void revertToDefaultUniverseColor(){
        settings.setUniverseBackgroundColor(defaultUniverseBackgroundColorValue);
    }
    
    public void revertToDefaultPerimeterColor(){
        settings.setPerimeterColor(defaultPerimeterColorValue);
    }
    
    public void revertToDefaultPlayerColor(){
        settings.setPlayerColor(defaultPlayerColorValue);
    }

    /**
     * @return the playerSpeed
     */
    public int getPlayerSpeed() {
        return playerSpeed;
    }

    /**
     * @param playerSpeed the playerSpeed to set
     */
    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    /**
     * @return the universeBackgroundColor
     */
    public Color getUniverseBackgroundColor() {
        return universeBackgroundColor;
    }

    /**
     * @return the perimeterColor
     */
    public Color getPerimeterColor() {
        return perimeterColor;
    }

    /**
     * @return the playerColor
     */
    public Color getPlayerColor() {
        return playerColor;
    }
    
    
    
    
    
}
