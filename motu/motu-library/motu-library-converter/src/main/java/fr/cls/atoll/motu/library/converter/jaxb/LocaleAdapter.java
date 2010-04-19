/**
 * 
 */
package fr.cls.atoll.motu.library.converter.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Locale adapter that converts a xs:string into a {@link Locale} and vice-versa.
 * 
 * @author ccamel
 * @version $Revision: 1.1 $ - $Date: 2009-05-19 13:28:44 $ - $Author: dearith $
 */
public class LocaleAdapter extends XmlAdapter<String, Locale> {

    /**
     * Constructeur.
     */
    public LocaleAdapter() {
    }

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleAdapter.class);

    /**
     * Convert a given uri into a string representation.
     * 
     * @param locale the locale
     * 
     * @return the string representation.
     */
    @Override
    public String marshal(Locale locale) {
        if (locale == null) {
            return null;
        }
        return locale.getLanguage() + (StringUtils.isNotEmpty(locale.getCountry()) ? "-" + locale.getCountry() : "");
    }

    /**
     * Convert a given string locale representation into an instance of {@link Locale}.
     * 
     * @param localeString the string to convert into an locale.
     * 
     * @return a {@link Locale} instance.
     */
    @Override
    public Locale unmarshal(String localeString) {
        if (localeString == null) {
            return null;
        }
        String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
        String language = (parts.length > 0 ? parts[0] : "");
        String country = (parts.length > 1 ? parts[1] : "");
        String variant = "";
        if (parts.length >= 2) {
            // There is definitely a variant, and it is everything after the country
            // code sans the separator between the country code and the variant.
            int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
            // Strip off any leading '_' and whitespace, what's left is the variant.
            variant = (localeString.substring(endIndexOfCountryCode));
            if (variant.startsWith("_")) {
                variant = trimLeadingCharacter(variant, '_');
            }
        }
        return (language.length() > 0 ? new Locale(language, country, variant) : null);
    }

    /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * <p>
     * The given delimiters string is supposed to consist of any number of delimiter characters. Each of those
     * characters can be used to separate tokens. A delimiter is always a single character; for
     * multi-character delimiters, consider using <code>delimitedListToStringArray</code>
     * 
     * @param str the String to tokenize
     * @param delimiters the delimiter characters, assembled as String (each of those characters is
     *            individually considered as delimiter)
     * @param trimTokens trim the tokens via String's <code>trim</code>
     * @param ignoreEmptyTokens omit empty tokens from the result array (only applies to tokens that are empty
     *            after trimming; StringTokenizer will not consider subsequent delimiters as token in the
     *            first place).
     * @return an array of the tokens (<code>null</code> if the input String was <code>null</code>)
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim()
     * @see #delimitedListToStringArray
     */
    private static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return (String[]) tokens.toArray(new String[tokens.size()]);
    }

    /**
     * Trim leading whitespace from the given String.
     * 
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    private static String trimLeadingWhitespace(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    /**
     * Trim all occurences of the supplied leading character from the given String.
     * 
     * @param str the String to check
     * @param leadingCharacter the leading character to be trimmed
     * @return the trimmed String
     */
    private static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && buf.charAt(0) == leadingCharacter) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }
}
