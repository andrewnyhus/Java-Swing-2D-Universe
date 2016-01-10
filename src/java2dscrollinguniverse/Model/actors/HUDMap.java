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
import java.util.ArrayList;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.container.ContainerUniverse;
import java2dscrollinguniverse.SettingsSingleton;

/**
 *
 * @author andrewnyhus
 */
public class HUDMap extends Actor{
    
    private WindowCorner cornerOfWindow;
    private Dimension windowDimension;
    private ContainerUniverse container;
    private Actor[] childActors;
    private Shape shape;
    private TwoDimensionalMovement centerViewOffsetFromContainerOrigin;

    private final double percentOfWindowMapCanHave = 0.18;
    
    private final int inset = 20;
    private final Color colorOfContainerRepresentation = Color.BLACK;
    private final Color colorOfWindowRepresentation = new Color(255, 255, 255, 100);//gray
    private final Color colorOfOtherChildActors = new Color(227, 148, 0);//orange
    
    public HUDMap(TwoDimensionalMovement centerViewOffset,
            Dimension windowDimension,
            ContainerUniverse container){
        
        super(ActorType.HUDElement, new Point(0, 0), Color.BLACK);
        //^^ that call to super disregarded colorOfContainerRepresentation because super must be the first
        //call in the constructor.  That is why the wrong location or point was entered at first as
        // 0, 0  It is simply using the actor class to setup the object so that we can tinker with
        //the data members afterwards
        
        this.windowDimension = windowDimension;
        this.container = container;
        this.cornerOfWindow = WindowCorner.BOTTOM_LEFT;
        this.centerViewOffsetFromContainerOrigin = centerViewOffset;
        this.generateScaledContainerRectForMap();
    }
        
    public HUDMap(TwoDimensionalMovement centerViewOffset,
            Dimension windowDimension,
            ContainerUniverse container,
            WindowCorner cornerOfWindow){
        
        
        super(ActorType.HUDElement, new Point(0, 0), Color.BLACK);
        //^^same description/explanation as other constructor's call to super
        this.windowDimension = windowDimension;
        this.container = container;
        this.cornerOfWindow = cornerOfWindow;
        this.centerViewOffsetFromContainerOrigin = centerViewOffset;
        this.generateScaledContainerRectForMap();
    }

    
    
