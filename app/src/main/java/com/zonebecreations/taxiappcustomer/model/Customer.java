package com.zonebecreations.taxiappcustomer.model;

public class Customer {
    String name;
    String nic;
    String tp;
    String email;
    String gender;
    String image;
    boolean activeStatus;

    public Customer() {
    }

    public Customer(String name, String nic, String tp, String email, String gender, String image, boolean activeStatus) {
        this.name = name;
        this.nic = nic;
        this.tp = tp;
        this.email = email;
        this.gender = gender;
        this.image = image;
        this.activeStatus = activeStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
