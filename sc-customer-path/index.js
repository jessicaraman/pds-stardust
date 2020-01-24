var express = require("express"),
  app = express(),
  port = process.env.PORT || 3000;
var fs = require("fs");
var mongoose = require('mongoose');
var bodyParser = require("body-parser");
var customerPathController = require('./controllers/customerPathController.js')
var mapController = require('./controllers/mapController.js')

app.listen(port, function(){
    console.log('App run on port '+ port);
});

app.get('/', (req, res) => res.send('Hello World with Express'));
app.get('/map', (req, res, next) => {
    mapController.getMallMap;
});

app.get('/customerPath', (req, res, next) => {
    customerPathController.getCustomerPath;
});

mongoose.connect('mongodb://localhost/db').then(() => {
    console.log('Connected to mongoDB')
}).catch(e => {
    console.log('Error while DB connecting');
    console.log(e);
});
