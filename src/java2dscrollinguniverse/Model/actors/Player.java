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

import java.awt.Rectangle;
import java2dscrollinguniverse.Model.TwoDRectangle;
import java2dscrollinguniverse.Model.universe.Location;
import java2dscrollinguniverse.SettingsSingleton;

/**
 *
 * @author andrewnyhus
 */
public class Player extends Actor{
   // private final int playerWidth = 40;
   // private final int playerHeight = 40;
    //private TwoDRectangle viewRectangle = SettingsSingleton.getInstance().getViewRectangle();
    
    public Player(int playerWidthandHeight){
        super(ActorType.player, new Location(SettingsSingleton.getInstance().getViewRectangle().getWidth()/2 - playerWidthandHeight/2, SettingsSingleton.getInstance().getViewRectangle().getHeight()/2 - playerWidthandHeight/2), SettingsSingleton.getInstance().getPlayerColor(), new Rectangle(SettingsSingleton.getInstance().getViewRectangle().getWidth()/2 - playerWidthandHeight/2, SettingsSingleton.getInstance().getViewRectangle().getHeight()/2 - playerWidthandHeight/2, playerWidthandHeight, playerWidthandHeight));
    }
    
}
