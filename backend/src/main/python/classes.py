import json
from dataclasses import dataclass
from typing import List

@dataclass
class Review:
    rating: float
    text: str

# This is hardcoded, should probably be a datclass Category: name: str, rating: float
@dataclass
class Categories:
    plot: float = 5.0
    acting: float = 5.0
    ending: float = 5.0
    soundtrack: float = 5.0
    cinematography: float = 5.0
    familyFriendly: float = 5.0
    funny: float = 5.0
    action: float = 5.0

@dataclass
class Movie:
    id: int = None
    title: str = None
    starring: List[str] = None
    runtime: int = None
    genre: List[str] = None
    description: str = None
    releaseYear: int = None
    mpaaRating: str = None
    imageUrl: str = None
    categories: Categories = None
    reviews: List[Review] = None
    
    def json(self):
        return json.dumps(self.__dict__, default=lambda o: o.__dict__, indent=2)

if __name__ == "__main__":
    reviews = [
        Review(5, "Perfect 5/7"), 
        Review(3, "I dont get the reference"), 
        Review(10, "Robocop")
    ]

    data = {
    "id": 1,
    "title": "Foobar Strikes Back",
    "starring": ["Foo", "Bar", "Baz"],
    "runtime": 420,
    "genre": ["Coding", "Dummy"],
    "description": "Dummy data for a dumby movie.",
    "releaseYear": 2069,
    "mpaaRating": "Not Rated",
    "imageUrl": "localhost.jpg",
    "reviews" : reviews
    }
    movie_data = Movie(**data)
    #movie_data.reviews = reviews
    print(movie_data.json())