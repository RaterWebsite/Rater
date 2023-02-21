import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'

function populate(response, inputs, weights) {
  response.json()
  let responseData = JSON.stringify(response)
  var test_data = {
      "id": "5",
      "title": "Raiders of the Lost Ark",
      "starring": [
          "Harrison Ford",
          "Karen Allen",
          "John Rhys-Davies"
      ],
      "runtime": 105,
      "genre": [
          "Adventure",
          "Action"
      ],
      "description": "Epic tale in which an intrepid archaeologist tries to beat a band of Nazis to a unique religious relic which is central to their plans for world domination. Battling against a snake phobia and a vengeful ex-girlfriend, Indiana Jones is in constant peril, making hair's-breadth escapes at every turn in this celebration of the innocent adventure movies of an earlier era",
      "releaseYear": 1981,
      "rating": "PG",
      "image": "https://irs.www.warnerbros.com/keyart-jpeg/movies/media/browser/the_dark_knight_key_art.jpg",
      "categories": {
          "plot": 9,
          "acting": 7,
          "ending": 9,
          "soundtrack": 10,
          "cinematography": 9,
          "funny": 8,
          "action": 9,
          "familyFriendly": 6
      }
  }
  let il = document.getElementById('items-list')
  while(il.firstChild != null){
    il.removeChild(il.firstChild)
  }
  console.log(responseData)
  for(let data in responseData) {
    //document.getElementById('items-list').appendChild(generateItem(data, inputs, weights))
  }
}

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
          Click on tags that would like to see
        </p>
        <div className="sidenav-off" id = "search-nav">
        </div>
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
              body[inputs[i].replace(' ', '')] = weight_scale[weights[i]]
            }

            let url = 'http://localhost:9187/api/movie/categorySearch/'

            console.log(body)

            let requestBody = JSON.stringify(body)
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
