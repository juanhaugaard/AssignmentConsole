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
    this.state = { };
    this.state.users = [];
    this.state.selectedUser = props.selectedUser;
  }

  componentDidMount() {
    fetchSubjects((subjectsJson) => {
      console.log('subjects: ' + subjectsJson);
      var newState = Object.assign({}, this.state);
      newState.users = subjectsJson;
      newState.selectedUser = subjectsJson[0].identifier;
      this.setState(newState);
      this.props.setSelectedUserCallback(newState.selectedUser);
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
          value={this.props.selectedUser}
          id='usersSelector'
          className='default-margin'
          onChange={this.handleChangeFnc.bind(this)} >
          {userList(this.state.users)}
        </select>
        <span className='default-margin'>Selected user: {this.state.selectedUser}</span>
      </span>
    );
  }
}

export default Users;
