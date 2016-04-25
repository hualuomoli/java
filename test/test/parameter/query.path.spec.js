var assert = require('assert');
var should = require('should');
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent('http://localhost:80/web');

describe('test path parameter', function () {

  before(function (done) {
    request
      .post('/login')
      .send({
        username: 'admin',
        password: 'admin'
      })
      .expect(200)
      .end(done);
  });

  it('should auto set uri parameter data', function (done) {

    request
      .post('/auth/user/admin123')
      .expect(function (res) {
        console.log(res.body);
      })
      .end(done);

  });



});