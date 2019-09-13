package com.example.shanu.tutorialdemoapp.RestApi.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Joke implements Parcelable , Serializable {

    private int id ;
    private String type;
    private String general;
    private String setup;
    private String punchline;

    protected Joke(Parcel in) {
        id = in.readInt();
        type = in.readString();
        general = in.readString();
        setup = in.readString();
        punchline = in.readString();
    }

    public static final Creator<Joke> CREATOR = new Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel in) {
            return new Joke(in);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(general);
        parcel.writeString(setup);
        parcel.writeString(punchline);
    }
}
