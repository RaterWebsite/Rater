from analyzer import Analyzer

# Required information for a review
class Review:

    # Constructor. Performs sentiment analysis on the raw text
    def __init__(self, score, raw_text) -> None:
        self.score = score
        self.raw_text = raw_text

        #sentiment analysis
        a = Analyzer()
        self.sentiments = a.analyze(raw_text)