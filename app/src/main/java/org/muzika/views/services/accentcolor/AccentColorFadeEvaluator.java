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

package org.muzika.views.services.accentcolor;

import android.animation.TypeEvaluator;

/**
 * This evaluator fades ARGB colors
 */
public class AccentColorFadeEvaluator implements TypeEvaluator {

    private static final AccentColorFadeEvaluator sInstance = new AccentColorFadeEvaluator();

    public static AccentColorFadeEvaluator getInstance() {
        return sInstance;
    }

    /**
     * This function returns the calculated in-between value for a color
     * given integers that represent the start and end values in the four
     * bytes of the 32-bit int. This function is half-half, the first half
     * the start color is faded out, the second half the new color is faded in
     *
     * @param fraction The fraction from the starting to the ending values
     * @param startValue A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @param endValue A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @return A value that is calculated to be the linearly interpolated
     * result, derived by applying the fraction to the alpha channel of
     * the colors
     */
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        int start = (Integer) startValue;
        int end = (Integer) endValue;

        if (fraction < 0.5f) {
            // fading old color out
            float factor = 1 - (fraction * 2);
            return colorFactorAlpha(factor, start);

        } else {
            // fading new color in
            float factor = (fraction - 0.5f) * 2;
            return colorFactorAlpha(factor, end);
        }
    }

    /**
     * This method applies the factor to the alpha channel of the given color
     * @param factor the factor of the alpha channel, must not exceed 1.0
     * @param color A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @return the color with the new alpha channel
     */
    private int colorFactorAlpha(float factor, int color) {

        int a = (color >> 24) & 0xff;
        int r = (color >> 16) & 0xff;
        int g = (color >> 8) & 0xff;
        int b = color & 0xff;

        float alphaF = ((float) a) * factor;
        int a_f = Math.round(alphaF);

        return (a_f << 24) | (r << 16) | (g << 8) | b;

    }

}