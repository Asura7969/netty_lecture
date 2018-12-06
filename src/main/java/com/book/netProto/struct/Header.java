package com.book.netProto.struct;

import java.util.HashMap;
import java.util.Map;

public class Header {


    //唯一的通信标志
    private int crcCode = 0xadaf0105;

    //总消息的长度 header + body;
    private int length;

    //会话id
    private long sessionID;

    /**
     * 消息类型
     * @see
     */
    private byte type;

    //消息优先级
    private byte proiority;

    //附件
    private Map<String,Object> attachment = new HashMap<>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getProiority() {
        return proiority;
    }

    public void setProiority(byte proiority) {
        this.proiority = proiority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", proiority=" + proiority +
                ", attachment=" + attachment +
                '}';
    }
}
