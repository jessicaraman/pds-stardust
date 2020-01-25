var Node = require("../schemas/nodeSchema");
var AdjacentNode = require("../schemas/adjacentNodeSchema");

function getCustomerPath(req, res) {
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
}