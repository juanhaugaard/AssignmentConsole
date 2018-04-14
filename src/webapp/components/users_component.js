import React, { Component } from 'react';
import { fetchSubjects } from '../functions/fetch_subjects';
import PropTypes from 'prop-types';

class User extends Component {
  render() {
    return <option value={this.props.value}>{this.props.value}</option>;
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
        this.props.selectedUserCallback(subjectsJson[0].identifier);
    });
  }

  handleChangeFnc(event) {
    console.log('handleChange called: ' + event.target.value);
    // callback to let parent know which user is cuurently selected
    this.props.selectedUserCallback(event.target.value);
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
        value={this.props.selectedUser}
        id='usersSelector'
        className='default-margin'
        onChange={this.handleChangeFnc.bind(this)} >
        {userList(this.state.users)}
      </select>
    );
  }
}

Users.propTypes = {
  selectedUser: PropTypes.string.isRequired,
  selectedUserCallback: PropTypes.func.isRequired
};

export default Users;
