package app;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLRootResolver;
import org.springframework.stereotype.Component;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    public Integer likeRecipe(String detailsLink) {
        return RecipeDAL.newInstance().addLike(detailsLink);
    }
}
