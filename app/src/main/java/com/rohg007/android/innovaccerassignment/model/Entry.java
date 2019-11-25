package com.rohg007.android.innovaccerassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Entry implements Parcelable {

    private String entryId;
    private String visitorName;
    private String vistorEmail;
    private String visitorPhone;
    private Host host;
    private String checkInTime;
    private String checkOutTime;

    public Entry() {
        this.entryId="";
        this.visitorName="";
        this.vistorEmail="";
        this.visitorPhone="";
        this.host=new Host();
        this.checkInTime="";
        this.checkOutTime="";
    }

    public Entry(String entryId, String visitorName, String vistorEmail, String visitorPhone, Host host, String checkInTime, String checkOutTime) {
        this.entryId = entryId;
        this.visitorName = visitorName;
        this.vistorEmail = vistorEmail;
        this.visitorPhone = visitorPhone;
        this.host = host;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    protected Entry(Parcel in){
        entryId=in.readString();
        visitorName=in.readString();
        vistorEmail=in.readString();
        visitorPhone=in.readString();
        host= (Host) in.readValue(getClass().getClassLoader());
        checkInTime = in.readString();
        checkOutTime=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entryId);
        dest.writeString(visitorName);
        dest.writeString(vistorEmail);
        dest.writeString(visitorPhone);
        dest.writeValue(host);
        dest.writeString(checkInTime);
        dest.writeString(checkOutTime);
    }

    public static Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVistorEmail() {
        return vistorEmail;
    }

    public void setVistorEmail(String vistorEmail) {
        this.vistorEmail = vistorEmail;
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public static List<Entry> getTestEntries(){
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry("1","Rohan","gupta.rohan@live.com","99999999",
                new Host("1","Jim","17dcs012@lnmiit.ac.in","12345678","D-321"),
                Calendar.getInstance().getTime().toString(),""));
        entries.add(new Entry("1","Rohan G","gupta.rohan@live.in","99999999",
                new Host("1","Jim G","17dcs012@lnmiit.ac.in","12345678","D-321"),
                Calendar.getInstance().getTime().toString(),""));
        entries.add(new Entry("1","Rohan GG","gupta.rohan@live.com","99999999",
                new Host("1","Jim GG","17dcs012@lnmiit.ac.in","12345678","D-321"),
                Calendar.getInstance().getTime().toString(),""));
        return entries;
    }
}
