package com.bitinc.rest.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public abstract class BaseService implements UserDetails {
  protected final static String REMOTE_URL = "https://pokeapi.co/api/v2/";

    protected final RestTemplate restTemplate = new RestTemplate();

}
