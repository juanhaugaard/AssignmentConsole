import _ from "lodash";
import React, { Component } from "react";
import ReactDOM from "react-dom";
require.context("./", true, /^\.\/.*\.html$/);
require.context("./style/", true, /^\.\/.*\.css$/);
require.context("./images/", true, /^\.\/.*\.(jpg|png|svg)$/);

const fetchSubjects = () => {
  fetch('/api/subjects')
    .then(function (response) {
      return response.json();
    })
    .then(function (myJson) {
      console.log(myJson);
    });
}

class Title extends Component {
  constructor(props) {
    super(props);
  }
  componentDidMount() {
    var setState = this.setState;

    fetch('/api/subjects')
      .then(function (response) {
        return response.json();
      })
      .then(function (myJson) {
        console.log(myJson);
        setState((prevState, props) => {
          var newState = Object.assign({}, prevState);
          newState.users = myJson;
          return newState;
        }
        );
      });
  }
  render() {
    return (<center><h1>Assignment Console</h1></center>);
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
  }

  render() {
    return (
      <div>
        <div> <Title /> </div>
        <div> "Users: " + JSON.stringify({this.state.users}) </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.querySelector(".container"));
