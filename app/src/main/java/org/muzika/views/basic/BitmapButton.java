/*
 *  Copyright (c) 2017 Ognjen Galić
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package org.muzika.views.basic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;

/**
 * This class represents a button that has no text
 * but has only one bitmap inside of it (like the play button)
 */
public class BitmapButton extends android.widget.Button {

    public BitmapButton(Context context) {
        super(context);
    }

    public BitmapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method hijacks the incoming bitmap and adds a layer
     * behind it that respods to user input.
     *
     * The background of the button is 2-layered, the bitmap
     * infront of the back layer, and the back layer whos alpha
     * level is adjusted to be 0 if the button is not pressed and
     * 64 if the button is pressed.
     * @param background the background bitmap
     */
    @Override
    public void setBackground(Drawable background) {

        if (!(background instanceof BitmapDrawable)) {
            throw new RuntimeException("Only bitmaps may be backgrounds of buttons!");
        }

        super.setBackground(new ButtonBackground((BitmapDrawable) background));
    }

    /**
     * This class layers a solid color layer behind a bitmap for
     * indication of user interaction. The layer is transparent if
     * no user input is present and is level 64 if the user presses the button.
     *
     * This was implemented becauase the "alpha" propery of the drawable is not
     * settable with XML.
     */
    private static class ButtonBackground extends StateListDrawable {

        private final Drawable[] backgroundLayers = new Drawable[2];
        private LayerDrawable pressedBackground;

        ButtonBackground(BitmapDrawable bitmap) {
            super();

            ColorDrawable background = new ColorDrawable(Color.GRAY);
            background.setAlpha(64);

            backgroundLayers[0] = background;
            backgroundLayers[1] = bitmap;

            pressedBackground = new LayerDrawable(backgroundLayers);

            addState(new int[] { android.R.attr.state_pressed }, pressedBackground);
            addState(StateSet.WILD_CARD, bitmap);
        }


    }

}


