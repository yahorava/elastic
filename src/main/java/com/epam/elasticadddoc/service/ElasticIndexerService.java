package com.epam.elasticadddoc.service;

import com.epam.elasticadddoc.counter.AddCounter;

public interface ElasticIndexerService {
    public AddCounter indexFile(String path, String index, String type);
}
