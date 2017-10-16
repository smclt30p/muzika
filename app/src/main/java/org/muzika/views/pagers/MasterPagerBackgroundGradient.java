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

package org.muzika.views.pagers;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.muzika.views.services.accentcolor.AccentColorFadeEvaluator;
import org.muzika.views.services.accentcolor.AccentColor;
import org.muzika.views.services.accentcolor.AccentColorService;

import static org.muzika.core.Logger.debug;

/**
 * This is the gradient at the bottom of the master view, the pager
 * Uses the AccentColor service to change colors.
 */
public class MasterPagerBackgroundGradient extends View implements AccentColorService.AccentColorChangedListener {

    private GradientDrawable background;
    private int currentColor;

    public MasterPagerBackgroundGradient(Context context) {
        super(context);
        init(context, null);
    }

    public MasterPagerBackgroundGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context ctx, @Nullable AttributeSet attrs) {
        background = new GradientDrawable();
        background.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        int colors[] = { 0x00000000, 0x00000000 };
        background.setColors(colors);
        setBackground(background);
        AccentColorService.getAccentColorService().addAccentColorChangedListener(this);
    }

    @Override
    public void accentColorChanged(AccentColor color) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "gradientColor", color.toARGB());
        animator.setEvaluator(AccentColorFadeEvaluator.getInstance());
        animator.setDuration(3000);
        animator.start();
    }

    public void setGradientColor(int color) {
        background.setColors(new int[] {0x00000000, color});
        setBackground(background);
        currentColor = color;
    }

    public int getGradientColor() {
        return currentColor;
    }

}
