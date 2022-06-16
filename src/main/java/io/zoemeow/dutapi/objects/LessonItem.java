package io.zoemeow.dutapi.objects;

public class LessonItem {
    private Integer start;
    private Integer end;

    public LessonItem() {
        
    }

    public LessonItem(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}