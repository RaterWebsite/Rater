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
        l = [review.sentiments for review in reviews]
        df = pd.concat(l, axis=1).transpose()
        df["score"] = [int(review.score) for review in reviews]
        self.raw_sentiments = pd.concat([self.raw_sentiments, df], ignore_index=True)

    # Scores the movie
    def calculate_score(self):
        final_scores = {}
        # Take a weighted average of the scores based on the number of mentions in each review
        for column in self.raw_sentiments.drop(columns="score").columns:

            # Default to 5 if no reviews include categories
            if np.sum(self.raw_sentiments[column]) == 0:
                val = 5
            else:
                val = round(np.average(self.raw_sentiments["score"].to_numpy(), weights=self.raw_sentiments[column].to_numpy()), 1)
            final_scores[column] = val
        self.scores = final_scores

    # Print a json string
    def to_json(self):
      self.calculate_score()
      categories = {}
      for category in self.scores.keys():
          categories[category] = self.scores[category]
      return json.dumps(categories)


s = Sentiments(json_to_reviews("rater-backend/backend/src/main/resources/static/movies/movie1.json"))
s.to_json()