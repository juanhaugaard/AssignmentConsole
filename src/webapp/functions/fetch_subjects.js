
const SUBJECTS_URL = '/api/subjects';

/**
 * this function needs to be bound to a react component
 * because it calls this.setState()
 */
const fetchSubjects = function () {
  fetch('/api/subjects')
    .then((response) => { return response.json() })
    .then((myJson) => {
      if (!this) {
        console.error('then2 this is undefined');
      }
      if (!myJson) {
        console.error('then2 myJson is undefined');
      } else {
        console.log('retrieved JSON=' + JSON.stringify(myJson));
        console.log('current state =' + JSON.stringify(this.state));
        var newState = Object.assign({}, this.state);
        console.log('copied state  =' + JSON.stringify(newState));
        newState.users = myJson;
        console.log('mod new state =' + JSON.stringify(newState));
        this.setState(newState);
      }
    });
};

const fetchSubjectsWithCallback = function (subjectsCallback) {
  fetch(SUBJECTS_URL)
    .then((response) => { return response.json() })
    .then((subjectsJson) => subjectsCallback(subjectsJson));
};

// export { fetchSubjects as fetchSubjects };
export { fetchSubjectsWithCallback as fetchSubjects };
