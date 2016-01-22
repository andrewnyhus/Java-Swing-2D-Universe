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
import java2dscrollinguniverse.Model.actors.HUDMap.WindowCorner;

/**
 *
 * @author andrewnyhus
 */
public class SettingsSingleton {
    
    private static SettingsSingleton settings;
    
    private Color containerBackgroundColor;
    private Color perimeterColor;
    private Color centerOfViewActorColor;
    private Color labelColor;
    
    private final Color defaultContainerBackgroundColorValue = new Color(133, 133, 133);
    private final Color defaultPerimeterColorValue = new Color(255, 127, 0);
    private final Color defaultCenterOfViewActorColorValue = new Color(255, 255, 255, 0);//transparent
    private Dimension windowDimension = new Dimension(500, 500);//default window size
    private int cameraScrollingSpeed = 2; //camera scroll speed
    
    private boolean shouldShowHUDMap = false;
    private WindowCorner hudMapCorner = WindowCorner.BOTTOM_LEFT;
    private ViewScrollMode scrollMode = ViewScrollMode.MOUSE_ONLY;
    private boolean singleClickShowsInspector = false;
    private int borderThickness = 10;
    private boolean containerUniverseControllerShouldListenForKeys = true;
    private boolean actorsShouldGenerateRandomly = false;
    
    /**
     * Constructor for the settings class which is a singleton, meaning that it is the only instance of itself
     * in the program, which is because we simply cannot and should not have multiple sets of settings values.
     */
    private SettingsSingleton(){
        
        //set container background color to be gray
        this.containerBackgroundColor = defaultContainerBackgroundColorValue;
        
        //set perimeter yellow
        this.perimeterColor = defaultPerimeterColorValue;
        
        //set CenterOfViewActor to be white
        this.centerOfViewActorColor = defaultCenterOfViewActorColorValue;
        
        //set label color to be green
        this.labelColor = new Color(0, 235, 0);
        
    }

    /**
     * Rather than creating a constructor for this singleton Settings class, we access the 
     * single instance of the Settings object through this method and edit/retrieve our settings values
     * that way.
     * @return a SettingsSingleton instance.
     */
    public static SettingsSingleton getInstance(){
        if(settings == null){
            settings = new SettingsSingleton();
        }
        
        return settings;
    }
      
    /**
     * Gives access to the size of the window.
     * @return this.windowDimension - window size
     */
    public Dimension getWindowDimension(){
        return this.windowDimension;
    }
    
    /**
     * Gives access to the window width
     * @return int width- width of window
     */
    public int getWindowWidth(){
        return this.windowDimension.width;
    }

    /**
     * Gives access to the window height
     * @return int height- height of window
     */    
    public int getWindowHeight(){
        return this.windowDimension.height;
    }
        
    /**
     * Changes dimension of window.
     * @param d -new dimension for window.
     */
    public void setWindowDimension(Dimension d){
        this.windowDimension = d;
    }
    
    /**
     * Changes width of the window.
     * @param newWidth -int for window width
     */
    public void setWindowWidth(int newWidth){
        this.windowDimension.width = newWidth;
    }
    
    /**
     * Changes height of the window.
     * @param newHeight - new int for window height.
     */
    public void setWindowHeight(int newHeight){
        this.windowDimension.height = newHeight;
    }
    
    /**
     * Changes Color for background of container.
     * @param c - New Color for background of container.
     */
    public void setContainerBackgroundColor(Color c){
        this.containerBackgroundColor = c;
    }
    
    /**
     * Changes color of the perimeter.
     * @param c -new color for perimeter.
     */
    public void setPerimeterColor(Color c){
        this.perimeterColor = c;
    }
    
    /**
     * Changes color of the CenterOfViewActor.
     * @param c - new color for center of view actor.
     */
    public void setCenterOfViewActorColor(Color c){
        this.centerOfViewActorColor = c;
    }
    
    /**
     * Resets all entities with default colors to those default colors.
     */
    public void revertToDefaultColors(){
        settings.revertToDefaultContainerColor();
        settings.revertToDefaultPerimeterColor();
        settings.revertToDefaultCenterOfViewActorColor();
    }
    
    /**
     * Resetting the background color of the container to the default color.
     */
    public void revertToDefaultContainerColor(){
        settings.setContainerBackgroundColor(defaultContainerBackgroundColorValue);
    }
    
    /**
     * Resetting Perimeter Color to the default color.
     */
    public void revertToDefaultPerimeterColor(){
        settings.setPerimeterColor(defaultPerimeterColorValue);
    }
    
