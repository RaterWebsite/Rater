
from classes import Review, Categories, Movie
from scrape_imdb import getMovieTitles
from api_themoviedb import getMovieData
from scrape_metacritic import getMovieReviews
from collect import collect, serialize
from analyze import analyze, capFamilyFriendly
import csv

def read_csv(filename):
    with open(filename, newline='', encoding='utf-8') as file:
        reader = csv.DictReader(file)
        titles = [row['original_title'] for row in reader]
    return titles

if __name__=="__main__":
    titles = read_csv("LotOfMovies.csv")
    # This sucker is huge idk if I have the RAM for that
    # Break it up in chunks of 250
    step = 250
    for i in range(0, len(titles), step):
        sublist = titles[i:i+step]
        movies = collect(sublist, i + 1) # '+ 1' paranoid for repeating id's
        print("Analyzing...")
        for movie in movies:
            movie.categories = analyze(movie.reviews)
            capFamilyFriendly(movie)
        print("Serializing...")
        serialize(movies)
    print("Done.")
    