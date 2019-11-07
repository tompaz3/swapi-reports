package com.tp.sp.swapi.swapiclient;


import static java.lang.String.format;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

public class SwapiUriBuilder {

  private static final String PATH_VARIABLE_START_STRING = "{";
  private static final String PATH_VARIABLE_FOMAT = "{%s}";
  private static final String QUERY_PARAMS_URI_SEPARATOR = "?";
  private static final String QUERY_PARAMS_SEPARATOR = "&";
  private static final String QUERY_PARAMS_KEY_VALUE_SEPARATOR = "=";

  private final String uri;
  private final Map<String, String> pathVariables;
  private final Map<String, String> queryParams;

  private SwapiUriBuilder(String uri) {
    this.uri = uri;
    this.pathVariables = new HashMap<>();
    this.queryParams = new HashMap<>();
  }

  public SwapiUriBuilder pathVariable(String key, String value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    pathVariables.put(key, value);
    return this;
  }

  public SwapiUriBuilder queryParam(String key, String value) {
    queryParams.put(key, value);
    return this;
  }

  public String build() {
    val mappedPath = mapPathVariables(uri);
    return mapQueryParams(mappedPath);
  }

  private String mapPathVariables(String uriString) {
    // return uriString if empty uri or no path variables provided
    // or uri doesn't contain path variables pattern
    if (StringUtils.isEmpty(uriString) || pathVariables.isEmpty()
        || !uriString.contains(PATH_VARIABLE_START_STRING)) {
      return uriString;
    }
    val variables = HashSet.ofAll(pathVariables.entrySet());
    return mapPathVariables(uriString, variables.head(), variables.tail());
  }

  private String mapPathVariables(String uriString, Entry<String, String> pathVariable,
      Set<Entry<String, String>> variables) {
    val key = format(PATH_VARIABLE_FOMAT, pathVariable.getKey());
    val value = URLEncoder.encode(pathVariable.getValue(), StandardCharsets.UTF_8);
    val mappedUri = uriString.replace(key, value);
    if (variables.isEmpty()) {
      return mappedUri;
    } else {
      return mapPathVariables(uriString, variables.head(), variables.tail());
    }
  }

  private String mapQueryParams(String uriString) {
    // return uriString if no query params specified
    if (queryParams.isEmpty()) {
      return uriString;
    }
    val params = HashSet.ofAll(queryParams.entrySet());
    val mappedQueryParams = mapQueryParams(QUERY_PARAMS_URI_SEPARATOR, params.head(),
        params.tail());
    return uriString + mappedQueryParams;
  }

  private String mapQueryParams(String paramsPart, Entry<String, String> queryParam,
      Set<Entry<String, String>> params) {
    val key = queryParam.getKey();
    val value = URLEncoder.encode(queryParam.getValue(), StandardCharsets.UTF_8);
    val keyValue = key + QUERY_PARAMS_KEY_VALUE_SEPARATOR + value;
    val withAddedParam = paramsPart + keyValue;
    if (params.isEmpty()) {
      return withAddedParam;
    } else {
      return mapQueryParams(withAddedParam + QUERY_PARAMS_SEPARATOR, params.head(), params.tail());
    }
  }

  public static SwapiUriBuilder of(String uri) {
    return new SwapiUriBuilder(uri);
  }
}
