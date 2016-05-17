var should = require('should')
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent(require('../app').listen());
// var request = require('supertest').agent('http://localhost:3000');

describe('用户管理', function () {

  var user = {
    username: 'admin',
    password: 'admin'
  };

  it('未登录获取用户信息', function (done) {

    request
      .get('/user/info')
      .expect(401)
      .expect(function (res) {
        res.body.code.should.equal(0);
        res.body.msg.should.equal('no permission');
      })
      .end(done);

  });

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


  it('登录后获取用户信息', function (done) {

    request
      .get('/user')
      .expect(200)
      .expect(function (res) {
        res.body.username.should.equal(user.username);
        res.body.nickname.should.equal('花落莫离');

        res.headers.should.have.property('set-cookie');
      })
      .end(done);

  });

  it('修改用户信息', function (done) {

    request
      .put('/user')
      .send('address=山东省青岛市')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
        res.body.msg.should.equal('update user success');

        res.headers.should.have.property('set-cookie');
      })
      .end(done);

  });

  it('用户登出', function (done) {

    request
      .post('/logout')
      .set('content-type', 'application/x-www-form-urlencoded')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
      })
      .end(done);

  });

  it('登出后获取用户信息', function (done) {

    request
      .get('/user/info')
      .expect(401)
      .expect(function (res) {
        res.body.code.should.equal(0);
        res.body.msg.should.equal('no permission');
      })
      .end(done);

  });

});