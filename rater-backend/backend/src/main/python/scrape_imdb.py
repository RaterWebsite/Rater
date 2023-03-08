from bs4 import BeautifulSoup
import requests

def getMovieTitles():
    print("Scraping movie titles.")
    url = 'http://www.imdb.com/chart/top'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    movies = soup.select('td.titleColumn')
    
    movie_titles = []
    for index in range(0, len(movies)):
        movie_string = movies[index].get_text()
        movie = (' '.join(movie_string.split()).replace('.', ''))
        movie_title = movie[len(str(index))+1:-7]
        movie_titles.append(movie_title)
    return movie_titles

if __name__ == "__main__":
    print("Heres a list of the top 250 movies according to imdb: ")
    titles = getMovieTitles()
    for idx, title in enumerate(titles):
        print(f"{idx + 1}: {title}")
