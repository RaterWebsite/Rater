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
      <div id="popup" className="popup">
        <div className="popup-content">
          <h2>Login</h2>
          <label>
            Username:
          </label>
          <input type="text" id="username-input"/>
          <br></br>
          <label>
            Password:
          </label>
          <input type="password" id="password-input"/>
          <br></br>
          <button id="login-button">Login</button>
        </div>
        
      </div>
      <h1>Rater</h1>
      <h2>Movie search</h2>
      <div id='options-list'></div>
      <div id='button-list'></div>

      <div className="card">
        <p>
          Click on tags that would like to see!
        </p>
        <p>
          Drag sliders to how much weight you would like the tag to have (positive means more and negative means less weight)!
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
                "id": "69420",
                "title": "Robocop",
                "starring": [
                    "Nancy Allen",
                    "Peter Weller",
                    "Stephen Boxwell"
                ],
                "runtime": 102,
                "genre": [
                    "Adventure",
                    "Action",
                    "Science Fiction"
                ],
                "description": "In a dystopic and crime-ridden Detroit, a terminally wounded cop returns to the force as a powerful cyborg haunted by submerged memories.",
                "releaseYear": 1987,
                "mpaaRating": "R",
                "imageUrl": "https://upload.wikimedia.org/wikipedia/en/thumb/1/16/RoboCop_%281987%29_theatrical_poster.jpg/220px-RoboCop_%281987%29_theatrical_poster.jpg",
                "categories": {
                    "plot": 0.0,
                    "acting": 0.0,
                    "ending": 0.0,
                    "soundtrack": 0.0,
                    "cinematography": 0.0,
                    "familyFriendly": 0.0,
                    "funny": 0.0,
                    "action": 0.0
                },
                "reviews": [
                    {
                        "rating": 0,
                        "text": "Hello class, we will not be got again!"
                    },
                    {
                      "rating": 5,
                      "text": "Hello class, we will not be got again!"
                    },
                    {
                      "rating": 7,
                      "text": "really long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long reviewreally long review"
                    }
                  
                ]
                }
              document.getElementById('items-list').appendChild(generateItem(element, inputs, weights))
              document.getElementById('items-list').appendChild(generateItem(element, inputs, weights))
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
