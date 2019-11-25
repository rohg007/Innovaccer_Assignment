package com.rohg007.android.innovaccerassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Host implements Parcelable {

    private String hostId;
    private String hostName;
    private String hostEmail;
    private String hostPhone;
    private String hostAddress;

    public Host() {
        hostId="";
        hostName="";
        hostEmail="";
        hostPhone="";
        hostAddress="";
    }

    public Host(String hostId, String hostName, String hostEmail, String hostPhone, String hostAddress) {
        this.hostId = hostId;
        this.hostName = hostName;
        this.hostEmail = hostEmail;
        this.hostPhone = hostPhone;
        this.hostAddress = hostAddress;
    }

    protected Host(Parcel in){
        hostId = in.readString();
        hostName=in.readString();
        hostEmail=in.readString();
        hostPhone=in.readString();
        hostAddress=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hostId);
        dest.writeString(hostName);
        dest.writeString(hostEmail);
        dest.writeString(hostPhone);
        dest.writeString(hostAddress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Host> CREATOR = new Creator<Host>() {
        @Override
        public Host createFromParcel(Parcel source) {
            return new Host(source);
        }

        @Override
        public Host[] newArray(int size) {
            return new Host[size];
        }
    };

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getHostPhone() {
        return hostPhone;
    }

    public void setHostPhone(String hostPhone) {
        this.hostPhone = hostPhone;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public static List<Host> getTestHosts(){
        List<Host> hostArrayList = new ArrayList<>();
        hostArrayList.add( new Host("1","Jim","17dcs012@lnmiit.ac.in","12345678","D-321"));
        hostArrayList.add(new Host("1","Jim G","17dcs012@lnmiit.ac.in","12345678","D-321"));
        hostArrayList.add(new Host("1","Jim GG","17dcs012@lnmiit.ac.in","12345678","D-321"));
        return hostArrayList;
    }
}
