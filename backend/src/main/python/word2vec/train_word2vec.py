from gensim.parsing.preprocessing import preprocess_string
from gensim.models import Word2Vec
import pandas as pd
import logging

logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
logging.info("Program Start")

data = pd.read_csv("backend/src/main/python/word2vec/training_data/IMDB Dataset.csv")["review"].array
logging.info("Training data loaded")

logging.info("Beginning training")
preprocessed = [preprocess_string(review) for review in data]
logging.info("Data preprocessed")

model = Word2Vec(sentences=preprocessed, vector_size=100, window=5, min_count=10,workers=4)
model.save("backend/src/main/python/word2vec/model/word2vec.model")
model.wv.save_word2vec_format("backend/src/main/python/word2vec/model/vectors.kv")