    /**
     * Resetting CenterOfViewActor color to the default color.
     */
    public void revertToDefaultCenterOfViewActorColor(){
        settings.setCenterOfViewActorColor(defaultCenterOfViewActorColorValue);
    }

    /**
     * @return the cameraScrollingSpeed
     */
    public int getCameraScrollingSpeed() {
        return cameraScrollingSpeed;
    }

    /**
     * @param cameraScrollingSpeed the cameraScrollingSpeed to set
     */
    public void setCameraScrollingSpeed(int cameraScrollingSpeed) {
        this.cameraScrollingSpeed = cameraScrollingSpeed;
    }

    /**
     * @return the containerBackgroundColor
     */
    public Color getContainerBackgroundColor() {
        return containerBackgroundColor;
    }

    /**
     * @return the perimeterColor
     */
    public Color getPerimeterColor() {
        return perimeterColor;
    }

    /**
     * @return the centerOfViewActorColor
     */
    public Color getCenterOfViewActorColor() {
        return centerOfViewActorColor;
    }

    /**
     * @return the shouldShowHUDMap
     */
    public boolean shouldShowHUDMap() {
        return shouldShowHUDMap;
    }

    /**
     * @param shouldShowHUDMap the shouldShowHUDMap to set
     */
    public void setShouldShowHUDMap(boolean shouldShowHUDMap) {
        this.shouldShowHUDMap = shouldShowHUDMap;
    }

    /**
     * @return the hudMapCorner
     */
    public WindowCorner getHUDMapCorner() {
        return hudMapCorner;
    }

    /**
     * @param hudMapCorner the hudMapCorner to set
     */
    public void setHUDMapCorner(WindowCorner hudMapCorner) {
        this.hudMapCorner = hudMapCorner;
    }

    /**
     * @return the labelColor
     */
    public Color getLabelColor() {
        return labelColor;
    }

    /**
     * @param labelColor the labelColor to set
     */
    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    /**
     * @return the scrollMode
     */
    public ViewScrollMode getScrollMode() {
        return scrollMode;
    }

    /**
     * @param scrollMode the scrollMode to set
     */
    public void setScrollMode(ViewScrollMode scrollMode) {
        this.scrollMode = scrollMode;
    }

    /**
     * @return the singleClickShowsInspector
     */
    public boolean isSingleClickShowsInspector() {
        return singleClickShowsInspector;
    }

    /**
     * @param singleClickShowsInspector the singleClickShowsInspector to set
     */
    public void setSingleClickShowsInspector(boolean singleClickShowsInspector) {
        this.singleClickShowsInspector = singleClickShowsInspector;
    }

    /**
     * @return the borderThickness
     */
    public int getBorderThickness() {
        return borderThickness;
    }

    /**
     * @param borderThickness the borderThickness to set
     */
    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    /**
     * @return the containerUniverseShouldListenForKeys
     */
    public boolean shouldContainerUniverseControllerShouldListenForKeys() {
        return containerUniverseControllerShouldListenForKeys;
    }

    /**
     * @param containerUniverseShouldListenForKeys the containerUniverseShouldListenForKeys to set
     */
    public void setShouldContainerUniverseControllerShouldListenForKeys(boolean containerUniverseShouldListenForKeys) {
        this.containerUniverseControllerShouldListenForKeys = containerUniverseShouldListenForKeys;
    }

    /**
     * @return the actorsShouldGenerateRandomly
     */
    public boolean isActorsShouldGenerateRandomly() {
        return actorsShouldGenerateRandomly;
    }

    /**
     * @param actorsShouldGenerateRandomly the actorsShouldGenerateRandomly to set
     */
    public void setActorsShouldGenerateRandomly(boolean actorsShouldGenerateRandomly) {
        this.actorsShouldGenerateRandomly = actorsShouldGenerateRandomly;
    }
    
    
    public enum ViewScrollMode{
        DONT_SCROLL(0), ARROW_KEYS_ONLY(1), MOUSE_ONLY(2), ARROW_KEYS_AND_MOUSE(3);
        
        private int value;
        
        private ViewScrollMode(int value){
            this.value = value;
        }
        
        public boolean isMouseScrollingEnabled(){
            return (this.value == ViewScrollMode.MOUSE_ONLY.getValue() ||
                    this.value == ViewScrollMode.ARROW_KEYS_AND_MOUSE.getValue());
        }
        
        public boolean isArrowKeyScrollingEnabled(){
            return (this.value == ViewScrollMode.ARROW_KEYS_ONLY.getValue() ||
                    this.value == ViewScrollMode.ARROW_KEYS_AND_MOUSE.getValue());
        }
        
        public int getValue(){
            return this.value;
        }
        
    }
    
}
