import React, { Component } from 'react';
import { fetchSubjects } from '../functions/fetch_subjects';

class User extends Component {
  render() {
    return <option value={this.props.value}>{this.props.value}</option>;
  }
}

function userList(users, selectedUser) {
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
    fetchSubjects((subjectsJson) => {
      console.log('subjects: ' + subjectsJson);
      var newState = Object.assign({}, this.state);
      newState.users = subjectsJson;
      this.setState(newState);
    });
  }

  handleChangeFnc(event) {
    console.log('handleChange called: ' + event.target.value);
    var newState = Object.assign({}, this.state);
    newState.selectedUser = event.target.value;
    this.setState(newState);
    this.props.setSelectedUserCallback(event.target.value);
  }

  render() {
    return (
      <span>
        <select
          style={{marginLeft: 10 + 'px', marginRight: 10 + 'px'}}
          className = 'figure'
          onChange={this.handleChangeFnc.bind(this)} >
          {userList(this.state.users, this.props.selectedUser)}
        </select>
        Selected user: {this.state.selectedUser}
      </span>
    );
  }
}

export default Users;
