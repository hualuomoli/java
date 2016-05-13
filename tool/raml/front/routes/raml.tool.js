var logger = require('../logger/logger');



// get method
function getRaml(resource) {

  var content = '';
  // header
  content += getHeader();
  // line
  content += '\n\n';
  // method head
  content += getMethodHead(resource);
  // parameter
  switch (resource.method) {
  case 'get':
    content += getGETParameters(resource);
    break;
  case 'post':
  case 'put':
    switch (resource.queryMimeType) {
    case 'application/x-www-form-urlencoded':
    case 'multipart/form-data':
      content += getPostParameters(resource);
      break;
    case 'application/json':
      content += getPostJSONParameters(resource);
      break;
    default:
      break;
    }
    break;
  case 'delete':
    break;
  default:
    break;
  }
  // response
  content += getResponse(resource);

  return content;

}

// get方法参数
function getGETParameters(resource) {
  var parameters = '';
  var queryParams = resource.queryParams;
  if (queryParams === undefined || queryParams.length === 0) {
    return parameters;
  }
  parameters += getIndent(2) + 'queryParameters:' + '\n';
  for (var i = 0; i < queryParams.length; i++) {
    var queryParameter = queryParams[i] + '\n';
    parameters += getIndent(3) + 'displayName: ' + queryParameter.displayName + '\n';
    parameters += getIndent(4) + 'description: ' + queryParameter.description + '\n';
    parameters += getIndent(4) + 'type: ' + queryParameter.type + '\n';
    // rule
    var rule = queryParameter.rule;
    for (var key in rule) {
      parameters += getIndent(4) + key + ': ' + rule[key] + '\n';
    }
  }
}

// post/put方法参数
function getPostParameters(resource) {
  var body = '';
  var queryParams = resource.queryParams;
  if (queryParams === undefined || queryParams.length === 0) {
    return body;
  }
  // 
  body += getIndent(2) + 'body:' + '\n';
  body += getIndent(3) + resource.queryMimeType + ':' + '\n';
  body += getIndent(4) + 'formParameters:' + '\n';

  for (var i = 0; i < queryParams.length; i++) {
    var queryParameter = queryParams[i] + '\n';
    body += getIndent(5) + 'displayName: ' + queryParameter.displayName + '\n';
    body += getIndent(6) + 'description: ' + queryParameter.description + '\n';
    body += getIndent(6) + 'type: ' + queryParameter.type + '\n';
    // rule
    var rule = queryParameter.rule;
    for (var key in rule) {
      body += getIndent(6) + key + ': ' + rule[key] + '\n';
    }
  }
}

// post/put json方法参数
function getPostJSONParameters(resource) {
  var body = '';
  var queryParams = resource.queryParams;
  if (queryParams === undefined || queryParams.length === 0) {
    return body;
  }
  // 
  var jsons = getJsons(resource.queryParams, 0, 0)
    //
  body += getIndent(2) + 'body:' + '\n';
  body += getIndent(3) + resource.queryMimeType + ':' + '\n';
  body += getIndent(4) + 'example: |' + '\n';
  body += getIndent(5) + getExample(jsons) + '\n';
  body += getIndent(4) + 'schema: |' + '\n';
  body += getIndent(5) + getSchema(jsons) + '\n';

  return body;
}

// 响应
function getResponse(resource) {
  var response = '';
  var responseParams = resource.responseParams;
  if (responseParams === undefined || responseParams.length === 0) {
    return response;
  }
  // 
  var jsons = getJsons(resource.responseParams, 0, 0)
    //
  response += getIndent(2) + 'responses:' + '\n';
  response += getIndent(3) + '200:' + '\n';
  response += getIndent(4) + 'body:' + '\n';

  response += getIndent(5) + resource.queryMimeType + ':' + '\n';
  response += getIndent(6) + 'example: |' + '\n';
  response += getIndent(7) + getExample(jsons) + '\n';
  response += getIndent(6) + 'schema: |' + '\n';
  response += getIndent(7) + getSchema(jsons) + '\n';

  return response;

}

