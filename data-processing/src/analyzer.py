import json

class Analyzer:
    def __init__(self) -> None:
        self.thesaurus = json.load(open("data-processing\Synonyms.json", "r"))
            
    def analyze(self, text: str):
        sentiments = {}

        # Initialize the count of each sentiment to zero
        for key in self.thesaurus.keys():
            sentiments[key] = 0
        
        for word in text.split():
            for category in self.thesaurus.keys():
                for synonym in self.thesaurus[category]:
                    if word == synonym:
                        sentiments[category] += 1