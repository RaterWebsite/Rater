from gensim.models import KeyedVectors
import json

mv = KeyedVectors.load_word2vec_format("backend/src/main/python/word2vec/model/movies.kv")

for title in mv.key_to_index:
    with open("backend/src/main/python/documents/" + title + ".json") as file:
        movie = json.load(file)

    movie["recommendations"] = [x for x,y in mv.most_similar(positive=title, topn=25)]
    
    with open("backend/src/main/python/documents/" + title + ".json", "w") as file:
        json.dump(movie, file)