package com.example.restservice.service.model;

/**
 * java 12부터 이용가능한 record
 * - JSON 맞추기 위해 생성 
 */
public record Greeting(long id, String content) { }