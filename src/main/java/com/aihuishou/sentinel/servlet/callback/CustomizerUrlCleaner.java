package com.aihuishou.sentinel.servlet.callback;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@link UrlCleaner} Implement
 *
 * @author jiashuai.xie
 * @since 2018/12/14 11:01
 */
public class CustomizerUrlCleaner implements UrlCleaner {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    public static final List<String> MAPPING_URLS = new ArrayList<>();

    @Override
    public String clean(String originUrl) {

        Optional<String> mappingOptional = MAPPING_URLS.stream().filter(mappingUrl -> pathMatcher.match(mappingUrl, originUrl)).findFirst();

        if (mappingOptional.isPresent()) {
            return mappingOptional.get();
        }

        return originUrl;
    }

}
