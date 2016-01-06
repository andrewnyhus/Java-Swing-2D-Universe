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
package java2dscrollinguniverse.Model.actors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.universe.Universe;

/**
 *
 * @author andrewnyhus
 */
public class HUDMap extends Actor{
    
    private WindowCorner cornerOfWindow;
    private Dimension windowDimension;
    private Universe universe;
    private Actor[] childActors;
    private Shape shape;
    private TwoDimensionalMovement centerViewOffsetFromUniverseOrigin;

    private final double percentOfWindowMapCanHave = 0.18;
    
    private final int inset = 20;
    private final Color colorOfUniverseRepresentation = Color.BLACK;
    private final Color colorOfWindowRepresentation = new Color(255, 255, 255, 100);//gray
    private final Color colorOfOtherChildActors = new Color(227, 148, 0);//orange
    
    public HUDMap(TwoDimensionalMovement centerViewOffset,
            Dimension windowDimension,
            Universe universe){
        
        super(ActorType.HUDElement, new Point(0, 0), Color.BLACK);
        //^^ that call to super disregarded colorOfUniverseRepresentation because super must be the first
        //call in the constructor.  That is why the wrong location or point was entered at first as
        // 0, 0  It is simply using the actor class to setup the object so that we can tinker with
        //the data members afterwards
        
        this.windowDimension = windowDimension;
        this.universe = universe;
        this.cornerOfWindow = WindowCorner.BOTTOM_LEFT;
        this.centerViewOffsetFromUniverseOrigin = centerViewOffset;
        this.generateScaledUniverseRectForMap();
    }
        
    public HUDMap(TwoDimensionalMovement centerViewOffset,
            Dimension windowDimension,
            Universe universe,
            WindowCorner cornerOfWindow){
        
        
        super(ActorType.HUDElement, new Point(0, 0), Color.BLACK);
        //^^same description/explanation as other constructor's call to super
        this.windowDimension = windowDimension;
        this.universe = universe;
        this.cornerOfWindow = cornerOfWindow;
        this.centerViewOffsetFromUniverseOrigin = centerViewOffset;
        this.generateScaledUniverseRectForMap();
    }

    
    
