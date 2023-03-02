/** 
 * Generic format for a movie review
*/
class review {
    #movieID;
    #rawText;
    #sentiments;

    constructor(movieID, text){
        this.#movieID = movieID;
        this.#rawText = text;
        this.#analyze();
    }

    /**
     * Performs sentiment analysis on the raw text and stores it in sentiments
     */
    #analyze(){
        //TO-DO 
    }

    get movieID() {
        return this.#movieID;
    }

    get rawText() {
        return this.#rawText;
    }

    get sentiments() {
        return this.#sentiments;
    }
}