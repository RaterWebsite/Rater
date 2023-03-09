from classes import Categories
import json

thesaurus = json.load(open("./Synonyms.json"))

def analyze(reviews):
    categories = Categories()
    for category, synonyms in thesaurus.items():
        score, count = 0, 0
        for review in reviews:                                  # for each review
            try:
                for word in review.text.split():                # for each word in the review
                    for synonym in synonyms:                    # for each related synonym
                        if word == synonym:                     # if word matches category/synonyms
                            count += 1                          # add to count
                            score += float(review.rating)       # add to score
            except ValueError as error:
                print(error)  # ignore misformatted reviews
        if count:
            setattr(categories, category, score / count)
    return categories


if __name__ == "__main__":
    from scrape_metacritic import getMovieReviews
    reviews = getMovieReviews("Whiplash")
    print(analyze(reviews))
