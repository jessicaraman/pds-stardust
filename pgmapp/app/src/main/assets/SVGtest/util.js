function toggleClass (elementDOM, className) {
    if (elementDOM.classList.contains(className)) {
      elementDOM.classList.remove(className);
    } else {
      elementDOM.classList.add(className);
    }
  };
  module.exports = toggleClass;