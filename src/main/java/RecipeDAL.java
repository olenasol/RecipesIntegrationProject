import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RecipeDAL {

    private MongoCollection<Document> recipesCollection;
    private static RecipeDAL instance;

    public static RecipeDAL newInstance(){
        if(instance == null){
            instance = new RecipeDAL();
        }
        return instance;
    }

    private RecipeDAL(){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("recipes");
        recipesCollection =  database.getCollection("recipes");
        recipesCollection.drop();
        database.createCollection("recipes",
                new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));
        recipesCollection =  database.getCollection("recipes");
    }

    public void insertRecipes(List<Recipe> listOfRecipes){
        List<Document> listOfDocs = new ArrayList<Document>();
        for (Recipe recipe:listOfRecipes){
            Document document = new Document("name",recipe.getName())
                    .append("imageLink",recipe.getImageLink())
                    .append("detailsLink",recipe.getLink());
            listOfDocs.add(document);
        }
        recipesCollection.insertMany(listOfDocs);
    }
}
