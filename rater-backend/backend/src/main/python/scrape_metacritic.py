import requests
from bs4 import BeautifulSoup
from classes import Review

def _get_rating(movie_tag):
    return movie_tag.select(".metascore_w").pop().text

def _get_text(movie_tag):
    review_tags = movie_tag.select(".blurb_expanded")
    if not review_tags:
            review_tags = movie_tag.select(".review_body span")
    if not review_tags:
        return ""
    text = review_tags[0].text.replace("\n", "").strip()
    return text

def getMovieReviews(movie_title):
    # Set header so that you don't get rejected as a bot
    headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36"}
    # Make the title match the search request
    movie_title = movie_title.strip().lower().replace(' ', '-').replace(':',"")
    url = f"https://www.metacritic.com/movie/{movie_title}/user-reviews"
    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")
    tags = soup.select(".review")
    # Make a list of movie reviews
    reviews = list()
    for tag in tags[:50]:
        review = Review(_get_rating(tag), _get_text(tag))
        reviews.append(review)
    return reviews 

if __name__ == "__main__":
    movie_title = "The Godfather" 
    reviews = getMovieReviews(movie_title)
    for review in reviews[:3]:
        print(review)
    