const mongoose = require('mongoose');

var adjacentNodeSchema = mongoose.Schema({
  mainNodeId: {
    type : String,
    trim: true,
    unique: true,
    required: true
  },
  adjacentNodeId: {
    type: String,
    required: true
  },
  distance:{
    type: Number,
    required: true
  }
})

module.exports = mongoose.model('AdjacentNode', adjacentNodeSchema);
