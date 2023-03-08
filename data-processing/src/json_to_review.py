from review import Review
import json

# Converts a json file into a list of reviews
def json_to_reviews(file_path: str):
    file  = open(file_path, "r")
    extracted = json.load(file)
    reviews = []
    for x in extracted["reviews"]:
        reviews.append(Review(x["rating"], x["text"]))
    
    return reviews