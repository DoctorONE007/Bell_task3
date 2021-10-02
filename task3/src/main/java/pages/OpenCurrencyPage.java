package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OpenCurrencyPage implements CurrencyPage, Page {

    @FindBy(xpath = "//div[@role = 'tablist']//div[@role = 'tab' and contains(., 'Стандартный')]")
    private WebElement standardCurrencyButton;
    @FindBy(xpath = "(//span[@class = 'main-page-exchange__rate'])[1]")
    private WebElement dollarBuyCurrency;
    @FindBy(xpath = "(//span[@class = 'main-page-exchange__rate'])[2]")
    private WebElement dollarSellCurrency;
    @FindBy(xpath = "(//span[@class = 'main-page-exchange__rate'])[3]")
    private WebElement euroBuyCurrency;
    @FindBy(xpath = "(//span[@class = 'main-page-exchange__rate'])[4]")
    private WebElement euroSellCurrency;


    public OpenCurrencyPage() {
        initPage();
    }

    @Override
    public Double getCurrency(String currencyName, String operationType) {
        if (currencyName.equals("USD")) {
            if (operationType.equals("SELL")) {
                return Double.parseDouble(dollarSellCurrency.getText().replace(",", "."));
            }
            return Double.parseDouble(dollarBuyCurrency.getText().replace(",", "."));
        }
        if (currencyName.equals("EUR")) {
            if (operationType.equals("SELL")) {
                return Double.parseDouble(euroSellCurrency.getText().replace(",", "."));
            }
            return Double.parseDouble(euroBuyCurrency.getText().replace(",", "."));
        }
        return null;
    }

    @Override
    public void preActions() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver.WebDriverManager.getCurrentDriver();
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        javascriptExecutor.executeScript(scrollElementIntoMiddle, euroBuyCurrency);
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }
}
