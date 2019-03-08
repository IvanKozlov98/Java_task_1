public class Archiver {

    public String getCompressData(String string)
    {
        StringBuilder builder = new StringBuilder();
        int len = string.length();
        if(len == 0)
            return "";
        //
        int countOfSymblos = 1;
        int currentCodePoint,prevCodePoint = string.codePointAt(0);
        int offset = Character.charCount(prevCodePoint);
        for(;offset < len;)
        {
            currentCodePoint = string.codePointAt(offset);
            //work with this codepoint
            if(currentCodePoint != prevCodePoint)
            {
                builder.appendCodePoint(prevCodePoint);
                builder.append(String.valueOf(countOfSymblos));
                countOfSymblos = 1;
            }
            else
                countOfSymblos++;

            //to offset on string
            offset+=Character.charCount(currentCodePoint);
            prevCodePoint = currentCodePoint;
        }
        //At the end - AGAIN
        builder.appendCodePoint(prevCodePoint);
        builder.append(String.valueOf(countOfSymblos));
        return builder.toString();
    }
}
