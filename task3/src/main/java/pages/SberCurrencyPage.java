package pages;

import helpers.PageUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SberCurrencyPage implements CurrencyPage, Page {

    @FindBy(xpath = "//div[@data-id = 'sbol']//table[@class = 'rates-table-component']//tr[2]//div[contains(.,'Доллар США') " +
            "and @aria-hidden = 'true']/parent::*/following-sibling::td[2]")
    private WebElement dollarBuyCurrency;
    @FindBy(xpath = "//div[@data-id = 'sbol']//table[@class = 'rates-table-component']//tr[2]//div[contains(.,'Доллар США') " +
            "and @aria-hidden = 'true']/parent::*/following-sibling::td[3]")
    private WebElement dollarSellCurrency;
    @FindBy(xpath = "//div[@data-id = 'sbol']//table[@class = 'rates-table-component']//tr[7]//div[contains(.,'Евро') " +
            "and @aria-hidden = 'true']/parent::*/following-sibling::td[2]")
    private WebElement euroBuyCurrency;
    @FindBy(xpath = "//div[@data-id = 'sbol']//table[@class = 'rates-table-component']//tr[7]//div[contains(.,'Евро') " +
            "and @aria-hidden = 'true']/parent::*/following-sibling::td[3]")
    private WebElement euroSellCurrency;

    public SberCurrencyPage() {
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
