import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="App">
      <div>
        <a target="_blank">
          <img src="/logo.png" className="logo" alt="Rater logo" />
        </a>
      </div>
      <h1>Rater</h1>
      <h2>Movie search</h2>
      <div id='button-list'></div>

      <div className="card">
        <p>
          Click on tags that would like to see or enter in your own custom tags!
        </p>
        <div className="sidenav-off" id = "search-nav">
        </div>
        {/* <div className="App">
            <div id="tag-input"/>
        </div> */}
        {/* <input type="text" id="tag-input"/> */}
        <button className="button" onClick={() => {
            //var inputs = document.getElementById('tag-input').value.split(',')
            let il = document.getElementById('items-list')
            while(il.firstChild != null){
              il.removeChild(il.firstChild)
            }
            var inputs = []
            var weight_scale = {
              "0.0": 0.0,
              "0.5": 0.5,
              "1.0": 1.0,
              "1.5": 1.5,
              "2.0": 2.0
            }
            var allButtons = document.getElementById('search-nav').children
            document.getElementById('search-nav').hidden = true
            var weights = []
            let body = {}
            for(let i = 0; i < allButtons.length; i++){
              inputs.push(allButtons[i].children[0].textContent)
              weights.push(allButtons[i].children[1].children[1].textContent.replace('x', ''))
              body[inputs[i].replace(' ', '')] = parseFloat(weights[i])
            }

            let url = 'http://localhost:9187/api/movie/categorySearch/'

            let requestBody = JSON.stringify(body)
            console.log(requestBody)
            let use_elastic = true;

            if(use_elastic) {
            const address = fetch(url, {
              method: 'POST',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
              },
              body: requestBody
              })
              .then(response => response.json())
              .then(response => response.forEach((element) => { document.getElementById('items-list').appendChild(generateItem(element, inputs, weights)) }))
            }else {
              let element = {
                  "id": "6",
                  "title": "The Dark Knight",
                  "starring": [
                      "Christian Bale",
                      "Michael Caine",
                      "Heath Ledger"
                  ],
                  "runtime": 152,
                  "genre": [
                      "Superhero",
                      "Action"
                  ],
                  "description": "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                  "releaseYear": 2008,
                  "rating": "PG-13",
                  "image": "https://irs.www.warnerbros.com/keyart-jpeg/movies/media/browser/the_dark_knight_key_art.jpg",
                  "categories": {
                      "plot": 8,
                      "acting": 10,
                      "ending": 7,
                      "soundtrack": 10,
                      "cinematography": 8,
                      "familyFriendly": 5,
                      "funny": 2,
                      "action": 8
                  }
                }
              document.getElementById('items-list').appendChild(generateItem(element, inputs, weights))

            }


        }}>
          search
        </button>

        <div className="card" id="items-list">

        </div>
      </div>

    </div>
  )
}

export default App
