const mongoose = require('mongoose');

var nodeCategorySchema = mongoose.Schema({
  id: {
    type : String,
    trim: true,
    unique: true,
    required: true
  },
  label: {
    type: String,
    required: true
  }
})

module.exports = mongoose.model('NodeCategory', nodeCategorySchema);
