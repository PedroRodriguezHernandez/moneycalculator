package software.moneycalculator;

public record Currency(String symbol, String name) {
    @Override
    public String toString() {
        return symbol + " : " + name ;
    }
}
