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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java2dscrollinguniverse.Model.TwoDimensionalMovement;
import java2dscrollinguniverse.Model.actors.Actor;
import java2dscrollinguniverse.Model.actors.HUDMap;
import java2dscrollinguniverse.Model.actors.HUDMap.WindowCorner;
import java2dscrollinguniverse.Model.actors.Wall;
import java2dscrollinguniverse.Model.container.ContainerUniverse;
import java2dscrollinguniverse.SettingsSingleton;
import javax.swing.JPanel;

/**
 *
 * @author andrewnyhus
 */
public class MainViewComponent extends JPanel{
    
    private ContainerUniverse updatedContainer;
    private ArrayList<LabelTuple> arrayListOfLabelsToDraw;    
    
    
    public MainViewComponent(ContainerUniverse container){
        this.updatedContainer = container;
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

    private void drawActorLabels(Graphics2D g2d){
        for(LabelTuple lt: this.arrayListOfLabelsToDraw){
            g2d.drawString(lt.getString(), lt.getPoint().x, lt.getPoint().y);
        }
    }
    
    private Dimension drawCenterOfViewActor(Graphics2D g2d) {
        //set "pen" to proper color for drawing the centerOfViewActor
        g2d.setColor(SettingsSingleton.getInstance().getCenterOfViewActorColor());
        // get shape of centerOfViewActor
        Shape centerOfViewActorShape = this.updatedContainer.getCenterOfViewActor().getShape();
        Dimension viewDimensions = SettingsSingleton.getInstance().getWindowDimension();
        Point centerViewPoint = new Point( (viewDimensions.width/2),
                (viewDimensions.height/2) );
        g2d.fill(this.getShapeWithOffsetFromOrigin(centerOfViewActorShape, centerViewPoint));
        
        Point centerOfViewActorLabelLoc = this.updatedContainer.getCenterOfViewActor().
                                            getLocationOfLabelPosition();
        
        String centerOfViewActorLabelStr = this.updatedContainer.getCenterOfViewActor().
                                            getActorLabel().getLabelText();

        this.prepareToDrawString(centerOfViewActorLabelLoc, centerOfViewActorLabelStr);
        
        return viewDimensions;
    }

    private void drawContainerBackground(Graphics2D g2d, TwoDimensionalMovement centerOfViewOffsetFromModel) {
        //set "pen" with proper color for background rectangle
        g2d.setColor(SettingsSingleton.getInstance().getContainerBackgroundColor());
        
        //get shape of background rect
        Shape bgRectShape = this.updatedContainer.getBackgroundRect().getShape();
        
        //get top left location of backgroundRect
        Point bgRectTopLeftLocationToDraw =
                centerOfViewOffsetFromModel.getPointWithMovementAppliedFromPoint(this.updatedContainer.getBackgroundRect().getTopLeftLocation());
        
        
        //set topLeft location to the bgRectShape variable, and use it to draw
        //bgRectShape.getBounds2D()..setLocation(bgRectTopLeftLocationToDraw.getX(), bgRectTopLeftLocationToDraw.getX());
        
        g2d.fill(this.getShapeWithOffsetFromOrigin(bgRectShape, bgRectTopLeftLocationToDraw));    
    }


    private void drawHUDMap(Dimension viewDimensions, Graphics2D g2d) {
        boolean showHUDMap = SettingsSingleton.getInstance().shouldShowHUDMap();
        
        if(showHUDMap){
            WindowCorner HUDMapWindowCorner = SettingsSingleton.getInstance().getHUDMapCorner();
            
            HUDMap map = new HUDMap(this.getCenterOfViewActorOffsetFromModelToView(),
                    viewDimensions, this.updatedContainer, HUDMapWindowCorner);
            
            g2d.setColor(map.getColor());
            
            Shape mapShapeToConvert = map.getShape();
            
            Shape mapActorShape;
            
            Point mapActorPoint = map.getTopLeftLocation();
            
            mapActorShape = new Rectangle(mapActorPoint.x, mapActorPoint.y,
                    mapShapeToConvert.getBounds().width,
                    mapShapeToConvert.getBounds().height);
            
            
            g2d.fill(mapActorShape);

            
            if(map.getChildActors() != null){
                for(Actor childActor: map.getChildActors()){
                    //set "pen" to proper color for current child actor
                    g2d.setColor(childActor.getColor());
                    
                    // get shape of current child actor
                    Shape currentChildActorShapeToConvert = childActor.getShape();
                    Shape currentChildActorShape = null;
                    
                    Point currentChildActorPoint = childActor.getTopLeftLocation();
                    
                    if(currentChildActorShapeToConvert instanceof Rectangle){
                        
                        currentChildActorShape = new Rectangle(currentChildActorPoint.x,
                                currentChildActorPoint.y,
                                currentChildActorShapeToConvert.getBounds().width,
                                currentChildActorShapeToConvert.getBounds().height);
                        
                    }else if(currentChildActorShapeToConvert instanceof Ellipse2D.Double){
                        
                        currentChildActorShape = new Ellipse2D.Double(currentChildActorPoint.getX(),
                                currentChildActorPoint.getY(),
                                currentChildActorShapeToConvert.getBounds().getWidth(),
                                currentChildActorShapeToConvert.getBounds().getHeight());
                        
                        
                    }else{
                        currentChildActorShape = currentChildActorShapeToConvert;
                    }
                    
                    g2d.fill(currentChildActorShape);
                }
            }
        }
    }
    
    private void drawMembersOfContainer(Graphics2D g2d, TwoDimensionalMovement centerOfViewOffsetFromModel) {
        for(Actor a: this.updatedContainer.getMembersOfContainer()){
            
            
            if(a.getType().viewLocationShouldChange()){
                
                //set "pen" to proper color for current actor
                g2d.setColor(a.getColor());
                
                // get shape of current actor in iteration
                Shape currentActorShape = a.getShape();
                
                //get top left location of the current Actor
                Point currentActorTopLeftLocationToDraw =
                        centerOfViewOffsetFromModel.getPointWithMovementAppliedFromPoint(a.getTopLeftLocation());
                
                g2d.fill(this.getShapeWithOffsetFromOrigin(currentActorShape, currentActorTopLeftLocationToDraw));
                
                String alString = a.getActorLabel().getLabelText();
                Point alPoint = a.getLocationOfLabelPosition();
                
                this.prepareToDrawString(alPoint, alString);
                
            }
        }
    }

    private void drawWalls(Graphics2D g2d, TwoDimensionalMovement centerOfViewOffsetFromModel) {
        //iterate through all walls in perimeter of the container
        for(Wall w: this.updatedContainer.getPerimeterWalls()){
            //set "pen" to proper color for walls
            g2d.setColor(SettingsSingleton.getInstance().getPerimeterColor());
            
            // get shape of current wall in iteration
            Shape currentWallShape = w.getShape();
            
            //get top left location of the current wall
            Point currentWallTopLeftLocationToDraw =
                    centerOfViewOffsetFromModel.getPointWithMovementAppliedFromPoint(w.getTopLeftLocation());
            
            g2d.fill(this.getShapeWithOffsetFromOrigin(currentWallShape, currentWallTopLeftLocationToDraw));

            String wlString = w.getActorLabel().getLabelText();
            Point wlPoint = w.getLocationOfLabelPosition();

            this.prepareToDrawString(wlPoint, wlString);
            
            
        }
    }
    
    public TwoDimensionalMovement getCenterOfViewActorOffsetFromModelToView(){
        
        
        Dimension viewDimensions = SettingsSingleton.getInstance().getWindowDimension();
        
        Point centerViewPoint = new Point(viewDimensions.width/2, viewDimensions.height/2);
        Point centerOfViewActorPointInModel = this.updatedContainer.getCenterOfViewActor().getTopLeftLocation();
        
        return new TwoDimensionalMovement(centerViewPoint.x - centerOfViewActorPointInModel.x,
                centerViewPoint.y - centerOfViewActorPointInModel.y);        
    }

    public Shape getShapeWithOffsetFromOrigin(Shape s, Point p){
                
        if(s instanceof Rectangle){
            Rectangle returnShape = new Rectangle(p.x, p.y, s.getBounds().width, s.getBounds().height);
            return returnShape;
        }else if(s instanceof Ellipse2D.Double){
            Ellipse2D.Double returnShape = new Ellipse2D.Double(p.getX(), p.getY(), s.getBounds().getWidth(), s.getBounds().getHeight());
            return returnShape;
        }
        
        //TODO: include more shapes than just rectangle
        
        return s;
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
            
            this.arrayListOfLabelsToDraw = new ArrayList();
            
            //gets the offset of view's center from origin of the container (0, 0)
            TwoDimensionalMovement centerOfViewOffsetFromModel = this.getCenterOfViewActorOffsetFromModelToView();
            
            drawContainerBackground(g2d, centerOfViewOffsetFromModel);
            
            drawWalls(g2d, centerOfViewOffsetFromModel);
            
            drawMembersOfContainer(g2d, centerOfViewOffsetFromModel);
            
            Dimension viewDimensions = drawCenterOfViewActor(g2d);            
            drawHUDMap(viewDimensions, g2d);
                        
            
            g2d.setColor(SettingsSingleton.getInstance().getLabelColor());
            this.drawActorLabels(g2d);
            
            
            
    }

    
    private void prepareToDrawString(Point labelLocToBeDrawn, String s){
        
        if(s.equals("")){
            return;
        }
        
        labelLocToBeDrawn.x += this.getCenterOfViewActorOffsetFromModelToView().
                                                                    getXMovement();
        labelLocToBeDrawn.y += this.getCenterOfViewActorOffsetFromModelToView().
                                                                    getYMovement();
        //now labelLocToBeDrawn represents the location to be drawn in the view
        //no longer does it represent the location of the label in the container data model
        
        int x = 0;
        boolean labelDataWasInserted = false;
        for(LabelTuple labelData: this.arrayListOfLabelsToDraw){
        
            if(labelData.getPoint().equals(labelLocToBeDrawn)){
                System.out.println("labelString: " + labelData.getString() + " s: " + s);
                labelDataWasInserted = true;
            }
                    
            x++;
        }
        
        if(!labelDataWasInserted){
            this.arrayListOfLabelsToDraw.add(new LabelTuple(s, labelLocToBeDrawn));
        }
        
    }

    
    
    public void updatedContainer(ContainerUniverse container){
        this.updatedContainer = container;
        this.repaint();
    }
    
    
    class LabelTuple{
        private String string;
        private Point locInView;

        public LabelTuple(String s, Point p){
            this.string = s;
            this.locInView = p;
        }

        public String getString(){
            return this.string;
        }

        public Point getPoint(){
            return this.locInView;
        }

        public void setString(String s){
            this.string = s;
        }

        public void setPoint(Point p){
            this.locInView = p;
        }

    }

}
