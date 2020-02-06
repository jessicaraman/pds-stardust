var Node = require('../schemas/nodeSchema');
var NodeSchema = require('../schemas/nodeCategorySchema');
function getNodeById(req, res) {
    var id = req.body.id;
    if (!id) {
        return res.status(400).json({
            text: "id is missing. "
        });
    }
    try {
        Node.findById(id, function (err, node) {
            if (err) {
                return res.status(500).send(err);
            }
            else {
                return res.status(200).send(node);
            }
        });
    }
    catch (error) {
        res.status(500).send(error);
    }
}

function getAllNodes(req, res) {

    Node.find({}).toArray(function (err, result) {
        if (err) {
            res.status(500).send(err);
        } else {

            res.status(200).send(JSON.stringify(result));
        }
    })

}
function createNode(req, res) {
    var id = req.body.id;
    var label = req.body.label;
    var nodeCategory = req.body.nodeCategory;

    if (!id | !label | !nodeCategory){
        return res.status(400).json({
            text: "body incomplete."
        });
    }
    var node = {
        id,
        label,
        nodeCategory
    }
    try{
        const findNode = Node.findOne({id});
        if(findNode){
            return res.status(400).json({
                text: "Id already used by another node."
            });
        }
    }
    catch(error){
        res.status(500).json({
            error
        });
    }
    try{
        var nodeData = new Node(node);
        var nodeObject = nodeData.save();
        return res.status(200).json({
            text: "Success"
        });
    }
    catch(error){
        res.status(500).json({
            error
        });
    }
}

function deleteNode(req, res) {
    var id = req.body.id;
    if (!id) {
        return res.status(400).json({
            text: "id is missing. "
        });
    }
    try {
        Node.findByIdAndDelete(id, (err, node) => {
            if (err) {
                return res.status(500).send(err);
            }
            const response = {
                message: "node deleted",
                id: node._id
            };
            return res.status(200).send(response);
        });
    }
    catch (error) {
        res.status(500).send(error);
    }
}

function updateNode(req, res) {

}