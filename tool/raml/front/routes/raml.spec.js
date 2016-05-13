var should = require('should')
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent(require('../app').listen());
// var request = require('supertest').agent('http://localhost:3000');

describe('Raml的基础维护', function () {

  var id = ''; // 
  var raml = {}; // 

  // 测试使用数据
  var data = {
    "queryParams": [{
      "level": 0,
      "index": 0,
      "blanks": [],
      "displayName": "style",
      "description": "收货方式",
      "type": "string",
      "required": true,
      "children": []
    }, {
      "level": 0,
      "index": 1,
      "blanks": [],
      "displayName": "address",
      "description": "收货地址",
      "type": "object",
      "required": true,
      "children": [{
        "level": 1,
        "index": 2,
        "blanks": [0],
        "displayName": "id",
        "description": "收货地址ID",
        "type": "string",
        "required": true,
        "rule": {
          "example": "00000001",
          "minLength": "1",
          "maxLength": "20"
        },
        "children": []
      }, {
        "level": 1,
        "index": 3,
        "blanks": [0],
        "displayName": "province",
        "description": "省",
        "type": "string",
        "rule": {
          "example": "山东省"
        },
        "children": []
      }, {
        "level": 1,
        "index": 4,
        "blanks": [0],
        "displayName": "city",
        "description": "市",
        "type": "string",
        "rule": {
          "example": "青岛市"
        },
        "children": []
      }, {
        "level": 1,
        "index": 5,
        "blanks": [0],
        "displayName": "county",
        "description": "县",
        "type": "string",
        "rule": {
          "example": "市北区"
        },
        "children": []
      }, {
        "level": 1,
        "index": 6,
        "blanks": [0],
        "displayName": "town",
        "description": "镇",
        "type": "string",
        "required": false,
        "rule": {
          "example": "合肥路街道"
        },
        "children": []
      }, {
        "level": 1,
        "index": 7,
        "blanks": [0],
        "displayName": "village",
        "description": "村",
        "type": "string",
        "required": false,
        "rule": {
          "example": "288号佳世客"
        },
        "children": []
      }]
    }, {
      "level": 1,
      "index": 2,
      "blanks": [0],
      "displayName": "id",
      "description": "收货地址ID",
      "type": "string",
      "required": true,
      "rule": {
        "example": "00000001",
        "minLength": "1",
        "maxLength": "20"
      },
      "children": []
    }, {
      "level": 1,
      "index": 3,
      "blanks": [0],
      "displayName": "province",
      "description": "省",
      "type": "string",
      "rule": {
        "example": "山东省"
      },
      "children": []
    }, {
      "level": 1,
      "index": 4,
      "blanks": [0],
      "displayName": "city",
      "description": "市",
      "type": "string",
      "rule": {
        "example": "青岛市"
      },
      "children": []
    }, {
      "level": 1,
      "index": 5,
      "blanks": [0],
      "displayName": "county",
      "description": "县",
      "type": "string",
      "rule": {
        "example": "市北区"
      },
      "children": []
    }, {
      "level": 1,
      "index": 6,
      "blanks": [0],
      "displayName": "town",
      "description": "镇",
      "type": "string",
      "required": false,
      "rule": {
        "example": "合肥路街道"
      },
      "children": []
    }, {
      "level": 1,
      "index": 7,
      "blanks": [0],
      "displayName": "village",
      "description": "村",
      "type": "string",
      "required": false,
      "rule": {
        "example": "288号佳世客"
      },
      "children": []
    }, {
      "level": 0,
      "index": 8,
      "blanks": [],
      "displayName": "products",
      "description": "购买商品",
      "type": "array",
      "required": true,
      "children": [{
        "level": 1,
        "index": 9,
        "blanks": [0],
        "displayName": "id",
        "description": "商品编码",
        "type": "string",
        "required": true,
        "rule": {
          "example": "0000000002",
          "minLength": "1",
          "maxLength": "32"
        },
        "children": []
      }, {
        "level": 1,
        "index": 10,
        "blanks": [0],
        "displayName": "name",
        "description": "商品名称",
        "type": "string",
        "required": true,
        "rule": {
          "example": "IPAD",
          "minLength": "3",
          "maxLength": "32"
        },
        "children": []
      }, {
        "level": 1,
        "index": 11,
        "blanks": [0],
        "displayName": "price",
        "description": "商品单价",
        "type": "number",
        "required": true,
        "rule": {
          "example": "6666"
        },
        "children": []
      }, {
        "level": 1,
        "index": 12,
        "blanks": [0],
        "displayName": "amount",
        "description": "购买数量",
        "type": "integer",
        "required": true,
        "children": []
      }]
    }, {
      "level": 1,
      "index": 9,
      "blanks": [0],
      "displayName": "id",
      "description": "商品编码",
      "type": "string",
      "required": true,
      "rule": {
        "example": "0000000002",
        "minLength": "1",
        "maxLength": "32"
      },
      "children": []
    }, {
      "level": 1,
      "index": 10,
      "blanks": [0],
      "displayName": "name",
      "description": "商品名称",
      "type": "string",
      "required": true,
      "rule": {
        "example": "IPAD",
        "minLength": "3",
        "maxLength": "32"
      },
      "children": []
    }, {
      "level": 1,
      "index": 11,
      "blanks": [0],
      "displayName": "price",
      "description": "商品单价",
      "type": "number",
      "required": true,
      "rule": {
        "example": "6666"
      },
      "children": []
    }, {
      "level": 1,
      "index": 12,
      "blanks": [0],
      "displayName": "amount",
      "description": "购买数量",
      "type": "integer",
      "required": true,
      "children": []
    }],
    "responseParams": [{
      "level": 0,
      "index": 0,
      "blanks": [],
      "displayName": "msg",
      "description": "响应编码",
      "type": "integer",
      "rule": {
        "example": "0",
        "minimum": "1",
        "maximum": "3"
      },
      "required": true
    }, {
      "level": 0,
      "index": 1,
      "blanks": [],
      "displayName": "msg",
      "description": "响应消息",
      "type": "string",
      "rule": {
        "example": "处理成功",
        "minLength": "0",
        "maxLength": "200"
      },
      "required": false
    }, {
      "level": 0,
      "index": 2,
      "blanks": [],
      "type": "object",
      "displayName": "data",
      "description": "订单信息",
      "required": true
    }, {
      "level": 1,
      "index": 3,
      "blanks": [0],
      "displayName": "orderID",
      "description": "订单ID",
      "type": "string",
      "required": true,
      "rule": {
        "example": "201605141235260000001",
        "minLength": "1",
        "maxLength": "32"
      }
    }, {
      "level": 1,
      "index": 4,
      "blanks": [0],
      "displayName": "addressID",
      "description": "收货地址ID",
      "type": "string",
      "required": true,
      "rule": {
        "minLength": "1",
        "maxLength": "20",
        "example": "00000002"
      }
    }],
    "url": "/u/{usercode}/order",
    "queryMimeType": "application/json",
    "method": "post",
    "responseMimeType": "application/json",
    "description": "下订单"
  };



  it('添加数据', function (done) {

    var url = Math.random().toString(36).substr(2);

    request
      .post('/raml')
      .set('content-type', 'application/json')
      .send(data)
      .expect(200)
      .expect(function (res) {
        should.exist(res.body.id);
        id = res.body.id;
      })
      .end(done);

  });

  it('获取数据', function (done) {

    request
      .get('/raml/' + id)
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
        raml = res.body;
      })
      .end(done);

  });

  it('修改数据', function (done) {

    raml.description = "修改描述信息";

    request
      .put('/raml/' + id)
      .set('content-type', 'application/json')
      .send(raml)
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
      })
      .end(done);

  });

  it('查询列表', function (done) {
    request
      .get('/raml')
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
        var datas = res.body;
        datas.length.should.not.equal(0);
      })
      .end(done);
  });

  // it('删除数据', function (done) {

  //   request
  //     .delete('/raml/' + id)
  //     .expect(200)
  //     .expect(function (res) {
  //       res.body.should.not.be.empty();
  //     })
  //     .end(done);

  // });


});