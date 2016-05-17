// raml工具

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

// uri参数
function getUriParameters(resource) {
  var parameters = '';
  var uriParams = resource.uriParams;
  if (uriParams === undefined || uriParams.length === 0) {
    return parameters;
  }
  parameters += getIndent(1) + 'uriParameters:' + '\n';
  for (var i = 0; i < uriParams.length; i++) {
    var uriParameter = uriParams[i];
    parameters += getIndent(2) + uriParameter.displayName + ':' + '\n';
    parameters += getIndent(3) + 'displayName: ' + uriParameter.displayName + '\n';
    parameters += getIndent(3) + 'description: ' + uriParameter.description + '\n';
    parameters += getIndent(3) + 'type: ' + uriParameter.type + '\n';
    // rule
    var rule = uriParameter.rule;
    for (var key in rule) {
      parameters += getIndent(3) + key + ': ' + rule[key] + '\n';
    }
  }
  return parameters;
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
    var queryParameter = queryParams[i];
    parameters += getIndent(3) + queryParameter.displayName + ':' + '\n';
    parameters += getIndent(4) + 'displayName: ' + queryParameter.displayName + '\n';
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
    var queryParameter = queryParams[i];
    body += getIndent(5) + queryParameter.displayName + ':' + '\n';
    body += getIndent(6) + 'displayName: ' + queryParameter.displayName + '\n';
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

  // example
  body += getIndent(4) + 'example: |' + '\n';
  body += getIndent(5) + '{';
  body += getExample(jsons, 6) + '\n';
  body += getIndent(5) + '}' + '\n';

  // schema
  body += getIndent(4) + 'schema: |' + '\n';
  body += getIndent(5) + '{';
  body += getSchema(jsons, 6) + '\n';
  body += getIndent(5) + '}' + '\n';


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

  response += getIndent(5) + resource.responseMimeType + ':' + '\n';

  // example
  response += getIndent(6) + 'example: |' + '\n';
  response += getIndent(7) + '{';
  response += getExample(jsons, 8) + '\n';
  response += getIndent(7) + '}' + '\n';

  // schema
  response += getIndent(6) + 'schema: |' + '\n';
  response += getIndent(7) + '{';
  response += getSchema(jsons, 8) + '\n';
  response += getIndent(7) + '}' + '\n';

  return response;

}

// 获取examp
function getExample(jsons, level) {
  var str = '';
  //
  for (var i = 0; i < jsons.length; i++) {
    var json = jsons[i];
    // // ,

    var data = ''; // key : value
    // key
    data += '\n' + getIndent(level) + '"' + json.displayName + '"';
    // :
    data += ' : ';

    var type = json.type;
    var rule = json.rule || {};
    var value;

    switch (type) {
    case 'object':
      // object
      value = '{';
      value += getExample(json.children, level + 1);
      value += '\n' + getIndent(level) + '}';
      break;
    case 'array':
      // array
      value = '[{';
      value += getExample(json.children, level + 1);
      value += '\n' + getIndent(level) + '}]';
      break;
    case 'string':
      // string
      value = '"' + (rule.example ? rule.example : '') + '"';
      break;
    case 'boolean':
      // boolean
      value = rule.example ? rule.example : 'false';
      break;
    case 'date':
      // date
      value = rule.example ? rule.example : '2016-05-14 12:25:36';
      break;
    case 'integer':
      // integer
      value = rule.example ? rule.example : '0';
      break;
    default:
      // default
      value = '"' + (rule.example ? rule.example : '') + '"';
    }
    data += value;
    //
    str += data;
    // add ,
    if (i < jsons.length - 1) {
      str += ',';
    }

    // end json
  }

  return str;

}

// 获取schema
function getSchema(jsons, level) {

  var url = 'http://jsonschema.net';

  var str = '';

  str += '\n' + getIndent(level) + '"$schema": "http://json-schema.org/draft-04/schema#",';
  str += '\n' + getIndent(level) + '"id": "' + url + '",';
  str += '\n' + getIndent(level) + '"type": "object",';
  str += '\n' + getIndent(level) + '"properties": {';

  // properties
  str += getSchemaProperties(jsons, level + 1, url);

  str += '\n' + getIndent(level) + '},';
  // required
  str += getRequired(jsons, level);

  return str;
}

// 获取schema的properties
function getSchemaProperties(jsons, level, url) {
  var str = '';

  for (var i = 0; i < jsons.length; i++) {
    var json = jsons[i];

    // data
    var data = ''; // key : value
    // key
    data += '\n' + getIndent(level) + '"' + json.displayName + '"';
    // :
    data += ' : {';

    // value
    var value = '';

    var newUrl = url + '/' + json.displayName;
    value += '\n' + getIndent(level + 1) + '"id" : "' + newUrl + '",';
    value += '\n' + getIndent(level + 1) + '"type" : "' + json.type + '",';
    value += '\n' + getIndent(level + 1) + '"description" : "' + (json.description ? json.description : '') + '"'; // no ,

    // type
    switch (json.type) {
    case 'object':
      value += ','; // description's ,
      value += '\n' + getIndent(level + 1) + '"properties" : {';
      value += getSchemaProperties(json.children, level + 2, newUrl);
      value += '\n' + getIndent(level + 1) + '},'; // ./properties
      value += getRequired(json.children, level + 1);
      break;
    case 'array':
      value += ','; // description's ,
      newUrl += '/0';
      value += '\n' + getIndent(level + 1) + '"items" : {'; // item

      value += '\n' + getIndent(level + 2) + '"id" : "' + newUrl + '",';
      value += '\n' + getIndent(level + 2) + '"type" : "object",';

      value += '\n' + getIndent(level + 2) + '"properties" : {'; // properties
      value += getSchemaProperties(json.children, level + 3, newUrl);
      value += '\n' + getIndent(level + 2) + '},'; // ./properties

      value += getRequired(json.children, level + 2);

      value += '\n' + getIndent(level + 1) + '}'; // ./item
      break;
    default:
      // default
      var rule = json.rule || {};
      var ruleStr = '';
      for (var key in rule) {
        ruleStr += '\n' + getIndent(level + 1) + '"' + key + '" : "' + rule[key] + '",';
      }
      if (ruleStr.length > 0) {
        value += ','; // description's ,
        value += ruleStr.substring(0, ruleStr.length - 1);
      }

    }

    data += value;

    data += '\n' + getIndent(level) + '}';

    str += data;

    // ,
    if (i < jsons.length - 1) {
      str += ',';
    }

  }



  return str;
}


// 获取必填
function getRequired(jsons, level) {
  var str = '';
  str += '\n' + getIndent(level) + '"required": [';
  var required = '';
  for (var i = 0; i < jsons.length; i++) {
    var json = jsons[i];
    if (json.required) {
      required += '\n' + getIndent(level + 1) + '"' + json.displayName + '",';
    }
  }
  if (required.length > 0) {
    str += required.substring(0, required.length - 1);
  }

  str += '\n' + getIndent(level) + ']';

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
  methodHead += getUriParameters(resource);

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
  // getJsons: getJsons,
  // getExample: getExample,
  // getSchema: getSchema,
  getRaml: getRaml
}

module.exports = tool;