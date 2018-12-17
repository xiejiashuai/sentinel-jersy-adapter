package com.aihuishou.sentinel.servlet.callback;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@link UrlBlockHandler} Implement
 *
 * @author jiashuai.xie
 * @since 2018/12/14 11:00
 */
@Slf4j
public class CustomizerUrlBlockHandler implements UrlBlockHandler {

    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws IOException {
        String message = StringUtils.hasText(ex.getRuleLimitApp()) ? ex.getRuleLimitApp() : "application" + "is limited as the over the threshold";
        log.info("urlBlockHandler is triggered , message:{}", message);
        throw new RuntimeException(message);

    }

}
