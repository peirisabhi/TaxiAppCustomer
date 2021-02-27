package com.zonebecreations.taxiappcustomer.model;

public class OrderModel {

    private int order_id;
    private int product_master_id;
    private String fname;
    private String lname;
    private String address;
    private String phone;
    private String email;
    private String note;
    private String status;
    private String customerDocId;


    public OrderModel(){}

    public OrderModel(int order_id, int product_master_id, String fname, String lname, String address, String phone, String email, String note, String status) {
        this.order_id = order_id;
        this.product_master_id = product_master_id;
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.status = status;
    }


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_master_id() {
        return product_master_id;
    }

    public void setProduct_master_id(int product_master_id) {
        this.product_master_id = product_master_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerDocId() {
        return customerDocId;
    }

    public void setCustomerDocId(String customerDocId) {
        this.customerDocId = customerDocId;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "order_id=" + order_id +
                ", product_master_id=" + product_master_id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
