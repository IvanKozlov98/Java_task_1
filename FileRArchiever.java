import java.io.*;

public class FileRArchiever {
    private static final int BASE_NUMBER_SYSTEM = 255;


    public void decompressData(FileInputStream istream, FileOutputStream ostream)throws IOException
    {
        try(BufferedInputStream bfs = new BufferedInputStream(istream);
            BufferedOutputStream bws = new BufferedOutputStream(ostream)) {
            int symbol,number = 0;
            long degree = 1;
            long countSymbols = 0;
            while((symbol = bfs.read()) != -1)
            {
                while((number = bfs.read()) != -1 && number != BASE_NUMBER_SYSTEM) {
                    countSymbols = number * degree + countSymbols;
                    degree*=BASE_NUMBER_SYSTEM;
                }
                //write temp result
                for(int i = 0;i < countSymbols;++i)
                    bws.write(symbol);
                //update
                countSymbols = 0;
                degree = 1;
            }
        }
    }


}
