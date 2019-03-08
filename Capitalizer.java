public class Capitalizer {

    public String changeOnCapitalLetters(String sentence)
    {
        StringBuilder builder = new StringBuilder();
        //to get length of sequence of symbols representing this "sentence"
        int len = sentence.length();
        if(len == 0)
            return "";
        //in the current position we locating in word
        boolean inWord = false;
        int codePoint;
        for(int offset = 0;offset < len;)
        {
            codePoint = sentence.codePointAt(offset);
            if(Character.isSpaceChar(codePoint))
            {
                builder.appendCodePoint(codePoint);
                inWord = false;
                offset+=Character.charCount(codePoint);
                continue;
            }
            if(inWord)
                builder.appendCodePoint(Character.toLowerCase(codePoint));
            else
            {
                builder.appendCodePoint(Character.toTitleCase(codePoint));
                inWord = true;
            }
            offset+=Character.charCount(codePoint);
        }
       return builder.toString();
    }

}
