package adf.homework.domain;

public enum PaymentType {
    TRANSFER_PREPAID("Prepaid - bank transfer"),
    CARD_PREPAID("Prepaid - card"),
    TRANSFER_ON_RECEIPT("Bank transfer of receipt"),
    CARD_ON_RECEIPT("Card on receipt"),
    CASH_ON_RECEIPT("Cash on receipt");

    private final String displayValue;

    PaymentType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
