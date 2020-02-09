//nodes
db.Node.insertOne({
    id: 1,
    label: "reception2",
    nodeCategory: null
});
db.Node.insertOne({
    id: 2,
    label: "entrance",
    nodeCategory: null
});
db.Node.insertOne({
    id: 3,
    label: "stairs",
    nodeCategory: null
});
db.Node.insertOne({
    id: 4,
    label: "c1",
    nodeCategory: null
});
db.Node.insertOne({
    id: 5,
    label: "reception",
    nodeCategory: null
});
db.Node.insertOne({
    id: 6,
    label: "c2",
    nodeCategory: null
});
db.Node.insertOne({
    id: 7,
    label: "p1",
    nodeCategory: null
});
db.Node.insertOne({
    id: 8,
    label: "p2",
    nodeCategory: null
});
db.Node.insertOne({
    id: 9,
    label: "c3",
    nodeCategory: null
});
db.Node.insertOne({
    id: 10,
    label: "bde",
    nodeCategory: null
});
db.Node.insertOne({
    id: 11,
    label: "p3",
    nodeCategory: null
});
db.Node.insertOne({
    id: 12,
    label: "td5",
    nodeCategory: null
});
db.Node.insertOne({
    id: 13,
    label: "c4",
    nodeCategory: null
});
db.Node.insertOne({
    id: 14,
    label: "x3",
    nodeCategory: null
});
db.Node.insertOne({
    id: 15,
    label: "r1",
    nodeCategory: null
});
db.Node.insertOne({
    id: 16,
    label: "r2",
    nodeCategory: null
});
db.Node.insertOne({
    id: 17,
    label: "r3",
    nodeCategory: null
});
db.Node.insertOne({
    id: 18,
    label: "p8",
    nodeCategory: null
});
db.Node.insertOne({
    id: 19,
    label: "p11",
    nodeCategory: null
});
db.Node.insertOne({
    id: 20,
    label: "p9",
    nodeCategory: null
});
db.Node.insertOne({
    id: 21,
    label: "p7",
    nodeCategory: null
});
db.Node.insertOne({
    id: 22,
    label: "td3",
    nodeCategory: null
});


//adjacent node 1 reception2
db.AdjacentNode.insertOne({
    mainNodeId: 1,
    adjacentNodeId: 2,
    distance:3
});

//adjacent node 2 entrance
db.AdjacentNode.insertOne({
    mainNodeId: 2,
    adjacentNodeId: 1,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 2,
    adjacentNodeId: 2,
    distance:3
}); 
db.AdjacentNode.insertOne({
    mainNodeId: 2,
    adjacentNodeId: 4,
    distance:3
});

//adjacent node 3 stairs
db.AdjacentNode.insertOne({
    mainNodeId: 3,
    adjacentNodeId: 4,
    distance:3
});

//adjacent node 4 c1
db.AdjacentNode.insertOne({
    mainNodeId: 4,
    adjacentNodeId: 2,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 4,
    adjacentNodeId: 6,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 4,
    adjacentNodeId: 3,
    distance:3
});

//adjacent node 5 reception
db.AdjacentNode.insertOne({
    mainNodeId: 5,
    adjacentNodeId: 2,
    distance:3
});
//adjacent node 6 c2
db.AdjacentNode.insertOne({
    mainNodeId: 6,
    adjacentNodeId: 4,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 6,
    adjacentNodeId: 7,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 6,
    adjacentNodeId: 8,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 6,
    adjacentNodeId: 9,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 6,
    adjacentNodeId: 10,
    distance:3
});

//adjacent node 7 p1
db.AdjacentNode.insertOne({
    mainNodeId: 7,
    adjacentNodeId: 6,
    distance:3
});
//adjacent node 8 p2
db.AdjacentNode.insertOne({
    mainNodeId: 8,
    adjacentNodeId: 6,
    distance:3
});
//adjacent node 9 c3
db.AdjacentNode.insertOne({
    mainNodeId: 9,
    adjacentNodeId: 6,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 9,
    adjacentNodeId: 8,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 9,
    adjacentNodeId: 12,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 9,
    adjacentNodeId: 11,
    distance:3
});
//adjacent node 10 bdr
db.AdjacentNode.insertOne({
    mainNodeId: 10,
    adjacentNodeId: 6,
    distance:3
});
//adjacent node 11 p3
db.AdjacentNode.insertOne({
    mainNodeId: 11,
    adjacentNodeId: 9,
    distance:3
});
//adjacent node 12 td5
db.AdjacentNode.insertOne({
    mainNodeId: 12,
    adjacentNodeId: 9,
    distance:3
});
//adjacent node 13 c4
db.AdjacentNode.insertOne({
    mainNodeId: 13,
    adjacentNodeId: 4,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 13,
    adjacentNodeId: 14,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 13,
    adjacentNodeId: 15,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 13,
    adjacentNodeId: 16,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 13,
    adjacentNodeId: 17,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 13,
    adjacentNodeId: 18,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 12,
    adjacentNodeId: 19,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 12,
    adjacentNodeId: 20,
    distance:3
});
db.AdjacentNode.insertOne({
    mainNodeId: 12,
    adjacentNodeId: 21,
    distance:3
});

//adjacent node 14 x3
db.AdjacentNode.insertOne({
    mainNodeId: 14,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 15 r1
db.AdjacentNode.insertOne({
    mainNodeId: 15,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 16 r2
db.AdjacentNode.insertOne({
    mainNodeId: 16,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 17 r3
db.AdjacentNode.insertOne({
    mainNodeId: 17,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 18 p8
db.AdjacentNode.insertOne({
    mainNodeId: 18,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 19 p11
db.AdjacentNode.insertOne({
    mainNodeId: 19,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 20 p9
db.AdjacentNode.insertOne({
    mainNodeId: 20,
    adjacentNodeId: 13,
    distance:3
});
//adjacent node 21 td3
db.AdjacentNode.insertOne({
    mainNodeId: 21,
    adjacentNodeId: 13,
    distance:3
});