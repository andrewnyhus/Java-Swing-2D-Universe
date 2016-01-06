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

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java2dscrollinguniverse.Model.actors.ActorLabel.PositionOfLabel;
import java2dscrollinguniverse.SettingsSingleton;

/**
 *
 * @author andrewnyhus
 */
public class CenterOfViewActor extends Actor{
    
    public CenterOfViewActor(int centerOfViewActorWidth, int centerOfViewActorHeight){
        super(ActorType.centerOfViewActor, 
            new Point(SettingsSingleton.getInstance().getWindowWidth()/2 ,//x
                    SettingsSingleton.getInstance().getWindowHeight()/2 ),//y
            SettingsSingleton.getInstance().getCenterOfViewActorColor(),//color 
            new Ellipse2D.Double(0.0, 0.0, 1.0*centerOfViewActorWidth, 1.0*centerOfViewActorHeight),//shape
            null,//childActors
            new ActorLabel("^CenterOfView Actor", PositionOfLabel.MIDDLE_OF_BOTTOM));//label
    }
    
    
    public static CenterOfViewActor copyInstanceOfCenterOfViewActor(CenterOfViewActor cva){
        return new CenterOfViewActor(cva.getWidth(), cva.getHeight());
    }

}
