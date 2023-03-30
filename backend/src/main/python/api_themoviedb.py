import requests
import json
from classes import Movie

# Define the API endpoint URLs
search_url = 'https://api.themoviedb.org/3/search/movie'
movie_url = 'https://api.themoviedb.org/3/movie/'

# Set the API key
api_key = 'StopStealingSecretesNerd'

def getMovieData(movie_title): 
    # Set the query parameters for the search endpoint
    search_params = {
    'api_key': api_key,
    'query': movie_title
    }
    
    # Send the HTTP GET request to the search endpoint
    search_response = requests.get(search_url, params=search_params)

    # Parse the JSON response
    search_data = json.loads(search_response.text)

    # Extract the movie ID from the search results
    movie_id = search_data['results'][0]['id']

    # Set the query parameters for the movie details endpoint
    movie_params = {
        'api_key': api_key,
        'append_to_response': 'credits,release_dates'
    }

    # Send the HTTP GET request to the movie details endpoint
    movie_response = requests.get(movie_url + str(movie_id), params=movie_params)

    # Parse the JSON response
    movie_data = json.loads(movie_response.text)

    # Extract the relevant movie information
    title = movie_data['title']
    starring = [cast_member['name'] for cast_member in movie_data['credits']['cast'][:2]]
    runtime = movie_data['runtime']
    genres = [genre['name'] for genre in movie_data['genres']]
    description = movie_data['overview']
    year = movie_data['release_date'][:4]

    # Get the MPAA rating (if available)
    mpaa_rating = None
    for country in movie_data['release_dates']['results']:
        if country['iso_3166_1'] == 'US':
            for certification in country['release_dates']:
                if certification['certification']:
                    mpaa_rating = certification['certification']
                    break
            break

    # Construct the image URL for the movie poster
    poster_path = movie_data['poster_path']
    image_url = f'https://image.tmdb.org/t/p/w500{poster_path}'

    # Map the extracted data to dict
    data = {
        'title': title,
        'starring': starring,
        'runtime': runtime,
        'genre': genres,
        'description': description,
        'releaseYear': year,
        'mpaaRating': mpaa_rating,
        'imageUrl': image_url
    }
    
    return Movie(**data)

if __name__ == "__main__":
    movie_title = "The Godfather"
    data = getMovieData(movie_title)
    print(json.dumps(data.__dict__, indent=2))
