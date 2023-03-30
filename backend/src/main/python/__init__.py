
from classes import Review, Categories, Movie
from scrape_imdb import getMovieTitles
from api_themoviedb import getMovieData
from scrape_metacritic import getMovieReviews
from collect import collect, serialize
from analyze import analyze, capFamilyFriendly
import csv

def read_csv(filename):
    with open(filename, "r") as fileIn:
        csv_reader = csv.reader(fileIn, delimiter=',')
        output = []
        for row in csv_reader:
            output.append(row[0])
    return output

if __name__=="__main__":
    movies = collect()
    movies.extend(collect(read_csv("movies.csv"), 250))
    print("Analyzing...")
    for movie in movies:
        movie.categories = analyze(movie.reviews)
        capFamilyFriendly(movie)
    print("Serializing...")
    serialize(movies)
    print("Done.")