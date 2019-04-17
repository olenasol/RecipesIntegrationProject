package app;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    public DataFetcher<List<Recipe>> getRecipes() {
        RecipeDAL dal =RecipeDAL.newInstance();
        return dataFetchingEnvironment -> dal.getRecipes();
    }
}
