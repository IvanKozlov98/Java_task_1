public class RArchieve {

    //return null - if "string" is empry or "string" consist wrong data
    public String getDecompressData(String string)
    {
        StringBuilder builder = new StringBuilder();
        int len = string.length();
        if(len == 0)
            return null;
        int currentCodePoint,appendCodePoint = 0,countOfSymblos = 0,currentDegree = 1;
        for(int offset = 0;offset < len;)
        {
            currentCodePoint = string.codePointAt(offset);
            //
            if(Character.isDigit(currentCodePoint)) {
                countOfSymblos = countOfSymblos + currentDegree * (currentCodePoint - '0');
                currentDegree*=10;
            }
            else {
                //case error format data
                if(countOfSymblos == 0 && offset != 0)
                    return null;
                //add countOfSymbols by word
                for(int i = 0;i < countOfSymblos;++i)
                    builder.appendCodePoint(appendCodePoint);
                //resetting variables
                currentDegree = 1;
                countOfSymblos = 0;
                appendCodePoint = currentCodePoint;
            }
            //to offset on string
            offset+=Character.charCount(currentCodePoint);
        }
        //At the end - AGAIN
        //case error format data
        if(countOfSymblos == 0)
            return null;
        else
            //add countOfSymbols by word
            for(int i = 0;i < countOfSymblos;++i)
                builder.appendCodePoint(appendCodePoint);
        //
        return builder.toString();
    }
}
