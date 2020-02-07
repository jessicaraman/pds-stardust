function toggleClass (elementDOM, className) {
    if (elementDOM.classList.contains(className)) {
      elementDOM.classList.remove(className);
    } else {
      elementDOM.classList.add(className);
    }
  };


  var activeAreaPath = function(DOMid){
        DOMid.classList.add('is-active');
}

module.exports = {
  activeAreaPath:activeAreaPath,
  toggleClass:toggleClass}
  ;

