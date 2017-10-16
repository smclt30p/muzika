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

package org.muzika.core;

import android.util.Log;

/**
 * This is a bridge between the Android logging
 * framework and a few simple methods that can be imported
 * statically and be called without any class prefix.
 *
 * For ex:
 *
 * import static org.muzika.core.Logger.*;
 *
 * debug("Hello, world!");
 */
public class Logger {

    /**
     * Debug toggle switch, if this is disabled all DEBUG
     * level messages are discarded (not printed)
     */
    private static final boolean ENABLE_DEBUG = true;

    /**
     * DEBUG level message, optional, can be disabled.
     *
     * @param source the source object, should always pass "this"
     * @param data the data to be logged
     */
    public static void debug(Object source, Object data) {
        if (!ENABLE_DEBUG) return;
        Log.d(source.getClass().getName(), data.toString());
    }

    /**
     * DEBUG level message, optional, can be disabled.
     *
     * @param source the source object, should always pass "this"
     * @param format the printf format to format to
     * @param args the printf arguments
     */
    public static void debug(Object source, String format, Object...args) {
        if (!ENABLE_DEBUG) return;
        debug(source, String.format(format, args));
    }

    /**
     * INFO level messsages.
     *
     * @param source the source object, should always pass "this"
     * @param format the printf format to format to
     * @param args the printf arguments
     */
    public static void info(Object source, String format, Object...args) {
        Log.i(source.getClass().getName(), String.format(format, args));
    }

}
