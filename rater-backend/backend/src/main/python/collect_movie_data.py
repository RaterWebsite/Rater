from time import sleep
from api_themoviedb import getMovieData
from scrape_imdb import getMovieTitles
from scrape_metacritic import getMovieReviews

def get_movie_data():
    titles = getMovieTitles()
    movies = list()
    id = 0
    for title in titles:
        sleep(.25)
        try:
            movie_data = getMovieData(title)
            reviews = getMovieReviews(title)
            if reviews:
                id += 1
                movie_data.id = id
                movie_data.reviews = reviews
                movies.append(movie_data)
                print(f"Scraped {id}: {title}")
        except Exception as error:
            print(f"Could not get required data for {title}")
            print(error)
    return movies

if __name__ == "__main__":
    movies = get_movie_data()
    dir_name = "movies"
    for movie in movies:
        file_name = f"movie{movie.id}.json"
        with open(f"{dir_name}/{file_name}", "w") as file:
            file.write(movie.json())