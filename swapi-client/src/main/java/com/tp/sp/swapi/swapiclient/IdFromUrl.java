package com.tp.sp.swapi.swapiclient;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.regex.Pattern;
import lombok.val;

public final class IdFromUrl {

  private static final String TRAILING_SLASH = "/";
  private static final String ID_GROUP_NAME = "id";
  private static final Pattern ID_PATTERN = Pattern.compile("(?<id>\\d+/?$)");

  private IdFromUrl() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated.");
  }

  public static int toId(String url) {
    return Integer.parseInt(findId(url));
  }

  private static String findId(String url) {
    val matcher = ID_PATTERN.matcher(url);
    matcher.find();
    return matcher.group(ID_GROUP_NAME).replace(TRAILING_SLASH, EMPTY);
  }
}
