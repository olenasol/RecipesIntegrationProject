package app;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Updates.inc;

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
                    .append("detailsLink",recipe.getLink())
                    .append("numberOfLikes",recipe.getNumberOfLikes());
            listOfDocs.add(document);
        }
        recipesCollection.insertMany(listOfDocs);
    }

    public  int addLike(String recipeLink){
        UpdateResult updateResult = recipesCollection.updateMany(eq("detailsLink", recipeLink), inc("numberOfLikes", 1));
        return (int)(updateResult.getModifiedCount());
    }

    public List<Recipe> getRecipes(){
        List<Recipe> list = new ArrayList<>();
        Block<Document> converterBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                Gson gson = new Gson();
                Recipe recipe = gson.fromJson(document.toJson(),Recipe.class);
                list.add(recipe);
            }
        };
        recipesCollection.find().forEach(converterBlock);
        addLike("https://www.smachno.in.ua/prygotuvannya.php?id=352");
        return list;
    }
}
