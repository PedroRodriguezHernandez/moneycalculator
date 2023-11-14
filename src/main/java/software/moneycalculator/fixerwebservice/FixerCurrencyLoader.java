package software.moneycalculator.fixerwebservice;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.moneycalculator.Currency;
import software.moneycalculator.CurrencyLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

public class FixerCurrencyLoader implements CurrencyLoader {
    @Override
    public List<Currency> load() {
        try {
            return toList(loadJson());
        }catch (IOException e){
            return emptyList();
        }
    }

    private List<Currency> toList(String json) {
        List<Currency> currencies = new ArrayList<>();
        Map<String, JsonElement> symbols = new Gson().fromJson(json, JsonObject.class)
                                                        .get("symbols")
                                                        .getAsJsonObject()
                                                        .asMap();
        for (String s : symbols.keySet()) {
            currencies.add(new Currency(s, symbols.get(s).getAsString()));
        }
        return currencies;
    }

    private String loadJson() throws IOException {
        URL url = new URL("http://data.fixer.io/api/symbols?access_key="+FixerAPI.key);
        try (InputStream is = url.openStream()){
            return new String(is.readAllBytes());
        }
    }
}
