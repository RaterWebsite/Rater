from review import Review
import json

def json_to_reviews(file_path: str):
    file  = open(file_path, "r")
    extracted = json.load(file)
    reviews = []
    id = extracted["id"]
    for x in extracted["reviews"]:
        a = Review(id, x["rating"], x["text"])
        reviews.append(Review(id, x["rating"], x["text"]))
    
    return reviews