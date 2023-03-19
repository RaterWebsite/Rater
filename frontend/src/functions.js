function getProgressColor(percent){
  let r = percent<50 ? 255 : Math.floor(255-(percent*2-100)*255/100);
  let g = percent>50 ? 255 : Math.floor((percent*2)*255/100);
  return 'rgb('+r+','+g+',0)';
}

function getTimeString(minutes){
  let message = ""
  if(minutes >= 60){
    message+= Math.floor(minutes/60) + 'h'
    minutes = minutes % 60
    if(minutes>0){
      message+= ' '
    }
  }
  if(minutes > 0){
    message+= minutes +'m'
  }
  return message
}
module.exports = {getTimeString} 

function generateItem(movieData, categories, weights){
  const containerDiv = document.createElement("div")
  containerDiv.className = 'container'

  const leftDiv = document.createElement("div")
  leftDiv.className = 'left-container'

  const middleDiv = document.createElement("div")
  middleDiv.className = 'middle-container'

  const rightDiv = document.createElement("div")
  rightDiv.className = 'right-container'

  containerDiv.appendChild(leftDiv)
  containerDiv.appendChild(middleDiv)
  containerDiv.appendChild(rightDiv)

  const imageDiv = document.createElement("img")
  imageDiv.className = 'movie-image'
  imageDiv.src = movieData['imageUrl']
  leftDiv.appendChild(imageDiv)

  const titleDiv = document.createElement("div")
  titleDiv.className = 'title-item'
  titleDiv.style.fontSize = "32px";
  titleDiv.innerHTML = movieData['title'] + ' (' + movieData['releaseYear'] + ')'
  middleDiv.appendChild(titleDiv)

  const infoDiv = document.createElement("div")
  infoDiv.className = 'info-item'
  infoDiv.innerHTML = movieData['releaseYear'] + ' • ' + movieData['mpaaRating'] + ' • ' + getTimeString(movieData['runtime'])
  middleDiv.appendChild(infoDiv)

  const descDiv = document.createElement("div")
  descDiv.className = 'desc-item'
  descDiv.innerHTML = movieData['description']
  middleDiv.appendChild(descDiv)

  const genreDiv = document.createElement("div")
  genreDiv.className = 'genre-item'
  genreDiv.innerHTML = movieData['genre'].join(' • ')
  middleDiv.appendChild(genreDiv)

  const starringDiv = document.createElement("div")
  starringDiv.className = 'starring-item'
  starringDiv.innerHTML = movieData['starring'].join(' • ')
  middleDiv.appendChild(starringDiv)


  var overallTotal = 0
  var overallOutOf = 0
  for(let i = 0; i < categories.length; i++){
    let categoryName = categories[i]
    categories[i] = categories[i][0].toLowerCase() + categories[i].substring(1).replace(' ','')
    const ratingDiv = document.createElement("div")
    ratingDiv.className = 'rating-item'
    //let rating = Math.floor(Math.random() * 10)
    let rating = movieData['categories'][categories[i]]
    overallTotal+= rating * weights[i]
    overallOutOf+= 10 * weights[i]
    ratingDiv.innerHTML = categoryName + ': ' + rating;
    ratingDiv.style.borderColor = getProgressColor(rating*10)
    rightDiv.appendChild(ratingDiv)
  }

  const overallDiv = document.createElement("div")
  overallDiv.className = 'rating-item'
  let rating = (overallTotal / overallOutOf) * 10
  overallDiv.innerHTML =  'Overall: ' + rating.toFixed(2);;
  overallDiv.style.borderColor = getProgressColor(rating*10)
  //rightDiv.appendChild(overallDiv)

  return containerDiv
}
