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

import java.util.ArrayList;

import static org.muzika.core.Logger.*;

/**
 * This ist the accent color service.
 *
 * The app has various elements that have
 * an accent color. The accent color is dicated by the
 * average color of the currently active album art.
 */
public class AccentColorService {

    /**
     * The accent color service is a singleton
     */
    private static AccentColorService instance;
    private AccentColor currentAccentColor;
    private AccentColorService() {
        listeners = new ArrayList<>();
    }
    public static AccentColorService getAccentColorService() {
        if (instance == null) {
            instance = new AccentColorService();
        }
        return instance;
    }

    private ArrayList<AccentColorChangedListener> listeners;

    public void addAccentColorChangedListener(AccentColorChangedListener listener) {
        debug(this, "Adding new listener: %s", listener.toString());
        listeners.add(listener);
    }

    public AccentColor getCurrentAccentColor() {
        return currentAccentColor;
    }

    public void setCurrentAccentColor(AccentColor currentAccentColor) {
        this.currentAccentColor = currentAccentColor;
        for (AccentColorChangedListener listener : this.listeners) {
            listener.accentColorChanged(currentAccentColor);
        }
    }

    public interface AccentColorChangedListener {
        void accentColorChanged(AccentColor color);
    }

}
