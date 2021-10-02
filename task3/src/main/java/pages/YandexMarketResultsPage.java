package pages;

import helpers.PageUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YandexMarketResultsPage implements Page {
    @FindBy(xpath = "//legend[contains(text(),'Название видеокарты' )]/..//button[contains(text(),'Показать всё' )]")
    private WebElement showAllButton;
    @FindBy(xpath = "//legend[contains(text(),'Название видеокарты' )]/..//button[contains(text(),'Свернуть' )]")
    private WebElement rollAllButton;
    @FindBy(xpath = "//button[@data-autotest-id='dprice']")
    private WebElement sortLowPriceButton;
    @FindBy(xpath = "//button[@data-autotest-id='dpop']")
    private WebElement sortPopular;
    @FindBy(xpath = "//div[@data-zone-name='price']//span/span[1]")
    private List<WebElement> price;
    @FindBy(xpath = "//h3")
    private List<WebElement> product;
    @FindBy(xpath = "//legend[contains(text(),'Название видеокарты' )]/..//input[@type='text']")
    private WebElement searchField;
    @FindBy(xpath = "(//legend[contains(text(),'Название видеокарты' )]/..//input[@type='checkbox'])[1]/..//span")
    private WebElement firstSearch;
    @FindBy(xpath = "//legend[contains(text(),'Название видеокарты' )]/..//input[@type='checkbox']/../div")
    private List<WebElement> checkBoxesList;

    public YandexMarketResultsPage() {
        initPage();
    }

    public void showAllClick() {
        PageUtils.waitUntilElementBeClickable(showAllButton);
        showAllButton.click();
    }

    public void search(String name) {
        PageUtils.waitUntilElementBeClickable(searchField);
        searchField.click();
        searchField.clear();
        searchField.sendKeys(name);
    }

    public void checkBoxesClick(String name) {
        if (PageUtils.isElementTextContains(firstSearch, name))
            for (WebElement element : checkBoxesList) {
                PageUtils.waitUntilElementTextContains(element.findElement(By.xpath("./..//span")), name);
                element.click();
            }
    }

    public void rollAllClick() {
        PageUtils.waitUntilElementBeClickable(rollAllButton);
        rollAllButton.click();
    }

    public void sortLowPrice() {
        PageUtils.waitUntilElementBeClickable(sortLowPriceButton);
        sortLowPriceButton.click();
    }

    public void sortPopular() {
        PageUtils.waitUntilElementBeClickable(sortPopular);
        sortPopular.click();
    }

    public double getPrice(String name) {

        PageUtils.isElementTextContains(product.get(0), name);
        return Integer.parseInt(price.get(0).getText().replaceAll("[^0-9]", ""));
    }

    @Override
    public boolean isPageLoaded() {
        return PageUtils.isClickable(showAllButton);
    }

    public void preActions() {
    }


}
