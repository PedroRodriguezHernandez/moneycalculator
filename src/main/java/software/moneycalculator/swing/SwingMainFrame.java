package software.moneycalculator.swing;

import software.moneycalculator.*;
import software.moneycalculator.fixerwebservice.FixerCurrencyLoader;
import software.moneycalculator.fixerwebservice.FixerExchangeRateLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SwingMainFrame extends JFrame {
    private final Map<String,Command> commands = new HashMap<>();
    private final List<Currency> currencies;

    private SwingMoneyDisplay moneyDisplay;
    private SwingCurrencyDialog currencyDialog;
    private SwingMoneyDialog moneyDialog;

    public SwingMainFrame() throws HeadlessException {
        this.setTitle("Money Calculator");
        this.setLocationRelativeTo(null);
        this.setSize(800,600);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.add(createMoneyDialog());
        this.add(createCurrencyDialog());
        this.add(toolbar());
        this.add(createMoneyDisplay());
        this.currencies = new FixerCurrencyLoader().load();
        Command command = createCommand();
        add("exchange money", command);
    }

    private Component toolbar(){
        JButton calculate = new JButton("Calculate");
        calculate.addActionListener(e -> commands.get("exchange money").execute());
        return calculate;
    }

    private Component createMoneyDialog() {
        SwingMoneyDialog swingMoneyDialog = new SwingMoneyDialog();
        this.moneyDialog = swingMoneyDialog;
        return swingMoneyDialog;
    }

    private Component createCurrencyDialog() {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        this.currencyDialog = dialog;
        return dialog;
    }

    private Component createMoneyDisplay() {
        SwingMoneyDisplay swingMoneyDisplay = new SwingMoneyDisplay();
        this.moneyDisplay = swingMoneyDisplay;
        return swingMoneyDisplay;
    }
   private void add(String name, Command command) {
       commands.put(name, command);
   }
    private Command createCommand() {
        return new ExchangeMoneyCommand(
                this.moneyDialog.define(currencies),
                this.currencyDialog.define(currencies),
                new FixerExchangeRateLoader(),
                this.moneyDisplay);
    }
}
