
/**
 * callback function to update state with new selected user
 *
 * @param {any} selectedUser the id of the new selected user
 * @memberof App
 */
const selectedUserCallback = function (selectedUser) {
  // create copy of current state
  var newState = Object.assign({}, this.state);
  // update new state with changed attribute(s)
  newState.selectedUser = selectedUser;
  // set state to be the new state
  this.setState(newState);
}

/**
 * callback function to update state with new selected scope
 * 
 * @param {any} selectedScope the id of the new selected scope
 * @memberof App
 */
const selectedScopeCallback = function (selectedScope) {
  // create copy of current state
  var newState = Object.assign({}, this.state);
  // update new state with changed attribute(s)
  newState.selectedScope = selectedScope;
  // set state to be the new state
  this.setState(newState);
}

export { selectedUserCallback, selectedScopeCallback };