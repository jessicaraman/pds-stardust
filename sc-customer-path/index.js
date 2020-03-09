var express = require("express"),
  app = express(),
  port = process.env.PORT || 3000;
var fs = require("fs");
var mongoose = require('mongoose');
var bodyParser = require("body-parser");
var customerPathController = require('./controllers/customerPathController');
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


app.listen(port, function(){
    console.log('App run on port '+ port);
});

app.get('/', (req, res) => res.status(200).json({
    text: 'customer path API for the SC Mall :)'
}));
app.get('/map', (req, res, next) => {
        console.log("oui");
        var message = customerPathController.testFunction();
        console.log(message);
        res.status(200).json({
            text: message
        });
});

app.get('/customerPath', (req, res, next) => {
    try{

    var startingNode = req.body.startingNode;
    var nodesList = req.body.listOfNode;
    console.log(" starting node: " + startingNode + " list of nodes: " +nodesList);
    var optimizedPath =customerPathController.getOptimizedPath(startingNode, nodesList);
    console.log("result on indexjs: " + optimizedPath);
    if(optimizedPath == null){
        res.status(400).json({
            text: "can't generate path: There is an issue with the nodes selected. Please try again"
        });
    }
    else{
        res.status(200).json({
            'path': optimizedPath,
            'pathId': null
        })
    }
    }
    catch(error){
        res.status(500).json({
            error
        });
    }
});

mongoose.connect('mongodb://pds:pds@node1:27017,node1:27017,node1:27017/cspath?replicaSet=rs0').then(() => {
    console.log('Connected to mongoDB')
}).catch(e => {
    console.log('Error while DB connecting');
    console.log(e);
});
