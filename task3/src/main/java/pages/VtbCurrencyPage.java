package pages;

import helpers.PageUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VtbCurrencyPage implements CurrencyPage, Page {

    @FindBy(xpath = "(//div[@class='base-table__toggler_item']/span)[1]")
    private WebElement dollarBuyCurrency;
    @FindBy(xpath = "(//div[@class='base-table__toggler_item']/span)[2]")
    private WebElement dollarSellCurrency;
    @FindBy(xpath = "(//div[@class='base-table__toggler_item']/span)[3]")
    private WebElement euroBuyCurrency;
    @FindBy(xpath = "(//div[@class='base-table__toggler_item']/span)[4]")
    private WebElement euroSellCurrency;

    public VtbCurrencyPage() {
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
        javascriptExecutor.executeScript(scrollElementIntoMiddle, dollarBuyCurrency);
    }

    @Override
    public boolean isPageLoaded() {
        return PageUtils.isElementTextContains(dollarBuyCurrency, ",");
    }
}
