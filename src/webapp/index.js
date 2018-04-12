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
            selectedUser: null,
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
                <span>
                    <div>Users:</div>
                    <Users>
                        users = {this.state.users}
                    </Users>
                </span>
            </div>
        );
    }
}

ReactDOM.render(<App />, document.querySelector('.container'));
