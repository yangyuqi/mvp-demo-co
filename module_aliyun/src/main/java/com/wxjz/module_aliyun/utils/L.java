/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.wxjz.module_aliyun.utils;

import android.util.Log;

/**
 * "Less-word" analog of Android {@link Log logger}
 * 
 */
public final class L {

	private static final String TAG = "MCPCore";
	private static final String LOG_FORMAT = "%1$s\n%2$s";
	private static volatile boolean DISABLED = false;
	private static L logger = new L();;

	public static L getLogger() {

		return logger;
	}

	private L() {
	}

	public String getClassName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}

		for (StackTraceElement st : sts) {

			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return st.getFileName().replace(".java", "");
		}
		return null;
	}

	public static boolean isDisabled() {
		return DISABLED;
	}

	public static void enableLogging() {
		DISABLED = false;
	}

	public static void disableLogging() {
		DISABLED = true;
	}

	private static String getTag(Object object) {
		Class<?> cls = object.getClass();
		return cls.getSimpleName();
	}

	private static String getTag(Class<?> cls) {
		return cls.getSimpleName();
	}

	public static void v(String message, Object... args) {
		log(Log.VERBOSE, TAG, null, message, args);
	}

	public static void v(Object object, String message, Object... args) {
		v(getTag(object), message, args);
	}

	public static void v(Class<?> cls, String message, Object... args) {
		v(getTag(cls), message, args);
	}

	public static void v(String tag, String message, Object... args) {
		log(Log.VERBOSE, tag, null, message, args);
	}

	public static void d(String message, Object... args) {
		log(Log.DEBUG, TAG, null, message, args);
	}

	public static void d(Class<?> cls, String message, Object... args) {
		d(getTag(cls), message, args);
	}

	public static void d(Object object, String message, Object... args) {
		d(getTag(object), message, args);
	}

	public static void d(String tag, String message, Object... args) {
		log(Log.DEBUG, tag, null, message, args);
	}

	public static void i(String message, Object... args) {
		log(Log.INFO, TAG, null, message, args);
	}

	public static void i(Object object, String message, Object... args) {
		i(getTag(object), message, args);
	}

	public static void i(Class<?> cls, String message, Object... args) {
		i(getTag(cls), message, args);
	}

	public static void i(String tag, String message, Object... args) {
		log(Log.INFO, tag, null, message, args);
	}

	public static void w(String message, Object... args) {
		log(Log.WARN, TAG, null, message, args);
	}

	public static void w(Object object, String message, Object... args) {
		w(getTag(object), null, message, args);
	}

	public static void w(Class<?> cls, String message, Object... args) {
		w(getTag(cls), message, args);
	}

	public static void w(String tag, String message, Object... args) {
		log(Log.WARN, tag, null, message, args);
	}

	public static void e(Throwable ex) {
		log(Log.ERROR, TAG, ex, null);
	}

	public static void e(Object object, Throwable ex) {
		e(getTag(object), ex);
	}

	public static void e(Class<?> cls, Throwable ex) {
		e(getTag(cls), ex);
	}

	public static void e(String tag, Throwable ex) {
		log(Log.ERROR, tag, ex, null);
	}

	public static void e(String message, Object... args) {
		log(Log.ERROR, TAG, null, message, args);
	}

	public static void e(Object object, String message, Object... args) {
		e(getTag(object), message, args);
	}

	public static void e(Class<?> cls, String message, Object... args) {
		e(getTag(cls), message, args);
	}

	public static void e(String tag, String message, Object... args) {
		log(Log.ERROR, tag, null, message, args);
	}

	public static void e(Throwable ex, String message, Object... args) {
		log(Log.ERROR, TAG, ex, message, args);
	}

	public static void e(Object object, Throwable ex, String message,
                         Object... args) {
		e(getTag(object), ex, message, args);
	}

	public static void e(Class<?> cls, Throwable ex, String message,
                         Object... args) {
		e(getTag(cls), ex, message, args);
	}

	public static void e(String tag, Throwable ex, String message,
                         Object... args) {
		log(Log.ERROR, tag, ex, message, args);
	}

	public static void e(String message, Throwable ex, Object... args) {
		log(Log.ERROR, TAG, ex, message, args);
	}

	public static void e(String tag, String message, Throwable ex,
                         Object... args) {
		log(Log.ERROR, tag, ex, message, args);
	}

	public static void e(Object object, String message, Throwable ex,
                         Object... args) {
		e(getTag(object), ex, message, args);
	}

	public static void e(Class<?> cls, String message, Throwable ex,
                         Object... args) {
		e(getTag(cls), ex, message, args);
	}

	private static void log(int priority, String tag, Throwable ex,
                            String message, Object... args) {
		if (DISABLED)
			return;
		if (args.length > 0) {
			message = String.format(message, args);
		}

		String log;
		if (ex == null) {
			log = message;
		} else {
			String logMessage = message == null ? ex.getMessage() : message;
			String logBody = Log.getStackTraceString(ex);
			log = String.format(LOG_FORMAT, logMessage, logBody);
		}
		Log.println(priority, tag, log);
	}
}