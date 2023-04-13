from gensim.parsing.preprocessing import preprocess_string
from gensim.models import Word2Vec, KeyedVectors
import numpy as np
import pandas as pd
import logging

logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
logging.info("Program Start")

vectors = KeyedVectors.load_word2vec_format("backend/src/main/python/word2vec/model/vectors.kv")

word = preprocess_string("acting")
print(vectors.most_similar(positive=word))