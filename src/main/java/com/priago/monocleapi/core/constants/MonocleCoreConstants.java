package com.priago.monocleapi.core.constants;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

/**
 * A class containing constants used throughout the Monocle Api
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class MonocleCoreConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonocleCoreConstants.class);

    private static final String NO_INSTANTIATION = "Utility classes should not be instantiated";

    private MonocleCoreConstants() {
        throw new UnsupportedOperationException(NO_INSTANTIATION);
    }


    //  FIELDS

    public static class Cache {
        private Cache() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final String ARTICLE_KEY = "articles";
        public static final String AUTHOR_KEY = "authors";
        public static final String SOURCE_KEY = "sources";
    }

    public static class Client {

        private Client() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final String API_KEY = "apiKey";
        public static final String ARTICLES = "articles";
        public static final String AUTHOR = "author";
        public static final String CATEGORY = "category";
        public static final String CODE = "code";
        public static final String CONTENT = "content";
        public static final String COUNTRY = "country";
        public static final String DESCRIPTION = "description";
        public static final String DOMAINS = "domains";
        public static final String EXCLUDE_DOMAINS = "excludeDomains";
        public static final String FROM = "from";
        public static final String ID = "id";
        public static final String LANGUAGE = "language";
        public static final String MESSAGE = "message";
        public static final String NAME = "name";
        public static final String PAGE = "page";
        public static final String PAGE_SIZE = "pageSize";
        public static final String PUBLISHED_AT = "publishedAt";
        public static final String Q = "q";
        public static final String Q_IN_TITLE = "qInTitle";
        public static final String SORT_BY = "sortBy";
        public static final String SOURCE = "source";
        public static final String SOURCES = "sources";
        public static final String STATUS = "status";
        public static final String TITLE = "title";
        public static final String TO = "to";
        public static final String TOTAL_RESULTS = "totalResults";
        public static final String URL = "url";
        public static final String URL_TO_IMAGE = "urlToImage";

        /**
         * Returns the API key for NewsAPI
         *
         * @return a decrypted view of the api key
         */
        public static String getNewsAPIApiKey() {
            InputStream in = Client.class.getClassLoader().getResourceAsStream("key.txt");
            if (in != null) {
                try {
                    return IOUtils.toString(in, StandardCharsets.UTF_8);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    return StringUtils.EMPTY;
                }
            }

            return StringUtils.EMPTY;
        }
    }

    public static class Core {
        private Core() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final String TIME_ZONE = "GMT";
        public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("GMT");
        public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        public static final Locale MONOCLE_LOCALE = Locale.CANADA;
    }

    public static class Database {
        private Database() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final String ENTITY = "entity";
        public static final String CARDINALITY = "cardinality";
    }

    public static class Lexicon {
        private Lexicon() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final List<String> THIRTY_STOP_WORDS = Lists.newArrayList(
                "a", "and", "as", "at", "be", "but", "by", "do", "for", "from", "have", "his", "in", "it", "not", "of", "on", "or", "say", "she", "that", "that", "the", "they", "this", "to", "to", "we", "with", "you"
        );
        public static final List<String> ONE_FIFTY_STOP_WORDS = Lists.newArrayList(
                "I", "a", "about", "after", "all", "also", "and", "another", "any", "as", "as", "as", "ask", "at", "back", "be", "because", "become", "between", "but",
                "by", "call", "can", "child", "come", "could", "day", "do", "down", "even", "family", "feel", "find", "first", "for", "from", "get", "give", "go", "good", "have", "he", "her", "her", "here", "high",
                "him", "his", "how", "if", "in", "in", "into", "it", "its", "just", "know", "last", "leave", "life", "like", "look", "make", "man", "many", "may", "me", "more", "more", "most", "much", "my", "n't",
                "need", "never", "new", "no", "not", "now", "of", "on", "one", "one", "only", "or", "other", "our", "out", "out", "over", "own", "people", "really", "say", "school", "see", "she", "should", "so",
                "some", "something", "state", "still", "take", "tell", "than", "that", "that", "the", "their", "them", "then", "there", "there", "these", "they", "thing", "think", "this", "those", "three",
                "through", "time", "to", "to", "too", "try", "two", "up", "us", "use", "very", "want", "way", "we", "well", "what", "when", "when", "which", "who", "will", "with", "woman", "work", "world", "would",
                "year", "you", "your"
        );
    }

    public static class Security {
        private Security() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final String ACTIVE = "active";
        public static final String CONFIRM_PASSWORD = "confirmPassword";
        public static final String EMAIL = "email";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String PASSWORD = "password";
        public static final String USERNAME = "username";
    }

    public static class UI {
        private UI() {
            throw new UnsupportedOperationException(NO_INSTANTIATION);
        }

        public static final String CARDINALITY = "cardinality";
        public static final String ENTITY = "entity";
        public static final String USER = "user";
    }
}
