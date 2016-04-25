var assert = require('assert');
var should = require('should');
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent('http://localhost:80/web');

describe('test java security', function () {

  var token;
  var cookie;

  it('should login with invalid username or password', function (done) {

    request
      .post('/login')
      .send('username=admin')
      .send('password=admin123')
      .expect(200)
      .expect(function (res) {
        should(res.body).have.property('code', '9989');
        res.body.should.have.property('code', '9989');

        res.body.msg.should.equal('用户名或密码错误');

        assert(res.body.code, '9989')
        assert(res.body.msg, '用户名或密码错误')
      })
      .end(done);

  });


  it('should login and get token', function (done) {

    request
      .post('/login')
      .send('username=admin')
      .send('password=admin')
      .set('accept', 'application/json')
      .expect(200)
      .expect(function (res) {
        res.headers.should.have.property('token');
        res.headers.should.not.have.property('Token');

        res.headers.should.have.property('set-cookie');

        token = res.headers.token;
        cookie = res.headers['set-cookie'];
      })
      .end(done);

  });

  it('should get user message when has logged', function (done) {
    request
      .get('/auth/user/admin')
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
        res.body.should.have.property('id');
        res.body.should.not.have.property('UUID');
        res.body.should.type('object')
      })
      .end(done);
  });

  it('should get user message use empty security', function (done) {
    request
      .get('/auth/user/admin')
      .set('Cookie', '')
      .expect(401)
      .expect(function (res) {
        res.body.code.should.equal('9999');
        res.body.msg.should.equal('未登录,请先登录');
      })
      .end(done);
  });

  it('should get user message by empty cookie valid header', function (done) {
    request
      .get('/auth/user/admin')
      .set('Cookie', 's=abcd')
      .set('token', token)
      .expect(200)
      .expect(function (res) {
        res.body.should.type('object');
        res.body.id.should.equal('admin');
        res.body.id.should.not.equal('admin123');
      })
      .end(done);
  });

  it('should get user message by invalid security', function (done) {
    request
      .get('/auth/user/admin')
      .set('Cookie', 'token=abcd')
      .expect(401)
      .expect(function (res) {
        res.body.should.type('object');
        res.body.should.have.property('code', '9998')
        res.body.msg.should.be.equal('登录超时,请重新登录')
      })
      .end(done);
  });

  it('should get user message by invalid cookie valid header', function (done) {
    request
      .get('/auth/user/admin')
      .set('Cookie', 'token=abcd')
      .set('token', token)
      .expect(401)
      .end(done);
  });



  it('should get user message by valid security', function (done) {
    request
      .get('/auth/user/admin')
      .set('Cookie', cookie)
      .expect(200)
      .end(done);
  });

  it('should logout by invalid token', function (done) {
    request
      .get('/logout')
      .set('Cookie', 'token=fffffffffff')
      .expect(200)
      .end(done);
  });

  it('should get user message by no logout', function (done) {
    request
      .get('/auth/user/admin')
      .expect(200)
      .end(done);
  });

  it('should logout by valid token', function (done) {
    request
      .get('/logout')
      .set('Cookie', cookie)
      .expect(200)
      .end(done);
  });

  it('should get user message by logout', function (done) {
    request
      .get('/auth/user/admin')
      .expect(401)
      .end(done);
  });



});