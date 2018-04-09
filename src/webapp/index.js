import _ from "lodash";
import React, { Component } from "react";
import ReactDOM from "react-dom";
require.context("./", true, /^\.\/.*\.html/);
require.context("./style/", true, /^\.\/.*\.css/);

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
         <center><h1>Assignment Console</h1></center>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.querySelector(".container"));
