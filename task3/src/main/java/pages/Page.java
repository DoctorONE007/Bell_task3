package pages;

import driver.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

public interface Page {
    boolean isPageLoaded();

    default void initPage() {
        WebDriver driver = WebDriverManager.getCurrentDriver();
        try {
            PageFactory.initElements(driver, this);
            if (!isPageLoaded()) {
                throw new RuntimeException("Страница" + this.getClass().getSimpleName() + "не загружена");
            }
        } catch (WebDriverException e) {
            throw new RuntimeException("Ошибка при загрузки страницы " + this.getClass().getSimpleName() + "\n" + e.toString());
        }
    }
}
