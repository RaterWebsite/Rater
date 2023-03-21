var categories = ['Plot', 'Acting', 'Ending', 'Soundtrack', 'Cinematography', 'Family Friendly', 'Funny', 'Action']

window.onload = function () {
    // document.getElementById('tag-input').style.height="35px";
    // document.getElementById('tag-input').style.fontSize="16pt";
    var buttonList = document.getElementById('button-list')

    for(var i = 0; i < categories.length; i++){
        var categoryButton = document.createElement('button')
        categoryButton.innerHTML = categories[i]
        categoryButton.className = 'category-button'
        categoryButton.addEventListener("click", function(event) {
            var nav = document.getElementById('search-nav')
            const containerDiv = document.createElement("div")
            containerDiv.className = 'search-container'

            //nav.appendChild(containerDiv)

            
            const textContainerDiv = document.createElement("div")
            textContainerDiv.className = 'text-container'

            const barContainerDiv = document.createElement("p")
            barContainerDiv.className = 'bar-container'

            const closeButtonContainer = document.createElement("p")
            barContainerDiv.className = 'close-container'

            const closeButton = document.createElement('button')
            closeButton.className = 'close-button'
            closeButton.innerHTML = 'remove'

            closeButton.addEventListener("click", (event) => {
              console.log('hi')
              containerDiv.remove()

            })

            const text = document.createElement("label")

            text.innerHTML = event.target.innerHTML

            const range = document.createElement("input")
            range.type = 'range'
            range.min = 0
            range.max = 40
            range.value = 20

            const multiplier = document.createElement("label")
            var yv = '0.0'
            multiplier.innerHTML = yv
            let multiplier_values = []
            for(let i = 0; i <= 40; i++){
              multiplier_values.push(('' + (-1.0 + i * (2.0 / 40)).toFixed(2)))
            }
            range.addEventListener("input", (event) => {
                multiplier.innerHTML = multiplier_values[event.target.value]

              })

            textContainerDiv.appendChild(text)
            barContainerDiv.appendChild(range)
            barContainerDiv.appendChild(multiplier)
            closeButtonContainer.appendChild(closeButton)
            containerDiv.appendChild(textContainerDiv)
            containerDiv.appendChild(barContainerDiv)
            containerDiv.appendChild(closeButtonContainer)

            var currentParameters = []
            let parameterChildren = nav.children
            for(let i = 0; i < parameterChildren.length; i++){
              currentParameters.push(parameterChildren[i].children[0].children[0].innerHTML)
            }
            if(!currentParameters.includes(event.target.innerHTML)){
              nav.appendChild(containerDiv)
            }
        })
        buttonList.appendChild(categoryButton)

      }

    var b = document.getElementById('button1')
}
