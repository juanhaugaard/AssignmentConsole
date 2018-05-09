
const SUBJECTS_URL = '/api/subjects';
const SCOPES_URL = '/api/scopes';

function fetchSubjects(subjectsCallback) {
  fetch(SUBJECTS_URL)
    .then(response => { return response.json() })
    .then(json => subjectsCallback(json));
}

function fetchScopes(scopesCallback) {
  fetch(SCOPES_URL)
    .then(response => { return response.json() })
    .then(json => scopesCallback(json));
}

export { fetchSubjects, fetchScopes };
