var categories = ['Plot', 'Acting', 'Ending', 'Soundtrack', 'Cinematography', 'Family Friendy', 'Funny', 'Action']

window.onload = function () {
    var buttonList = document.getElementById('button-list')

    for(var i = 0; i < categories.length; i++){
        var categoryButton = document.createElement('button')
        categoryButton.innerHTML = categories[i]
        categoryButton.addEventListener("click", function(event) {
            var nav = document.getElementById('search-nav')
            const containerDiv = document.createElement("div")
            containerDiv.className = 'search-container'
    
            nav.appendChild(containerDiv)
    
            const textContainerDiv = document.createElement("div")
            textContainerDiv.className = 'text-container'
    
            const barContainerDiv = document.createElement("p")
            barContainerDiv.className = 'bar-container'
    
            const text = document.createElement("label")

            text.innerHTML = event.target.innerHTML
    
            const range = document.createElement("input")
            range.type = 'range'
            range.min = 0
            range.max = 4
            range.value = 2
    
            const multiplier = document.createElement("label")
            var yv = '1.0x'
            multiplier.innerHTML = yv
            
            range.addEventListener("input", (event) => {
                multiplier_values = ['0.0x', '0.5x', '1.0x', '1.5x', '2.0x']
                multiplier.innerHTML = multiplier_values[event.target.value]
                
              })
    
            textContainerDiv.appendChild(text)
            barContainerDiv.appendChild(range)
            barContainerDiv.appendChild(multiplier)
            containerDiv.appendChild(textContainerDiv)
            containerDiv.appendChild(barContainerDiv)

            nav.appendChild(containerDiv)
        })
        buttonList.appendChild(categoryButton)
        
      }

    var b = document.getElementById('button1')
    console.log(b)
    console.log(document.getElementById('tag-input'))

}