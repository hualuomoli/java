package com.github.hualuomoli.raml.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.exception.ParserException;
import com.google.common.collect.Sets;

/**
 * RAML工具
 * @author hualuomoli
 *
 */
public class ParserUtils {

	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data";
	public static final String MIME_TYPE_JSON = "application/json";
	public static final String MIME_TYPE_XML = "application/xml";

	/**
	 * 获取资源下的action
	 * @param resource 资源 
	 * @return action集合
	 */
	public static Set<Action> getActions(Resource resource) {
		Set<Action> actions = Sets.newHashSet();

		Map<ActionType, Action> actionMap = resource.getActions();
		if (actionMap == null || actionMap.size() == 0) {
			return actions;
		}

		// add
		for (Action action : actionMap.values()) {
			actions.add(action);
		}

		return actions;

	}

	/**
	 * 获取资源下的叶子资源(该资源下没有resource)
	 * @param resource 资源 
	 * @return 叶子资源集合
	 */
	public static Set<Resource> getLeafResources(Resource resource) {
		Set<Resource> resources = Sets.newHashSet();

		Map<String, Resource> resourceMap = resource.getResources();
		if (resourceMap == null || resourceMap.size() == 0) {
			return resources;
		}

		for (Resource r : resourceMap.values()) {
			if (isLeaf(r)) {
				resources.add(r);
			}
		}

		return resources;

	}

	/**
	* 获取资源下的叶子资源(该资源下没有resource)
	* @param resource 资源 
	* @return 叶子资源集合
	*/
	public static Set<Resource> getNotLeafResources(Resource resource) {
		Set<Resource> resources = Sets.newHashSet();

		Map<String, Resource> resourceMap = resource.getResources();
		if (resourceMap == null || resourceMap.size() == 0) {
			return resources;
		}

		for (Resource r : resourceMap.values()) {
			if (!isLeaf(r)) {
				resources.add(r);
			}
		}

		return resources;

	}

	/**
	 * 资源是否是叶子资源(该资源下没有resource)
	 * @param resource 资源 
	 * @return 是否是叶子资源
	 */
	public static boolean isLeaf(Resource resource) {
		Map<String, Resource> rs = resource.getResources();
		return rs == null || rs.size() == 0;
	}

	/**
	 * 获取URI资源路经
	 * @param resource 资源
	 * @return URI资源路经
	 */
	public static String getRelativeUri(Resource resource) {
		String relativeUri = resource.getRelativeUri();
		return StringUtils.isEmpty(relativeUri) ? StringUtils.EMPTY : relativeUri;
	}

	/**
	 * 获取URI参数
	 * @param resource 资源
	 * @return URI参数
	 */
	public static Set<UriParameter> getUriParameters(Resource resource) {
		Set<UriParameter> uriParameters = Sets.newHashSet();

		Map<String, UriParameter> uriParameterMap = resource.getUriParameters();
		if (uriParameterMap == null || uriParameterMap.size() == 0) {
			return uriParameters;
		}

		for (UriParameter uriParameter : uriParameterMap.values()) {
			uriParameters.add(uriParameter);
		}

		return uriParameters;
	}

	/**
	 * 获取Query参数
	 * @param action Action
	 * @return 查询参数
	 */
	public static Set<QueryParameter> getQueryParameters(Action action) {
		Set<QueryParameter> queryParameters = Sets.newHashSet();

		Map<String, QueryParameter> queryParameterMap = action.getQueryParameters();

		if (queryParameterMap == null || queryParameterMap.size() == 0) {
			return queryParameters;
		}

		for (QueryParameter queryParameter : queryParameterMap.values()) {
			queryParameters.add(queryParameter);
		}

		return queryParameters;

	}

	/**
	 * 获取表单参数
	 * @param mimeType MimeType
	 * @return 表单参数
	 */
	public static Set<FormParameter> getFormParameters(MimeType mimeType) {
		Set<FormParameter> formParameters = Sets.newHashSet();

		Map<String, List<FormParameter>> formParameterMap = mimeType.getFormParameters();
		if (formParameterMap == null || formParameterMap.size() == 0) {
			return formParameters;
		}

		for (String displayName : formParameterMap.keySet()) {
			List<FormParameter> formParameterList = formParameterMap.get(displayName);
			if (formParameterList == null || formParameterList.size() != 1) {
				throw new ParserException("can not support.");
			}
			// add
			for (FormParameter formParameter : formParameterList) {
				formParameters.add(formParameter);
			}
		}

		return formParameters;

	}

	/**
	 * 获取表单文件参数
	 * @param mimeType MimeType
	 * @return 表单文件参数
	 */
	public static Set<FormParameter> getFormFileParameters(MimeType mimeType) {
		Set<FormParameter> formFileParameters = Sets.newHashSet();

		Set<FormParameter> formParameters = getFormParameters(mimeType);
		for (FormParameter formParameter : formParameters) {
			if (formParameter.getType() == ParamType.FILE) {
				formFileParameters.add(formParameter);
			}
		}

		return formParameters;

	}

	/**
	 * 获取表单MimeType
	 * @param action Action
	 * @return 表单MimeType
	 */
	public static Set<MimeType> getFormMimeTypes(Action action) {
		Set<MimeType> formMimeTypes = Sets.newHashSet();

		Map<String, MimeType> body = action.getBody();
		if (body == null || body.size() == 0) {
			return formMimeTypes;
		}

		for (MimeType formMimeType : body.values()) {
			formMimeTypes.add(formMimeType);
		}

		return formMimeTypes;
	}

	/**
	 * 获取响应MimeType
	 * @param response Response
	 * @return 响应MimeType
	 */
	public static Set<MimeType> getResponseMimeTypes(Response response) {
		Set<MimeType> responseMimeTypes = Sets.newHashSet();

		Map<String, MimeType> body = response.getBody();
		if (body == null || body.size() == 0) {
			return responseMimeTypes;
		}

		for (MimeType responseMimeType : body.values()) {
			responseMimeTypes.add(responseMimeType);
		}

		return responseMimeTypes;
	}

	/**
	 * 获取响应的状态、MimeType的集合
	 * @param action Action
	 * @return 响应的状态、MimeType的集合
	 */
	public static Set<ResponseSM> getResponseSM(Action action) {

		Set<ResponseSM> responseSMs = Sets.newHashSet();

		Map<String, Response> responseMap = action.getResponses();

		for (String status : responseMap.keySet()) {
			Set<MimeType> responseMimeTypes = getResponseMimeTypes(responseMap.get(status));
			if (responseMimeTypes == null || responseMimeTypes.size() == 0) {
				ResponseSM responseSM = new ResponseSM();
				responseSM.responseStatus = status;
				responseSMs.add(responseSM);
			} else {
				for (MimeType responseMimeType : responseMimeTypes) {
					ResponseSM responseSM = new ResponseSM();
					responseSM.responseStatus = status;
					responseSM.responseMimeType = responseMimeType;
					responseSMs.add(responseSM);
				}
			}
		}

		return responseSMs;
	}

	/**
	 * Response(Status MimeType)
	 * @author hualuomoli
	 *
	 */
	public static class ResponseSM {
		public String responseStatus;
		public MimeType responseMimeType;
	}

}
