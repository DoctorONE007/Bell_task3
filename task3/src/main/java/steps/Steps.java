package steps;

import driver.WebDriverManager;
import helpers.AllureEdit;
import helpers.CustomUtils;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.CurrencyPage;
import pages.YandexMarketHomePage;
import pages.YandexMarketResultsPage;

import java.util.*;


public class Steps {
    private final WebDriver driver;


    public Steps() {
        this.driver = WebDriverManager.getCurrentDriver();
    }

    @Step("Перейти на страницу {url}")
    public void goPage(String url) {
        driver.get(url);
        AllureEdit.setLastStepName("Перейти на страницу " + driver.getTitle());
        //Assertions.assertTrue(driver.getTitle().contains(url));
    }

    @Step("Получить курс валюты {currencyName} по операции {operationType}")
    public Double getCurrency(String currencyName, String operationType, CurrencyPage page) {
        AllureEdit.deleteLastStepParameter("page");
        page.preActions();
        CustomUtils.getScreen(driver);
        return page.getCurrency(currencyName, operationType);
    }


    @Step("Определение лучшего курса")
    public void getMostAndLessProfitableCorrency(Map<String, Map<String, Double>> currencies) {
        TreeSet<String> sellUSD = new TreeSet<>();
        TreeSet<String> sellEUR = new TreeSet<>();
        TreeSet<String> buyUSD = new TreeSet<>();
        TreeSet<String> buyEUR = new TreeSet<>();
        List<TreeSet<String>> list = new ArrayList<>();
        list.add(sellUSD);
        list.add(sellEUR);
        list.add(buyUSD);
        list.add(buyEUR);

        for (Map.Entry<String, Map<String, Double>> entry : currencies.entrySet()) {
            for (Map.Entry<String, Double> element : entry.getValue().entrySet()) {
                if (element.getKey().equals("SELL-USD"))
                    sellUSD.add(element.getValue().toString() + " " + entry.getKey());
                if (element.getKey().equals("SELL-EUR"))
                    sellEUR.add(element.getValue().toString() + " " + entry.getKey());
                if (element.getKey().equals("BUY-USD"))
                    buyUSD.add(element.getValue().toString() + " " + entry.getKey());
                if (element.getKey().equals("BUY-EUR"))
                    buyEUR.add(element.getValue().toString() + " " + entry.getKey());

            }
        }

        System.out.println("Самый выгодный курс для продажи доллара - " + sellUSD.last());
        System.out.println("Самый выгодный курс для продажи евро - " + sellEUR.last());
        System.out.println("Самый выгодный курс для покупки доллара - " + buyUSD.first());
        System.out.println("Самый выгодный курс для покупки евро - " + buyEUR.first());


        checkDifference(list);
        checkDollarRate(buyUSD, 71.2);


    }

    @Step("Cравнение, что разница самого выгодного курса не превышает 1 руб/у.е. по сравнению с самым невыгодным")
    public void checkDifference(List<TreeSet<String>> list) {
        boolean flag = true;
        for (TreeSet<String> set : list)
            if (!isMoreThenOne(set)) {
                flag = false;
                AllureEdit.setLastStepStatusFailed();
            }
        Assertions.assertTrue(flag, "Ошибка! В процессе теста были ошибки. Подробности в отчёте");
    }

    @Step("Проверка разности курсов")
    public boolean isMoreThenOne(TreeSet<String> input) {
        if ((Double.parseDouble(input.last().split(" ")[0]) - Double.parseDouble(input.first().split(" ")[0])) > 1) {
            AllureEdit.setLastStepStatusFailed();
            return false;
        }
        return true;
    }

    @Step("Cравнение, что на сайтах всех банков курс покупки банком доллара не превышает {num}")
    public void checkDollarRate(TreeSet<String> set, double num) {
        boolean flag = true;
        for (String rate : set)
            if (Double.parseDouble(rate.split(" ")[0]) >= num) {
                flag = false;
                AllureEdit.setLastStepStatusFailed();
            }
        Assertions.assertTrue(flag, "Ошибка! В процессе теста были ошибки. Подробности в отчёте");
    }

    @Step("Кликнуть по кнопке Каталог")
    public void clickCatalog(YandexMarketHomePage page) {
        page.preActions();
        page.clickCatalog();
    }

    @Step("Навести на меню {menu}")
    public void hoverOnMenu(YandexMarketHomePage page, String menu) {
        page.hoverOnMenu(menu);
    }

    @Step("Кликнуть на  подменю {submMenu}")
    public void clickOnSubMenu(YandexMarketHomePage page, String subMenu) {
        page.clickOnSubMenu(subMenu);
    }

    @Step("Поиск {name}")
    public void find(YandexMarketResultsPage page, String name) {
        clickShowAll(page);
        search(page, name);
        clickCheckBoxes(page, name);
        sortLowPrice(page);
    }

    @Step("Нажать кнопку показать все")
    public void clickShowAll(YandexMarketResultsPage page) {
        page.showAllClick();

    }

    @Step("Ввод в поиска {name}")
    public void search(YandexMarketResultsPage page, String name) {
        page.search(name);

    }

    @Step("Отметить все checkboxes содержащие {name}")
    public void clickCheckBoxes(YandexMarketResultsPage page, String name) {
        page.checkBoxesClick(name);

    }

    @Step("Нажать кнопку свернуть")
    public void rollAllClick(YandexMarketResultsPage page) {
        page.rollAllClick();

    }

    @Step("Нажать кнопку по цене")
    public void sortLowPrice(YandexMarketResultsPage page) {
        page.sortLowPrice();
    }

    @Step("Сохранить минимальную стоимость")
    public double getPrice(YandexMarketResultsPage page, String name) {
        double res = page.getPrice(name);
        clickCheckBoxes(page, name);
        rollAllClick(page);
        sortPopular(page);
        return res;
    }

    @Step("Нажать кнопку по популярности")
    public void sortPopular(YandexMarketResultsPage page) {
        page.sortPopular();
    }

    @Step("Сравнение цен товаров")
    public void priceCompare(Map<String, Double> prices, String name1, String name2) {
        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            System.out.println(entry.getKey() + " цена - " + entry.getValue());
            Assertions.assertTrue(prices.get(name1) < prices.get(name2), "Цена " + name1 + " не меньше цены " + name2);

        }
    }
}
