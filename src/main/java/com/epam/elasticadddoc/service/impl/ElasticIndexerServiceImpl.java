package com.epam.elasticadddoc.service.impl;

import com.epam.elasticadddoc.counter.AddCounter;
import com.epam.elasticadddoc.exception.ElasticException;
import com.epam.elasticadddoc.reader.CsvReader;
import com.epam.elasticadddoc.service.ElasticIndexerService;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static com.epam.elasticadddoc.constant.Constant.*;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class ElasticIndexerServiceImpl implements ElasticIndexerService {
    private static final String SEPARATOR = " ";
    private static final String CLUSTER_NAME_PROPERTY = "cluster.name";
    private static final String CLUSTER_NAME = "elasticsearch";
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9300;

    private CsvReader csvReader;

    @Autowired
    public ElasticIndexerServiceImpl(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    public AddCounter indexFile(String path, String index, String type) {
        Settings settings = Settings.builder()
                .put(CLUSTER_NAME_PROPERTY, CLUSTER_NAME).build();
        AddCounter counter = new AddCounter();
        try(TransportClient client = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT))) {
            List<CSVRecord> records = csvReader.readCsvFile(path);
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            for(CSVRecord record : records) {
                bulkRequest.add(prepareBuilder(client, record, index, type));
            }
            BulkResponse bulkResponse = bulkRequest.get();
            for(BulkItemResponse response : bulkResponse) {
                countAddResults(counter, response.getVersion());
            }
        } catch (IOException e) {
            throw new ElasticException(e);
        }
        return counter;
    }

    private IndexRequestBuilder prepareBuilder(TransportClient client, CSVRecord record,
            String index, String type) throws IOException {
        IndexRequestBuilder requestBuilder = client.prepareIndex(index, type,
                record.get(FROM_DISPLAY) + SEPARATOR + record.get(DATE_SENT)).setSource(
                jsonBuilder().startObject().field(SUBJECT, record.get(SUBJECT))
                        .field(DATE_SENT, record.get(DATE_SENT)).field(BODY, record.get(BODY))
                        .field(FROM_ADDRESS_TYPE, record.get(FROM_ADDRESS_TYPE))
                        .field(FROM_ADDRESS, record.get(FROM_ADDRESS))
                        .field(FROM_DISPLAY, record.get(FROM_DISPLAY))
                        .field(TO_ADDRESS_TYPE, record.get(TO_ADDRESS_TYPE))
                        .field(TO_ADDRESS, record.get(TO_ADDRESS))
                        .field(TO_DISPLAY, record.get(TO_DISPLAY))
                        .field(CC_ADDRESS_TYPE, record.get(CC_ADDRESS_TYPE))
                        .field(CC_ADDRESS, record.get(CC_ADDRESS))
                        .field(CC_DISPLAY, record.get(CC_DISPLAY))
                        .field(BCC_ADDRESS_TYPE, record.get(BCC_ADDRESS_TYPE))
                        .field(BCC_ADDRESS, record.get(BCC_ADDRESS))
                        .field(BCC_DISPLAY, record.get(BCC_DISPLAY))
                        .field(IMPORTANCE, record.get(IMPORTANCE)).
                        endObject());
        return requestBuilder;
    }

   /* private long prepareIndex(TransportClient client, CSVRecord record,
            String index, String type) throws IOException {
        IndexResponse response = client.prepareIndex(index, type,
                record.get(FROM_DISPLAY) + SEPARATOR + record.get(DATE_SENT)).setSource(
                jsonBuilder().startObject().field(SUBJECT, record.get(SUBJECT))
                        .field(DATE_SENT, record.get(DATE_SENT)).field(BODY, record.get(BODY))
                        .field(FROM_ADDRESS_TYPE, record.get(FROM_ADDRESS_TYPE))
                        .field(FROM_ADDRESS, record.get(FROM_ADDRESS))
                        .field(FROM_DISPLAY, record.get(FROM_DISPLAY))
                        .field(TO_ADDRESS_TYPE, record.get(TO_ADDRESS_TYPE))
                        .field(TO_ADDRESS, record.get(TO_ADDRESS))
                        .field(TO_DISPLAY, record.get(TO_DISPLAY))
                        .field(CC_ADDRESS_TYPE, record.get(CC_ADDRESS_TYPE))
                        .field(CC_ADDRESS, record.get(CC_ADDRESS))
                        .field(CC_DISPLAY, record.get(CC_DISPLAY))
                        .field(BCC_ADDRESS_TYPE, record.get(BCC_ADDRESS_TYPE))
                        .field(BCC_ADDRESS, record.get(BCC_ADDRESS))
                        .field(BCC_DISPLAY, record.get(BCC_DISPLAY))
                        .field(IMPORTANCE, record.get(IMPORTANCE)).
                        endObject()).get();
        return response.getVersion();
    }*/

   /*
    public AddCounter indexFile(String path, String index, String type) {
        Settings settings = Settings.builder()
                .put(CLUSTER_NAME_PROPERTY, CLUSTER_NAME).build();
        AddCounter counter = new AddCounter();
        try(TransportClient client = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT))) {
            List<CSVRecord> records = csvReader.readCsvFile(path);
            for(CSVRecord record : records) {
                long result = prepareIndex(client, record, index, type);
                countAddResults(counter, result);
            }
        } catch (IOException e) {
            throw new ElasticException(e);
        }
        return counter;
    }
    */

    private AddCounter countAddResults(AddCounter counter, long result) {
        if (result == 1) {
            counter.incrementCreated();
        } else {
            counter.incrementUpdated();
        }
        return counter;
    }
}
