var should = require('should')
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent(require('../app').listen());
// var request = require('supertest').agent('http://localhost:3000');

describe('登录登出', function () {

  var user = {
    username: 'admin',
    password: 'admin'
  };

  it('登录', function (done) {

    request
      .post('/login')
      .set('content-type', 'application/x-www-form-urlencoded')
      .send(user)
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
      })
      .end(done);

  });


  it('登录-使用错误密码', function (done) {
    // 修改密码为错误的数据
    user.password = '123456';

    request
      .post('/login')
      .set('content-type', 'application/x-www-form-urlencoded')
      .send(user)
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(9);
      })
      .end(done);

  });

  it('登录-用户不存在', function (done) {
    // 修改密码为错误的数据
    user.username = '123456';

    request
      .post('/login')
      .set('content-type', 'application/x-www-form-urlencoded')
      .send(user)
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(9);
      })
      .end(done);

  });

  it('登出', function (done) {

    request
      .post('/logout')
      .set('content-type', 'application/x-www-form-urlencoded')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
      })
      .end(done);

  });

});