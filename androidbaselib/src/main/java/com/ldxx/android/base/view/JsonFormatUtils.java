package com.ldxx.android.base.view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map.Entry;
import java.util.Set;

class JsonFormatUtils {
	private Gson gson;

	public JsonFormatUtils() {
		this.gson = new Gson();
	}

	int i = 0;

	public String format(String json) {
        if(!isJsonStr(json)){
            return  json;
        }
		StringBuilder sb = new StringBuilder();

		i += 1;
		String tab = getTabStr(i);
		sb.append("{\n");
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		Set<Entry<String, JsonElement>> set = obj.entrySet();
		for (Entry<String, JsonElement> entry : set) {
			if (entry.getValue().isJsonArray()) {
				System.out.println(entry.getKey() + ":array");
			} else if (entry.getValue().isJsonObject()) {
				// System.out.println(entry.getKey()+":obj");

				sb.append(tab).append(entry.getKey()).append(":").append(format(entry.getValue().toString()));
			} else {
				sb.append(tab).append(entry.getKey()).append(":").append(entry.getValue()).append(",\n");
			}
		}
		sb.append(getTabStr(i - 1)).append("}");
		i -= 1;
		return sb.append("\n").toString();
	}

	private String getTabStr(int i) {
		String str = " ";
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < i * 4; j++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 简单判断字符串是不是json格式
	 * @param json 要判断的字符串
	 * @return 返回是否是字符串
	 */
	private boolean isJsonStr(String json) {
		return json.startsWith("{") && json.endsWith("}")
				&& (numberOfStr(json, "[{]") == numberOfStr(json, "[}]"));
	}

	private static int numberOfStr(String str, String con) {
		str = " " + str + " ";
		if (str.endsWith(con)) {
			return str.split(con).length;
		} else {
			return str.split(con).length - 1;
		}
	}
}
