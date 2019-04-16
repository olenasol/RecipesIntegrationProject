import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        RecipeService myclass = new RecipeService();
        myclass.invokeBrowser();
        List<Recipe> listOfRecipes = myclass.getRecipes(7);
        for (Recipe recipe : listOfRecipes) {
            System.out.println(recipe.getName());
            System.out.println(recipe.getLink());
            System.out.println(recipe.getImageLink());
        }
    }
}
