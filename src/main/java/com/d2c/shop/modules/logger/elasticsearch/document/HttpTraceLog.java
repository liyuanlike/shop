package com.d2c.shop.modules.logger.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author BaiCai
 */
@Data
@Document(indexName = "logger-index", type = "operation")
public class HttpTraceLog implements Serializable {

    @Id
    private Long id;
    private String time;
    private String path;
    private String method;
    private Integer status;
    private String username;
    private Long timeTaken;
    private String parameterMap;
    private String requestBody;
    private String responseBody;

}
