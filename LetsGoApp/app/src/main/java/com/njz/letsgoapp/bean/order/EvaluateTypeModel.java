package com.njz.letsgoapp.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LGQ
 * Time: 2018/10/13
 * Function:
 */

public class EvaluateTypeModel implements Parcelable{

    boolean isAttitude;
    boolean isQuality;
    boolean isScheduling;
    boolean isCarCondition;
    boolean isCustom;

    public EvaluateTypeModel() {
    }


    protected EvaluateTypeModel(Parcel in) {
        isAttitude = in.readByte() != 0;
        isQuality = in.readByte() != 0;
        isScheduling = in.readByte() != 0;
        isCarCondition = in.readByte() != 0;
        isCustom = in.readByte() != 0;
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

    public boolean isAttitude() {
        return isAttitude;
    }

    public void setAttitude(boolean attitude) {
        isAttitude = attitude;
    }

    public boolean isQuality() {
        return isQuality;
    }

    public void setQuality(boolean quality) {
        isQuality = quality;
    }

    public boolean isScheduling() {
        return isScheduling;
    }

    public void setScheduling(boolean scheduling) {
        isScheduling = scheduling;
    }

    public boolean isCarCondition() {
        return isCarCondition;
    }

    public void setCarCondition(boolean carCondition) {
        isCarCondition = carCondition;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isAttitude ? 1 : 0));
        dest.writeByte((byte) (isQuality ? 1 : 0));
        dest.writeByte((byte) (isScheduling ? 1 : 0));
        dest.writeByte((byte) (isCarCondition ? 1 : 0));
        dest.writeByte((byte) (isCustom ? 1 : 0));
    }
}
