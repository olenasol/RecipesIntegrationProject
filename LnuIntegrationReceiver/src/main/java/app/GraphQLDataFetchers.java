package app;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:8080")
@Component
public class GraphQLDataFetchers {

    public DataFetcher<List<Recipe>> getRecipes() {
        RecipeDAL dal =RecipeDAL.newInstance();
        return dataFetchingEnvironment -> dal.getRecipes();
    }
}
