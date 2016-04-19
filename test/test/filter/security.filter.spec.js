var assert = require('assert');
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent('http://localhost:80/web');

describe('test java security', function () {

  var token;

  it('should login with invalid username or password', function (done) {

    request
      .post('/login')
      .send('username=admin')
      .send('password=admin123')
      .expect(500)
      .expect(function (res) {
        assert.equal(res.headers.code, '1');
        assert.equal(res.headers.msg, 'user invalid');
      })
      .end(done);

  });


  it('should login and get token', function (done) {

    request
      .post('/login')
      .send('username=admin')
      .send('password=admin')
      .expect(200)
      .expect(function (res) {
        token = res.headers.token;
        console.log(token);
        console.log(res.text);
      })
      .end(done);

  });

  it('should get user message by no token', function (done) {
    request
      .get('/a/user/admin')
      .expect(401)
      .expect(function (res) {
        assert.equal(res.headers.code, '2');
        assert.equal(res.headers.msg, 'user no login');
        console.log(res.body);
      })
      .end(done);
  });

  it('should get user message by invalid token', function (done) {
    request
      .get('/a/user/admin')
      .set('token', 'abcd')
      .expect(401)
      .expect(function (res) {
        console.log(res.body);
      })
      .end(done);
  });

  it('should get user message by invalid token', function (done) {
    request
      .get('/a/user/admin')
      .set('token', token)
      .expect(200)
      .expect(function (res) {
        console.log(res.text);
      })
      .end(done);
  });

  it('should logout by invalid token', function (done) {
    request
      .get('/logout')
      .set('token', 'abcd')
      .expect(200)
      .expect(function (res) {})
      .end(done);
  });

  it('should get user message by no logout', function (done) {
    request
      .get('/a/user/admin')
      .set('token', token)
      .expect(200)
      .expect(function (res) {
        console.log(res.text);
      })
      .end(done);
  });

  it('should logout by valid token', function (done) {
    request
      .get('/logout')
      .set('token', token)
      .expect(200)
      .expect(function (res) {})
      .end(done);
  });

  it('should get user message by logout', function (done) {
    request
      .get('/a/user/admin')
      .set('token', token)
      .expect(401)
      .expect(function (res) {
        console.log(res.body);
      })
      .end(done);
  });



});