//
var request = require('supertest').agent('http://localhost:80/web');

module.exports = request;