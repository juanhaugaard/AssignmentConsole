// import _ from 'lodash';
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
require.context('./', true, /^\.\/.*\.html$/);
require.context('./style/', true, /^\.\/.*\.css$/);
require.context('./images/', true, /^\.\/.*\.(jpg|png|svg)$/);

/* global fetch */

const fetchSubjects = function () {
  fetch('/api/subjects')
    .then((response) => { return response.json() })
    .then((myJson) => {
      if (!this) {
        console.log('then2 this is undefined');
      }
      if (!myJson) {
        console.log('then2 myJson is undefined');
      } else {
        console.log('retrieved JSON=' + JSON.stringify(myJson));
        console.log('current state =' + JSON.stringify(this.state));
        var newState = Object.assign({}, this.state);
        console.log('copied state  =' + JSON.stringify(newState));
        newState.users = myJson;
        console.log('mod new state =' + JSON.stringify(newState));
        this.setState(newState);
      }
    });
};

class Title extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (<div><center><h1>Assignment Console</h1></center></div>);
  }
}

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      selectedUser: null,
      privileges: [],
      selectedPrivileges: [],
      scopes: [],
      selectedScope: null,
      assignemnts: [],
      selectedAssignment: null
    };
    // this.fetchSubjects = fetchSubjects.bind(this);
  }

  componentDidMount() {
    // this.fetchSubjects();
    fetchSubjects.bind(this)();
  }

  render() {
    return (
      <div>
        <Title />
        <div> 'Users: ' + JSON.stringify({this.state.users}) </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.querySelector('.container'));
