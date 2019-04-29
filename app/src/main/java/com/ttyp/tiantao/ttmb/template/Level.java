package com.ttyp.tiantao.ttmb.template;

import android.util.Log;

public enum Level {
    VERBOSE(Log.VERBOSE),

    DEBUG(Log.DEBUG),

    INFO(Log.INFO),

    WARN(Log.WARN),

    ERROR(Log.ERROR),

    ASSERT(Log.ASSERT),

    CLOSE(Log.ASSERT + 1);

    public int value;

    Level(int value) {
        this.value = value;
    }
}
