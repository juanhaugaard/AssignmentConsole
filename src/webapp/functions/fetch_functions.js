
const SUBJECTS_URL = '/api/subjects';
const SCOPES_URL = '/api/scopes';

const fetchSubjects = function (subjectsCallback) {
  fetch(SUBJECTS_URL)
    .then((response) => { return response.json() })
    .then((json) => subjectsCallback(json));
};

const fetchScopes = function (scopesCallback) {
  fetch(SCOPES_URL)
    .then((response) => { return response.json() })
    .then((json) => scopesCallback(json));
};

export { fetchSubjects, fetchScopes };
