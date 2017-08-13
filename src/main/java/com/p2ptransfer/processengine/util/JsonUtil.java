package com.p2ptransfer.processengine.util;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.p2ptransfer.model.RestResponseObject;
import com.p2ptransfer.model.User;

import spark.Request;

public class JsonUtil {
	public static <T> T fromJson(String json, Class<T> classOfT){
		Gson gson = new Gson();
		return (T) gson.fromJson(json, classOfT);
	}
	
	public static <T> String toJson(T instance){
		Gson gson = new Gson();
		return gson.toJson(instance);
	}
	
	public static <T> String createResponse(User data, String error, String code){
		
		RestResponseObject restRespObj =  new RestResponseObject(data, error,code);
		return toJson(restRespObj);
	}
	
public static <T> String createResponse(boolean response, String error, String code){
		
		RestResponseObject restRespObj =  new RestResponseObject(null, error,code);
		return toJson(restRespObj);
	}

	public static Object createErrorResponse( String error, String code) {
		RestResponseObject restRespObj =  new RestResponseObject(null, error,code);
		return toJson(restRespObj);
	}
	public static Map<String, String> parseBody(Request request) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();

        return gson.fromJson(request.body(), type);
    }
}
