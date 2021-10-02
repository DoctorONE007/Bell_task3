package pages;

import helpers.CustomUtils;
import helpers.PageUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YandexMarketHomePage implements Page {

    @FindBy(xpath = "//button//span[contains(text(),'Каталог')]")
    private WebElement catalogButton;
    @FindBy(xpath = "(//div[@data-apiary-widget-name='@MarketNode/HeaderCatalog']//ul)[1]//span")
    private List<WebElement> menuList;
    @FindBy(xpath = "//div[@data-apiary-widget-name='@MarketNode/HeaderCatalog']//ul//div//*[not(self::span)]")
    private List<WebElement> subMenuList;

    public YandexMarketHomePage() {
        initPage();
    }

    public void clickCatalog() {
        PageUtils.waitUntilElementBeClickable(catalogButton);
        catalogButton.click();

    }

    public void hoverOnMenu(String menu) {
        PageUtils.waitUntilElementBeVisible(menuList.get(0));
        boolean isSuccess = false;
        for (WebElement element : menuList)
            if (element.getText().equals(menu)) {
                Actions actions = new Actions(driver.WebDriverManager.getCurrentDriver());
                actions.moveToElement(element).build().perform();
                isSuccess = true;
                break;
            }
        Assertions.assertTrue(isSuccess, "Меню с названием " + menu + " не найдено");
    }

    public void clickOnSubMenu(String subMenu) {
        PageUtils.waitUntilElementBeVisible(subMenuList.get(0));
        boolean isSuccess = false;
        for (WebElement element : subMenuList)
            if (element.getText().equals(subMenu)) {
                element.click();
                isSuccess = true;
                break;
            }
        Assertions.assertTrue(isSuccess, "Поденю с названием " + subMenu + " не найдено");

    }

    @Override
    public boolean isPageLoaded() {
        return PageUtils.isClickable(catalogButton);
    }

    public void preActions() {
    }



}
