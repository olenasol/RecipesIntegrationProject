import app.GraphQLDataFetchers;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class MainClass {

    public static void main(String[] args) {

        URL url = Resources.getResource("schema.graphqls");
        String sdl = "";
        try {
            sdl = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(sdl);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("recipes", new GraphQLDataFetchers().getRecipes()))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{ recipes { name link imageLink } }");

        System.out.println(executionResult.getData().toString());
//        RecipeDAL dal =RecipeDAL.newInstance();
//        for (Recipe recipe:dal.getRecipes()){
//            System.out.println(recipe.getName());
//            System.out.println(recipe.getLink());
//            System.out.println(recipe.getImageLink());
//        }

    }

    private static GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private static RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("recipes", (new GraphQLDataFetchers()).getRecipes()))
                .build();
    }
}
