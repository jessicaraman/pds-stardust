var Node = require("../src/controllers/schemas/nodeSchema");
var Graph = require('node-dijkstra');
var route = new Graph();
var AdjacentNode = require("../src/controllers/schemas/adjacentNodeSchema");

function getNodePath(a, b){
    const route = new Graph();
        route.addNode("reception2", {"entrance": 1});
        route.addNode("entrance", {"reception2":1, "stairs":1, "c1": 1});
        route.addNode("stairs", {"c1":1});
        route.addNode("c1", {"entrance":1, "c2": 1, "stairs":1, "c4":1});
        route.addNode("reception", {"entrance":1});
        route.addNode("c2", {"c1":1, "p1":1, "p2":1, "c3":1, "bde":1});
        route.addNode("p1", {"c2":1});
        route.addNode("p2", {"c2":1});
        route.addNode("c3", {"c2":1, "p2":1, "td5":1, "p3":1});
        route.addNode("bde",{"c2":1});
        route.addNode("p3",{"c3":1});
        route.addNode("td5", {"c3":1});
        route.addNode("c4", {"c1":1,"x3":1,"r1":1,"r2":1,"r3":1,"p8":1,"p11":1,"p9":1,"td3":1});
        route.addNode("x3",{"c4":1});
        route.addNode("r1",{"c4":1});
        route.addNode("r2",{"c4":1});
        route.addNode("r3",{"c4":1});
        route.addNode("p8", {"c4":1});
        route.addNode("p11", {"c4":1});
        route.addNode("p9", {"c4":1});
        route.addNode("td3", {"c4":1});
        var nodeA =a;
        var nodeB =b;
    return route.path(nodeA, nodeB);
}
function getClosestNodeFromList(startingNode, nodesList) {
    try{
        console.log("enter into method");
        const route = new Graph();
        route.addNode("reception2", {"entrance": 1});
        route.addNode("entrance", {"reception2":1, "stairs":1, "c1": 1});
        route.addNode("stairs", {"c1":1});
        route.addNode("c1", {"entrance":1, "c2": 1, "stairs":1, "c4":1});
        route.addNode("reception", {"entrance":1});
        route.addNode("c2", {"c1":1, "p1":1, "p2":1, "c3":1, "bde":1});
        route.addNode("p1", {"c2":1});
        route.addNode("p2", {"c2":1});
        route.addNode("c3", {"c2":1, "p2":1, "td5":1, "p3":1});
        route.addNode("bde",{"c2":1});
        route.addNode("p3",{"c3":1});
        route.addNode("td5", {"c3":1});
        route.addNode("c4", {"c1":1,"x3":1,"r1":1,"r2":1,"r3":1,"p8":1,"p11":1,"p9":1,"td3":1});
        route.addNode("x3",{"c4":1});
        route.addNode("r1",{"c4":1});
        route.addNode("r2",{"c4":1});
        route.addNode("r3",{"c4":1});
        route.addNode("p8", {"c4":1});
        route.addNode("p11", {"c4":1});
        route.addNode("p9", {"c4":1});
        route.addNode("td3", {"c4":1});
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
            else{
                return null;
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

        var newStartingPoint = a;
        var finalOrderedList = []; 
        if(a != null){
            finalOrderedList.push(a);
        }
        while(typeof nodesList != 'undefined' && nodesList.length >= 1){
            var tempClosestNode = getClosestNodeFromList(newStartingPoint, nodesList);
            console.log("tempClosestNode: " + tempClosestNode);
            if(tempClosestNode != null){
                var tempClosestNodePath = getNodePath(newStartingPoint, tempClosestNode);
                tempClosestNodePath.splice( 0, 1 );
                console.log("tempClosestNodePath: " + tempClosestNodePath);
                nodesList.splice( nodesList.indexOf(tempClosestNode), 1 );
                for(node of tempClosestNodePath){
                    finalOrderedList.push(node);
                }
                newStartingPoint = tempClosestNode;
            }
            else{
                return null;
            }
            console.log("Liste initial: " + nodesList);
            console.log("Liste finale: " + finalOrderedList);
        }
        console.log("final list: " + finalOrderedList )
        return finalOrderedList;
    }
  };