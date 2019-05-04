package app;

import com.google.gson.annotations.SerializedName;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {


    private String name;

    @SerializedName("detailsLink")
    private String link;

    private String imageLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public static Recipe decodeFromJSON(JSONObject jsonObject) {
        Recipe recipe = new Recipe();
        try {
            recipe.name = jsonObject.getString("name");
            recipe.link = jsonObject.getString("link");
            recipe.imageLink = jsonObject.getString("imageLink");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipe;
    }
}
