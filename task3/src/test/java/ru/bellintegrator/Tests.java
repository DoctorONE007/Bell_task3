package ru.bellintegrator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.*;
import steps.Steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tests extends BaseTests {
    Steps steps;

    @BeforeEach
    public void beforeEach() {
        steps = new Steps();
    }

    @Test
    public void currencyTest() {
        Map<String, Map<String, Double>> currencies = new HashMap<>();

        steps.goPage("https://open.ru");
        OpenCurrencyPage openCurrencyPage = new OpenCurrencyPage();
        currencies.put("Открытие", Map.of("SELL-USD", steps.getCurrency("USD", "SELL", openCurrencyPage)
                , "SELL-EUR", steps.getCurrency("EUR", "SELL", openCurrencyPage)
                , "BUY-USD", steps.getCurrency("USD", "BUY", openCurrencyPage)
                , "BUY-EUR", steps.getCurrency("EUR", "BUY", openCurrencyPage)));

        steps.goPage("https://www.sberbank.ru/ru/quotes/currencies");
        SberCurrencyPage sberCurrencyPage = new SberCurrencyPage();
        currencies.put("Сбербанк", Map.of("SELL-USD", steps.getCurrency("USD", "SELL", sberCurrencyPage)
                , "SELL-EUR", steps.getCurrency("EUR", "SELL", sberCurrencyPage)
                , "BUY-USD", steps.getCurrency("USD", "BUY", sberCurrencyPage)
                , "BUY-EUR", steps.getCurrency("EUR", "BUY", sberCurrencyPage)));

        steps.goPage("https://alfabank.ru/currency/");
        AlfaCurrencyPage alfaCurrencyPage = new AlfaCurrencyPage();
        currencies.put("Альфабанк", Map.of("SELL-USD", steps.getCurrency("USD", "SELL", alfaCurrencyPage)
                , "SELL-EUR", steps.getCurrency("EUR", "SELL", alfaCurrencyPage)
                , "BUY-USD", steps.getCurrency("USD", "BUY", alfaCurrencyPage)
                , "BUY-EUR", steps.getCurrency("EUR", "BUY", alfaCurrencyPage)));

        steps.goPage("https://www.vtb.ru/personal/platezhi-i-perevody/obmen-valjuty/");
        VtbCurrencyPage vtbCurrencyPage = new VtbCurrencyPage();
        currencies.put("Втб", Map.of("SELL-USD", steps.getCurrency("USD", "SELL", vtbCurrencyPage)
                , "SELL-EUR", steps.getCurrency("EUR", "SELL", vtbCurrencyPage)
                , "BUY-USD", steps.getCurrency("USD", "BUY", vtbCurrencyPage)
                , "BUY-EUR", steps.getCurrency("EUR", "BUY", vtbCurrencyPage)));

        steps.getMostAndLessProfitableCorrency(currencies);

    }

    @Test
    public void yandexMarketTest() throws IOException {
        String name1 = "GTX 1060";
        String name2 = "RTX 3060";
        Map<String, Double> prices = new HashMap<>();
        steps.goPage("https://market.yandex.ru/");
        YandexMarketHomePage yandexMarketHomePage = new YandexMarketHomePage();
        steps.clickCatalog(yandexMarketHomePage);
        steps.hoverOnMenu(yandexMarketHomePage, "Компьютеры");
        steps.clickOnSubMenu(yandexMarketHomePage, "Видеокарты");
        YandexMarketResultsPage yandexMarketResultsPage = new YandexMarketResultsPage();
        steps.find(yandexMarketResultsPage, name1);
        prices.put(name1, steps.getPrice(yandexMarketResultsPage, name1));
        steps.find(yandexMarketResultsPage, name2);
        prices.put(name2, steps.getPrice(yandexMarketResultsPage, name2));
        steps.priceCompare(prices, name1, name2);
    }

}
