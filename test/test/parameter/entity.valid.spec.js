var assert = require('assert');
var should = require('should');
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent('http://localhost:80/web');

describe('test entity valid', function () {

  it('should login with valid entity parameter', function (done) {

    request
      .post('/login')
      .send('username=admin')
      .send('password=admin')
      .expect(200)
      .expect(function (res) {
        res.headers.should.have.property('token');
      })
      .end(done);

  });

  it('should login with outlength username parameter', function (done) {

    request
      .post('/login')
      .send('username=admin123')
      .send('password=admin')
      .expect(400)
      .expect(function (res) {
        res.body.code.should.equal('9979');
      })
      .end(done);

  });

  it('should login with outlength username password parameter', function (done) {

    request
      .post('/login')
      .send('username=admin123')
      .send('password=')
      .expect(400)
      .expect(function (res) {
        res.body.code.should.equal('9979');
        console.log(res.body);
      })
      .end(done);

  });



});