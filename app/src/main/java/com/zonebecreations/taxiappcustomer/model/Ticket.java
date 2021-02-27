package com.zonebecreations.taxiappcustomer.model;

public class Ticket {
    String title;
    String body;
    String category;
    String customerDocId;

    public Ticket() {
    }

    public Ticket(String title, String body, String category, String customerDocId) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.customerDocId = customerDocId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCustomerDocId() {
        return customerDocId;
    }

    public void setCustomerDocId(String customerDocId) {
        this.customerDocId = customerDocId;
    }
}
