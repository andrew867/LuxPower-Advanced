package com.lux.luxcloud.global.bean.property;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Property implements Parcelable {
    public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() { // from class: com.lux.luxcloud.global.bean.property.Property.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Property createFromParcel(Parcel parcel) {
            return new Property(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Property[] newArray(int i) {
            return new Property[i];
        }
    };
    private String name;
    private String value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Property() {
    }

    public Property(String str, String str2) {
        this.name = str;
        this.value = str2;
    }

    protected Property(Parcel parcel) {
        this.name = parcel.readString();
        this.value = parcel.readString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return this.value;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.value);
    }
}