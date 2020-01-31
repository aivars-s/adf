package adf.homework.domain;

public enum DeliveryType {
    RECEIVE_AT_OFFICE("Receive at office"),
    DELIVER_TO_ADDRESS("Deliver to address"),
    DELIVER_TO_POSTAL_OFFICE("Deliver to postal office");

    private final String displayValue;

    DeliveryType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
