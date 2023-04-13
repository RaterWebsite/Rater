from gensim.parsing.preprocessing import preprocess_string, STOPWORDS
from gensim.models import Word2Vec, KeyedVectors
import json
import numpy as np
import pandas as pd
import logging

logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
logging.info("Program Start")

class embedder:

    def __init__(self, vector_embeddings: str) -> None:
        self.wv = KeyedVectors.load_word2vec_format("backend/src/main/python/word2vec/model/vectors.kv")

    def embed_movie(self, movie_file: str):
        movie = json.load(open(movie_file))
        embedding = np.zeros(100)
        for review in movie["reviews"]:
            text = review["text"]
            embedding += self.__embed_review(text)
        print(self.wv.most_similar(positive=embedding))
    
    def __embed_review(self, review: str):
        review = preprocess_string(review)
        embedding = np.zeros(100)
        for word in review:
            if self.wv.has_index_for(word):
                embedding += self.wv.get_vector(word)
        return embedding


e = embedder("backend/src/main/python/word2vec/model/vectors.kv")
e.embed_movie("backend/src/main/python/documents/101_dalmatians.json")
"""
word = preprocess_string("cool")
print(vectors.most_similar(positive=word))
"""