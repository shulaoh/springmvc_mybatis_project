package com.web.service;

import com.web.data.pojo.AccessToken;

public interface IAccessTokenService {

	AccessToken getAccessToken();
	
	int updateAccessToken(AccessToken token);
	
	void addAccessToken(AccessToken token);
}
