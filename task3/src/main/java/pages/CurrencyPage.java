package pages;

public interface CurrencyPage {
    Double getCurrency(String currencyName, String operationType);

    void preActions();
}
