package com.rappidandroiddemo.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andres on 3/16/16.
 */

public class CategoryItem implements Parcelable {
    private String id;
    private String term;
    private String scheme;
    private String label;

    public CategoryItem(
            String id,
            String term,
            String scheme,
            String label){
        this.id = id;
        this.term = term;
        this.scheme = scheme;
        this.label = label;
    }

    protected CategoryItem(Parcel in) {
        id = in.readString();
        term = in.readString();
        scheme = in.readString();
        label = in.readString();
    }

    //Setters
    public void setId(String id){
        this.id = id;
    }

    public void setTerm(String term){
        this.term = term;
    }

    public void setScheme(String scheme){
        this.scheme = scheme;
    }

    public void setLabel(String label){
        this.label = label;
    }

    //Getters
    public String getId(){
        return this.id;
    }

    public String getTerm(){
        return this.term;
    }

    public String getScheme(){
        return this.scheme;
    }

    public String getLabel(){
        return this.label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!CategoryItem.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final CategoryItem other = (CategoryItem) obj;

        if (!this.id.equalsIgnoreCase(other.id)) {
            return false;
        }
        return true;
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.term);
        parcel.writeString(this.scheme);
        parcel.writeString(this.label);
    }
}
