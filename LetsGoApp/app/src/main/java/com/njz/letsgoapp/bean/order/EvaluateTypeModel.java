package com.njz.letsgoapp.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LGQ
 * Time: 2018/10/13
 * Function:
 */

public class EvaluateTypeModel implements Parcelable{

    boolean isGuide;
    boolean isCar;
    boolean isTrip;
    boolean isTravel;

    public EvaluateTypeModel() {
    }

    public boolean isGuide() {
        return isGuide;
    }

    public void setGuide(boolean guide) {
        isGuide = guide;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setCar(boolean car) {
        isCar = car;
    }

    public boolean isTrip() {
        return isTrip;
    }

    public void setTrip(boolean trip) {
        isTrip = trip;
    }

    public boolean isTravel() {
        return isTravel;
    }

    public void setTravel(boolean travel) {
        isTravel = travel;
    }


    protected EvaluateTypeModel(Parcel in) {
        isGuide = in.readByte() != 0;
        isCar = in.readByte() != 0;
        isTrip = in.readByte() != 0;
        isTravel = in.readByte() != 0;
    }

    public static final Creator<EvaluateTypeModel> CREATOR = new Creator<EvaluateTypeModel>() {
        @Override
        public EvaluateTypeModel createFromParcel(Parcel in) {
            return new EvaluateTypeModel(in);
        }

        @Override
        public EvaluateTypeModel[] newArray(int size) {
            return new EvaluateTypeModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isGuide ? 1 : 0));
        dest.writeByte((byte) (isCar ? 1 : 0));
        dest.writeByte((byte) (isTrip ? 1 : 0));
        dest.writeByte((byte) (isTravel ? 1 : 0));
    }
}