// 获取examp
function getExample(jsons) {
  var str = '';
  //
  str += '{';
  //
  for (var i = 0; i < jsons.length; i++) {
    var json = jsons[i];
    // ,
    if (i > 0) {
      str += ',';
    }
    // key
    str += '"' + json.displayName + '"';
    // :
    str += ':';

    var type = json.type;
    var rule = json.rule || {};
    var example;

    switch (type) {
    case 'object':
      // object
      example = getExample(json.children);
      break;
    case 'array':
      // array
      example = '[' + getExample(json.children) + ']';
      break;
    case 'string':
      // string
      example = '"' + (rule.example ? rule.example : '') + '"';
      break;
    case 'boolean':
      // boolean
      example = rule.example ? rule.example : 'false';
      break;
    case 'date':
      // date
      example = rule.example ? rule.example : '2016-05-14 12:25:36';
      break;
    case 'integer':
      // integer
      example = rule.example ? rule.example : '0';
      break;
    default:
      // default
      example = '"' + (rule.example ? rule.example : '') + '"';
    }
    str += example;

  }

  // 
  str += '}';

  return str;

}

// 获取schema
function getSchema(jsons) {

  var url = 'http://jsonschema.net';

  var str = '';

  str += '{';
  str += '"$schema": "http://json-schema.org/draft-04/schema#",';
  str += '"id": "' + url + '",';
  str += '"type": "object",';
  str += '"properties": ';

  // properties
  str += getSchemaProperties(jsons, url);
  // required
  str += getRequired(jsons);

  str += '}';

  return str;
}

// 获取schema的properties
function getSchemaProperties(jsons, url) {
  var str = '';

  str += '{';

  for (var i = 0; i < jsons.length; i++) {
    if (i > 0) {
      str += ',';
    }
    var json = jsons[i];

    // key
    str += '"' + json.displayName + '"';
    // :
    str += ':';
    // value
    var schema = '';

    var newUrl = url + '/' + json.displayName;
    schema += '{';
    schema += '"id": "' + newUrl + '",';
    schema += '"type": "' + json.type + '",';
    schema += '"description": "' + (json.description ? json.description : '') + '"';
    // type
    switch (json.type) {
    case 'object':
      schema += ',"properties": ' + getSchemaProperties(json.children, newUrl);
      schema += getRequired(json.children);
      break;
    case 'array':
      newUrl += '/0';
      schema += ',"items": {';
      schema += '"id": "' + newUrl + '",';
      schema += '"type": "object",';
      schema += '"properties": ' + getSchemaProperties(json.children, newUrl);
      schema += '}';
      schema += getRequired(json.children);
      break;
    default:
      // default
      var rule = json.rule || {};
      var ruleStr = '';
      for (var key in rule) {
        ruleStr += ',"' + key + '":"' + rule[key] + '"';
      }
      schema += ruleStr;
    }

    schema += '}'; // end schema

    str += schema;
  }

  str += '}';

  return str;
}

// 获取必填
function getRequired(jsons) {
  var str = '';
  str += ',"required": [';
  var required = '';
  for (var i = 0; i < jsons.length; i++) {
    var json = jsons[i];
    if (json.required) {
      if (required.length > 0) {
        required += ',';
      }
      required += '"' + json.displayName + '"';
    }
  }
  str += required;
  str += ']';


  return str;
}

// 获取参数的json方式
function getJsons(parameters, index, level) {
  var jsons = [];
  if (parameters === undefined || parameters.length === 0) {
    return jsons;
  }
  for (var i = index; i < parameters.length; i++) {
    // 获取计算level的数据
    var parameter = parameters[i];
    if (parameter.level == level) {
      var json = parameter;
      // 添加子集
      json.children = getJsons(parameters, i + 1, level + 1);
      jsons[jsons.length] = json;
    } else if (parameter.level < level) {
      // 到达上一级
      break;
    }
  }
  return jsons;
}

// 方法的头
function getMethodHead(resource) {
  var methodHead = '';
  // url
  methodHead += resource.url + ':' + '\n';
  // uri parameters

  // method
  methodHead += getIndent(1) + resource.method + ':' + '\n';
  // 
  methodHead += getIndent(2) + 'description: ' + resource.description + '\n';

  return methodHead;

}


// header
function getHeader() {
  var header = '';
  header += '#%RAML 0.8' + '\n';
  header += 'title: API' + '\n';
  header += 'baseUri: http://localhost:3000' + '\n';
  header += 'version: v0.1' + '\n';
  header += 'mediaType: application/json' + '\n';

  return header;
}

// 获取缩进
function getIndent(level) {
  var indent = '';
  for (var i = 0; i < level; i++) {
    indent += '  ';
  }
  return indent;
}


var tool = {
  getJsons: getJsons,
  getExample: getExample,
  getSchema: getSchema,
  getRaml: getRaml
}

module.exports = tool;