import java.io.*;

public class FileRArchiever {

    private RArchieve rarchiver;

    public FileRArchiever()
    {
        rarchiver = new RArchieve();
    }

    public void decompressData(Reader reader, Writer writer)throws IOException
    {
        BufferedReader bfr = new BufferedReader(reader);
        BufferedWriter bwr = new BufferedWriter(writer);
        //
        String bufferRead,bufferWrite = "";
        int lenBr,rightBoundary = 0,maxSizeCodePoint = 4;
        int currentCodePoint,prevCodePoint = -1,rightCodePoint = 0;
        String tail = "";
        //main loop
        while((bufferRead = bfr.readLine()) != null)
        {
            //append prev tail
            bufferRead = tail + bufferRead;
            //work with bufferRead
            lenBr = bufferRead.length() - maxSizeCodePoint;//сразу немного обрезаем(на всякий случай)
            if(lenBr > 0) {
                for (int offset = 0; offset < lenBr; ++offset) {
                    currentCodePoint = bufferRead.codePointAt(offset);
                    //work with codepoint
                    if (currentCodePoint != prevCodePoint) {
                        rightBoundary = offset;
                        rightCodePoint = prevCodePoint;
                    }
                    //update vars
                    prevCodePoint = currentCodePoint;
                    offset += Character.charCount(currentCodePoint);
                }
                bufferWrite = bufferRead.substring(0, rightBoundary);
                //at now archive this
                bufferWrite = rarchiver.getDecompressData(bufferWrite);
                //specify tail as
                tail = bufferRead.substring(rightBoundary,bufferRead.length());
                bwr.write(bufferWrite);
            }
            else
                tail = bufferRead;
        }
        //at now archive last tail
        bufferWrite = rarchiver.getDecompressData(tail);
        bwr.write(bufferWrite);
        bwr.close();
        bfr.close();
    }


}
