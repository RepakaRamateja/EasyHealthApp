package com.SoftwareEng.RishitReddyMuthyala.easyHealth;


import java.io.Serializable;


/* 
 * This class holds the details of the hospital.
 */
public class Hospital implements Serializable{

    String name;
    String description;
    String id;
    String primaryID;
    String image;
    String url;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    String contact;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;


    public Hospital() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   // public String getDirector() {
    //    return director;
   // }

   // public void setDirector(String director) {
       // this.director = director;
   // }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }*/

    public String getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /*public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   /* public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }*/

    public void get(String name) {

    }
}
