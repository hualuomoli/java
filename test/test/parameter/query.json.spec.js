var assert = require('assert');
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent('http://localhost:80/web');

describe('test java security', function () {

  it('should login with json', function (done) {

    request
      .post('/login')
      .send({
        username: 'admin',
        password: 'admin'
      })
      .set('content-type', 'application/json;charset=utf-8')
      .expect(200)
      .expect(function (res) {
        console.log(res.headers);
      })
      .end(done);

  });



});