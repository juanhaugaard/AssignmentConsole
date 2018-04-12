import React, { Component } from 'react';

class User extends Component {
    render() {
        <option value={props.key}>{props.value}</option>
    }
}


function userList(users) {
    if (!users) {
        return 'users is undefined';
    } else { 
        return (
            users.map(
                user => {
                    return (
                        <User>
                            key={user.identifier},
                            value={user.identifier}
                        </User>
                    );
                }
            )
        )
    }
}

class Users extends Component {
    constructor(props) {
        super(props);
        console.log('Users constructor called, props=' + this.props);
    }

    render() {
        var propsDefined = 'true';
        if (!this.props)
            propsDefined = 'false';
        return (
            <div className='search-bar'>
                Users component rendered {propsDefined}
                {<select>
                    {userList(this.props.users)}
                </select>}
            </div>
        );
    }
}

export default Users;