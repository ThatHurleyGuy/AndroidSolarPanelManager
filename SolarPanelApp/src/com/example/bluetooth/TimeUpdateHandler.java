package com.example.bluetooth;

import net.minidev.json.JSONObject;

import com.example.solarpanelmanager.api.parsers.MessageKeys;
import com.example.solarpanelmanager.api.parsers.MessageTypes;
import com.example.solarpanelmanager.api.responses.BaseResponse;

public class TimeUpdateHandler extends BaseResponseHandler {

	private long timestamp;

	public TimeUpdateHandler(Callback<BaseResponse> callback, String device, long timestamp) {
		super(callback, device);
		this.timestamp = timestamp;
	}

	@Override
	protected String getRequest() {
		JSONObject json = new JSONObject();
		json.put(MessageKeys.MESSAGE_TYPE, MessageTypes.TIME_UPDATE);
		json.put(MessageKeys.TIME_TIME, this.timestamp);
		return json.toJSONString();
	}
}
