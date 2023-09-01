package com.vvs.murator.feign.youtube;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class YoutubeSearchListRes {

    private List<Item> items = new ArrayList<>();

    @Getter
    public static class Item {

        private Id id;
        private Snippet snippet;

        @Getter
        @NoArgsConstructor
        public static class Id {
            private String videoId;

            public Id(String videoId) {
                this.videoId = videoId;
            }
        }

        @Getter
        @NoArgsConstructor
        public static class Snippet {
            private String title;
            private Thumbnails thumbnails;

            public static class Thumbnails {
                private List<Default> defaults = new ArrayList<>();

                public static class Default {
                    private String url;
                }
            }
        }
    }
}
