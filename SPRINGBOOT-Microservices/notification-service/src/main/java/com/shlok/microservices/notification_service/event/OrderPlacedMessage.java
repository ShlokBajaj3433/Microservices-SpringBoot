package com.shlok.microservices.notification_service.event;

public class OrderPlacedMessage {
    private String orderNumber;
    private String email;
    private String firstName;
    private String lastName;

    public OrderPlacedMessage() {
    }

    public OrderPlacedMessage(String orderNumber, String email, String firstName, String lastName) {
        this.orderNumber = orderNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "OrderPlacedMessage{" +
                "orderNumber='" + orderNumber + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}