package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import java.io.Serializable;

/* 
 * This is a class to get and set data of blood banks which implements serializable interface
 */

public class BloodBank implements Serializable {

    String name;
    String description;
    String id;
    String primaryID;
    String image;
    String url;
    String contact;
    String address;

    /*
     * Get Contact Details of the user.
     */
    public String getContact() {
        return contact;
    }

    /*
     * Set Contact Details of the user.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }


    /*
     * Get Address Details of the user.
     */
    public String getAddress() {
        return address;
    }

    /*
     * Set Address Details of the user.
     */
    public void setAddress(String address) {
        this.address = address;
    }


    public BloodBank() {

    }

    /*
     * Get Name of the user.
     */
    public String getName() {
        return name;
    }

    /*
     * Set Name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Get Description Details of the user.
     */
    public String getDescription() {
        return description;
    }

    /*
     * Set Description Details of the user.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /*
     * Get ID of the user.
     */
    public String getId() {
        return id;
    }
    
    /*
     * Set ID of the user.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /*
     * Get Primary ID of the user.
     */
    public String getPrimaryID() {
        return primaryID;
    }

    /*
     * Set Primary ID of the user.
     */
    public void setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
    }

    /*
     * Get Image.
     */
    public String getImage() {
        return image;
    }

    /*
     * Set Image.
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /*
     * Get URL.
     */
    public String getUrl() {
        return url;
    }

    /*
     * Set URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }
    public void get(String name) {

    }

}