    private Actor[] generateChildActorsForMap(int ratioContainerToMapOf){

        //setting up
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=                        
        TwoDimensionalMovement scaledCenterViewOffset =
                this.centerViewOffsetFromContainerOrigin.getMovementDividedByFactor(ratioContainerToMapOf);

        int viewMapPointX = this.getTopLeftLocation().x -
                scaledCenterViewOffset.getXMovement();
        
        int viewMapPointY = this.getTopLeftLocation().y -
                scaledCenterViewOffset.getYMovement();
        
        Point viewMapPoint = new Point(viewMapPointX, viewMapPointY);

        ArrayList<Actor> membersOfContainer = this.container.getMembersOfContainer();
        int numChildActors = 2/*for viewMap & center point*/ + membersOfContainer.size();
        
        Actor[] childActors = new Actor[numChildActors];
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=                
        //done w/ setup
        
        
        //scaling and re-creating members of container
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=                
        int i = 0;
        for(Actor member: membersOfContainer){
            
            Point memberOfContainerLoc = member.getTopLeftLocation();
            
            int scaledX = this.getTopLeftLocation().x + (memberOfContainerLoc.x/ratioContainerToMapOf);
            
            int scaledY = this.getTopLeftLocation().y + (memberOfContainerLoc.y/ratioContainerToMapOf);
            Point convertedPoint = new Point(scaledX, scaledY);
            
            Shape memberShapeForMap = this.getShapeShrunkByFactorPositionedAtPoint(
                                            member.getShape(),ratioContainerToMapOf,
                                            convertedPoint);
            
            Actor currentActorConvertedForMap = new Actor(ActorType.HUDElement,
                    convertedPoint, member.getColor(), memberShapeForMap);

            childActors[i] = currentActorConvertedForMap;       
            
            i++;
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=        
        //done scaling and re-creating members of container

        
        //creating map view actor
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=        
        
        int viewMapWidth = this.getWindowDimension().width/ratioContainerToMapOf;
        int viewMapHeight = this.getWindowDimension().height/ratioContainerToMapOf;
        Rectangle viewMapRect = new Rectangle(0, 0, viewMapWidth, viewMapHeight);
        
        Actor viewMap = new Actor(ActorType.HUDElement, viewMapPoint, this.colorOfWindowRepresentation, viewMapRect);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //done creating view map actor
                
        //added view map actor to the array
        childActors[childActors.length - 2] = viewMap;

        //create and add centerOfViewHUDMenu actor to the array
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        int centerOfViewX = viewMap.getTopLeftLocation().x + viewMapWidth/2;
        int centerOfViewY = viewMap.getTopLeftLocation().y + viewMapHeight/2;
        double diameter = (0.06)*((viewMapWidth+viewMapHeight)/2.0);
        
        Ellipse2D.Double centerOfViewShape = new Ellipse2D.Double(0.0, 0.0, diameter, diameter);
        
        Actor centerOfViewInHUDMenu = new Actor(ActorType.HUDElement,
                new Point(centerOfViewX, centerOfViewY),
                SettingsSingleton.getInstance().getCenterOfViewActorColor(), centerOfViewShape);
        
        childActors[childActors.length - 1] = centerOfViewInHUDMenu;
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //done adding centerOfViewHUDMenu actor 
        
        return childActors;
    }
    

    private void generateScaledContainerRectForMap(){
        
        
        int maxMapWidthOrHeight = (int)(percentOfWindowMapCanHave*this.windowDimension.getHeight());        
        
        Dimension containerRect = this.getProperSizeForDimensionWithLimits(this.container.getBoundsDimension(),
                maxMapWidthOrHeight);

        int scaleRatioContainerOverMapRepr = (this.container.getBoundsDimension().width/
                                                containerRect.width);

        containerRect.width += (int)(containerRect.getWidth()*.10);
        containerRect.height += (int)(containerRect.getHeight()*.10);
        
        this.shape = new Rectangle(0, 0, containerRect.width, containerRect.height);
        
        int pointX = 0, pointY = 0;
        
        switch(this.getCornerOfWindow()){
        
            case TOP_RIGHT:
                pointX = (this.windowDimension.width - containerRect.width) - this.inset;
                pointY = this.inset;
                break;
                
            case TOP_LEFT:
                pointX = this.inset;
                pointY = this.inset;
                
                break;
                
            case BOTTOM_LEFT:
                pointX = this.inset;
                pointY = (this.windowDimension.height - containerRect.height) - this.inset;
                
                break;
                
            case BOTTOM_RIGHT:
                pointX = (this.windowDimension.width - containerRect.width) - this.inset;
                pointY = (this.windowDimension.height - containerRect.height) - this.inset;

                break;
        }
        
        Point mapContainerRectPoint = new Point(pointX, pointY);
                
        this.setTopLeftLocation(mapContainerRectPoint);
        this.setChildActors(this.generateChildActorsForMap(scaleRatioContainerOverMapRepr));
        
    }

    /**
     * @return the childActors
     */
    @Override
    public Actor[] getChildActors() {
        return childActors;
    }
   
    /**
     * @return the container
     */
    private ContainerUniverse getContainer() {
        return container;
    }
 

    /**
     * @return the cornerOfWindow
     */
    public WindowCorner getCornerOfWindow() {
        return cornerOfWindow;
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
        
    @Override
    public Shape getShape(){
        return this.shape;
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
    
    /**
     * @return the windowDimension
     */
    public Dimension getWindowDimension() {
        return windowDimension;
    }

    
    /**
     * @param childActors the childActors to set
     */
    @Override
    public void setChildActors(Actor[] childActors) {
        this.childActors = childActors;
    }


    /**
     * @param container the container to set
     */
    public void setContainer(ContainerUniverse container) {
        this.container = container;
    }

    
    /**
     * @param cornerOfWindow the cornerOfWindow to set
     */
    public void setCornerOfWindow(WindowCorner cornerOfWindow) {
        this.cornerOfWindow = cornerOfWindow;
    }

    
    @Override
    public void setShape(Shape s){
        this.shape = s;
    }

    
    /**
     * @param windowDimension the windowDimension to set
     */
    public void setWindowDimension(Dimension windowDimension) {
        this.windowDimension = windowDimension;
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
