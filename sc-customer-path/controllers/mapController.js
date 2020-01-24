var Node = require("../schemas/nodeSchema");
var AdjacentNode = require("../schemas/adjacentNodeSchema");

function getMallMap(req, res) {
    try{
        return res.status(200).json({
            text: "to be implemented soon... :)"
        });
    }
    catch(error){
        return res.status(500).json({
            text: "internal server error",
            error
        })
    }
}