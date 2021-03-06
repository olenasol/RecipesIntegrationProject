import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class RecipeService {

    private WebDriver driver;

    void invokeBrowser(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\olena\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
    }
    List<Recipe> getRecipes(int pageId) {
        List<Recipe> listOfRecipes = new ArrayList<>();
        driver.get("https://www.smachno.in.ua/index.php?page=" + pageId);
        System.out.println(driver.getTitle());

        List<WebElement> list = driver.findElements(By.className("mini_table"));
        for (WebElement element : list) {
            Recipe recipe = new Recipe();
            List<WebElement> childLinks = element.findElements(By.tagName("a"));
            recipe.setLink(childLinks.get(0).getAttribute("href"));
            recipe.setImageLink(childLinks.get(0).findElement(By.tagName("img")).getAttribute("src"));
            recipe.setName(childLinks.get(1).getText());
            listOfRecipes.add(recipe);
        }
        return listOfRecipes;
    }

}
