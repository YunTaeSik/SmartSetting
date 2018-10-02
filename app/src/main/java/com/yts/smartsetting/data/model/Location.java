package com.yts.smartsetting.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yts.smartsetting.utill.NullFilter;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Location extends RealmObject implements Parcelable {
    @PrimaryKey
    public String date;

    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private boolean arriveBlueTooth;
    private boolean arriveWifi;
    private boolean leaveBlueTooth;
    private boolean leaveWifi;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isArriveBlueTooth() {
        return NullFilter.check(arriveBlueTooth);
    }

    public void setArriveBlueTooth(boolean arriveBlueTooth) {
        this.arriveBlueTooth = arriveBlueTooth;
    }

    public boolean isArriveWifi() {
        return NullFilter.check(arriveWifi);
    }

    public void setArriveWifi(boolean arriveWifi) {
        this.arriveWifi = arriveWifi;
    }

    public boolean isLeaveBlueTooth() {
        return NullFilter.check(leaveBlueTooth);
    }

    public void setLeaveBlueTooth(boolean leaveBlueTooth) {
        this.leaveBlueTooth = leaveBlueTooth;
    }

    public boolean isLeaveWifi() {
        return NullFilter.check(leaveWifi);
    }

    public void setLeaveWifi(boolean leaveWifi) {
        this.leaveWifi = leaveWifi;
    }

    public Location() {
        setDate(String.valueOf(System.currentTimeMillis()));
    }

    public Location(Location location) {
        setDate(location.getDate());
        setName(location.getName());
        setAddress(location.getAddress());
        setLatitude(location.getLatitude());
        setLongitude(location.getLongitude());
        setArriveBlueTooth(location.isArriveBlueTooth());
        setArriveWifi(location.isArriveWifi());
        setLeaveBlueTooth(location.isLeaveBlueTooth());
        setLeaveWifi(location.isLeaveWifi());
    }


    public void setLocation(PlaceData placeData) {
        setDate(String.valueOf(System.currentTimeMillis()));
        setName(placeData.getName());
        setAddress(placeData.getAddress());
        setLatitude(String.valueOf(placeData.getLatLng().latitude));
        setLongitude(String.valueOf(placeData.getLatLng().longitude));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeByte(this.arriveBlueTooth ? (byte) 1 : (byte) 0);
        dest.writeByte(this.arriveWifi ? (byte) 1 : (byte) 0);
        dest.writeByte(this.leaveBlueTooth ? (byte) 1 : (byte) 0);
        dest.writeByte(this.leaveWifi ? (byte) 1 : (byte) 0);
    }

    protected Location(Parcel in) {
        this.date = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.arriveBlueTooth = in.readByte() != 0;
        this.arriveWifi = in.readByte() != 0;
        this.leaveBlueTooth = in.readByte() != 0;
        this.leaveWifi = in.readByte() != 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public boolean isValidity() {
        return getName() != null && getName().length() > 0 && getAddress() != null && getAddress().length() > 0 && getLatitude() != null && getLatitude().length() > 0 && getLongitude() != null && getLongitude().length() > 0;
    }
}
