package com.hans.hennge;

public class Main {
	private static String url = "https://hdechallenge-solve.appspot.com/challenge/003/endpoint";
	private static String json = "{\"github_url\": \"https://gist.github.com/XiaoYunhan/a9b2f219b082c8ad8812c43a656e83f4\",\"contact_email\": \"e0253700@u.nus.edu\"}";
	
	public static void main(String[] args) {
		String result = "";
		result = HttpClientUtil.doPostForJson(url, json);
	}
}
