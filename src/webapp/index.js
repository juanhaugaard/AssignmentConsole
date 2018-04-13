// import _ from 'lodash';
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { fetchSubjects } from './functions/fetch_subjects';
import Title from './components/title_component'
import Users from './components/users_component'
require.context('./', true, /^\.\/.*\.html$/);
require.context('./style/', true, /^\.\/.*\.css$/);
require.context('./images/', true, /^\.\/.*\.(jpg|png|svg)$/);

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      selectedUser: '**',
      privileges: [],
      selectedPrivileges: [],
      scopes: [],
      selectedScope: null,
      assignemnts: [],
      selectedAssignment: null
    };
  }

  componentDidMount() {
    // fetchSubjects.bind(this)();
    fetchSubjects((subjectsJson) => {
      console.log('subjects: ' + subjectsJson);
      var newState = Object.assign({}, this.state);
      newState.users = subjectsJson;
      this.setState(newState);
    });
  }

  render() {
    return (
      <div>
        <Title />
        <div>
          <span>Users:</span>
          <Users
            users={this.state.users}
            selectedUser={this.state.selectedUser}
            setState={this.setState}
            state={this.state}
          />
        </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.querySelector('.container'));
