// import _ from 'lodash';
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Title from './components/title_component'
import Users from './components/users_component'
require.context('./', true, /^\.\/.*\.html$/);
require.context('./style/', true, /^\.\/.*\.css$/);
require.context('./images/', true, /^\.\/.*\.(jpg|png|svg)$/);
/**
 * 
 * 
 * @class App
 * @extends {Component}
 */
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      // users: [],
      // privileges: [],
      // scopes: [],
      // assignemnts: [],
      selectedPrivileges: [],
      selectedAssignment: null,
      selectedScope: null,
      selectedUser: 'Test'
    };
  }

  /**
   * callback function to update state with new selected user
   *
   * @param {any} selectedUser the id of the new selected user
   * @memberof App
   */
  setSelectedUserCallback(selectedUser) {
    // create copy of current state
    var newState = Object.assign({}, this.state); 
    // update new state with changed attribute(s)
    newState.selectedUser = selectedUser;        
    // set state to be the new state
    this.setState(newState);                     
  }

  render() {
    return (
      <div>
        <Title />
        <div>
          <span>Users:</span>
          <Users
            selectedUser={this.state.selectedUser}
            setSelectedUserCallback={this.setSelectedUserCallback.bind(this)}
          />
          <span className='default-margin'>ROOT selected user: {this.state.selectedUser} </span>
        </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.querySelector('.container'));
