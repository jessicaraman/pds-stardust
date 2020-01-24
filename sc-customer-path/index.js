var express = require("express"),
  app = express(),
  port = process.env.PORT || 3000;
var fs = require("fs");
var mongoose = require('mongoose');
var bodyParser = require("body-parser");
app.listen(port, function(){
    console.log('App run on port '+ port);
});

app.get('/', (req, res) => res.send('Hello World with Express'));
/*app.get('/getProductById', (req, res, next) => {
    cproduct.findProductById;
});

app.post('/postProduct', (req, res, next) => {
    cproduct.createProduct;
})
app.delete('/deleteProduct', (req, res, next) => {
    cproduct.deleteProductById;
})


mongoose.connect('mongodb://localhost/db').then(() => {
    console.log('Connected to mongoDB')
}).catch(e => {
    console.log('Error while DB connecting');
    console.log(e);
});*/
