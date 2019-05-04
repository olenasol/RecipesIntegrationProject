import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONArray;

import java.util.List;
import java.util.TimerTask;

public class RetrieveSendTask extends TimerTask {

    private static int pageNumber = 1;
    private static final String QUEUE_NAME = "recipe_list";

    private List<Recipe> retrieveRecipes(){
        RecipeService recipeService = new RecipeService();
        recipeService.invokeBrowser();
        List<Recipe> listOfRecipes = recipeService.getRecipes(pageNumber);
        pageNumber++;
        return listOfRecipes;
    }

    public void run() {
        List<Recipe> list = retrieveRecipes();
        JSONArray jsonArray = new JSONArray();
        for(Recipe recipe:list){
            jsonArray.put(recipe.encodeToJSON());
        }
        sendMessage(jsonArray.toString());
    }

    private void sendMessage(String string){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, string.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + string + "'");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
