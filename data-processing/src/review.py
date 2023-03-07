from analyzer import Analyzer

# Required information for a review
class Review:

    def __init__(self, movie_id, score, raw_text) -> None:
        self.movie_id = movie_id
        self.score = score
        self.raw_text = raw_text

        #sentiment analysis
        a = Analyzer()
        self.sentiments = a.analyze(raw_text)