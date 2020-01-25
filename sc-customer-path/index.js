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

app.get('/', (req, res) => res.send('hello'));
app.get('/map', (req, res, next) => {
    try{
        console.log("this service is not working yet, please stay tuned");
        return res.status(200).json({
            text: "to be implemented soon... :)"
        });
    }
    catch(error){
        return res.status(500).json({
            error
        });
    }
});

app.get('/customerPath', (req, res, next) => {
    try{
        console.log("this service is not working yet, please stay tuned");
        return res.status(200).json({
            text: "to be implemented soon... :)"
        });
    }
    catch(error){
        return res.status(500).json({
            error
        });
    }
});

mongoose.connect('mongodb://localhost/db').then(() => {
    console.log('Connected to mongoDB')
}).catch(e => {
    console.log('Error while DB connecting');
    console.log(e);
});
