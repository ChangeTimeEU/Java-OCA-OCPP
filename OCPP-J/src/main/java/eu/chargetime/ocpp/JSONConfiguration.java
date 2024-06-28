package eu.chargetime.ocpp;

/*
 ubitricity.com - Java-OCA-OCPP

 MIT License

 Copyright (C) 2018 Evgeny Pakhomov <eugene.pakhomov@ubitricity.com>

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

import java.util.HashMap;

public class JSONConfiguration {

  public static final String TCP_NO_DELAY_PARAMETER = "TCP_NO_DELAY";
  public static final String REUSE_ADDR_PARAMETER = "REUSE_ADDR";
  public static final String PROXY_PARAMETER = "PROXY";
  public static final String PING_INTERVAL_PARAMETER = "PING_INTERVAL";
  public static final String USERNAME_PARAMETER = "USERNAME";
  public static final String PASSWORD_PARAMETER = "PASSWORD";
  public static final String CONNECT_NON_BLOCKING_PARAMETER = "CONNECT_NON_BLOCKING";
  public static final String CONNECT_TIMEOUT_IN_MS_PARAMETER = "CONNECT_TIMEOUT_IN_MS";
  public static final String WEBSOCKET_WORKER_COUNT = "WEBSOCKET_WORKER_COUNT";
  public static final String HTTP_HEALTH_CHECK_ENABLED = "HTTP_HEALTH_CHECK_ENABLED";
  public static final String OCPPJ_CP_MIN_PASSWORD_LENGTH = "OCPPJ_CP_MIN_PASSWORD_LENGTH";
  public static final String OCPPJ_CP_MAX_PASSWORD_LENGTH = "OCPPJ_CP_MAX_PASSWORD_LENGTH";
  public static final String OCPP2J_CP_MIN_PASSWORD_LENGTH = "OCPP2J_CP_MIN_PASSWORD_LENGTH";
  public static final String OCPP2J_CP_MAX_PASSWORD_LENGTH = "OCPP2J_CP_MAX_PASSWORD_LENGTH";

  private final HashMap<String, Object> parameters = new HashMap<>();

  private JSONConfiguration() {}

  public static JSONConfiguration get() {
    return new JSONConfiguration();
  }

  public <T> JSONConfiguration setParameter(String name, T value) {
    parameters.put(name, value);
    return this;
  }

  public <T> T getParameter(String name) {
    //noinspection unchecked
    return (T) parameters.get(name);
  }

  public <T> T getParameter(String name, T defaultValue) {
    //noinspection unchecked
    T value = (T) parameters.get(name);
    return value != null ? value : defaultValue;
  }
}
