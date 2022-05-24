package io.zoemeow.dutapi.objects;

import java.time.LocalDateTime;

public class SubjectInfo {
    private String id;
    private String name;
    private Float credit;
    private Boolean isHighQuality;
    private String lecturer;
    private String scheduleStudy;
    private String weeks;
    private String pointFomula;
    private String groupExam;
    private Boolean isGlobalExam;
    private String roomExam;
    private String dateExamInString;
    private LocalDateTime dateExam;

    public SubjectInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Boolean getIsHighQuality() {
        return isHighQuality;
    }

    public void setIsHighQuality(Boolean isHighQuality) {
        this.isHighQuality = isHighQuality;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getScheduleStudy() {
        return scheduleStudy;
    }

    public void setScheduleStudy(String scheduleStudy) {
        this.scheduleStudy = scheduleStudy;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getPointFomula() {
        return pointFomula;
    }

    public void setPointFomula(String pointFomula) {
        this.pointFomula = pointFomula;
    }

    public String getGroupExam() {
        return groupExam;
    }

    public void setGroupExam(String groupExam) {
        this.groupExam = groupExam;
    }

    public Boolean getIsGlobalExam() {
        return isGlobalExam;
    }

    public void setIsGlobalExam(Boolean isGlobalExam) {
        this.isGlobalExam = isGlobalExam;
    }

    public String getRoomExam() {
        return roomExam;
    }

    public void setRoomExam(String roomExam) {
        this.roomExam = roomExam;
    }

    public String getDateExamInString() {
        return dateExamInString;
    }

    public void setDateExamInString(String dateExamInString) {
        this.dateExamInString = dateExamInString;
    }

    public LocalDateTime getDateExam() {
        return dateExam;
    }

    public void setDateExam(LocalDateTime dateExam) {
        this.dateExam = dateExam;
    }
}
