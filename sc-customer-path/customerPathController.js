var Node = require("./src/controllers/schemas/nodeSchema");
var Graph = require('node-dijkstra');
var route = new Graph();
var AdjacentNode = require("./src/controllers/schemas/adjacentNodeSchema");

function getClosestNode(startingNode, nodesList) {
    try{
        console.log("enter into method");
        const route = new Graph();
        route.addNode('A', { 'B':1, 'C':1 });
        route.addNode('B', { 'A':1, 'D':1, 'E': 1 });
        route.addNode('C', { 'A':1, 'D':1 });
        route.addNode('D', { 'C':1, 'B':1, 'F':1 });
        route.addNode('E', { 'B':1, 'F':1});
        route.addNode('F', { 'D':1, 'E':1});
        for(node of nodesList){
            console.log(node);
        }
        // finding the closest node from the starting point
        var closestNodeName = null;
        var closestNodeDistance=10000000;
        for(node of nodesList){
            var tempsDistance = route.path(startingNode, node);
            if(tempsDistance != null){
                console.log("chemin entre " + startingNode + " et " + node + ": " + tempsDistance +" soit " + tempsDistance.length);
            if(closestNodeName ==  null || closestNodeDistance > tempsDistance.length){
                if(node != startingNode){
                    closestNodeDistance = tempsDistance.length;
                closestNodeName = node;
                }
            }
            }
        }
         return closestNodeName;
    }
    catch(error){
        return error;
    }
};

module.exports = {
    getOptimizedPath: function(a, b){
        var startingNode = a;
        var nodesList = b;
        var finalOrderedList = []; 
        while(typeof nodesList != 'undefined' && nodesList.length > 1){
            var tempClosestNode = getClosestNode(startingNode, nodesList);
            if(tempClosestNode != null){
                nodesList.splice( nodesList.indexOf(tempClosestNode), 1 );
                finalOrderedList.push(tempClosestNode);
            }
            console.log("Liste initial: " + nodesList);
            console.log("Liste finale: " + finalOrderedList);
        }
        console.log("final list: " + finalOrderedList )
        return finalOrderedList;
    }
  };