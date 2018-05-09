import React, { Component } from 'react';
import { fetchScopes } from '../functions/fetch_functions';
import PropTypes from 'prop-types';

class Scope extends Component {
  constructor(props) {
    super(props);
    console.log('Scope.constructor called');
  }

  render() {
    if (this.props.value)
      return (
        <option value={this.props.value.id}>
          {this.props.value.name}
        </option>
      );
    else {
      console.warn('Scope.render() No scope defined');
      return <option>No scope defined</option>;
    }
  }
}

Scope.propTypes = {
  value: PropTypes.shape({ id: '', name: '' }).isRequired
};

function scopeList(scopes) {
  if (!scopes) {
    console.error('scopeList() parameter "scopes" is undefined');
    return null;
  } else {
    return (
      scopes.map(
        scope => {
          return (
            <Scope
              key={scope.id}
              value={scope}
            />
          );
        }
      )
    )
  }
}

class Scopes extends Component {
  constructor(props) {
    super(props);
    this.state = { scopes: [] };
    console.log('Scopes.constructor called');
  }

  componentDidMount() {
    fetchScopes((json) => {
      if (!json) {
        console.warn('scopes json is undefined');
      } else {
        // create copy of current state
        let newState = Object.assign({}, this.state);
        // update new state with changed attribute(s)
        newState.scopes = json;
        // set state to be the new state
        this.setState(newState);
        // callback to let parent know which user is cuurently selected
        if (json.length > 0)
          this.props.selectedScopeCallback(json[0]);
      }
    });
  }

  handleChangeFnc(event) {
    let foundScope = this.state.scopes.find(scope => scope.id === event.target.value);
    if (!foundScope) {
      console.warn('Scope changed: ' + 'scope not found');
    } else {
      console.log('Scope changed: ' + foundScope.name);
      // callback to let parent know which scope is cuurently selected
      this.props.selectedScopeCallback(foundScope);
    }
  }

  /**
   * React render function
   * 
   * @returns JSX script to be rendered
   * @memberof Scopes
   */
  render() {
    return (
      <select
        value={this.props.selectedScope.id}
        id='scopesSelector'
        className='btn btn-info btn-sm default-margin'
        onChange={this.handleChangeFnc.bind(this)} >
        {scopeList(this.state.scopes)}
      </select>
    );
  }
}

Scopes.propTypes = {
  selectedScope: PropTypes.shape({ id: '', name: '' }).isRequired,
  selectedScopeCallback: PropTypes.func.isRequired
};

export default Scopes;
