package pages;

import helpers.PageUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class AlfaCurrencyPage implements CurrencyPage, Page {

    @FindBy(xpath = "//tbody/tr[1]/td[@align='right'][1]")
    private WebElement dollarBuyCurrency;
    @FindBy(xpath = "//tbody/tr[1]/td[@align='right'][1]")
    private WebElement dollarSellCurrency;
    @FindBy(xpath = "//tbody/tr[1]/td[@align='right'][1]")
    private WebElement euroBuyCurrency;
    @FindBy(xpath = "//tbody/tr[2]/td[@align='right'][1]")
    private WebElement euroSellCurrency;
    @FindBy(xpath = "//button[@data-test-id='currency-EUR']")
    private WebElement euroButton;
    @FindBy(xpath = "//button[@data-test-id='currency-USD']")
    private WebElement usdButton;
    @FindBy(xpath = "//table//th[2]")
    private WebElement dateLabel;


    public AlfaCurrencyPage() {
        initPage();
    }

    @Override
    public Double getCurrency(String currencyName, String operationType) {
        isPageLoaded();
        if (currencyName.equals("USD")) {
            if (operationType.equals("SELL")) {
                return Double.parseDouble(dollarSellCurrency.getText().split("₽")[0].replace(",", "."));
            }
            usdButton.click();
            return Double.parseDouble(dollarBuyCurrency.getText().split("₽")[0].replace(",", "."));
        }
        if (currencyName.equals("EUR")) {
            if (operationType.equals("SELL")) {
                return Double.parseDouble(euroSellCurrency.getText().split("₽")[0].replace(",", "."));
            }
            euroButton.click();
            return Double.parseDouble(euroBuyCurrency.getText().split("₽")[0].replace(",", "."));
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
        return PageUtils.isElementTextContains(dateLabel, "2021");
    }

}
