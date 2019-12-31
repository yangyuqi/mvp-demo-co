package com.wxjz.module_base.event;

/**
 * Created by a on 2019/9/18.
 */

public class HaveNoteEvent {
    public boolean isHaveNote() {
        return haveNote;
    }

    public void setHaveNote(boolean haveNote) {
        this.haveNote = haveNote;
    }

    private boolean haveNote = true;

    public HaveNoteEvent(boolean haveNote) {
        this.haveNote = haveNote;
    }
}
