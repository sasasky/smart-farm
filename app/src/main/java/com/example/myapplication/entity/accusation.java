package com.example.myapplication.entity;

public class accusation {
    private String uid;
    private int infoId;
    private String reason;
    public enum InfoType {
        product,land;
    };
    private InfoType infoType;
    public enum State{
        unhandled,rejected,consent;
    }
    private State state;

    public accusation(String uid, int infoId, String reason,InfoType infoType,State state) {
        this.uid = uid;
        this.infoId = infoId;
        this.reason = reason;
        this.infoType = infoType;
        this.state = state;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public int getInfoId() {
        return infoId;
    }
    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public InfoType getType() {
        return infoType;
    }
    public void setType(InfoType infoType) {
        this.infoType = infoType;
    }
    public State getAccuseState() {
        return state;
    }
    public void setAccuseState(State state) {
        this.state = state;
    }
}
