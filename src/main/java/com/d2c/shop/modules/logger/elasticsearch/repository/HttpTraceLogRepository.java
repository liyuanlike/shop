package com.d2c.shop.modules.logger.elasticsearch.repository;

import com.d2c.shop.modules.logger.elasticsearch.document.HttpTraceLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author BaiCai
 */
public interface HttpTraceLogRepository extends ElasticsearchRepository<HttpTraceLog, Long> {

}
