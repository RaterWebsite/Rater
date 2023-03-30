import re
from time import sleep
from scrape_imdb import getMovieTitles
from api_themoviedb import getMovieData
from scrape_metacritic import getMovieReviews

def collect(titles=None, offset=0):
    titles = titles or getMovieTitles()
    movies = list()
    id = offset
    print(f"Collecting data for {len(titles)} movies.")
    for idx, title in enumerate(titles):
        sleep(.125)
        try:
            movie_data = getMovieData(title)
            reviews = getMovieReviews(title)
            if reviews:
                id += 1
                movie_data.id = id
                movie_data.reviews = reviews
                movies.append(movie_data)
                print(f"{idx + offset + 1}/{len(titles)}", end="\r")
        except Exception: pass # This is a pretty bad idea
    print(f"\nSuccesfully scraped {id} movies.")
    return movies

import os
def serialize(movies, dir="./documents"):
    if not os.path.exists(dir):
        os.makedirs(dir)
    for movie in movies:
        filename = make_filename(movie.title, "json")
        with open(f"./{dir}/{filename}", "w") as file:
            file.write(movie.json())

import re
def make_filename(string, extension="json"):
    # Replace any non-alphanumeric characters (except underscore) with an empty string
    filename = re.sub(r'[^\w\s-]', '', string)
    # Replace any spaces or dashes with underscores
    filename = re.sub(r'[-\s]+', '_', filename)
    return f"{filename.lower()}.{extension}"

if __name__ == "__main__":
    print("Collecting...")
    movies = collect()
    print("Collected.")
    for movie in movies:
        print(f"{movie.id}: {movie.title}")