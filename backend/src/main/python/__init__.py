
from classes import Review, Categories, Movie
from scrape_imdb import getMovieTitles
from api_themoviedb import getMovieData
from scrape_metacritic import getMovieReviews
from collect import collect, serialize
from analyze import analyze

if __name__=="__main__":
    movies = collect()
    print("Analyzing...")
    for movie in movies:
        movie.categories = analyze(movie.reviews)
    print("Serializing...")
    serialize(movies)
    print("Done.")