    private Actor[] generateChildActors(int ratioUniverseToMapOf){

        //setting up
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=                        
        TwoDimensionalMovement scaledCenterViewOffset =
                this.centerViewOffsetFromUniverseOrigin.getMovementDividedByFactor(ratioUniverseToMapOf);

        int viewMapPointX = this.getTopLeftLocation().x -
                scaledCenterViewOffset.getXMovement();
        
        int viewMapPointY = this.getTopLeftLocation().y -
                scaledCenterViewOffset.getYMovement();
        
        Point viewMapPoint = new Point(viewMapPointX, viewMapPointY);

        Actor[] membersOfUniverse = this.universe.getMembersOfUniverse();
        int numChildActors = 1/*for viewMap*/ + membersOfUniverse.length;
        
        Actor[] childActors = new Actor[numChildActors];
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=                
        //done w/ setup
        
        
        //scaling and re-creating members of universe
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=                
        int i = 0;
        for(Actor member: membersOfUniverse){
            
            Point memberUniverseLoc = member.getTopLeftLocation();
            
            int scaledX = this.getTopLeftLocation().x + (memberUniverseLoc.x/ratioUniverseToMapOf);
            
            int scaledY = this.getTopLeftLocation().y + (memberUniverseLoc.y/ratioUniverseToMapOf);
            Point convertedPoint = new Point(scaledX, scaledY);
            
            Shape memberShapeForMap = this.getShapeShrunkByFactorPositionedAtPoint(
                                            member.getShape(),ratioUniverseToMapOf,
                                            convertedPoint);
            
            Actor currentActorConvertedForMap = new Actor(ActorType.HUDElement,
                    convertedPoint, member.getColor(), memberShapeForMap);

            childActors[i] = currentActorConvertedForMap;       
            
            i++;
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=        
        //done scaling and re-creating members of universe

        
        //creating map view actor
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=        
        
        int viewMapWidth = this.getWindowDimension().width/ratioUniverseToMapOf;
        int viewMapHeight = this.getWindowDimension().height/ratioUniverseToMapOf;
        
        Rectangle viewMapRect = new Rectangle(0, 0, viewMapWidth, viewMapHeight);
        
        Actor viewMap = new Actor(ActorType.HUDElement, viewMapPoint, this.colorOfWindowRepresentation, viewMapRect);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //done creating view map actor
                
        childActors[childActors.length - 1] = viewMap;
        
        return childActors;
    }
    

    private void generateScaledUniverseRectForMap(){
        
        
        int maxMapWidthOrHeight = (int)(percentOfWindowMapCanHave*this.windowDimension.getHeight());        
        
        Dimension universeRect = this.getProperSizeForDimensionWithLimits(
                this.universe.getBoundsDimension(),
                maxMapWidthOrHeight);

        int scaleRatioUniverseOverMapRepr = (this.universe.getBoundsDimension().width/
                                                universeRect.width);
                
        
        this.shape = new Rectangle(0, 0, universeRect.width, universeRect.height);
        
        int pointX = 0, pointY = 0;
        
        switch(this.getCornerOfWindow()){
        
            case TOP_RIGHT:
                pointX = (this.windowDimension.width - universeRect.width) - this.inset;
                pointY = this.inset;
                break;
                
            case TOP_LEFT:
                pointX = this.inset;
                pointY = this.inset;
                
                break;
                
            case BOTTOM_LEFT:
                pointX = this.inset;
                pointY = (this.windowDimension.height - universeRect.height) - this.inset;
                
                break;
                
            case BOTTOM_RIGHT:
                pointX = (this.windowDimension.width - universeRect.width) - this.inset;
                pointY = (this.windowDimension.height - universeRect.height) - this.inset;

                break;
        }
        
        Point mapUniverseRectPoint = new Point(pointX, pointY);
                
        this.setTopLeftLocation(mapUniverseRectPoint);
        this.setChildActors(this.generateChildActors(scaleRatioUniverseOverMapRepr));
        
    }

    @Override
    public Shape getShape(){
        return this.shape;
    }
    
    @Override
    public void setShape(Shape s){
        this.shape = s;
    }
    
    /**
     * @return the cornerOfWindow
     */
    public WindowCorner getCornerOfWindow() {
        return cornerOfWindow;
    }

    /**
     * @param cornerOfWindow the cornerOfWindow to set
     */
    public void setCornerOfWindow(WindowCorner cornerOfWindow) {
        this.cornerOfWindow = cornerOfWindow;
    }

    /**
     * @return the windowDimension
     */
    public Dimension getWindowDimension() {
        return windowDimension;
    }

    /**
     * @param windowDimension the windowDimension to set
     */
    public void setWindowDimension(Dimension windowDimension) {
        this.windowDimension = windowDimension;
    }

    /**
     * @return the universe
     */
    private Universe getUniverse() {
        return universe;
    }

    /**
     * @param universe the universe to set
     */
    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    /**
     * @param childActors the childActors to set
     */
    @Override
    public void setChildActors(Actor[] childActors) {
        this.childActors = childActors;
    }

    /**
     * @return the childActors
     */
    @Override
    public Actor[] getChildActors() {
        return childActors;
    }
    
    private Dimension getProperSizeForDimensionWithLimits(Dimension currentDimension, int maxMapWidthOrHeight){
        double givenHeightOverWidthRatio = (double)currentDimension.height/
                                             (double)currentDimension.width;
        
        Dimension returnDimension;
        
        if(givenHeightOverWidthRatio == 1.0){
            //given height is equal to given width
            
            //so... we want to set 'maxMapWidthOrHeight' as 
            //length & width for returnDimension
            
            returnDimension = new Dimension(maxMapWidthOrHeight, maxMapWidthOrHeight);
            
        }else if(givenHeightOverWidthRatio > 1.0){
            //given height is greater than given width
            
            //so... for returnDimension, we want height: maxMapWidthOrHeight
            //and width: (maxMapWidthOrHeight)/(givenHeightOverWidthRatio)
            
            int returnWidth = (int)(((double)maxMapWidthOrHeight)/(givenHeightOverWidthRatio));
            
            returnDimension = new Dimension(returnWidth, maxMapWidthOrHeight);
            
        }else if(givenHeightOverWidthRatio < 1.0){
            //given width is greater than given height
            
            //so... for returnDimension, we want width: maxMapWidthOrHeight
            //and height: (maxMapWidthOrHeight)*(givenHeightOverWidthRatio) 

            int returnHeight = (int)(((double)maxMapWidthOrHeight)*(givenHeightOverWidthRatio));
            
            returnDimension = new Dimension(maxMapWidthOrHeight, returnHeight);
        
        }else{
            returnDimension = new Dimension(maxMapWidthOrHeight, maxMapWidthOrHeight);
        }
        
        return returnDimension;
    }
    
    private Shape getShapeShrunkByFactorPositionedAtPoint(Shape s, int factorToShrinkBy, Point p){
        
        if(s instanceof Rectangle){
            int shrunkWidth = ((Rectangle) s).width/factorToShrinkBy;
            int shrunkHeight = ((Rectangle) s).height/factorToShrinkBy;
            
            Rectangle returnShape = new Rectangle(p.x, p.y, shrunkWidth, shrunkHeight);
            return returnShape;
        }else if(s instanceof Ellipse2D.Double){
            double shrunkWidth = ((Ellipse2D.Double) s).width/factorToShrinkBy;
            double shrunkHeight = ((Ellipse2D.Double) s).height/factorToShrinkBy;

            Ellipse2D.Double returnShape = new Ellipse2D.Double(p.getX(), p.getY(), shrunkWidth, shrunkHeight);
            return returnShape;
        }else{
            return s;
        }
        
    }
    
    public enum WindowCorner{
        TOP_RIGHT(0), TOP_LEFT(1), BOTTOM_LEFT(2), BOTTOM_RIGHT(3);
        
        private int value;
        
        private WindowCorner(int value){
            this.value = value;
        }
        
        public int getValue(){
            return this.value;
        }
    }
    
}
