// import _ from 'lodash';
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Title from './components/title_component';
import Users from './components/users_component';
import Scopes from './components/scopes_component';
import { selectedUserCallback, selectedScopeCallback } from './functions/select_callbacks';
require.context('./', true, /^\.\/.*\.html$/);
require.context('./style/', true, /^\.\/.*\.css$/);
require.context('./images/', true, /^\.\/.*\.(jpg|png|svg)$/);

/**
 * Main class of the application
 * 
 * @class App
 * @extends {Component}
 */
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      // I don't know if we will need these fields up at this level
      // even though I know they are needed at lower levels.
      // users: [],
      // privileges: [],
      // scopes: [],
      // assignemnts: [],
      // These fields are required
      selectedPrivileges: [],
      selectedAssignment: { id: '', scopes: [], privileges: [] },
      selectedScope: { id: '', name: '' },
      selectedUser: { identifier: '', type: '' }
    };
  }

  /**
   * React render function
   * 
   * @returns JSX script to be rendered
   * @memberof App
   */
  render() {
    return (
      <div>
        <Title />
        <div>
          <span className='default-margin'>Users:</span>
          <Users
            className='default-margin'
            selectedUser={this.state.selectedUser}
            selectedUserCallback={selectedUserCallback.bind(this)}
          />
          <span className='default-margin'>
            selected user: {this.state.selectedUser ? this.state.selectedUser.identifier : 'undefined'}
          </span>
        </div>
        <div>
          <span className='default-margin'>Scopes:</span>
          <Scopes
            className='default-margin'
            selectedScope={this.state.selectedScope}
            selectedScopeCallback={selectedScopeCallback.bind(this)}
          />
          <span className='default-margin'>
            selected scope: {this.state.selectedScope ? this.state.selectedScope.name : 'undefined'}
          </span>
        </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.querySelector('.container'));
