from gensim.models import KeyedVectors
from gensim.parsing.preprocessing import preprocess_string
import json
import os
import numpy as np
import logging


logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
logging.info("Program Start")

class embedder:

    def __init__(self, vector_embeddings: str) -> None:
        self.wv = KeyedVectors.load_word2vec_format(vector_embeddings)

    def embed_corpus(self, corpus_folder: str, dest: str) -> None:
        movie_embeds = KeyedVectors(100)

        vectors = {filename.replace(".json", ""): self.embed_movie(corpus_folder + filename) for filename in os.listdir(corpus_folder)}
        movie_embeds.add_vectors(list(vectors.keys()), list(vectors.values()))

        movie_embeds.save_word2vec_format(dest)

    def embed_movie(self, movie_file: str):
        with open(movie_file) as file:
            movie = json.load(file)

        embedding = np.zeros(100)
        for review in movie["reviews"]:
            text = review["text"]
            embedding += self.__embed_review(text)
        
        return embedding
    
    def __embed_review(self, review: str):
        review = preprocess_string(review)
        embedding = np.zeros(100)
        for word in review:
            if self.wv.has_index_for(word):
                embedding += self.wv.get_vector(word)
        return embedding
    
if __name__ == "__main__":
    e = embedder("backend/src/main/python/word2vec/model/vectors.kv")
    e.embed_corpus("backend/src/main/python/documents", "backend/src/main/python/word2vec/model/movies.kv")