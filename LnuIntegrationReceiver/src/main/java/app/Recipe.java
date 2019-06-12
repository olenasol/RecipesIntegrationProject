package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {


    private String name;

    @SerializedName("detailsLink")
    private String link;

    private String imageLink;

    private int numberOfLikes;
    @JsonIgnore
    private ObjectId _id;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

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

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
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
