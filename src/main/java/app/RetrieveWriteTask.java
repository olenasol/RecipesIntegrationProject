package app;

import java.util.List;
import java.util.TimerTask;

public class RetrieveWriteTask extends TimerTask {

    private static int pageNumber = 1;

    private List<Recipe> retrieveRecipes(){
        RecipeService recipeService = new RecipeService();
        recipeService.invokeBrowser();
        List<Recipe> listOfRecipes = recipeService.getRecipes(pageNumber);
        pageNumber++;
        return listOfRecipes;
    }

    public void run() {
        List<Recipe> list = retrieveRecipes();
        RecipeDAL dal = RecipeDAL.newInstance();
        dal.insertRecipes(list);
        System.out.println("Writtem to db "+pageNumber);
    }
}
