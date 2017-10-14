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

package org.muzika.views.services;

/**
 * This class represents a ARGB accent color
 */
public class AccentColor {

    public int a = 0;
    public int r = 0;
    public int g = 0;
    public int b = 0;

    /**
     * Create an accent color from a hexadecimal integer string.
     * For ex.: 0xff00ff00
     *
     * @param color the color string in hexadecimal format (10 chars)
     * @return the AccentColor from that string
     */
    public static AccentColor fromString(String color) {

        int a = Integer.parseInt(color.substring(2, 4), 16);
        int r = Integer.parseInt(color.substring(4, 6), 16);
        int g = Integer.parseInt(color.substring(6, 8), 16);
        int b = Integer.parseInt(color.substring(8, 10), 16);

        int argb = ((a << 24) | (r << 16) | (g << 8) | b);

        return AccentColor.fromARGB(argb);
    }

    /**
     * Create an accent color from a 4-byte ARGB integer
     *
     * @param color the 4byte integer describing that color
     * @return the AccentColor representing that 4byte integer
     */
    public static AccentColor fromARGB(int color) {
        AccentColor ret = new AccentColor();
        ret.a = (color >> 24) & 0x000000FF;
        ret.r = ((color >> 16) & 0x000000FF);
        ret.g = ((color >> 8) & 0x000000FF);
        ret.b = ((color) & 0x000000ff);
        return ret;
    }

    /**
     * Returns the 4-byte 32bit ARGB representation of the AccentColor
     *
     * @return the 4-byte 32bit ARGB representation of the AccentColor
     */
    public int toARGB() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Returns the ARGB string representation of the AccentColor. The opposite
     * of fromARGBString
     *
     * @return the ARGB string representation of the AccentColor
     */
    public String toARGBString() {
        String aS = (a < 9) ? String.format("0%d", a) : String.format("%d", a);
        String rS = (r < 9) ? String.format("0%d", r) : String.format("%d", r);
        String gS = (g < 9) ? String.format("0%d", g) : String.format("%d", g);
        String bS = (b < 9) ? String.format("0%d", b) : String.format("%d", b);
        return String.format("0x%s%s%s%s", aS, rS, gS, bS);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AccentColor)) return false;
        AccentColor that = (AccentColor) o;
        return a == that.a && r == that.r && g == that.g && b == that.b;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }


}