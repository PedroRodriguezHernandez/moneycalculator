package software.moneycalculator.fixerwebservice;

import software.moneycalculator.Currency;
import software.moneycalculator.CurrencyLoader;
import software.moneycalculator.ExchangeRateLoader;

import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        CurrencyLoader currencyLoader = new FixerCurrencyLoader();
        List<Currency> currencies = currencyLoader.load();
        for (Currency currency : currencies) {
            System.out.println(currency.toString());
        }
        */

       Currency from = new Currency("USD", "Dolar United States");
       Currency to = new Currency("EUR", "Euro");
       ExchangeRateLoader exchangeRateLoader = new FixerExchangeRateLoader(from, to);
       System.out.println(exchangeRateLoader.load().toString());
    }
}
