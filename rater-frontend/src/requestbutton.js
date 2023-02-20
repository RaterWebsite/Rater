window.onload = function () {
    var buttonList = document.getElementById('items-list')
    var requestButton = document.createElement('button')
    requestButton.innerHTML = 'make request'
    buttonList.appendChild(requestButton)

    let url = 'http://localhost:9200'
    let body = {'Plot': 1.0,
                'Acting': 1.0,
                'Ending': 1.0,
                'Soundtrack': 1.0,
                'Cinematography': 1.0,
                'FamilyFriendy': 1.0,
                'Funny': 1.0,
                'Action': 1.0 }

    let requestBody = JSON.stringify(body)

    requestButton.addEventListener("click", function(event) {
      fetch(url, {
      method: 'POST',
      headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
      },
      body: requestBody
      })
      .then(response => response.json())
      .then(response => console.log(JSON.stringify(response)))
    })


}
