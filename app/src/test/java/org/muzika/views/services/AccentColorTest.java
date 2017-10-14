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

import org.junit.Before;
import org.junit.Test;
import org.muzika.views.services.accentcolor.AccentColor;

import static org.junit.Assert.*;

public class AccentColorTest {

    private AccentColor color;

    @Before
    public void setUp() throws Exception {

        color = new AccentColor();
        color.a = 1;
        color.r = 2;
        color.g = 3;
        color.b = 4;

    }

    @Test
    public void fromString() throws Exception {
        AccentColor color = AccentColor.fromString("0x01020304");
        assertTrue(String.format("Expected %s got %s", this.color, color), this.color.equals(color));
    }

    @Test
    public void fromARGB() throws Exception {
        AccentColor color = AccentColor.fromARGB(0x01020304);
        assertTrue(this.color.equals(color));
    }

    @Test
    public void toARGB() throws Exception {
        assertEquals(String.format("Expected %x got %x", 0x01020304, color.toARGB()), 0x01020304, color.toARGB());
    }

    @Test
    public void toStringARGB() throws Exception {
        assertEquals("0x01020304", color.toARGBString());
    }

}