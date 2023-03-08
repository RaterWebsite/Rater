from review import Review
import pandas as pd
import numpy as np
import json
from json_to_review import json_to_reviews

# Keeps track of the sentiments extracted from reviews of a particular movie
class Sentiments:
    
    # Constructor for a known movie but no known reviews
    def __init__(self) -> None:
        self.raw_sentiments = pd.DataFrame()
        
    # Constructor for a set of reviews
    def __init__(self, reviews: list[Review]) -> None:
        self.raw_sentiments = pd.DataFrame()
        self.add_reviews(reviews)
    
    # Adds the sentiments of multiple reviews
    def add_reviews(self, reviews: list[Review]) -> None:
        # Extract the sentiments from the reviews
        sentiments = [review.sentiments for review in reviews]
        
        # Transform to a dataframe
        sentiments = pd.concat(sentiments, axis=1).transpose()
        sentiments["score"] = [int(review.score) for review in reviews]

        # Append to existing data
        self.raw_sentiments = pd.concat([self.raw_sentiments, sentiments], ignore_index=True)

    # Scores the movie
    def __calculate_score(self):
        final_scores = {}
        # Take a weighted average of the scores based on the number of mentions in each review
        for column in self.raw_sentiments.drop(columns="score").columns:

            # Default to 5 if no reviews includes that category
            if np.sum(self.raw_sentiments[column]) == 0:
                val = 5
            else:
                val = round(np.average(self.raw_sentiments["score"].to_numpy(), weights=self.raw_sentiments[column].to_numpy()), 1)
            final_scores[column] = val
        self.scores = final_scores

    # Creates a json string representing this object
    def to_json(self) -> str:
      self.__calculate_score()
      categories = {}
      for category in self.scores.keys():
          categories[category] = self.scores[category]
      return json.dumps(categories)


s = Sentiments(json_to_reviews("rater-backend/backend/src/main/resources/static/movies/movie1.json"))
print(s.to_json())