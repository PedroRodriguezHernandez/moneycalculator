package software.moneycalculator;

public record Money(double amount, Currency currency ) {
    @Override
    public String toString() {
        return String.format("%.2f",amount) + " " + currency;
    }
}
