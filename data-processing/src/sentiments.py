from review import Review
from averagesentiments import average_sentiments
import pandas as pd
import numpy as np
import json
from json_to_review import json_to_reviews

# Keeps track of the sentiments extracted from reviews of a particular movie
class Movie:
    
    # Constructor for a known movie but no known reviews
    def __init__(self, id) -> None:
        self.sentiments = pd.DataFrame()
        self.id = id

    # Constructor for a set of reviews
    def __init__(self, reviews: list[Review]) -> None:
        self.sentiments = pd.DataFrame()
        self.id = reviews[0].movie_id
        self.add_reviews(reviews)
    
    # Adds the sentiments of multiple reviews
    def add_reviews(self, reviews: list[Review]) -> None:
        l = [review.sentiments for review in reviews]
        df = pd.concat(l, axis=1).transpose()
        df["score"] = [int(review.score) for review in reviews]
        self.sentiments = pd.concat([self.sentiments, df], ignore_index=True)

    # Scores the movie
    def calculate_score(self):
        final_scores = {}
        # Take a weighted average of the scores based on the number of mentions in each review
        for column in self.sentiments.drop(columns="score").columns:

            # Default to 5 if no reviews include categories
            if np.sum(self.sentiments[column]) == 0:
                val = 5
            else:
                val = round(np.average(self.sentiments["score"].to_numpy(), weights=self.sentiments[column].to_numpy()), 1)
            final_scores[column] = val
        self.scores = final_scores

    # Print a json string
    def to_json(self):
      categories = {}
      for category in self.scores.keys():
          categories[category] = self.scores[category]
      print(json.dumps(categories))  


s = Movie(json_to_reviews("rater-backend/backend/src/main/resources/static/movies/movie1.json"))
s.calculate_score()
s.to_json()