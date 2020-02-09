const mongoose = require('mongoose');

var nodeSchema = mongoose.Schema({
  id: {
    type : String,
    trim: true,
    unique: true,
    required: true
  },
  label: {
    type: String,
    required: true
  },
  nodeCategory:{
    type: Number,
    required: true
  }
})

module.exports = mongoose.model('Node', nodeSchema);
