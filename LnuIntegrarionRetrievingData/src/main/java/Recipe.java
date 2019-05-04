import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {


    private String name;

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

    public JSONObject encodeToJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("name",name);
            result.put("link",link);
            result.put("imageLink",imageLink);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
