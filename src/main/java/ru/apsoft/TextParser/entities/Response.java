package ru.apsoft.TextParser.entities;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Response {

    public List<String> content;
    public List<Section> structure;

}