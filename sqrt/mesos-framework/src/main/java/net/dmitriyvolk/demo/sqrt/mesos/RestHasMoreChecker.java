package net.dmitriyvolk.demo.sqrt.mesos;

import org.springframework.web.client.RestTemplate;

/**
 * Created by xbo on 2/21/15.
 */
public class RestHasMoreChecker implements HasMoreChecker {
    RestTemplate restTemplate = new RestTemplate();
    private final String sqrtServerUrl;

    public RestHasMoreChecker(String sqrtServerUrl) {
        this.sqrtServerUrl = sqrtServerUrl;
    }

    @Override
    public boolean hasMore() {
        return restTemplate.getForObject(sqrtServerUrl + "/workitems/havemore", HasMore.class).isHaveMore();
    }

    public static class HasMore {
        boolean haveMore;

        public boolean isHaveMore() {
            return haveMore;
        }
    }

}
