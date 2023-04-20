from flask import Flask, request, jsonify
from gensim.models import KeyedVectors
from gensim.parsing.preprocessing import preprocess_string
import numpy as np

app = Flask(__name__)

mv = KeyedVectors.load_word2vec_format("resources/movies.kv")
wv = KeyedVectors.load_word2vec_format("resources/vectors.kv")

@app.route('/search', methods=['POST'])
def search():
    # Get the input data from the request
    text = request.json['text']

    # Parse through the words and get a composite vector
    vec = np.zeros(wv.vector_size)
    for word in preprocess_string(text):
        vec += wv.get_vector(word)

    movie_ids = mv.similar_by_vector(vec, topn=25)

    # Return the result as a JSON response
    return {'movie_ids': movie_ids}

if __name__ == '__main__':
    app.run()