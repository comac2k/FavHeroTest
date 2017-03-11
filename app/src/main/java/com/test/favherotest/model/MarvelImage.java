package com.test.favherotest.model;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelImage {
    private String path;
    private String extension;

    public String getUrl(Variants variant) {
        return path + variant.getmUrlPath() + extension;
    }

    public enum Variants {
        PORTRAIT_INCREDIBLE("/portrait_incredible."),
        STANDARD_FANTASTIC("/standard_fantastic."),
        LANDSCAPE_XLARGE("/landscape_xlarge.");

        private final String mUrlPath;

        Variants(String name) {
            mUrlPath = name;
        }

        public String getmUrlPath() {
            return mUrlPath;
        }
    }
}
