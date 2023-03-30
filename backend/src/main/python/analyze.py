from classes import Categories
import json

thesaurus = json.load(open("./Synonyms.json"))

def analyze(reviews):
    categories = Categories()
    for category, synonyms in thesaurus.items():
        score, count = 0, 0
        for review in reviews:                              # for each review
            try:
                for word in review.text.split():            # for each word in the review
                    if word in synonyms:                    # if word matches category/synonyms
                        count += 1                          # add to count
                        score += float(review.rating)       # add to score
            except ValueError as error:
                print(error)  # ignore misformatted reviews
        if count:
            setattr(categories, category, score / count)
    return categories

def capFamilyFriendly(movie):
    if not movie.mpaaRating:
        return
    cap = float(10)
    caps = {"G":float(10),"PG":float(8),"PG-13":float(6),"NR":float(4),"R":float(3)}
    if movie.mpaaRating in caps:
        cap = caps[movie.mpaaRating]
    movie.categories.familyFriendly = min(movie.categories.familyFriendly, cap)
    
    



if __name__ == "__main__":
    from scrape_metacritic import getMovieReviews
    reviews = getMovieReviews("Whiplash")
    print(analyze(reviews))
