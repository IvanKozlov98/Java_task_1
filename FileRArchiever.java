import java.io.*;

public class FileRArchiever {

    private final static int BUFFER_SIZE = 512;
    private RArchieve rarchiver;

    public FileRArchiever()
    {
        rarchiver = new RArchieve();
    }

    public void decompressData(FileInputStream istream, FileOutputStream ostream)throws IOException
    {
        //init byte buffer
        byte[] buffer = new byte[BUFFER_SIZE];
        //
        BufferedInputStream bfs = new BufferedInputStream(istream);
        BufferedOutputStream bws = new BufferedOutputStream(ostream);
        //
        String bufferRead,bufferWrite = "";
        int lenBr,rightBoundary = 0,maxSizeCodePoint = 4;
        int currentCodePoint,prevCodePoint = -1,rightCodePoint = 0;
        String tail = "";
        //
        int countRead = -1;
        //main loop
        while((countRead = bfs.read(buffer)) != -1)
        {
            bufferRead = new String(buffer,0,countRead);
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
                bws.write(bufferWrite.getBytes());
            }
            else
                tail = bufferRead;
        }
        //at now archive last tail
        bufferWrite = rarchiver.getDecompressData(tail);
        bws.write(bufferWrite.getBytes());
        bws.close();
        bfs.close();
    }


}
