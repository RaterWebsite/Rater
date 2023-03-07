from review import Review
from averagesentiments import average_sentiments
import pandas as pd
import numpy as np
from json_to_review import json_to_reviews

class Sentiments:

    def __init__(self, data: list[Review]) -> None:
        l = [review.sentiments for review in data]
        df = pd.concat(l, axis=1).transpose()
        df["score"] = [int(review.score) for review in data]

        final_scores = {}

        # Take a weighted average of the scores based on the number of mentions in each review
        for column in df.drop(columns="score").columns:

            # Default to 5 if no reviews include categories
            if np.sum(df[column]) == 0:
                val = 5
            else:
                val = round(np.average(df["score"].to_numpy(), weights=df[column].to_numpy()), 1)
            final_scores[column] = val
        self.scores = final_scores