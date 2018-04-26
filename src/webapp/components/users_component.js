import React, { Component } from 'react';
import { fetchSubjects } from '../functions/fetch_functions';
import PropTypes from 'prop-types';

class User extends Component {
  constructor(props) {
    super(props);
    console.log('User.constructor called');
  }

  render() {
    if (!this.props.value) {
      console.warn('No users defined');
    } else {
      return (
        <option value={this.props.value}>
          {this.props.value}
        </option>
      );
    }
  }
}

User.propTypes = {
  value: PropTypes.string.isRequired
};

function userList(users) {
  if (!users) {
    console.error('parameter "users" is undefined');
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
    console.log('Users.constructor called');
  }

  componentDidMount() {
    fetchSubjects((subjectsJson) => {
      // create copy of current state
      var newState = Object.assign({}, this.state);
      // update new state with changed attribute(s)
      newState.users = subjectsJson;
      // set state to be the new state
      this.setState(newState);
      // callback to let parent know which user is cuurently selected
      if (subjectsJson[0])
        this.props.selectedUserCallback(subjectsJson[0]);
    });
  }

  findUserByIdentifier(identifier) {
    return this.state.users.find((user) => { user.identifier == identifier });
  }

  handleChangeFnc(event) {
    // callback to let parent know which user is cuurently selected
    let foundUser = this.state.users.find(user => user.identifier == event.target.value);
    if (!foundUser) {
      console.log('User changed: ' + 'No user found');
    } else {
      console.log('User changed: ' + foundUser.identifier);
      this.props.selectedUserCallback(foundUser);
    }
  }

  /**
   * React render function
   * 
   * @returns JSX script to be rendered
   * @memberof Users
   */
  render() {
    return (
      <select
        value={this.props.selectedUser.identifier}
        id='usersSelector'
        className='btn btn-primary default-margin'
        onChange={this.handleChangeFnc.bind(this)} >
        {userList(this.state.users)}
      </select>
    );
  }
}

Users.propTypes = {
  selectedUser: PropTypes.shape({ identifier: '', type: '' }).isRequired,
  selectedUserCallback: PropTypes.func.isRequired
};

export default Users;
