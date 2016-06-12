package com.rappidandroiddemo.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andres on 3/16/16.
 */
public class ApplicationItem implements Parcelable {
    private String name;
    private String image;
    private String summary;
    private CategoryItem category;

    public ApplicationItem(
            String name,
            String image,
            String summary,
            CategoryItem category){
        this.name = name;
        this.image = image;
        this.summary = summary;
        this.category = category;
    }

    protected ApplicationItem(Parcel in) {
        name = in.readString();
        image = in.readString();
        summary = in.readString();
        category = in.readParcelable(CategoryItem.class.getClassLoader());
    }

    public static final Creator<ApplicationItem> CREATOR = new Creator<ApplicationItem>() {
        @Override
        public ApplicationItem createFromParcel(Parcel in) {
            return new ApplicationItem(in);
        }

        @Override
        public ApplicationItem[] newArray(int size) {
            return new ApplicationItem[size];
        }
    };

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public void setCategory(CategoryItem category){
        this.category = category;
    }

    //Getters
    public String getName(){
        return this.name;
    }

    public String getImage(){
        return this.image;
    }

    public String getSummary(){
        return this.summary;
    }

    public CategoryItem getCategory(){
        return this.category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.image);
        parcel.writeString(this.summary);
        parcel.writeParcelable(this.category,i);
    }
}
