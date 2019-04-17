package app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecipeService {

    private WebDriver driver;

    public void invokeBrowser(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\olena\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
    }
    public List<Recipe> getRecipes(int pageId) {
        List<Recipe> listOfRecipes = new ArrayList<Recipe>();
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
