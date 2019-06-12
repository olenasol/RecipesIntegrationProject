import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import ApolloClient from "apollo-boost";
import { gql } from "apollo-boost";
import { ApolloProvider } from "react-apollo";
import { Query } from "react-apollo";
import { Mutation } from "react-apollo";

const client = new ApolloClient({
  uri: " http://5e8c0865.ngrok.io/graphql"
});



const RecipesList = () => (
  <Query
    query={gql`
      {
       recipes{
         	name,
         	link,
         	imageLink
         }
      }
    `}
  >
    {({ loading, error, data }) => {
      if (loading) return <p>Loading...</p>;
      if (error) return <p>Error :(</p>;

      return data.recipes.map(({ name,link,imageLink }) => (
        <div>
          <img src={imageLink} width="300" height="200"/>
          <a href={link}>{name}</a>
        </div>
      ));
    }}
  </Query>
);  
const App = () => (
  <ApolloProvider client={client}>
    <div>
      <h2>Recipes</h2>
    </div>
    <RecipesList/>
  </ApolloProvider>
);

ReactDOM.render(<App />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
