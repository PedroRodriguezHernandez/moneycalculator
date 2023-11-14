package software.moneycalculator.fixerwebservice;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.moneycalculator.Currency;
import software.moneycalculator.ExchangeRate;
import software.moneycalculator.ExchangeRateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixerExchangeRateLoader implements ExchangeRateLoader {

    private final Currency from;
    private final Currency to;


    public FixerExchangeRateLoader(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public ExchangeRate load() {
        try {
            return toExchangeRate(fromJson());
        }catch (IOException e){
            return null;
        }
    }

    private ExchangeRate toExchangeRate(String json) {
        Map<String, JsonElement> rates = new Gson().fromJson(json, JsonObject.class)
                .get("rates")
                .getAsJsonObject()
                .asMap();
        ExchangeRate exchangeRate = new ExchangeRate(
                from,
                to,
                rateTo(rates)
        );
        return exchangeRate;
    }

    private double rateTo(Map<String, JsonElement> rates) {
        double rate;
        rate = rates.get(to.symbol()).getAsDouble()/rates.get(from.symbol()).getAsDouble();
        return rate;
    }

    /*
        The API that I use to change currency only works with respect to the EUR, so I will have to calculate it with
        respect to that currency to later make the exchange rate.
     */
    private String fromJson() throws IOException{
        URL url = new URL("http://data.fixer.io/api/latest?access_key=" + FixerAPI.key + "&symbols="+from.symbol()+","+to.symbol());
        try(InputStream is = url.openStream()) {
            return new String(is.readAllBytes());
        }
    }


}
