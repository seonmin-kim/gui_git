package kr.co.mystockhero.geotogong.common;

/**
 * Created by sesang on 16. 5. 25..
 */
public class KoreanString {

    private final String _value;

    public KoreanString(String s) {
        _value = s;
    }

    public boolean equals(String other) {
        if (other == null)
            return false;
        if (_value.length() != other.length())
            return false;

        for (int i = 0; i < _value.length(); i++) {
            final char c;
            if (KoreanChar.isHangulChoseong(other.charAt(i))
                    && KoreanChar.isHangulSyllable(_value.charAt(i)))
                c = new KoreanChar(_value.charAt(i), false).getChoseong();
            else if (KoreanChar.isHangulCompatibilityChoseong(other.charAt(i))
                    && KoreanChar.isHangulSyllable(_value.charAt(i)))
                c = new KoreanChar(_value.charAt(i), true).getChoseong();
            else
                c = _value.charAt(i);

            if (c != other.charAt(i))
                return false;
        }
        return true;
    }

    public boolean startsWith(String prefix) {
        if (prefix == null)
            return false;
        if (_value.length() < prefix.length())
            return false;

        for (int i = 0; i < prefix.length(); i++) {
            final char c;
            if (KoreanChar.isHangulChoseong(prefix.charAt(i))
                    && KoreanChar.isHangulSyllable(_value.charAt(i)))
                c = new KoreanChar(_value.charAt(i), false).getChoseong();
            else if (KoreanChar.isHangulCompatibilityChoseong(prefix.charAt(i))
                    && KoreanChar.isHangulSyllable(_value.charAt(i)))
                c = new KoreanChar(_value.charAt(i), true).getChoseong();
            else
                c = _value.charAt(i);

            if (c != prefix.charAt(i))
                return false;
        }
        return true;
    }
}
