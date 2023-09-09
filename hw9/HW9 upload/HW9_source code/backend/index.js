//require: load module (similar with python import)
const express = require('express');
const app = express();
const artsyAPI = require('./artsyAPI');
const cors = require('cors');
const path = require('path');

app.use(cors());
//app.use(express.static(path.join(__dirname, './dist/frontend')));

app.get('/', (req, res) => {
  //res.sendFile(path.join(__dirname + './dist/frontend/index.html'));
  return res.send("hello world!")
})

app.get('/search_list/:keyword', async function(req, res){
  let searchList = await artsyAPI.get_searchList(req.params.keyword)
  return res.send(searchList);
})

app.get('/info/:id', async function(req, res){
  let detail = await artsyAPI.get_artistDetail(req.params.id)
  return res.send(detail);
})

app.get('/artworks/:id', async function(req, res){
  let artwork = await artsyAPI.get_artworks(req.params.id)
  return res.send(artwork);
})

app.get('/genes/:id', async function(req, res){
  let genes = await artsyAPI.get_genes(req.params.id)
  return res.send(genes);
})

// Listen to the App Engine-specified port, or 3000 otherwise
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
});