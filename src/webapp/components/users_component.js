import React, { Component } from 'react';
import { fetchSubjects } from './functions/fetch_subjects';

class User extends Component {
  render() {
    return <option value={this.props.value}>{this.props.value}</option>;
  }
}

function userList(users) {
  if (!users) {
    console.log('parameter "users" is undefined');
    return null;
  } else {
    return (
      users.map(
        user => {
          return (
            <User
              key={user.identifier}
              value={user.identifier}
            />
          );
        }
      )
    )
  }
}

class Users extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [] };
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

  handleChangeFnc(event) {
    console.log('handleChange called: ' + event.target.value);
    var newState = Object.assign({}, this.props.state);
    newState.selectedUser = event.target.value;
    this.setState(newState);
  }

  render() {
    this.handleChange = this.handleChangeFnc.bind(this);
    return (
      <span>
        <select
          style={{marginLeft: 10 + 'px', marginRight: 10 + 'px'}}
          className = 'figure'
          onChange={this.handleChange} >
          {userList(this.props.users)}
        </select>
        Selected user: {this.props.selectedUser}
      </span>
    );
  }
}

export default Users;
